package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.SessionDao;
import com.team.app.backend.persistance.model.Quiz;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserToSession;
import com.team.app.backend.service.SessionService;
import com.team.app.backend.service.UserToSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionDao sessionDao;

    @Autowired
    UserToSessionService userToSessionService;

    @Override
    public Session newSessionForQuiz(Quiz quiz) {
        return sessionDao.save(new Session(quiz.getId()));
    }

    @Override
    public Session updateSession(Session session) {
        return sessionDao.update(session);
    }

    @Override
    public Session getSessionById(Long id) {
        return sessionDao.getById(id);
    }

    @Override
    public List<Session> getAllByQuizId(Long quizId) {
        return sessionDao.list().stream()
                .filter(s -> s.getQuizId().equals(quizId))
                .collect(Collectors.toList());
    }
}
