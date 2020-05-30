package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserQuizFavDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserQuizFavDaoImpl implements UserQuizFavDao {

    private JdbcTemplate jdbcTemplate;

    private final Environment env;


    @Autowired
    public UserQuizFavDaoImpl(DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
    }


    @Override
    public void makeFavorite(Long user_id, Long quiz_id) {
        jdbcTemplate.update(
                env.getProperty("create.favourite"),
                user_id, quiz_id);
    }

    @Override
    public void deleteFavorite(Long user_id, Long quiz_id) {
        System.out.println(user_id+" "+quiz_id);
        jdbcTemplate.update(
                env.getProperty("delete.favourite"),
                user_id,quiz_id
        );
    }
}
