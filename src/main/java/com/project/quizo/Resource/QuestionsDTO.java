package com.project.quizo.Resource;

import com.project.quizo.Domain.NlpManagement.MultiChoiceQuestion;
import com.project.quizo.Domain.NlpManagement.SingleChoiceQuestion;
import com.project.quizo.Domain.NlpManagement.TrueOrFalseQuestion;

import java.io.Serializable;
import java.util.List;

public class QuestionsDTO implements Serializable {
    private List<SingleChoiceQuestion> singleChoiceQuestions;
    private List<MultiChoiceQuestion> multiChoiceQuestions;
    private List<TrueOrFalseQuestion> trueOrFalseQuestions;

    public QuestionsDTO(List<SingleChoiceQuestion> singleChoiceQuestions, List<MultiChoiceQuestion> multiChoiceQuestions, List<TrueOrFalseQuestion> trueOrFalseQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
        this.multiChoiceQuestions = multiChoiceQuestions;
        this.trueOrFalseQuestions = trueOrFalseQuestions;
    }

    public List<SingleChoiceQuestion> getSingleChoiceQuestions() {
        return singleChoiceQuestions;
    }

    public void setSingleChoiceQuestions(List<SingleChoiceQuestion> singleChoiceQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
    }

    public List<MultiChoiceQuestion> getMultiChoiceQuestions() {
        return multiChoiceQuestions;
    }

    public void setMultiChoiceQuestions(List<MultiChoiceQuestion> multiChoiceQuestions) {
        this.multiChoiceQuestions = multiChoiceQuestions;
    }

    public List<TrueOrFalseQuestion> getTrueOrFalseQuestions() {
        return trueOrFalseQuestions;
    }

    public void setTrueOrFalseQuestions(List<TrueOrFalseQuestion> trueOrFalseQuestions) {
        this.trueOrFalseQuestions = trueOrFalseQuestions;
    }
}
