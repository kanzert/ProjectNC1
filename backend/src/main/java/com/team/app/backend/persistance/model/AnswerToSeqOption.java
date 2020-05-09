package com.team.app.backend.persistance.model;

public class AnswerToSeqOption {

    private Long userAnswerId;
    private Long seqOptionId;
    private Integer serialNum;

    public AnswerToSeqOption() {}

    public AnswerToSeqOption(Long userAnswerId, Long seqOptionId, Integer serialNum) {
        this.userAnswerId = userAnswerId;
        this.seqOptionId = seqOptionId;
        this.serialNum = serialNum;
    }

    public Long getUserAnswerId() {
        return userAnswerId;
    }

    public AnswerToSeqOption setUserAnswerId(Long userAnswerId) {
        this.userAnswerId = userAnswerId;
        return this;
    }

    public Long getSeqOptionId() {
        return seqOptionId;
    }

    public AnswerToSeqOption setSeqOptionId(Long seqOptionId) {
        this.seqOptionId = seqOptionId;
        return this;
    }

    public Integer getSerialNum() {
        return serialNum;
    }

    public AnswerToSeqOption setSerialNum(Integer serialNum) {
        this.serialNum = serialNum;
        return this;
    }
}
