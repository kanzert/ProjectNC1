package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.NotificationDao;
import com.team.app.backend.persistance.dao.mappers.NotificationRowMapper;
import com.team.app.backend.persistance.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NotificationDaoImpl implements NotificationDao {


    private JdbcTemplate jdbcTemplate;

    private final Environment env;

    private NotificationRowMapper notificationRowMapper = new NotificationRowMapper();

    @Autowired
    public NotificationDaoImpl(DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
    }

    @Override
    public void create(Notification not) {

        jdbcTemplate.update(env.getProperty("create.notification"),
                not.getText(),
                not.isSeen(),
                not.getDate(),
                not.getCategoryId(),
                not.getUserId());
    }
    @Override
    public void update(Notification not) {
        jdbcTemplate.update(
                env.getProperty("update.notification"),
                not.getText(),
                not.isSeen(),
                not.getDate(),
                not.getCategoryId(),
                not.getUserId(),
                not.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                env.getProperty("delete.notification"),
                id
        );
    }
    @Override
    public void setSetting(Notification not) {
        jdbcTemplate.update(env.getProperty("set.setting.notification"),
                not.getCategoryId(),
                not.getUserId(),
                not.isSeen(),
                not.isSeen());
    }
    public List<Notification> getSetting(Long userId) {
        return jdbcTemplate.query(
                env.getProperty("get.notification.settings"),
              new Object[]{userId}
                ,(resultSet, i) -> {
                    Notification not = new Notification();
                    not.setCategoryId(resultSet.getLong("cat_id"));
                    not.setUserId(resultSet.getLong("user_id"));
                    not.setSeen(resultSet.getBoolean("enabled"));
                    return not;
                });

    }

    @Override
    public List<Notification> getAll (Long user_id) {

        return jdbcTemplate.query(
                env.getProperty("get.all.notification"),
                new Object[]{user_id,user_id}
                ,notificationRowMapper);
    }
}
