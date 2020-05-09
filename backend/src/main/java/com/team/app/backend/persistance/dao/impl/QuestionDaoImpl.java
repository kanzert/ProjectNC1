package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.QuestionDao;
import com.team.app.backend.persistance.dao.mappers.QuestionRowMapper;
import com.team.app.backend.persistance.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JdbcTemplate jdbcTemplate;
    public QuestionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private QuestionRowMapper questionRowMapper;




    @Override
    public List<Question> getQuizQusetions(Long id) {
            return jdbcTemplate.query("SELECT Q.id, Q.time, Q.text, Q.max_points, Q.image, Q.type_id, QT.name AS type_name, Q.quiz_id FROM question Q INNER JOIN quest_type QT ON Q.type_id=QT.id where Q.quiz_id = ? "
                    ,new Object[] { id },
                    questionRowMapper);

    }

    @Override
    public Question getQuestion(Long id) {
        return jdbcTemplate.queryForObject(
        "SELECT Q.id, Q.time, Q.text, Q.max_points, Q.image, Q.type_id, QT.name AS type_name, Q.quiz_id FROM question Q INNER JOIN quest_type QT ON Q.type_id=QT.id WHERE Q.id = ?",
                new Object[]{id},
                questionRowMapper
        );
    }

    @Override
    public Long save(Question question) {
        String sql="INSERT INTO question( time, text, max_points, image, type_id, quiz_id) VALUES ( ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                    ps.setInt(1, question.getTime());
                    ps.setString(2, question.getText());
                    ps.setInt(3, question.getMaxPoints());
                    ps.setBytes(4, question.getImage());
                    ps.setLong(5, question.getType().getId());
                    ps.setLong(6, question.getQuizId());

                    return ps;
                },
                    keyHolder);
        return  keyHolder.getKey().longValue();
    }


//        Long id =(long) jdbcTemplate.update(
//                ,
//                question.getTime(),
//                question.getText(),
//                question.getMax_points(),
//                question.getImage(),
//                question.getType().getId(),
//                question.getQuiz_id()
//        );
//        return id;
//    }

    @Override
    public void update(Question question) {
        jdbcTemplate.update(
                "UPDATE public.question SET time =?, text=?, max_points=?, image=?, type_id=?, quiz_id=? WHERE id = ?",
                question.getTime(),
                question.getText(),
                question.getMaxPoints(),
                question.getImage(),
                question.getType().getId(),
                question.getQuizId(),
                question.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "DELETE from question where id = ?",
                id
        );
    }
}
