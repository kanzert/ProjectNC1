package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.AnnouncementDao;
import com.team.app.backend.persistance.dao.NotificationDao;
import com.team.app.backend.persistance.dao.UserActivityDao;
import com.team.app.backend.persistance.dao.UserDao;
import com.team.app.backend.persistance.model.Announcement;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.persistance.model.UserActivity;
import com.team.app.backend.service.AnnouncementService;
import com.team.app.backend.service.NotificationService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.Date;
import java.util.List;


@Service

public class AnnouncemrntServiceImpl implements AnnouncementService {

    private final long ANNOUNCEMENT_CREATE = 1L;

    private final long ANNOUNCEMENT_APPROVED = 2L;

    private final long NOTIFICATION_ANNOUNCEMENT = 1L;

    private final long USER_ANNOUNCEMENT_ACTIVITY = 4L;

    private final AnnouncementDao announcementDao;

    private final UserActivityDao userActivityDao;

    private final NotificationService notificationService;

    private final UserService userService;

    private final MessageSource messageSource;

    @Autowired
    public AnnouncemrntServiceImpl(AnnouncementDao announcementDao, UserActivityDao userActivityDao, UserDao userDao, NotificationService notificationService, UserService userService, MessageSource messageSource) {
        this.announcementDao = announcementDao;
        this.userActivityDao = userActivityDao;
        this.notificationService = notificationService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @Transactional
    public void createAnnouncement(Announcement announcement) {

        announcement.setCategoryId(ANNOUNCEMENT_CREATE);
        long millis=System.currentTimeMillis();
        java.sql.Timestamp date = new java.sql.Timestamp(millis);
        announcement.setDate(date);

        UserActivity userActivity=new UserActivity();
        userActivity.setCategoryId(USER_ANNOUNCEMENT_ACTIVITY);
        userActivity.setDate(date);
        userActivity.setUserId(announcement.getUserId());
        userActivity.setElem_id(announcement.getId());

        userActivityDao.create(userActivity);
        announcementDao.create(announcement);
    }

    @Override
    public List<Announcement> getCreated() {
        return announcementDao.getCreated();
    }


    @Override
    public List<Announcement> getAll(Long userId) {
        return announcementDao.getAll(userId);
    }

    @Override
    public void approve(Announcement announcement) {
        Notification notification = new Notification();
        notification.setCategoryId(NOTIFICATION_ANNOUNCEMENT);
        notification.setUserId(announcement.getUserId());
        String[] params = new String[]{announcement.getTitle()};
        if(announcement.getStatusId() == ANNOUNCEMENT_APPROVED) {
            announcementDao.approve(announcement.getId());
            notification.setText(messageSource.
                    getMessage("announcement.approved", params,
                            userService.getUserLanguage(announcement.getUserId())));
        } else {
            notification.setText(
                    messageSource.
                    getMessage("announcement.not.approved", params,
                            userService.getUserLanguage(announcement.getUserId()))
            );
            announcementDao.delete(announcement.getId());
        }
        notificationService.create(notification);
    }

    public Announcement  getAnnouncement(Long id) {
       return new Announcement();
    }
    @Transactional
    public void updateAnnouncement(Announcement announcement) {
        announcementDao.update(announcement);
    }
    @Transactional
    public void deleteAnnouncement(Long id) {
        announcementDao.delete(id);
    }

}
