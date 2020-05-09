package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.DefaultQuest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DefOptionRowMapper implements RowMapper<DefaultQuest> {

    @Override
    public DefaultQuest mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        DefaultQuest defaultQuest = new DefaultQuest();
        defaultQuest.setId((long) resultSet.getInt("id"));
        defaultQuest.setAnswer( resultSet.getString("answer"));
        defaultQuest.setImage(resultSet.getBytes("image"));
        defaultQuest.setQuestionId((long) resultSet.getInt("quest_id"));
        return defaultQuest;
    }
}
