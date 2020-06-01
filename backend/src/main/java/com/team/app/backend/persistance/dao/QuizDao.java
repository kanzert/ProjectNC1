package com.team.app.backend.persistance.dao;

import com.team.app.backend.dto.SessionStatsDto;
import com.team.app.backend.persistance.model.Quiz;

import java.util.List;

public interface QuizDao {
    Quiz get(Long id);

    List<Quiz> getByUserId(Long id);

    List<Quiz> getApproved();

    List<Quiz> getApprovedForUser(Long user_id);

    List<Quiz> getFavoriteQuizes(Long user_id);

    List<Quiz> getCompletedQuizes(Long user_id);

    List<Quiz> getSuggestion(Long user_id);

    List<Quiz> getCategoryQuizes(String category);

    List<Quiz> searchQuizes(String[] categories, String searchstring, String dateFrom, String dateTo, String user);

    List<Quiz> getAll();

    Long save(Quiz quiz);

    void update(Quiz quiz);

    void delete(Long id);

    void approve(Long id,Long status);

    List<Quiz> getCreated();

    Long getUserIdByQuiz(Long quizId);

    String getTitle(Long quizId);

    List<SessionStatsDto>getTopStats(Long quizId);

}
