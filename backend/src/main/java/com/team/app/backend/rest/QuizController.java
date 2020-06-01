package com.team.app.backend.rest;

import com.team.app.backend.dto.QuizAddDto;
import com.team.app.backend.dto.QuizCategoryDto;
import com.team.app.backend.dto.QuestionDefAddDto;
import com.team.app.backend.dto.QuestionOptAddDto;
import com.team.app.backend.dto.QuestionSeqAddDto;
import com.team.app.backend.persistance.model.Question;
import com.team.app.backend.persistance.model.Quiz;
import com.team.app.backend.service.QuizService;
import com.team.app.backend.service.UserQuizFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping("api")
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    UserQuizFavoriteService userQuizFavoriteService;

    @PostMapping("/quiz")
    public HashMap<String,Long> createMewQuiz(
            @RequestBody QuizAddDto quizDto) {
        System.out.println(quizDto.getUser_id());
        HashMap<String,Long>result = new HashMap<String,Long>();
        result.put("id",quizService.addQuiz(quizDto));
        return result;
    }

    @DeleteMapping("/quiz/{id}")
    public void deleteQuiz(@PathVariable("id") long id) {
        quizService.deleteQuiz(id);
    }

    @GetMapping("/question/{id}")
    public Question getQuestion(@PathVariable("id") long id) {
        return quizService.getQuestion(id);
    }


 //   @PostMapping("/question/1")
    @RequestMapping(value={ "/question/1", "/question/2" },method = { RequestMethod.POST })
    public HashMap<String,Long>  createNewQuestion(@RequestBody QuestionDefAddDto questionDefAddDto) {
        System.out.println("add question1");
        HashMap<String,Long>result = new HashMap<String,Long>();
        result.put("id",quizService.addDefQuestion(questionDefAddDto));
        return result;
    }

    @PostMapping("/question/3")
    public HashMap<String,Long> createNewQuestion(@RequestBody QuestionOptAddDto questionOptAddDto) {
        System.out.println("add question3");
        HashMap<String,Long>result = new HashMap<String,Long>();
        result.put("id",quizService.addOptQuestion(questionOptAddDto));
        return result;
    }

    @PostMapping("/question/4")
    public HashMap<String,Long> createNewQuestion(@RequestBody QuestionSeqAddDto questionSeqAddDto) {
        System.out.println("add question4");
        HashMap<String,Long>result = new HashMap<String,Long>();
        result.put("id",quizService.addSeqOptQuestion(questionSeqAddDto));
        return result;
    }

    @DeleteMapping("/question/{id}")
    public void deleteQuestion(@PathVariable("id") long id) {
        quizService.deleteQuestion(id);
    }

    @GetMapping("/questions/{quiz_id}")
    public List<Question> questions(@PathVariable("quiz_id") long id) {
        return quizService.getQuizQuestion(id);
    }


    @GetMapping("/quiz/{id}")
    public Quiz quiz(@PathVariable("id") long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping("/quiz")
    public List<Quiz> quizes() {
        return quizService.getAllQuizes();
    }

    //
    @GetMapping("quiz/approved")
    public List<Quiz> approvedQuizes() {
        return quizService.getApprovedQuizes();
    }

    @GetMapping("quiz/approved/{user_id}")
    public List<Quiz> getApprovedUserQuizes(@PathVariable("user_id") long user_id) {
        return quizService.getApprovedUserQuizes(user_id);
    }
    
    @GetMapping("quiz/favorite/{user_id}")
    public List<Quiz> getFavoriteQuizes(@PathVariable("user_id") long user_id) {
        return quizService.getUserFavoritesQuizes(user_id);
    }
    @GetMapping("quiz/completed/{user_id}")
    public List<Quiz> getCompleted(@PathVariable("user_id") long user_id) {
        return quizService.getCompletedQuizes(user_id);
    }

    @GetMapping("quiz/suggestion/{user_id}")
    public List<Quiz> getSuggestionQuizes(@PathVariable("user_id") long user_id) {
        return quizService.getSuggestion(user_id);
    }

    @GetMapping("quiz/category/{category}")
    public List<Quiz> categoryQuizes(@PathVariable("category") String category) {
        return quizService.getCategoryQuizes(category);
    }


    @PostMapping("quiz/favorite/{quiz_id}/{user_id}")
    public void addFavorite(@PathVariable("quiz_id") long quiz_id,@PathVariable("user_id") long user_id){
        userQuizFavoriteService.addFavorite(user_id,quiz_id);
    }

    @DeleteMapping("quiz/favorite/{quiz_id}/{user_id}")
    public void deleteFavorite(@PathVariable("quiz_id") long quiz_id,@PathVariable("user_id") long user_id){
        userQuizFavoriteService.deleteFavorite(user_id,quiz_id);
    }

    @GetMapping("/quiz/user/{id}")
    public List<Quiz> userQuizes(@PathVariable("id") long id) {
        return quizService.getUserQuizes(id);
    }

    @PostMapping("/quiz/search")
    public List<Quiz> searchQuizes(@RequestBody QuizCategoryDto quizCategoryDto) {
        System.out.println(quizCategoryDto.getTitle());
        return quizService.searchQuizes(quizCategoryDto.getCategories(),quizCategoryDto.getTitle(),quizCategoryDto.getDateFrom(),quizCategoryDto.getDateTo(),quizCategoryDto.getUser());
    }

	@GetMapping("/quiz/search/{searchstring}")
    public List<Quiz> searchQuizes(@PathVariable("searchstring") String searchstring) {
        return quizService.searchQuizes(searchstring);
    }

    @PostMapping("/quiz/approve")
    public ResponseEntity approveQuiz(@RequestBody Quiz quiz) {
            quizService.approveQuiz(quiz);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/quiz/created")
    public ResponseEntity getCreatedQuizs() {
        List<Quiz> quizzes;
        quizzes = quizService.getCreated();
        return ResponseEntity.ok().body(quizzes);
    }
}
