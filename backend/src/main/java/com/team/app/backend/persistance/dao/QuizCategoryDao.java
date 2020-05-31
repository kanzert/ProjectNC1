package com.team.app.backend.persistance.dao;

import com.team.app.backend.persistance.model.QuizCategory;

import java.util.List;

public interface QuizCategoryDao {
    List<QuizCategory> getAll();
    void addQuizToCategory(Long quiz_id,Long cat_id);
}
