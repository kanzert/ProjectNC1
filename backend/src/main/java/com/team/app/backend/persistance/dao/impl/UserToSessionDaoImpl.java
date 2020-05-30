package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserToSessionDao;
import com.team.app.backend.persistance.dao.mappers.UserActivityRowMapper;
import com.team.app.backend.persistance.dao.mappers.UserToSessionRowMapper;
import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.UserToSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserToSessionDaoImpl implements UserToSessionDao {

    private JdbcTemplate jdbcTemplate;

    private final Environment env;

    private final UserToSessionRowMapper userToSessionRowMapper;


    @Autowired
    public UserToSessionDaoImpl(DataSource dataSource, Environment env, UserToSessionRowMapper userToSessionRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
        this.userToSessionRowMapper = userToSessionRowMapper;
    }

    @Override
    public void save(UserToSession userToSession) {
        jdbcTemplate.update(
                env.getProperty("save.user.session"),
                userToSession.getSession_id(),
                userToSession.getUser_id());
    }

    @Override
    public UserToSession getById(Long id) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.user.session.by.id"),
                new Object[]{id},
                userToSessionRowMapper);
    }

    @Override
    public void deleteById(Long id) {
         jdbcTemplate.update(
                env.getProperty("delete.user.session.by.id"),
                id);
    }


    @Override
    public void update(UserToSession userToSession) {
        jdbcTemplate.update(
                env.getProperty("update.user.session"),
                userToSession.getScore(),
                userToSession.getTime(),
                userToSession.getSession_id(),
                userToSession.getUser_id()
        );
    }

    @Override
    public List<UserToSession> getAllBySes(Long ses_id) {
        return jdbcTemplate.query(
                env.getProperty("get.all.user.session.by.ses"),
                new Object[]{ses_id}
                ,userToSessionRowMapper);
    }
}
