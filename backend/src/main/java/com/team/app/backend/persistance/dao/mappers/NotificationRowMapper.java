package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.Notification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NotificationRowMapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new Notification(
                resultSet.getLong("id"),
                resultSet.getString("text"),
                resultSet.getBoolean("seen"),
                resultSet.getTimestamp("date"),
                resultSet.getLong("cat_id"),
                resultSet.getLong("user_id"));
    }
}
