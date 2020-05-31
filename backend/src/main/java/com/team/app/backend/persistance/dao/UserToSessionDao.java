package com.team.app.backend.persistance.dao;

import com.team.app.backend.dto.SessionStatsDto;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.UserToSession;

import java.util.List;

public interface UserToSessionDao {

    void save(UserToSession session);

    UserToSession getById(Long id);

    void deleteById(Long id);

    void update(UserToSession session);

    List<SessionStatsDto> getStats(Long sessionId);
}
