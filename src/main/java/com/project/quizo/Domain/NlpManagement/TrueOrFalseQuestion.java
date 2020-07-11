package com.project.quizo.Domain.NlpManagement;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("CONDITION_QUESTION")
@Table(name = "condition_question", schema = "public")
public class TrueOrFalseQuestion extends Question {
    @NotNull
    private boolean isTrue;

    public TrueOrFalseQuestion() {

    }
    public TrueOrFalseQuestion(Long number, String question, boolean isTrue) {
        super(number, question);
        this.isTrue = isTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
