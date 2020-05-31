package com.team.app.backend.service;

import com.team.app.backend.persistance.model.QuizCategory;

import java.util.List;

public interface QuizCategoryService {
    List<QuizCategory> getAllQuizCategories();
}
