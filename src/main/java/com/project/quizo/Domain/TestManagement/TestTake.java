package com.project.quizo.Domain.TestManagement;

import com.project.quizo.Domain.UserManagement.User;
import com.project.quizo.Listener.TestTakeListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@EntityListeners(TestTakeListener.class)
@Table(name = "test_take", schema = "public")
public class TestTake {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_take_id_generator")
    @SequenceGenerator(name = "test_take_id_generator", sequenceName = "test_take_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "success_rate")
    private int successRate;

    @Column(name = "correct_answer_count")
    private int correctAnswersCount;

    @Column(name = "wrong_answer_count")
    private int wrongAnswersCount;

    @Column(name = "create_date")
    private Timestamp createdDate;

    @OneToMany(mappedBy = "testTake", cascade = CascadeType.ALL)
    private List<TestAnswer> testAnswerList;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test exam;

    public TestTake(int successRate, int correctAnswersCount, int wrongAnswersCount, Timestamp createdDate, User createdBy, List<TestAnswer> testAnswerList) {
        this.successRate = successRate;
        this.correctAnswersCount = correctAnswersCount;
        this.wrongAnswersCount = wrongAnswersCount;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.testAnswerList = testAnswerList;
    }

    public TestTake() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(int correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }

    public int getWrongAnswersCount() {
        return wrongAnswersCount;
    }

    public void setWrongAnswersCount(int wrongAnswersCount) {
        this.wrongAnswersCount = wrongAnswersCount;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<TestAnswer> getTestAnswerList() {
        return testAnswerList;
    }

    public void setTestAnswerList(List<TestAnswer> testAnswerList) {
        this.testAnswerList = testAnswerList;
    }

    public Test getExam() {
        return exam;
    }

    public void setExam(Test exam) {
        this.exam = exam;
    }
}
