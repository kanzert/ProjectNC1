package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.QuestionDao;
import com.team.app.backend.persistance.dao.mappers.QuestionRowMapper;
import com.team.app.backend.persistance.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final QuestionRowMapper questionRowMapper;

    private final Environment env;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDaoImpl(DataSource dataSource, QuestionRowMapper questionRowMapper, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.questionRowMapper = questionRowMapper;
        this.env = env;
    }

    @Override
    public List<Question> getQuizQusetions(Long id) {
            return jdbcTemplate.query(env.getProperty("get.quiz.questions"),
                    new Object[] { id },
                    questionRowMapper);

    }

    @Override
    public Question getQuestion(Long id) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.question"),
                new Object[]{id},
                questionRowMapper
        );
    }

    @Override
    public Long save(Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(env.getProperty("create.question"),
                            new String[] {"id"});
                    ps.setInt(1, question.getTime());
                    ps.setString(2, question.getText());
                    ps.setInt(3, question.getMax_points());
                    ps.setString(4, question.getImage());
                    ps.setLong(5, question.getType().getId());
                    ps.setLong(6, question.getQuiz_id());

                    return ps;
                },
                    keyHolder);
        return  keyHolder.getKey().longValue();
    }


    @Override
    public void update(Question question) {
        jdbcTemplate.update(
                env.getProperty("update.question"),
                question.getTime(),
                question.getText(),
                question.getMax_points(),
                question.getImage(),
                question.getType().getId(),
                question.getQuiz_id(),
                question.getId()
        );
    }

    @Override
    public void delete(Long id) {

        jdbcTemplate.update(
                env.getProperty("delete.question"),
                id
        );
    }
}
