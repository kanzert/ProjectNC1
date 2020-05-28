package com.team.app.backend.rest;

import com.team.app.backend.dto.FinishedQuizDto;
import com.team.app.backend.persistance.model.*;
import com.team.app.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/quiz/")
public class PlayQuizController {

    @Autowired
    SessionService sessionService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserToSessionService userToSessionService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private SecurityService securityService;

    private final SimpMessagingTemplate template;

    @Autowired
    PlayQuizController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/start/game")
    public void sendMessage(Long sesId){
        this.template.convertAndSend("/start/"+sesId,  "true");
    }


    @GetMapping("play/{user_id}/{quiz_id}")
    public ResponseEntity playQuiz(
            @PathVariable("user_id") long user_id,
            @PathVariable("quiz_id") long quiz_id) {

        Quiz quiz = quizService.getQuiz(quiz_id);
        Session session = sessionService.newSessionForQuiz(quiz);

        sessionService.updateSession(session);
        User user = userService.getUserById(user_id);
        userToSessionService.createNewUserToSession(user, session);

        return ResponseEntity.ok(session);
    }


    @PostMapping("start/{ses_id}")
    public ResponseEntity startSession(@PathVariable("ses_id") long ses_id){
        sessionService.setSesionStatus(ses_id,new SessionStatus(3L,"started"));
        return ResponseEntity.ok("");
    }


    @GetMapping("access_code/{ses_id}")
    public ResponseEntity getAccessCode(
            @PathVariable("ses_id") long ses_id
    ) {
        Session session = sessionService.getSessionById(ses_id);
        return ResponseEntity.ok(session.getAccessCode());
    }

    @GetMapping("join")
    public ResponseEntity getAccessCode(
            @RequestParam("access_code") String accessCode
    ) {
        Long user_id = securityService.getCurrentUser().getId();
        Session session = sessionService.getSessionByAccessCode(accessCode);
        System.out.println(session);
        if(session != null){
            System.out.println("not null ses");
            User user = userService.getUserById(user_id);
            userToSessionService.createNewUserToSession(user, session);
            return ResponseEntity.ok(session);
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(messageSource.getMessage("session.start", null, LocaleContextHolder.getLocale()));
        }

    }

    @GetMapping("stats/{ses_id}")
    public ResponseEntity calculateResults(
            @PathVariable("ses_id") long ses_id
    ) {

        Map response = new HashMap();

        List<UserToSession> userToSessionList = new ArrayList<>(userToSessionService.getAllBySessionId(ses_id));

        userToSessionList.forEach(
                uts -> {response.put("username",userService.getUserById(uts.getUser_id()).getUsername());
                    response.put("score",uts.getScore());
                    response.put("time",uts.getTime()); }
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("topstats/{quiz_id}")
    public ResponseEntity topStats(@PathVariable("quiz_id") long quiz_id) {
        return ResponseEntity.ok(quizService.getTopStats(quiz_id));
    }

    @PostMapping("finish")
    public ResponseEntity finishQuiz(@RequestBody FinishedQuizDto finishedQuizDto) {
        System.out.println(finishedQuizDto.getUser_id()+" "+finishedQuizDto.getSes_id()+" "+finishedQuizDto.getTime()+" "+finishedQuizDto.getScore());
        sessionService.setSesionStatus(finishedQuizDto.getSes_id(),new SessionStatus(2L,"ended"));
        userToSessionService.insertScore(finishedQuizDto);
        return ResponseEntity.ok(messageSource.getMessage("result.ok", null, LocaleContextHolder.getLocale()));
    }

}
