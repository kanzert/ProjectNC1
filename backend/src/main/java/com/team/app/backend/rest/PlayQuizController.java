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

    private final SessionService sessionService;

    private final QuizService quizService;

    private final UserService userService;

    private final UserToSessionService userToSessionService;

    private final MessageSource messageSource;

    private final SimpMessagingTemplate template;

    @Autowired
    PlayQuizController(SimpMessagingTemplate template, SessionService sessionService, QuizService quizService, UserService userService, UserToSessionService userToSessionService, MessageSource messageSource){
        this.template = template;
        this.sessionService = sessionService;
        this.quizService = quizService;
        this.userService = userService;
        this.userToSessionService = userToSessionService;
        this.messageSource = messageSource;
    }

    @MessageMapping("/start/game")
    public void sendStart(Long sesId){
        this.template.convertAndSend("/start/"+sesId,  "true");
    }

    @MessageMapping("/finish/game")
    public void sendStats(FinishedQuizDto finishedQuizDto){
        Long sesId=finishedQuizDto.getSes_id();
        sessionService.setSessionStatus(sesId,new SessionStatus(2L,"ended"));
        userToSessionService.insertScore(finishedQuizDto);
        this.template.convertAndSend("/finish/"+sesId,  userToSessionService.getStats(sesId));
    }


    @GetMapping("play/{user_id}/{quiz_id}")
    public ResponseEntity playQuiz(
            @PathVariable("user_id") long user_id,
            @PathVariable("quiz_id") long quiz_id) {
        Long ses_id = sessionService.newSessionForQuiz(quiz_id);
        userToSessionService.createNewUserToSession(user_id, ses_id);
        return ResponseEntity.ok(sessionService.getSessionById(ses_id));
    }


    @PostMapping("start/{ses_id}")
    public ResponseEntity startSession(
            @PathVariable("ses_id") long ses_id){
        sessionService.setSessionStatus(ses_id,new SessionStatus(3L,"started"));
        return ResponseEntity.ok("");
    }


    @GetMapping("access_code/{ses_id}")
    public ResponseEntity getAccessCode(@PathVariable("ses_id") long ses_id) {
        Session session = sessionService.getSessionById(ses_id);
        return ResponseEntity.ok(session.getAccessCode());

    }

    @GetMapping("join")
    public ResponseEntity getAccessCode(
            @RequestParam("user_id") Long user_id,
            @RequestParam("access_code") String accessCode
    ) {
        Session session = sessionService.getSessionByAccessCode(accessCode);
        if(session != null){
            userToSessionService.createNewUserToSession(user_id, session.getId());
            return ResponseEntity.ok(session);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(messageSource.getMessage("session.start", null, LocaleContextHolder.getLocale()));
        }

    }

    @GetMapping("topstats/{quiz_id}")
    public ResponseEntity topStats(@PathVariable("quiz_id") long quiz_id) {
        return ResponseEntity.ok(quizService.getTopStats(quiz_id));
    }

    @PostMapping("finish")
    public ResponseEntity finishQuiz(@RequestBody FinishedQuizDto finishedQuizDto) {
        sessionService.setSessionStatus(finishedQuizDto.getSes_id(),new SessionStatus(2L,"ended"));
        userToSessionService.insertScore(finishedQuizDto);
        return ResponseEntity.ok(messageSource.getMessage("result.ok", null, LocaleContextHolder.getLocale()));
    }

}
