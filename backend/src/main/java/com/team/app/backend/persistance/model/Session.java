package com.team.app.backend.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    private Long id;
    private Long quiz_id;
    private String accessCode;
    private Timestamp date;
    private SessionStatus status;

}
