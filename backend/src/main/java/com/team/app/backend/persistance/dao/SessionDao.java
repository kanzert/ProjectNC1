package com.team.app.backend.persistance.dao;

import com.team.app.backend.persistance.model.Session;

public interface SessionDao {

    Long save(Session session);

    Session getById(Long id);

    void deleteById(Long id);

    void update(Session session);

    void setSessionStatus(Long ses_id,Long status_id);

    boolean checkAccesCodeAvailability(String access_code);

    Session getSessionByCode(String access_code);
}
