package org.klimenko.clientserverapps.serverbackend.mappers;

import org.klimenko.clientserverapps.serverbackend.enums.ItemType;
import org.klimenko.clientserverapps.serverbackend.models.Product;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductMapper implements RowMapper<Product> {
    public static final String BASE_SQL = "Select p.ID, p.Count, p.Type, p.Info, p.AInfo, p.Price from product p";

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(rs.getInt("ID"), rs.getInt("Count"), rs.getString("Type"), rs.getString("Info"),
                rs.getString("AInfo"), rs.getString("Price"), ItemType.TypeProduct);
    }
}
