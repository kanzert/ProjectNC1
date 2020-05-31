package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.SessionDao;
import com.team.app.backend.persistance.dao.mappers.SessionRowMapper;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.SessionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class SessionDaoImpl implements SessionDao {

    private JdbcTemplate jdbcTemplate;

    private final Environment env;

    private final SessionRowMapper sessionRowMapper;

    @Autowired
    public SessionDaoImpl(DataSource dataSource, Environment env, SessionRowMapper sessionRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
        this.sessionRowMapper = sessionRowMapper;
    }

    @Override
    public Long save(Session session) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            env.getProperty("create.session"),
                            new String[] {"id"}
                    );
                    ps.setString(1, session.getAccessCode());
                    ps.setTimestamp(2, session.getDate());
                    ps.setLong(3, session.getQuiz_id());
                    ps.setLong(4, session.getStatus().getId());
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public Session getById(Long id) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.session"),
                new Object[]{id},
                sessionRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                env.getProperty("delete.session"),
                id);
    }

    @Override
    public void update(Session session) {
        jdbcTemplate.update(
                env.getProperty("update.session"),
                session.getAccessCode(),
                session.getDate(),
                session.getQuiz_id(),
                session.getId());
    }

    @Override
    public void setSessionStatus(Long ses_id, Long status_id) {
        jdbcTemplate.update(
                env.getProperty("update.status.session"),
                status_id,ses_id
        );
    }


    @Override
    public boolean checkAccesCodeAvailability(String access_code) {
        return jdbcTemplate.queryForObject(
                env.getProperty("check.access_code.session"),
                new Object[]{access_code},
                Boolean.class);
    }

    @Override
    public Session getSessionByCode(String access_code) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.session.by.access_code"),
                new Object[]{access_code},
                sessionRowMapper);
    }


}
