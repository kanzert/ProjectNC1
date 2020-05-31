package com.team.app.backend.persistance.dao.impl;

import com.team.app.backend.persistance.dao.UserInviteDao;
import com.team.app.backend.persistance.dao.mappers.UserInviteRowMapper;
import com.team.app.backend.persistance.model.UserInvite;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UserInviteDaoImpl implements UserInviteDao {
    private final JdbcTemplate jdbcTemplate;
    private final Environment env;

    @Autowired
    public UserInviteDaoImpl(DataSource dataSource, Environment env) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.env = env;
    }

    @Override
    public void send(UserInvite userInvite) {
        @NonNull String sqlSend = env.getProperty("user.send");
        jdbcTemplate.update(
                sqlSend,
                userInvite.isActivated(),
                userInvite.getInviteText(),
                userInvite.getInviteDate(),
                userInvite.getUserIdFrom(),
                userInvite.getUserIdTo());
    }

    @Override
    public void accept(Long id) {
        @NonNull String sqlAccept = env.getProperty("user.accept");
        jdbcTemplate.update(sqlAccept, id);
    }

    @Override
    public void decline(Long id) {
        @NonNull String sqlDecline = env.getProperty("user.decline");
        jdbcTemplate.update(sqlDecline, id);
    }

    @Override
    public List<UserInvite> getUserInvite(Long userId) {
        @NonNull String sqlGetUserInvite = env.getProperty("user.getUserInvite");
        return jdbcTemplate.query(sqlGetUserInvite
                , new Object[] { userId },
                (resultSet, i) -> {
                    UserInvite userInvite = new UserInvite();
                    userInvite.setId(resultSet.getLong("id"));
                    userInvite.setInviteText(resultSet.getString("invite_text"));
                    userInvite.setUsernameFrom(resultSet.getString("username"));
                return userInvite;
        });
    }

    @Override
    public List<UserInvite> getFriendsList(Long userId) {
        @NonNull String sqlGetFriendsList = env.getProperty("user.getFriendsList");
        return jdbcTemplate.query(sqlGetFriendsList
                , new Object[] { userId, userId },
                (resultSet, i) -> {
                    UserInvite userInvite = new UserInvite();
                    userInvite.setUserIdFrom(resultSet.getLong("id"));
                    userInvite.setUsernameFrom(resultSet.getString("username"));
                    return userInvite;
                });
    }

    @Override
    public void deleteFriendFromList(Long userId, Long userIdDelete) {
        @NonNull String sqlDeleteFriendFromList = env.getProperty("user.deleteFriendFromList");
        jdbcTemplate.update(sqlDeleteFriendFromList, userId, userIdDelete, userId, userIdDelete);
    }
}
