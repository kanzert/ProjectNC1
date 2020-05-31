package com.team.app.backend.service;

import com.team.app.backend.persistance.model.*;

public interface SessionService {

    Session newSessionForQuiz(Long quiz_id);

    void updateSession(Session session);

    Session getSessionById(Long id);

    void setSessionStatus(Long ses_id,SessionStatus sessionStatus);

    Session getSessionByAccessCode(String access_code);
}
