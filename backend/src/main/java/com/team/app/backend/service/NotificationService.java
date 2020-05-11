package com.team.app.backend.service;

import com.team.app.backend.persistance.model.Notification;

import java.util.List;

public interface NotificationService {
    void create(Notification not);
    void update(Notification not);
    void delete(List<Notification> not);
    List<Notification> getAll (Long user_id);
}
