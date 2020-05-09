package com.team.app.backend.persistance.model;

import java.sql.Date;

public class Quiz {
    private Long id;
    private String title;
    private Date date;
    private String description;
    private byte[] image;
    private QuizStatus status;
    private Long userId;
    private String quizAuthor;

    public void setStatus(QuizStatus status) {
        this.status = status;
    }

    public String getQuizAuthor() {
        return quizAuthor;
    }

    public void setQuizAuthor(String quizAuthor) {
        this.quizAuthor = quizAuthor;
    }

    public Quiz() {
    }

    public Quiz(Long id, String title, Date date, String description, byte[] image, QuizStatus status, Long userId, String quizAuthor) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.image = image;
        this.status = status;
        this.userId = userId;
        this.quizAuthor = quizAuthor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public QuizStatus getStatus() {
        return status;
    }

    public void setStatus_Id(QuizStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
