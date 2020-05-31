package com.team.app.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FinishedQuizDto {
    long user_id;
    long ses_id;
    int score;
    int time;
}
