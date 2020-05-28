package com.team.app.backend.rest;

import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.service.NotificationService;
import com.team.app.backend.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @Autowired
    private SecurityService securityService;

    @MessageMapping("/delete/notifications")
    public void delete(List<Notification> notifications) {
        notificationService.delete(notifications);
    }

    @MessageMapping("/get/notifications")
    public void getAll(StompHeaderAccessor stompHeaderAccessor){
        Long userId = securityService.getCurrentUser().getId();
        notificationService.add(stompHeaderAccessor.getSessionId(), userId);
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Notification not) {
        try {
            notificationService.create(not);
        }
        catch (DataAccessException sqlEx)
        {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok().build();

    }
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Notification not) {
        try {
            notificationService.update(not);
        }
        catch (DataAccessException sqlEx)
        {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok().build();

    }



    @GetMapping("/settings/get/")
    public  ResponseEntity getSetting() {
        Long userId = securityService.getCurrentUser().getId();
        List<Notification> notifications = null;
        try {
            notifications = this.notificationService.getSetting(userId);
        }
        catch (DataAccessException sqlEx)
        {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/settings")
    public ResponseEntity setSetting(@RequestBody Notification not) {
        try {
            this.notificationService.setSetting(not);
        }
        catch (DataAccessException sqlEx)
        {
            ResponseEntity.badRequest();
        }
        return ResponseEntity.ok().build();

    }


}
