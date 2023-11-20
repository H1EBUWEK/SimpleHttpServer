package org.klimenko.clientserverapps.serverbackend.sdfsdf;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DesignComponentWrapperMapper implements RowMapper<DesignComponentWrapper> {
    public static final String BASE_SQL = "Select c.ID, c.URL, c.DESCRIPTION, from design";

    @Override
    public DesignComponentWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DesignComponentWrapper(rs.getInt("ID"), rs.getString("URL"), rs.getString("Description"));
    }
}