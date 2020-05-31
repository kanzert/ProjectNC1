package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.dto.SessionStatsDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SessionStatsRowMapper  implements RowMapper<SessionStatsDto> {
    @Override
    public SessionStatsDto mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new SessionStatsDto(
                resultSet.getInt("place"),
                resultSet.getString("username"),
                resultSet.getObject("score") != null ? resultSet.getInt("score") : null,
                resultSet.getObject("time") != null ? resultSet.getInt("time") : null
        );
    }
}
