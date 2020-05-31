package com.team.app.backend.service.impl;

import com.team.app.backend.dto.FinishedQuizDto;
import com.team.app.backend.dto.SessionStatsDto;
import com.team.app.backend.persistance.dao.*;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserActivity;
import com.team.app.backend.persistance.model.UserToSession;
import com.team.app.backend.service.UserToSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserToSessionServiceImpl implements UserToSessionService {

    private final
    UserToSessionDao userToSessionDao;

    private final
    SessionDao sessionDao;

    private final
    UserActivityDao userActivityDao;

    @Autowired
    public UserToSessionServiceImpl(UserToSessionDao userToSessionDao, SessionDao sessionDao, UserActivityDao userActivityDao) {
        this.userToSessionDao = userToSessionDao;
        this.sessionDao = sessionDao;
        this.userActivityDao = userActivityDao;
    }

    @Override
    public void createNewUserToSession(Long user_id, Long ses_id) {
        UserToSession userToSession = new UserToSession();
        userToSession.setUser_id(user_id);
        userToSession.setSession_id(ses_id);
        userToSessionDao.save(userToSession);
    }

    @Override
    public List<SessionStatsDto> getStats(Long sessionId) {
        List<SessionStatsDto> res = userToSessionDao.getStats(sessionId);
        for (SessionStatsDto s:res) {
            System.out.println(s.toString());
        }
        return res;
    }


    @Override
    public void insertScore(FinishedQuizDto finishedQuizDto) {
        Session session=sessionDao.getById(finishedQuizDto.getSes_id());
        Long user_id =finishedQuizDto.getUser_id();

        UserToSession userToSession = new UserToSession();
        userToSession.setScore(finishedQuizDto.getScore());
        userToSession.setSession_id(session.getId());
        userToSession.setUser_id(user_id);
        userToSession.setTime(finishedQuizDto.getTime());


        UserActivity userActivity=new UserActivity();
        userActivity.setCategoryId(1L);
        userActivity.setDate(session.getDate());
        userActivity.setUserId(user_id);
        userActivity.setElem_id(session.getQuiz_id());
        userActivityDao.create(userActivity);
        userToSessionDao.update(userToSession);
    }
}
