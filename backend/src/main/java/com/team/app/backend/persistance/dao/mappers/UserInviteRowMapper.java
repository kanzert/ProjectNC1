package com.team.app.backend.persistance.dao.mappers;

import com.team.app.backend.persistance.model.UserInvite;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserInviteRowMapper implements RowMapper<UserInvite> {
    @Override
    public UserInvite mapRow(ResultSet resultSet, int rownumber) throws SQLException {
        return new UserInvite(
                resultSet.getLong("id"),
                resultSet.getBoolean("activated"),
                resultSet.getString("invite_text"),
                resultSet.getDate("invite_date"),
                resultSet.getLong("user_id_from"),
                resultSet.getLong("user_id_to"),
                resultSet.getString("username"),
                resultSet.getString("image"));
    }
}
