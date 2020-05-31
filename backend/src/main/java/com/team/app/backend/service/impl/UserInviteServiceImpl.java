package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.NotificationDao;
import com.team.app.backend.persistance.dao.UserInviteDao;
import com.team.app.backend.persistance.model.Notification;
import com.team.app.backend.persistance.model.UserInvite;
import com.team.app.backend.service.UserInviteService;
import com.team.app.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserInviteServiceImpl implements UserInviteService {

    private final long NOTIFICATION_INVITE = 3L;

    private final UserInviteDao userInviteDao;

    private final NotificationDao notificationDao;

    private final MessageSource messageSource;

    private final UserService userService;

    @Autowired
    public UserInviteServiceImpl(UserInviteDao userInviteDao, NotificationDao notificationDao, MessageSource messageSource, UserService userService) {
        this.userInviteDao = userInviteDao;
        this.notificationDao = notificationDao;
        this.messageSource = messageSource;
        this.userService = userService;
    }

    @Override
    public void sendUserInvite(UserInvite userInvite) {
        Notification notification = new Notification();
        notification.setCategoryId(NOTIFICATION_INVITE);
        notification.setUserId(userInvite.getUserIdTo());
        String[] params = new String[]{userInvite.getUsernameFrom()};
        notification.setText(messageSource.getMessage("friend.invitation", params,
                userService.getUserLanguage(userInvite.getUserIdTo())));
        userInvite.setActivated(false);
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        userInvite.setInviteDate(date);
        userInviteDao.send(userInvite);
        notificationDao.create(notification);
    }

    @Override
    public void acceptUserInvite(Long id) {
        userInviteDao.accept(id);
    }

    @Override
    public List<UserInvite> getUserInvite(Long userId) {
        return userInviteDao.getUserInvite(userId);
    }


    @Override
    public void declineUserInvite(Long id) {
        userInviteDao.decline(id);
    }

    @Override
    public List<UserInvite> getFriendsList(Long userId) {
        return userInviteDao.getFriendsList(userId);
    }

    @Override
    public void deleteUserFromList(Long userId, Long deleteId) {
        userInviteDao.deleteFriendFromList(userId, deleteId);
    }
}
