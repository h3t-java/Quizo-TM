package com.project.quizo.Domain.TestManagement;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING, length = 20)
@DiscriminatorValue("QUESTION")
@Table(name = "question", schema = "public")
public class Question implements Serializable {
    private static final long serialVersionUID = -7588980448693010399L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_generator")
    @SequenceGenerator(name = "question_id_generator", sequenceName = "question_id_seq", allocationSize = 1)
    protected Long id;

    @Column(name = "number")
    @NotNull
    protected Long number;

    @NotBlank
    @Column(name = "question")
    @Size(max = 250)
    protected String question;

    public Question() {
    }

    public Question(Long number, String question) {
        this.number = number;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
