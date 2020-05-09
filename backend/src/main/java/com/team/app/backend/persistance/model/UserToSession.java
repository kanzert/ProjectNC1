package com.team.app.backend.persistance.model;

public class UserToSession {

    private Long id;
    private Long sessionId;
    private Long userId;
    private int score;

    public UserToSession() {}

    public UserToSession(Long sessionId, Long userId) {
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public UserToSession setScore(int score) {
        this.score = score;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserToSession setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public UserToSession setSessionId(Long sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserToSession setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
