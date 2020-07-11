package com.project.quizo.Resource;

import com.project.quizo.Domain.NlpManagement.Test;
import com.project.quizo.Domain.NlpManagement.TestAnswer;

import java.util.List;

public class AnswersDTO {
    private List<AnswerDTO> singleQuestionAnswers;
    private List<AnswerDTO> multiQuestionAnswers;
    private Test exam;

    public AnswersDTO() {

    }

    public AnswersDTO(List<AnswerDTO> singleQuestionAnswers, List<AnswerDTO> multiQuestionAnswers, Test exam) {
        this.singleQuestionAnswers = singleQuestionAnswers;
        this.multiQuestionAnswers = multiQuestionAnswers;
        this.exam = exam;
    }

    public Test getExam() {
        return exam;
    }

    public void setExam(Test exam) {
        this.exam = exam;
    }

    public List<AnswerDTO> getSingleQuestionAnswers() {
        return singleQuestionAnswers;
    }

    public void setSingleQuestionAnswers(List<AnswerDTO> singleQuestionAnswers) {
        this.singleQuestionAnswers = singleQuestionAnswers;
    }

    public List<AnswerDTO> getMultiQuestionAnswers() {
        return multiQuestionAnswers;
    }

    public void setMultiQuestionAnswers(List<AnswerDTO> multiQuestionAnswers) {
        this.multiQuestionAnswers = multiQuestionAnswers;
    }
}
