package com.team.app.backend.dto;

public class FinishedQuizDto {
    private long userId;
    private long sessionId;
    private int score;

    public FinishedQuizDto() {}

    public FinishedQuizDto(long userId, long sessionId, int score) {
        this.userId = userId;
        this.sessionId = sessionId;
        this.score = score;
    }

    public long getUserId() {
        return userId;
    }

    public FinishedQuizDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getSessionId() {
        return sessionId;
    }

    public FinishedQuizDto setSessionId(long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public int getScore() {
        return score;
    }

    public FinishedQuizDto setScore(int score) {
        this.score = score;
        return this;
    }
}
