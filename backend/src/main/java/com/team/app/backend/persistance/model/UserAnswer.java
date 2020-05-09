package com.team.app.backend.persistance.model;

public class UserAnswer {

    private Long id;
    private Long userToSessionId;
    private int points;
    private int time;

    public UserAnswer() {}

    public UserAnswer(Long id, Long userToSessionId, int points, int time) {
        this.id = id;
        this.userToSessionId = userToSessionId;
        this.points = points;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public UserAnswer setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserToSessionId() {
        return userToSessionId;
    }

    public UserAnswer setUserToSessionId(Long userToSessionId) {
        this.userToSessionId = userToSessionId;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public UserAnswer setPoints(int points) {
        this.points = points;
        return this;
    }

    public int getTime() {
        return time;
    }

    public UserAnswer setTime(int time) {
        this.time = time;
        return this;
    }
}
