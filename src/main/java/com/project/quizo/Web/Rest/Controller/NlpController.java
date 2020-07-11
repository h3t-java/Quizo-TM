package com.project.quizo.Web.Rest.Controller;

import com.project.quizo.Domain.NlpManagement.Answer;
import com.project.quizo.Domain.NlpManagement.Test;
import com.project.quizo.Domain.NlpManagement.TestAnswer;
import com.project.quizo.Domain.NlpManagement.TestTake;
import com.project.quizo.Resource.AnswerDTO;
import com.project.quizo.Resource.AnswersDTO;
import com.project.quizo.Resource.QuestionsDTO;
import com.project.quizo.Resource.TestGenDTO;
import com.project.quizo.Service.NlpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NlpController {

    @Autowired
    private NlpService nlpService;

    @Autowired
    private HttpSession session;

    @PostMapping("/questions")
    public List<String> getQuestions(String paragraph) {
        String test = "World War II (often abbreviated to WWII or WW2), also known as the Second World War, was a global war that lasted from 1939 to 1945. The vast majority of the world's countries—including all the great powers—eventually formed two opposing military alliances: the Allies and the Axis. A state of total war emerged, directly involving more than 100 million people from more than 30 countries. The major participants threw their entire economic, industrial, and scientific capabilities behind the war effort, blurring the distinction between civilian and military resources. World War II was the deadliest conflict in human history, marked by 70 to 85 million fatalities, most of whom were civilians in the Soviet Union and China. It included massacres, genocides including the Holocaust, strategic bombing, premeditated death from starvation and disease, and the only use of nuclear weapons in war.\n" +
                "\n" +
                "Japan, which aimed to dominate Asia and the Pacific, was at war with China by 1937,[b] though neither side had declared war on the other. World War II is generally said to have begun on 1 September 1939, with the invasion of Poland by Germany and subsequent declarations of war on Germany by France and the United Kingdom. From late 1939 to early 1941, in a series of campaigns and treaties, Germany conquered or controlled much of continental Europe, and formed the Axis alliance with Italy and Japan. Under the Molotov–Ribbentrop Pact of August 1939, Germany and the Soviet Union partitioned and annexed territories of their European neighbours, Poland, Finland, Romania and the Baltic states. Following the onset of campaigns in North Africa and East Africa, and the Fall of France in mid 1940, the war continued primarily between the European Axis powers and the British Empire. War in the Balkans, the aerial Battle of Britain, the Blitz, and the long Battle of the Atlantic followed. On 22 June 1941, the European Axis powers launched an invasion of the Soviet Union, opening the largest land theatre of war in history. This Eastern Front trapped the Axis, most crucially the German Wehrmacht, in a war of attrition. In December 1941, Japan launched a surprise attack on the United States as well as European colonies in the Pacific. Following an immediate U.S. declaration of war against Japan, supported by one from Great Britain, the European Axis powers quickly declared war on the U.S. in solidarity with their Japanese ally. Rapid Japanese conquests over much of the Western Pacific ensued, perceived by many in Asia as liberation from Western dominance and resulting in the support of several armies from defeated territories.";
        try {
            nlpService.getQuestions(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @GetMapping("/testgen")
    public String testGeneration(Model model) {
        model.addAttribute("testgen", new TestGenDTO());

        return "/testgen";
    }

    @GetMapping("/create-test")
    public String createTest(@ModelAttribute("questionsDTO")QuestionsDTO questionsDTO, Model model) {
        QuestionsDTO questionsDTO1 = (QuestionsDTO) session.getAttribute("questionsDTO");
        model.addAttribute("singlequestions", questionsDTO1.getSingleChoiceQuestions());
        model.addAttribute("multiquestions", questionsDTO1.getMultiChoiceQuestions());

        return "/questions";
    }

    @PostMapping("/create-test")
    public String createTestSave(@ModelAttribute("questionsDTO")QuestionsDTO questionsDTO, Model model) {
        QuestionsDTO questionsDTO1 = (QuestionsDTO) session.getAttribute("questionsDTO");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Test exam = nlpService.createExam(questionsDTO1, username);

        model.addAttribute("exam", exam);

        return "/exam";
    }

    @PostMapping("/generate-questions")
    public String generateQuestions(@ModelAttribute("testgen") @Valid TestGenDTO testgen, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return "/access-denied";
        }

        QuestionsDTO questionsDTO = nlpService.getQuestions(testgen.getText());
        model.addAttribute("questionsDTO", questionsDTO);
        session.setAttribute("questionsDTO", questionsDTO);

        return "redirect:/create-test";
    }

    @PostMapping("/show-test")
    public String showTest(@RequestParam("code") String code, Model model) {
        Test exam = nlpService.getTestByCode(code);
        int questionCount = exam.getMultiChoiceQuestions().size() + exam.getSingleChoiceQuestions().size();
        exam.setQuestionsCount(questionCount);
        model.addAttribute("exam", exam);
        session.setAttribute("test_code", exam.getCode());

        return "/exam";
    }

    @PostMapping("/start-test")
    public String startTest(Model model) {
        Test exam = nlpService.getTestByCode((String)session.getAttribute("test_code"));

        AnswersDTO answers = new AnswersDTO();
        answers.setExam(exam);

        List<AnswerDTO> singleAnswers = new ArrayList<>();
        answers.setSingleQuestionAnswers(singleAnswers);

        List<AnswerDTO> multiAnswers = new ArrayList<>();
        answers.setMultiQuestionAnswers(multiAnswers);

        for (int i = 0; i < exam.getSingleChoiceQuestions().size(); i++) {
            answers.getSingleQuestionAnswers().add(new AnswerDTO());
        }

        for (int j = 0; j < exam.getMultiChoiceQuestions().size(); j++) {
            answers.getMultiQuestionAnswers().add(new AnswerDTO());
        }

        model.addAttribute("answers", answers);

        return "/test";
    }

    @PostMapping("/finish-test")
    public String finishTest(@ModelAttribute("answers") AnswersDTO answers, Model model) {
        Test exam = nlpService.getTestByCode((String)session.getAttribute("test_code"));

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();

        Test updatedExam = nlpService.createTestTake(username, exam, answers.getSingleQuestionAnswers(), answers.getMultiQuestionAnswers());
        return "redirect:/user";
    }
}
