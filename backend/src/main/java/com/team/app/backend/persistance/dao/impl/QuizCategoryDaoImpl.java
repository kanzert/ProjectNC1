package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.QuizCategoryDao;
import com.team.app.backend.persistance.dao.mappers.QuizCategoryRowMapper;
import com.team.app.backend.persistance.model.QuizCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuizCategoryDaoImpl implements QuizCategoryDao {

    private JdbcTemplate jdbcTemplate;

    private final QuizCategoryRowMapper quizCategoryRowMapper;

    private final Environment env;


    @Autowired
    public QuizCategoryDaoImpl(DataSource dataSource, QuizCategoryRowMapper quizCategoryRowMapper, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.quizCategoryRowMapper = quizCategoryRowMapper;
        this.env = env;
    }

    @Override
    public List<QuizCategory> getAll() {
        return jdbcTemplate.query(
                env.getProperty("get.all.categories")
                ,quizCategoryRowMapper);
    }


    @Override
    public void addQuizToCategory(Long quiz_id, Long cat_id) {
        jdbcTemplate.update(
                env.getProperty("add.quiz.to.category")
                ,cat_id
                ,quiz_id
                );

    }
}
