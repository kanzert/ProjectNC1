package com.team.app.backend.service;

import com.team.app.backend.persistance.model.Notification;

import java.util.List;

public interface NotificationService {
    void create(Notification not);
    void update(Notification not);
    void delete(List<Notification> not);
    List<Notification> getAll (Long user_id);
    void setSetting(Notification not);
    List<Notification> getSetting(Long userId);
    void add(String sessionId, Long userId);
    void remove(String userId);
    void dispatch(String sessionId);
}
