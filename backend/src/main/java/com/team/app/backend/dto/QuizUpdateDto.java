package com.team.app.backend.dto;

import lombok.Data;

@Data
public class QuizUpdateDto {

    private Long id;
    private String title;
    private String description;
    private String image;

    public QuizUpdateDto(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
}