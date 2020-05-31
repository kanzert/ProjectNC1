package com.team.app.backend.service.impl;

import com.team.app.backend.persistance.dao.AnnouncementDao;
import com.team.app.backend.persistance.dao.QuizDao;
import com.team.app.backend.persistance.dao.UserActivityDao;
import com.team.app.backend.persistance.model.Setting;
import com.team.app.backend.persistance.model.UserActivity;
import com.team.app.backend.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivityServiceImpl implements UserActivityService {

    private final
    UserActivityDao userActivityDao;

    private final
    AnnouncementDao announcementDao;

    @Autowired
    public UserActivityServiceImpl(UserActivityDao userActivityDao, AnnouncementDao announcementDao) {
        this.userActivityDao = userActivityDao;
        this.announcementDao = announcementDao;
    }

    @Override
    public void createUserActivity(UserActivity userActivity) {
        userActivityDao.create(userActivity);
    }

    @Override
    public UserActivity getUserActivity(Long id) {
        return userActivityDao.get(id);
    }

    @Override
    public void updateUserActivity(UserActivity userActivity) {
        userActivityDao.update(userActivity);
    }

    @Override
    public void deleteUserActivity(Long id) {
        userActivityDao.delete(id);
    }

    @Override
    public List<UserActivity>  getFriendsActivities(Long user_id) {
        List<UserActivity> userActivityList= userActivityDao.getFriendsActivities(user_id);
        for(UserActivity userActivity:userActivityList){
            if(userActivity.getCategoryId()==4)userActivity.setElem_name(announcementDao.get(userActivity.getElem_id()).getTitle());
        }
        return userActivityList;
    }

    @Override
    public List<Setting> getActivitiesSettings(Long user_id) {
        return userActivityDao.getActivitiesSettings(user_id);
    }

    @Override
    public void setFriendActivitiesSetting(Setting setting) {
        userActivityDao.setFriendActivitiesSetting(setting);
    }

}
