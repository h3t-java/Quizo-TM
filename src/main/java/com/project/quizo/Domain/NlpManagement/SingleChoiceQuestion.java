package com.project.quizo.Domain.NlpManagement;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("SINGLE_QUESTION")
@Table(name = "single_question", schema = "public")
public class SingleChoiceQuestion extends Question {

    @OneToOne(mappedBy = "singleQuestion", cascade = CascadeType.ALL)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test exam;

    public SingleChoiceQuestion() {

    }
    public SingleChoiceQuestion(Long number, String question, Answer answer) {
        super(number, question);
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Test getExam() {
        return exam;
    }

    public void setExam(Test exam) {
        this.exam = exam;
    }
}
