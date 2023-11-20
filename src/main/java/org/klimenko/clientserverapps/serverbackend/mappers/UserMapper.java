package org.klimenko.clientserverapps.serverbackend.mappers;

import org.klimenko.clientserverapps.serverbackend.models.User;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public static final String BASE_SQL = "Select u.ID, u.Name, u.Balance, u.Hash, u.Tray from users u";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt("ID"), rs.getString("Name"), rs.getString("Balance"),
                rs.getString("Hash"), rs.getString("Tray"));
    }
}
