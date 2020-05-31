package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.dto.SessionStatsDto;
import com.team.app.backend.persistance.dao.QuizDao;
import com.team.app.backend.persistance.dao.mappers.QuizRowMapper;
import com.team.app.backend.persistance.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.core.env.Environment;


import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;


@Repository
public class QuizDaoImpl implements QuizDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private Environment env;

    @Autowired
    private QuizRowMapper quizRowMapper;

    public QuizDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Quiz get(Long id) {
        return jdbcTemplate.queryForObject(
                env.getProperty("get.quiz"),
                new Object[]{id},
                quizRowMapper
        );
    }



    @Override
    public List<Quiz> getByUserId(Long id) {
        return jdbcTemplate.query(
                env.getProperty("get.quiz.by.userId"),
                new Object[] { id },
                quizRowMapper);
    }

    @Override
    public List<Quiz> getApproved() {
        return jdbcTemplate.query(
                env.getProperty("get.approved.quizes"),
                quizRowMapper);
    }


    @Override
    public List<Quiz> getApprovedForUser(Long user_id) {
        return jdbcTemplate.query(
                env.getProperty("get.approved.user.quizes"),
                new Object[] { user_id,user_id }
                ,quizRowMapper);
    }

    @Override
    public List<Quiz> getFavoriteQuizes(Long user_id) {
        return jdbcTemplate.query(
                env.getProperty("get.favourite.quizes"),
                new Object[] { user_id,user_id }
                ,quizRowMapper);
    }

    @Override
    public List<Quiz> getCompletedQuizes(Long user_id) {
        return jdbcTemplate.query(
                env.getProperty("get.completed.quizes"),
                new Object[] { user_id,user_id }
                ,quizRowMapper);
    }

    @Override
    public List<Quiz> getSuggestion(Long user_id) {
        return jdbcTemplate.query(env.getProperty("get.suggestions")
                ,new Object[] { user_id,user_id,user_id,user_id,user_id,user_id,user_id },
                quizRowMapper);
    }

    // to do
    @Override
    public List<Quiz> getCategoryQuizes(String category) {
        return jdbcTemplate.query(env.getProperty("get.category.quizes"),
                new Object[] { category },
                quizRowMapper);
    }

    @Override
    public List<Quiz> searchQuizes(String[] categories, String searchstring, String dateFrom, String dateTo, String user) {
		String sqlQuizSearch = env.getProperty("search.quiz");
		String search = "%"+searchstring+"%";
		String[] searchCategories = {"geography", "programming", "math", "history", "modern"};
		String searchUsername = "%"+user+"%";
        if (categories.length != 0) {
			for (int i = 0; i < categories.length; i++) {
				searchCategories[i] = categories[i];
			}
			for (int j = categories.length; j < 5; j++) {
				searchCategories[j] = "";
			}
		}
        return jdbcTemplate.query(sqlQuizSearch, new Object[] {search, search, searchCategories[0], searchCategories[1], searchCategories[2], searchCategories[3], searchCategories[4], dateFrom, dateTo, searchUsername, searchUsername, searchUsername},
                quizRowMapper);
    }
	
	@Override
    public List<Quiz> searchQuizes(String searchstring) {
        String search="%"+searchstring+"%";
        System.out.println(search);
        return jdbcTemplate.query("select Q.id,Q.title,Q.date,Q.description,Q.image,Q.status_id, Q.user_id from quiz Q where Q.title LIKE ? "
                ,new Object[] {search},
                quizRowMapper);
    }

    @Override
    public List<Quiz> getAll() {
        return jdbcTemplate.query(env.getProperty("get.quizes"),
                quizRowMapper);
    }



    @Override
    public Long save(Quiz quiz) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            env.getProperty("create.quiz"),
                            new String[] {"id"});
                    ps.setString(1, quiz.getTitle());
                    ps.setDate(2,  quiz.getDate());
                    ps.setString(3, quiz.getDescription());
                    ps.setString(4, quiz.getImage());
                    ps.setLong(5,quiz.getStatus().getId());
                    System.out.println( quiz.getUser_id());
                    ps.setLong(6, quiz.getUser_id());

                    return ps;
                },
                keyHolder);
        return  keyHolder.getKey().longValue();
    }

    @Override
    public void update(Quiz quiz) {
        jdbcTemplate.update(
                env.getProperty("update.quiz"),
                quiz.getTitle(),
                quiz.getDate(),
                quiz.getDescription(),
                quiz.getImage(),
                quiz.getStatus().getId(),
                quiz.getUser_id(),
                quiz.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(env.getProperty("delete.quiz"),id);
    }
    @Override
    public void approve(Long id) {
        jdbcTemplate.update(env.getProperty("approve.quiz"),id);
    }

    @Override
    public List<Quiz> getCreated() {
        return jdbcTemplate.query(
                env.getProperty("get.created.quizes"),
                quizRowMapper);
    }

    @Override
    public List<SessionStatsDto> getTopStats(Long quizId) {
        return jdbcTemplate.query(
                env.getProperty("get.top.stats"),
                new Object[]{quizId},
                (resultSet, i) -> {
                    SessionStatsDto sessionStatsDto = new SessionStatsDto();
                    sessionStatsDto.setPlace(resultSet.getInt("place"));
                    sessionStatsDto.setScore(resultSet.getInt("score"));
                    sessionStatsDto.setTime(resultSet.getInt("time"));
                    sessionStatsDto.setUsername(resultSet.getString("username"));
                    return sessionStatsDto;
                });


    }


}
