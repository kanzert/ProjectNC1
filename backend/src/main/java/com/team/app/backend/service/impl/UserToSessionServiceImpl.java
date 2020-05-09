package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.UserToSessionDao;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.User;
import com.team.app.backend.persistance.model.UserToSession;
import com.team.app.backend.service.UserToSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserToSessionServiceImpl implements UserToSessionService {

    @Autowired
    private UserToSessionDao userToSessionDao;

    @Override
    public UserToSession createNewUserToSession(User user, Session session) {
        UserToSession userToSession = new UserToSession(user.getId(), session.getId());
        return userToSessionDao.save(userToSession);
    }
    // TODO: implement
    @Override
    public List<UserToSession> getAllBySessionId(Long sessionId) {
        return null;
    }
}
