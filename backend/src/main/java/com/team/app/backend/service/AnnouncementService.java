package com.team.app.backend.service;

import com.team.app.backend.persistance.model.Announcement;

import java.util.List;

public interface AnnouncementService {

    void createAnnouncement(Announcement announcement);
    void updateAnnouncement(Announcement announcement);
    void deleteAnnouncement(Long id);
    List<Announcement> getAll(Long userId);
    List<Announcement> getCreated();
    void approve(Announcement announcement);
}
