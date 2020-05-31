package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.UserActivity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserActivityRowMapper implements RowMapper<UserActivity> {

    @Override
    public UserActivity mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new UserActivity(
                resultSet.getLong("id"),
                resultSet.getString("text"),
                resultSet.getTimestamp("date"),
                resultSet.getLong("cat_id"),
                resultSet.getLong("user_id"),
                resultSet.getLong("elem_id"),
                resultSet.getString("username"),
                resultSet.getString("elem_name"),
                resultSet.getString("image"))        ;
    }
}
