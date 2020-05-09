package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserToSessionDao;
import com.team.app.backend.persistance.model.UserToSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UserToSessionDaoImpl implements UserToSessionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserToSession save(UserToSession userToSession) {
        String sql = "INSERT INTO UserToSession(ses_id, user_id, score) VALUES ( ?, ?, ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[] {"id"}
                    );
                    ps.setLong(1, userToSession.getSessionId());
                    ps.setLong(2, userToSession.getUserId());
                    ps.setInt(3, userToSession.getScore());
                    return ps;
                },
                keyHolder
        );
        return getById((Long) keyHolder.getKey());
    }

    @Override
    public UserToSession getById(Long id) {
        String sql = "SELECT * FROM UserToSession WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    UserToSession userToSession = new UserToSession();
                    userToSession
                            .setId(resultSet.getLong("id"))
                            .setSessionId(resultSet.getLong("ses_id"))
                            .setUserId(resultSet.getLong("user_id"))
                            .setScore(resultSet.getInt("score"));
                    return userToSession;
                });
    }

    @Override
    public UserToSession deleteById(Long id) {
        return null;
    }

    @Override
    public UserToSession update(UserToSession userToSession) {
        String sql = "UPDATE UserToSession SET ses_id = ?, user_id = ?, score = ? WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[] {"id"}
                    );
                    ps.setLong(1, userToSession.getSessionId());
                    ps.setLong(2, userToSession.getUserId());
                    ps.setInt(3, userToSession.getScore());
                    ps.setLong(4, userToSession.getId());
                    return ps;
                },
                keyHolder
        );
        return getById((Long) keyHolder.getKey());
    }
}
