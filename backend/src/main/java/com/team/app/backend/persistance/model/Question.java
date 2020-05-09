package com.team.app.backend.persistance.model;

import java.util.List;

public class Question {
    private Long id;
    private Integer time;
    private String text;
    private Integer maxPoints;
    private byte[] image;
    private QuestionType type;
    private Long quizId;
    private List<OptionObj> options;

    public Question(Long id, Integer time, String text, Integer maxPoints, byte[] image, QuestionType type, Long quizId) {
        this.id = id;
        this.time = time;
        this.text = text;
        this.maxPoints = maxPoints;
        this.image = image;
        this.type = type;
        this.quizId = quizId;
    }

    public Question() {

    }

    public Long getId() {
        return id;
    }


    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<OptionObj> getOptions() {
        return options;
    }

    public void setOptions(List<OptionObj> options) {
        this.options = options;
    }
}
