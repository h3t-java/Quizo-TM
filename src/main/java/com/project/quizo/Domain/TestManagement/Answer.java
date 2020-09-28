package com.project.quizo.Domain.TestManagement;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "answer", schema = "public")
public class Answer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_id_generator")
    @SequenceGenerator(name = "answer_id_generator", sequenceName = "answer_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "answer")
    @NotBlank
    @Size(max = 50)
    private String answer;

    @Column(name = "is_correct")
    @NotNull
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "multi_question_id")
    private MultiChoiceQuestion multiQuestion;

    @OneToOne
    @JoinColumn(name = "single_question_id")
    private SingleChoiceQuestion singleQuestion;

    public Answer() {
    }

    public Answer(String answer, boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }

    public MultiChoiceQuestion getMultiQuestion() {
        return multiQuestion;
    }

    public void setMultiQuestion(MultiChoiceQuestion multiQuestion) {
        this.multiQuestion = multiQuestion;
    }

    public SingleChoiceQuestion getSingleQuestion() {
        return singleQuestion;
    }

    public void setSingleQuestion(SingleChoiceQuestion singleQuestion) {
        this.singleQuestion = singleQuestion;
    }
}
