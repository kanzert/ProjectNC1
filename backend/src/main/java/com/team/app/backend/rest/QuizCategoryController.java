package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.QuizCategory;
import com.team.app.backend.service.QuizCategoryService;
import com.team.app.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api")
public class QuizCategoryController {

    private final QuizCategoryService quizCategoryService;

    public QuizCategoryController(QuizCategoryService quizCategoryService) {
        this.quizCategoryService = quizCategoryService;
    }

    @GetMapping("/categories")
    public List<QuizCategory> getQuestion() {
        return quizCategoryService.getAllQuizCategories();
    }
}
