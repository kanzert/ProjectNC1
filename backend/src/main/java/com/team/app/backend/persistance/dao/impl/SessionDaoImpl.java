package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.SessionDao;
import com.team.app.backend.persistance.dao.mappers.SessionRowMapper;
import com.team.app.backend.persistance.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class SessionDaoImpl implements SessionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Session save(Session session) {
        String sql = "INSERT INTO session(access_code, date, quiz_id) VALUES ( ?, ?, ? )";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[] {"id"}
                    );
                    ps.setString(1, session.getAccessCode());
                    ps.setDate(2, (Date) session.getDate());
                    ps.setLong(3, session.getQuizId());
                    return ps;
                },
                keyHolder
        );
        return getById((Long) keyHolder.getKey());
    }

    @Override
    public Session getById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    Session session = new Session();
                    session
                            .setId(resultSet.getLong("id"))
                            .setAccessCode(resultSet.getString("access_code"))
                            .setDate(resultSet.getDate("date"))
                            .setQuizId(resultSet.getLong("quiz_id"));
                    return session;
                });
    }

    @Override
    public Session deleteById(Long id) {
        return null;
    }

    @Override
    public Session update(Session session) {
        String sql = "UPDATE session SET access_code = ?, date = ?, quiz_id = ? WHERE id = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            sql,
                            new String[] {"id"}
                    );
                    ps.setString(1, session.getAccessCode());
                    ps.setDate(2, (Date) session.getDate());
                    ps.setLong(3, session.getQuizId());
                    ps.setLong(4, session.getId());
                    return ps;
                },
                keyHolder
        );
        return getById((Long) keyHolder.getKey());
    }

    @Override
    public List<Session> list() {
        String sql = "SELECT * FROM session";
        return jdbcTemplate.query(
                sql,
                new SessionRowMapper()
        );
    }
}
