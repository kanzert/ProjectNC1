package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.Option;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OptionRowMapper implements RowMapper<Option> {
    @Override
    public Option mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        Option option = new Option();
        option.setId((long) resultSet.getInt("id"));
        option.setIsCorrect(resultSet.getBoolean("is_correct"));
        option.setText(resultSet.getString("text"));
        option.setImage(resultSet.getBytes("image"));
        option.setQuestionId((long) resultSet.getInt("quest_id"));
        return option;
    }
}