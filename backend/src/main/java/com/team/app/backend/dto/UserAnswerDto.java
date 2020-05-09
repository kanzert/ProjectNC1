package com.team.app.backend.dto;

public class UserAnswerDto {

    private long userToSessionId;
    private int points;
    private int time;

    public UserAnswerDto() {}

    public UserAnswerDto(long userToSessionId, int points, int time) {
        this.userToSessionId = userToSessionId;
        this.points = points;
        this.time = time;
    }

    public long getUserToSessionId() {
        return userToSessionId;
    }

    public UserAnswerDto setUserToSessionId(long userToSessionId) {
        this.userToSessionId = userToSessionId;
        return this;
    }

    public int getPoints() {
        return points;
    }

    public UserAnswerDto setPoints(int points) {
        this.points = points;
        return this;
    }

    public int getTime() {
        return time;
    }

    public UserAnswerDto setTime(int time) {
        this.time = time;
        return this;
    }
}
