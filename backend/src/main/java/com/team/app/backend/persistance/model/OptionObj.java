package com.team.app.backend.persistance.model;

public class OptionObj {

    private Long id;
    private Long questionId;

    public OptionObj() {
    }

    public OptionObj(Long id, Long questionId) {
        this.id = id;
        this.questionId = questionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
