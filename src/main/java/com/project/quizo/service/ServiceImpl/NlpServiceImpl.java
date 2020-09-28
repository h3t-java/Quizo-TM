package com.project.quizo.service.ServiceImpl;

import com.project.quizo.config.VectorData;
import com.project.quizo.domain.testManagement.*;
import com.project.quizo.domain.testManagement.Test;
import com.project.quizo.domain.userManagement.User;
import com.project.quizo.exception.InvalidNameEntityRecognitionException;
import com.project.quizo.repository.MultiQuestionRepository;
import com.project.quizo.repository.SingleQuestionRepository;
import com.project.quizo.repository.TestRepository;
import com.project.quizo.repository.TestTakeRepository;
import com.project.quizo.resource.AnswerDTO;
import com.project.quizo.resource.QuestionsDTO;
import com.project.quizo.resource.ResourceNotFoundException;
import com.project.quizo.service.NlpService;
import com.project.quizo.service.UserService;
import com.project.quizo.util.KeyWordType;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NlpServiceImpl implements NlpService {

    @Value("classpath:files/stop_words.txt")
    private Resource stopWordsFile;

    @Value("classpath:files/deps.words.zip")
    private Resource trainedModel;

    @Value("classpath:models/en-ner-person.bin")
    private Resource nerPersonNameModel;

    @Value("classpath:models/en-ner-location.bin")
    private Resource nerLocationModel;

    @Value("classpath:models/en-ner-date.bin")
    private Resource nerDateModel;

    @Value("classpath:models/en-ner-organization.bin")
    private Resource nerOrganizationModel;

    @Value("classpath:models/en-ner-time.bin")
    private Resource nerTimeModel;

    @Value("classpath:models/en-pos-maxent.bin")
    private Resource posModel;

    @Autowired
    private VectorData trainedData;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SingleQuestionRepository singleQuestionRepository;

    @Autowired
    private MultiQuestionRepository multiQuestionRepository;

    @Autowired
    private TestTakeRepository testTakeRepository;

    @Autowired
    private UserService userService;

    private static final Integer MAX_NUM_OF_KEY_WORDS = 15;

    private static final String MASK = "_________";

    private List<String> generateSentences(String paragraph) throws IOException {
        InputStream is = getClass().getResourceAsStream("/models/en-sent.bin");
        SentenceModel model = new SentenceModel(is);

        SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(model);

        String[] sentences = sentenceDetectorME.sentDetect(paragraph);
        return Arrays.stream(sentences).collect(Collectors.toList());
    }

    private List<String> generateTokens(String sentence) throws IOException {
        InputStream inputStream = getClass()
                .getResourceAsStream("/models/en-token.bin");
        TokenizerModel model = new TokenizerModel(inputStream);

        TokenizerME tokenizer = new TokenizerME(model);

        String[] tokens = tokenizer.tokenize(sentence);
        return Arrays.stream(tokens).collect(Collectors.toList());
    }

    private List<String> getStopWords() throws IOException {
        Scanner scanner = new Scanner(stopWordsFile.getFile());
        List<String> stopWords = new ArrayList<>();

        while (scanner.hasNextLine()) {
            stopWords.add(scanner.nextLine());
        }

        return stopWords;
    }

    private List<Word> prepareWords(List<String> tokens, String sentence) {
        List<Word> words = new ArrayList<>();

        for (String token : tokens) {
            words.add(new Word(token, sentence));
        }

        return words;
    }

    private List<Word> removeStopWords(List<Word> words) throws IOException {
        List<String> stopWords = getStopWords();

        words.removeIf(word -> stopWords.contains(word.getWord()));

        return words;
    }

    public List<Word> getWords(String paragraph) throws IOException {
        List<String> sentences = generateSentences(paragraph);
        List<Word> words = new ArrayList<>();

        for (String sentence : sentences) {
            words.addAll(prepareWords(generateTokens(sentence), sentence));
        }

        return removeStopWords(words);
    }

    private List<String> generateNER(KeyWordType object, List<String> words) throws IOException {
        InputStream streamFinder;
        switch (object) {
            case NAME:
                streamFinder = nerPersonNameModel.getInputStream();
                break;
            case LOCATION:
                streamFinder = nerLocationModel.getInputStream();
                break;
            case ORGANIZATION:
                streamFinder = nerOrganizationModel.getInputStream();
                break;
            case DATE:
                streamFinder = nerDateModel.getInputStream();
                break;
            case TIME:
                streamFinder = nerTimeModel.getInputStream();
                break;
            default:
                throw new InvalidNameEntityRecognitionException(object.toString());
        }

        TokenNameFinderModel model = new TokenNameFinderModel(streamFinder);
        NameFinderME entityFinder = new NameFinderME(model);
        Span[] entitySpans = entityFinder.find(words.toArray(new String[0]));

        Set<String> entities = new HashSet<>();
        for (Span span : entitySpans) {
            entities.add(words.get(span.getStart()));
        }

        return new ArrayList<>(entities);
    }

    private List<String> getMostFrequentNouns(List<String> tokens) throws IOException {
        Map<String, Integer> words = new HashMap<>();

        for (String word : tokens) {
            words.put(word, Collections.frequency(tokens, word));
        }
        List<Map.Entry<String, Integer>> list = new LinkedList<>(words.entrySet());

        Collections.sort(list, (o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        List<String> frequentWords = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String word = list.get(i).getKey();
            if (Character.isLowerCase(word.charAt(0))) {
                frequentWords.add(list.get(i).getKey());
            }
        }

        InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
        POSModel posModel = new POSModel(inputStreamPOSTagger);
        POSTaggerME posTagger = new POSTaggerME(posModel);
        String tags[] = posTagger.tag(frequentWords.toArray(new String[0]));

        List<String> mostFrequentNouns = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
            if (tags[i].equals("NNP") || tags[i].equals("NN") || tags[i].equals("NNS")) {
                mostFrequentNouns.add(frequentWords.get(i));
            }
        }

        return mostFrequentNouns.subList(0, 10);
    }

    private Map<KeyWordType, List<String>> getKeyInformation(List<Word> words) throws IOException {
        List<String> tokens = words.stream().map(Word::getWord).collect(Collectors.toList());

        Map<KeyWordType, List<String>> keyInformation = new HashMap<>();
        keyInformation.put(KeyWordType.NAME, generateNER(KeyWordType.NAME, tokens));
        keyInformation.put(KeyWordType.LOCATION, generateNER(KeyWordType.LOCATION, tokens));
        keyInformation.put(KeyWordType.ORGANIZATION, generateNER(KeyWordType.ORGANIZATION, tokens));
        keyInformation.put(KeyWordType.DATE, generateNER(KeyWordType.DATE, tokens));
        keyInformation.put(KeyWordType.TIME, generateNER(KeyWordType.TIME, tokens));
        keyInformation.put(KeyWordType.MOST_FREQUENT, getMostFrequentNouns(tokens));

        return keyInformation;
    }

    private List<Word> filterWords(List<Word> words, Set<String> keyTokens) {
        return words.stream().filter(element -> keyTokens.contains(element.getWord())).collect(Collectors.toList());
    }

    public List<Word> getKeyWords(List<Word> words) throws IOException {
        Map<KeyWordType, List<String>> keyInformation = getKeyInformation(words);
        Set<String> keyTokens = new HashSet<>(keyInformation.get(KeyWordType.NAME));
        keyTokens.addAll(keyInformation.get(KeyWordType.LOCATION));
        keyTokens.addAll(keyInformation.get(KeyWordType.TIME));
        keyTokens.addAll(keyInformation.get(KeyWordType.ORGANIZATION));
        keyTokens.addAll(keyInformation.get(KeyWordType.DATE));
        keyTokens.addAll(keyInformation.get(KeyWordType.MOST_FREQUENT));

        return filterWords(words, keyTokens);
    }

    private MultiChoiceQuestion prepareMultiChoiceQuestion(Long questionNumber, Word answer, List<String> options) {
        List<Answer> otherAnswers = new ArrayList<>();
        otherAnswers.add(new Answer(answer.getWord(), Boolean.TRUE));

        for (String option : options) {
            otherAnswers.add(new Answer(option, Boolean.FALSE));
        }

        Collections.shuffle(otherAnswers);

        return new MultiChoiceQuestion(questionNumber, answer.getSentence().replace(answer.getWord(), MASK), otherAnswers);
    }

    private QuestionsDTO prepareQuestions(List<Word> keyWords) throws IOException {
        Collections.shuffle(keyWords);
        int questionCount = keyWords.size();

        if (questionCount > MAX_NUM_OF_KEY_WORDS) {
            questionCount = MAX_NUM_OF_KEY_WORDS;
        }

        List<Word> answers = keyWords.subList(0, questionCount);
        List<MultiChoiceQuestion> multiChoiceQuestions = new ArrayList<>();
        List<SingleChoiceQuestion> singleChoiceQuestions = new ArrayList<>();

        Long questionNumber = 1L;
        for (Word answer : answers) {
            try {
                Collection<String> similarities = trainedData.getWord2Vec().wordsNearestSum(answer.getWord(), 5);
                List<String> options = new ArrayList<>(similarities);
                options = options.subList(1, 4);
                multiChoiceQuestions.add(prepareMultiChoiceQuestion(questionNumber, answer, options));
            } catch (NullPointerException exception) {
                Answer temp = new Answer(answer.getWord(), Boolean.TRUE);
                SingleChoiceQuestion singleQuestion = new SingleChoiceQuestion(questionNumber, answer.getSentence().replace(answer.getWord(), MASK),
                        temp);
                singleQuestion.getAnswer().setSingleQuestion(singleQuestion);
                singleChoiceQuestions.add(singleQuestion);
            }
            questionNumber++;
        }

        for (int i = 0; i < multiChoiceQuestions.size(); i++) {
            MultiChoiceQuestion multiQuestion = multiChoiceQuestions.get(i);
            for (int j = 0; j < multiQuestion.getOptions().size(); j++) {
                multiQuestion.getOptions().get(j).setMultiQuestion(multiQuestion);
            }
        }

        return new QuestionsDTO(singleChoiceQuestions, multiChoiceQuestions, null);
    }

    public QuestionsDTO getQuestions(String paragraph) throws IOException {
        List<Word> words = getWords(paragraph);
        List<Word> keyWords = getKeyWords(words);
        QuestionsDTO questionsDTO = prepareQuestions(keyWords);
        return questionsDTO;
    }

    public com.project.quizo.domain.testManagement.Test createExam(QuestionsDTO questionsDTO, String username) {
        User user = userService.findByUsername(username);
        String randomId = UUID.randomUUID().toString();
        com.project.quizo.domain.testManagement.Test exam = new com.project.quizo.domain.testManagement.Test(randomId, Boolean.TRUE, 180L, new Timestamp(System.currentTimeMillis()), user, questionsDTO.getMultiChoiceQuestions(), questionsDTO.getSingleChoiceQuestions(), null);

        for (MultiChoiceQuestion multiChoiceQuestion: exam.getMultiChoiceQuestions()) {
            multiChoiceQuestion.setExam(exam);
        }

        for (SingleChoiceQuestion singleChoiceQuestion: exam.getSingleChoiceQuestions()) {
            singleChoiceQuestion.setExam(exam);
        }

        exam.setQuestionsCount(exam.getMultiChoiceQuestions().size() + exam.getSingleChoiceQuestions().size());
        return testRepository.save(exam);
    }

    public com.project.quizo.domain.testManagement.Test getTestByCode(String code) {
        return testRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Test not found.", "code", code)));
    }

    @Transactional
    public Test createTestTake(String username, Test exam, List<AnswerDTO> singleChoiceAnswers, List<AnswerDTO> multiChoiceAnswers) {
        TestTake testTake = new TestTake();

        testTake.setExam(exam);
        testTake.setCreatedBy(userService.findByUsername(username));
        testTake.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<TestAnswer> userAnswers = new ArrayList<>();

        int numOfSingleAnswers = singleChoiceAnswers.size();
        for (int i = 0; i < numOfSingleAnswers; i++) {
            TestAnswer temp = new TestAnswer(exam.getSingleChoiceQuestions().get(i).getNumber().intValue(), singleChoiceAnswers.get(i).getAnswer());
            temp.setTestTake(testTake);
            userAnswers.add(temp);
        }

        int numOfMultiAnswers = multiChoiceAnswers.size();
        for (int j = 0; j < numOfMultiAnswers; j++) {
            TestAnswer temp = new TestAnswer(exam.getMultiChoiceQuestions().get(j).getNumber().intValue(), multiChoiceAnswers.get(j).getAnswer());
            temp.setTestTake(testTake);
            userAnswers.add(temp);
        }

        testTake.setTestAnswerList(userAnswers);

        if (exam.getTestTakes().isEmpty()){
            List<TestTake> testTakes = new ArrayList<>();
            exam.setTestTakes(testTakes);
        }

        exam.getTestTakes().add(testTake);

        return testRepository.save(exam);
    }

}
