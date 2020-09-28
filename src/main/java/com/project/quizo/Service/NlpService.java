package com.project.quizo.Service;

import com.project.quizo.domain.testManagement.*;
import com.project.quizo.Resource.AnswerDTO;
import com.project.quizo.Resource.QuestionsDTO;

import java.io.IOException;
import java.util.List;

public interface NlpService {

	List<Word> getWords(String paragraph) throws IOException;

	//List<String> getKeyWords(String paragraph) throws IOException;

	//Map<NameEntityRecognition, List<String>> generateKeyInformation(List<String> words) throws IOException;

	//List<String> getSimilarWords(String word) throws IOException;

	QuestionsDTO getQuestions(String paragraph) throws IOException;

	Test createExam(QuestionsDTO questionsDTO, String username);

	Test getTestByCode(String code);

	Test createTestTake(String username, Test exam, List<AnswerDTO> singleChoiceAnswers, List<AnswerDTO> multiChoiceAnswers);

}