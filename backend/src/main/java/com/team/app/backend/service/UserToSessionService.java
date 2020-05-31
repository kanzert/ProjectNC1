package com.team.app.backend.service;

import com.team.app.backend.dto.FinishedQuizDto;
import com.team.app.backend.dto.SessionStatsDto;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserToSession;

import java.util.List;

public interface UserToSessionService {

    void createNewUserToSession(Long user_id, Long session_id);

    List<SessionStatsDto> getStats(Long sessionId);

    void insertScore(FinishedQuizDto finishedQuizDto);

}
