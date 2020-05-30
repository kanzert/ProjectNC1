package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.NotificationDao;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;
    private final SimpMessagingTemplate template;

    private Map<String,Long> listeners = new HashMap<>();

    @Autowired
    public NotificationServiceImpl(NotificationDao notificationDao, SimpMessagingTemplate template) {
        this.template = template;
        this.notificationDao = notificationDao;
    }

    public void add(String sessionId, Long userId) {
        listeners.put(sessionId,userId);
    }

    public void remove(String sessionId) {
        listeners.remove(sessionId);
    }

    @Scheduled(fixedDelay = 2000)
    public void dispatch() {
        for (Map.Entry<String,Long> listener : listeners.entrySet()) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(listener.getKey());
            headerAccessor.setLeaveMutable(true);
            template.convertAndSendToUser(
                    listener.getKey(),
                    "/notification",
                    getAll(listener.getValue()),
                    headerAccessor.getMessageHeaders());
        }
    }

    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        remove(sessionId);
    }
    @Transactional
    @Override
    public void create(Notification not) {

        notificationDao.create(not);
    }
    @Transactional
    @Override
    public void update(Notification not) {
        notificationDao.update(not);

    }
    @Transactional
    @Override
    public void delete(List<Notification> notifications) {
        for(Notification not : notifications){
            notificationDao.delete(not.getId());
        }
    }

    @Override
    public List<Notification> getAll (Long user_id) {
        return notificationDao.getAll(user_id);
    }


    @Override
    public List<Notification> getSetting(Long userId) {
        return notificationDao.getSetting(userId);
    }

    @Transactional
    @Override
    public void setSetting(Notification not) {
        this.notificationDao.setSetting(not);
    }
}
