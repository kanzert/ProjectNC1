package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.Session;
import com.team.app.backend.persistance.model.SessionStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SessionRowMapper implements RowMapper<Session> {

    @Override
    public Session mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new Session(
                resultSet.getLong("id"),
                resultSet.getLong("quiz_id"),
                resultSet.getString("access_code"),
                resultSet.getTimestamp("date"),
                new SessionStatus(resultSet.getLong("status_id"),resultSet.getString("status_name")));
    }
}