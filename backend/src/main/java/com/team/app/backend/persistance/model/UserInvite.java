package com.team.app.backend.persistance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInvite {
    private Long id;
    private boolean activated;
    private String inviteText;
    private Date inviteDate;
    private Long userIdFrom;
    private Long userIdTo;
    private String usernameFrom;
    private String image;
}
