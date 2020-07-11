package com.project.quizo.Domain.NlpManagement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "test_answer", schema = "public")
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_answer_id_generator")
    @SequenceGenerator(name = "test_answer_id_generator", sequenceName = "test_answer_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "question_number")
    private int questionNumber;

    @NotNull
    @Column(name = "answer")
    private String answerId;

    @ManyToOne
    @JoinColumn(name = "test_take_id")
    private TestTake testTake;

    public TestAnswer(int questionNumber, String answerId) {
        this.questionNumber = questionNumber;
        this.answerId = answerId;
    }

    public TestAnswer() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public TestTake getTestTake() {
        return testTake;
    }

    public void setTestTake(TestTake testTake) {
        this.testTake = testTake;
    }
}
