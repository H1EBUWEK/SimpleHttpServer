package org.klimenko.clientserverapps.serverbackend.mappers;

import org.klimenko.clientserverapps.serverbackend.models.ProductWrapper;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductWrapperMapper implements RowMapper<ProductWrapper> {
    public static final String BASE_SQL = "Select p.ID, p.Type, p.Info, p.Price from product p";

    @Override
    public ProductWrapper mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProductWrapper(rs.getInt("ID"), rs.getString("Type"), rs.getString("Info"),
                rs.getString("Price"));
    }

}
