package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserActivityDao;
import com.team.app.backend.persistance.dao.mappers.UserActivityRowMapper;
import com.team.app.backend.persistance.dao.mappers.UserActivitySettingRowMapper;
import com.team.app.backend.persistance.model.Setting;
import com.team.app.backend.persistance.model.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserActivityDaoImpl implements UserActivityDao {

    private JdbcTemplate jdbcTemplate;

    private final Environment env;

    private UserActivityRowMapper userActivityRowMapper = new UserActivityRowMapper();
    private UserActivitySettingRowMapper userActivitySettingRowMapper = new UserActivitySettingRowMapper();

    @Autowired
    public UserActivityDaoImpl(DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
    }

    @Override
    public void create(UserActivity userActivity) {
        jdbcTemplate.update(
                env.getProperty("create.activity"),
                userActivity.getText(),
                userActivity.getDate(),
                userActivity.getCategoryId(),
                userActivity.getUserId(),
                userActivity.getElem_id()
        );
    }

    @Override
    public UserActivity get(Long id) {
        return jdbcTemplate.queryForObject(env.getProperty("get.activity"),
                new Object[]{id}
                ,userActivityRowMapper);
    }

    @Override
    public void update(UserActivity userActivity) {
        jdbcTemplate.update(
                env.getProperty("update.activity"),
                userActivity.getText(),
                userActivity.getDate(),
                userActivity.getCategoryId(),
                userActivity.getUserId(),
                userActivity.getElem_id(),
                userActivity.getId()
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                env.getProperty("delete.activity"),id
        );
    }

    @Override
    public List<UserActivity> getFriendsActivities(Long user_id) {
        return jdbcTemplate.query(env.getProperty("get.friends.activities"),
                new Object[]{user_id,user_id,user_id}
                ,userActivityRowMapper);
    }

    @Override
    public List<Setting> getActivitiesSettings(Long user_id) {
        return jdbcTemplate.query(env.getProperty("get.friends.activities.settings"),
                new Object[]{user_id}
                ,userActivitySettingRowMapper
        );
    }

    @Override
    public void setFriendActivitiesSetting(Setting setting) {
        jdbcTemplate.update(
                env.getProperty("set.friends.activities.setting"),
                setting.getUser_id(),
                setting.getCat_id(),
                setting.isEnabled(),
                setting.isEnabled());
    }


}
