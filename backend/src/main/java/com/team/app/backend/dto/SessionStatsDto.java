package com.team.app.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SessionStatsDto {
    int place;
    String username;
    Integer score;
    Integer time;
}
