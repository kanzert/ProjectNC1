package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.Session;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionRowMapper implements RowMapper<Session> {
    @Override
    public Session mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Session(
                resultSet.getLong("id"),
                resultSet.getLong("quiz_id"),
                resultSet.getString("access_code"),
                resultSet.getDate("date")
        );
    }
}
