package com.team.app.backend.persistance.model;

public class AnswerToOption {

    private Long userAnswerId;
    private Long optionId;

    public AnswerToOption() {}

    public AnswerToOption(Long userAnswerId, Long optionId) {
        this.userAnswerId = userAnswerId;
        this.optionId = optionId;
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }

    public AnswerToOption setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
        return this;
    }

    public Long getOptionId() {
        return optionId;
    }

    public AnswerToOption setOptionId(Long optionId) {
        this.optionId = optionId;
        return this;
    }
}
