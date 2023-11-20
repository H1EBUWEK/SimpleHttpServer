package org.klimenko.clientserverapps.serverbackend.dao;

import org.klimenko.clientserverapps.serverbackend.models.Product;
import org.klimenko.clientserverapps.serverbackend.models.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.klimenko.clientserverapps.serverbackend.mappers.ProductMapper;
import org.klimenko.clientserverapps.serverbackend.mappers.ProductWrapperMapper;

import javax.sql.DataSource;
import java.util.List;


@Repository
@Transactional
public class ProductDAO extends JdbcDaoSupport {

    @Autowired
    public ProductDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Product getPet(int id) {
        String sql = ProductMapper.BASE_SQL + " where p.id = ?";

        Object[] params = new Object[] { id };
        ProductMapper mapper = new ProductMapper();
        try {
            Product product = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return product;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void buyPet(int id, int newCount)
    {
        String sql = "UPDATE pet SET Count = " + newCount + " WHERE ID = " + id;
        this.getJdbcTemplate().update(sql);
    }

    public List<ProductWrapper> getPets()
    {
        String sql = ProductWrapperMapper.BASE_SQL;
        ProductWrapperMapper mapper = new ProductWrapperMapper();
        try {
            List<ProductWrapper> list = this.getJdbcTemplate().query(sql, mapper);
            return list;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
