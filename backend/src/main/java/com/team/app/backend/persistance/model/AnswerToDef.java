package com.team.app.backend.persistance.model;

public class AnswerToDef {

    private Long userAnswerId;
    private Long defId;
    private String text;

    public AnswerToDef() {}

    public AnswerToDef(Long userAnswerId, Long defId, String text) {
        this.userAnswerId = userAnswerId;
        this.defId = defId;
        this.text = text;
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }

    public AnswerToDef setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
        return this;
    }

    public Long getDefId() {
        return defId;
    }

    public AnswerToDef setDefId(Long defId) {
        this.defId = defId;
        return this;
    }

    public String getText() {
        return text;
    }

    public AnswerToDef setText(String text) {
        this.text = text;
        return this;
    }
}
