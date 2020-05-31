package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.UserToSession;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserToSessionRowMapper implements RowMapper<UserToSession> {

    @Override
    public UserToSession mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new UserToSession(resultSet.getLong("id"),
        resultSet.getLong("ses_id"),
        resultSet.getLong("user_id"),
        resultSet.getInt("score"),
        resultSet.getInt("time"));
    }
}
