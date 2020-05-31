package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.SessionDao;
import com.team.app.backend.persistance.model.*;
import com.team.app.backend.service.SessionService;
import com.team.app.backend.service.UserToSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final SessionDao sessionDao;

    @Autowired
    public SessionServiceImpl(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }


    private String generateAccessCode(){
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String access_code = String.format("%06d", number);
        while(sessionDao.checkAccesCodeAvailability(access_code)) {
            number = rnd.nextInt(999999);
            access_code = String.format("%06d", number);
        }
        return access_code;
    }


    @Override
    public Long newSessionForQuiz(Long quiz_id) {
        long millis=System.currentTimeMillis();
        java.sql.Timestamp date=new java.sql.Timestamp(millis);

        Session session= new Session();
        session.setAccessCode(generateAccessCode());
        session.setQuiz_id(quiz_id);
        session.setDate(date);
        session.setStatus(new SessionStatus(1L,"waiting"));
        return sessionDao.save(session);
    }

    @Override
    public void updateSession(Session session) { sessionDao.update(session); }

    @Override
    public Session getSessionById(Long id) {
        return sessionDao.getById(id);
    }


    @Override
    public void setSessionStatus(Long ses_id, SessionStatus sessionStatus) {
        sessionDao.setSessionStatus(ses_id,sessionStatus.getId());
    }

    @Override
    public Session getSessionByAccessCode(String access_code) {
        if(sessionDao.checkAccesCodeAvailability(access_code))return sessionDao.getSessionByCode(access_code);
        return null;
    }
}
