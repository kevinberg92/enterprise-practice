package com.kevinberg.kontespringboot.backend.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Quiz{

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max=128)
    @Column(unique=true)
    private String question;

    @NotBlank
    @Size(max=128)
    private String firstAnswer;

    @NotBlank
    @Size(max=128)
    private String secondAnswer;

    @NotBlank
    @Size(max=128)
    private String thirdAnswer;

    @NotBlank
    @Size(max=128)
    private String fourthAnswer;

    @ManyToOne
    @NotNull
    private SubCategory subCategory;

    @Range(min = 0, max = 3)
    private int correctAnswer;


    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory parent) {
        this.subCategory = parent;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    public Quiz() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }






}