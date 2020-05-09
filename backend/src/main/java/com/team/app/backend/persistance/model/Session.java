package com.team.app.backend.persistance.model;

import java.util.Date;

public class Session {

    private Long id;
    private Long quizId;
    private String accessCode;
    private Date date;

    public Session() {}

    public Session(Long id, Long quizId, String accessCode, Date date) {
        this.id = id;
        this.quizId = quizId;
        this.accessCode = accessCode;
        this.date = date;
    }

    public Session(Long quizId) {
        this.quizId = quizId;
        this.date = new Date();
        this.accessCode = ""; // because of NOT_NULL constraint in the DB
    }

    public Long getId() {
        return id;
    }

    public Session setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getQuizId() {
        return quizId;
    }

    public Session setQuizId(Long quizId) {
        this.quizId = quizId;
        return this;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public Session setAccessCode(String accessCode) {
        this.accessCode = accessCode;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Session setDate(Date date) {
        this.date = date;
        return this;
    }
}
