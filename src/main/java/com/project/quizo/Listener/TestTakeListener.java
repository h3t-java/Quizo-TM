package com.project.quizo.Listener;

import com.project.quizo.Domain.NlpManagement.*;

import javax.persistence.PostPersist;
import java.util.List;
import java.util.stream.Collectors;

public class TestTakeListener {

    @PostPersist
    private void calculateStats(TestTake testTake) {
        int correctAnswers = 0;

        correctAnswers += calculateSingleQuestionAnswers(testTake);
        correctAnswers += calculateMultiQuestionAnswers(testTake);

        testTake.setCorrectAnswersCount(correctAnswers);
        testTake.setWrongAnswersCount(testTake.getTestAnswerList().size() - correctAnswers);

        int answersCount = testTake.getTestAnswerList().size();
        float grade = ((float)(correctAnswers)/(answersCount))*100;
        testTake.setSuccessRate(Math.round(grade));
    }

    private int calculateSingleQuestionAnswers(TestTake testTake) {
        List<TestAnswer> userAnswers = testTake.getTestAnswerList();
        List<SingleChoiceQuestion> singleChoiceQuestions = testTake.getExam().getSingleChoiceQuestions();
        int correctAnswers = 0;

        for (SingleChoiceQuestion singleChoiceQuestion : singleChoiceQuestions) {
            int index = findAnswerByNumber(singleChoiceQuestion.getNumber().intValue(), userAnswers);

            if (index == -1) {
                continue;
            }

            if (singleChoiceQuestion.getAnswer().getAnswer().equalsIgnoreCase(userAnswers.get(index).getAnswerId())) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private int calculateMultiQuestionAnswers(TestTake testTake) {
        List<TestAnswer> userAnswers = testTake.getTestAnswerList();
        List<MultiChoiceQuestion> multiChoiceQuestions = testTake.getExam().getMultiChoiceQuestions();
        int correctAnswers = 0;

        for (MultiChoiceQuestion multiChoiceQuestion : multiChoiceQuestions) {
            int index = findAnswerByNumber(multiChoiceQuestion.getNumber().intValue(), userAnswers);

            if (index == -1) {
                continue;
            }

            List<Answer> answers = multiChoiceQuestion.getOptions().stream().filter(Answer::isCorrect).collect(Collectors.toList());
            if (answers.isEmpty()) {
                continue;
            }

            if (answers.get(0).getAnswer().equalsIgnoreCase(userAnswers.get(index).getAnswerId())) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    private int findAnswerByNumber(int number, List<TestAnswer> answers) {
        int index = -1;
        for (TestAnswer testAnswer : answers) {
            if (testAnswer.getQuestionNumber() == number) {
                index = answers.indexOf(testAnswer);
                break;
            }
        }
        return index;
    }
}
