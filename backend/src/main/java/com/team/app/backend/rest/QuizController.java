package com.team.app.backend.rest;

import com.team.app.backend.dto.QuizAddDto;
import com.team.app.backend.dto.QuestionDefAddDto;
import com.team.app.backend.dto.QuestionOptAddDto;
import com.team.app.backend.dto.QuestionSeqAddDto;
import com.team.app.backend.persistance.model.Question;
import com.team.app.backend.persistance.model.Quiz;
import com.team.app.backend.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;



@RestController
@RequestMapping("api")
public class QuizController {

    @Autowired
    QuizService quizService;


    @PostMapping("/quiz")
    public HashMap<String,Long> createMewQuiz(
            @RequestBody QuizAddDto quizDto) {
        System.out.println(quizDto.getUserId());
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


//    @GetMapping("/quiz/quest/{id}")
//    public List<Question> questions(@PathVariable("id") long id) {
//        return quizService.getQuizQuestion(id);
//    }

    @GetMapping("/questions/{quiz_id}")
    public List<Question> questions(@PathVariable("quiz_id") long id) {
        return quizService.getQuizQuestion(id);
    }


    @GetMapping("/quiz/{id}")
    public Quiz quiz(@PathVariable("id") long id) {
        //System.out.println(id);
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
    @GetMapping("quiz/category/{category}")
    public List<Quiz> categoryQuizes(@PathVariable("category") String category) {
        return quizService.getCategoryQuizes(category);
    }


    @GetMapping("/quiz/user/{id}")    public List<Quiz> userQuizes(@PathVariable("id") long id) {
        return quizService.getUserQuizes(id);
    }

    @GetMapping("/quiz/search/{category}/{searchstring}")
    public List<Quiz> searchQuizes(@PathVariable("category") String category,@PathVariable("searchstring") String searchstring) {
        System.out.println(category+" "+searchstring);
        return quizService.searchQuizes(category,searchstring);
    }
}
