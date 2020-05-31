package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.QuizCategoryDao;
import com.team.app.backend.persistance.dao.mappers.QuizCategoryRowMapper;
import com.team.app.backend.persistance.model.QuizCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuizCategoryDaoImpl implements QuizCategoryDao {

    private JdbcTemplate jdbcTemplate;

    private final QuizCategoryRowMapper quizCategoryRowMapper;

    @Autowired
    public QuizCategoryDaoImpl(DataSource dataSource, QuizCategoryRowMapper quizCategoryRowMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.quizCategoryRowMapper = quizCategoryRowMapper;
    }

    @Override
    public void create(QuizCategory announcement) {

    }

    @Override
    public QuizCategory get(Long id) {
        return null;
    }

    @Override
    public List<QuizCategory> getAll() {
        return jdbcTemplate.query(
                "SELECT id, name, description FROM quiz_category;"
                ,quizCategoryRowMapper);
    }

    @Override
    public void update(QuizCategory announcement) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void addQuizToCategory(Long quiz_id, Long cat_id) {
        jdbcTemplate.update(
                "INSERT INTO quiz_to_categ(cat_id, quiz_id)VALUES (?, ?)"
                ,cat_id
                ,quiz_id
                );

    }
}
