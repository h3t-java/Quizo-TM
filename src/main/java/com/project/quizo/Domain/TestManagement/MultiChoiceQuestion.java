package com.project.quizo.Domain.TestManagement;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("MULTI_QUESTION")
@Table(name = "multi_question", schema = "public")
public class MultiChoiceQuestion extends Question {

    @OneToMany(mappedBy = "multiQuestion", cascade = CascadeType.ALL)
    private List<Answer> options;

    @ManyToOne
    @JoinColumn(name = "test_id", referencedColumnName = "id")
    private Test exam;

    public MultiChoiceQuestion() {

    }

    public MultiChoiceQuestion(Long number, String question, List<Answer> options) {
        super(number, question);
        this.options = options;
    }

    public List<Answer> getOptions() {
        return options;
    }

    public void setOptions(List<Answer> options) {
        this.options = options;
    }

    public Test getExam() {
        return exam;
    }

    public void setExam(Test exam) {
        this.exam = exam;
    }
}
