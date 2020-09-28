package com.project.quizo.domain.testManagement;

import com.project.quizo.domain.userManagement.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "test", schema = "public")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "test_id_generator")
    @SequenceGenerator(name = "test_id_generator", sequenceName = "test_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    @NotNull
    boolean isActive;

    @Column(name = "duration")
    @NotNull
    private Long duration;

    @Column(name = "create_date")
    private Timestamp createdDate;

    @Transient
    private int questionsCount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<MultiChoiceQuestion> multiChoiceQuestions;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<SingleChoiceQuestion> singleChoiceQuestions;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<TestTake> testTakes;

    public Test(String code, @NotNull boolean isActive, @NotNull Long duration, Timestamp createdDate, User createdBy,
                List<MultiChoiceQuestion> multiChoiceQuestions, List<SingleChoiceQuestion> singleChoiceQuestions,
                List<TestTake> testTakes) {
        this.code = code;
        this.isActive = isActive;
        this.duration = duration;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.multiChoiceQuestions = multiChoiceQuestions;
        this.singleChoiceQuestions = singleChoiceQuestions;
        this.testTakes = testTakes;
    }

    public Test() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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

    public List<MultiChoiceQuestion> getMultiChoiceQuestions() {
        return multiChoiceQuestions;
    }

    public void setMultiChoiceQuestions(List<MultiChoiceQuestion> multiChoiceQuestions) {
        this.multiChoiceQuestions = multiChoiceQuestions;
    }

    public List<SingleChoiceQuestion> getSingleChoiceQuestions() {
        return singleChoiceQuestions;
    }

    public void setSingleChoiceQuestions(List<SingleChoiceQuestion> singleChoiceQuestions) {
        this.singleChoiceQuestions = singleChoiceQuestions;
    }

    public List<TestTake> getTestTakes() {
        return testTakes;
    }

    public void setTestTakes(List<TestTake> testTakes) {
        this.testTakes = testTakes;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }
}
