package org.klimenko.clientserverapps.serverbackend.dao;

import org.klimenko.clientserverapps.serverbackend.models.ProductTrayWrapper;
import org.klimenko.clientserverapps.serverbackend.models.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.klimenko.clientserverapps.serverbackend.exceptions.IDNotFoundException;
import org.klimenko.clientserverapps.serverbackend.mappers.ProductTrayWrapperMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class TrayDAO extends JdbcDaoSupport {

    public TrayDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public void createTray(User user) {
        this.getJdbcTemplate().execute("CREATE TABLE " + user.getTray() + " " +
                "(ID int PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "ProductID int NOT NULL, " +
                "Count int NOT NULL, " +
                "ItemType varchar(255) NOT NULL);");
    }

    public void emptyTray(User user) {
        this.getJdbcTemplate().execute("TRUNCATE table " + user.getTray());
    }

    public List<ProductTrayWrapper> getTrayItems(User user) {
        String sql = ProductTrayWrapperMapper.BASE_SQL + user.getTray();
        ProductTrayWrapperMapper mapper = new ProductTrayWrapperMapper();
        try {
            List<ProductTrayWrapper> list = this.getJdbcTemplate().query(sql, mapper);
            return list;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addToCart(User user, ProductTrayWrapper productTrayWrapper)
    {
        this.getJdbcTemplate().update("INSERT INTO " + user.getTray() + " (ProductID, Count, ItemType) " +
                "VALUES (?, ?, ?)", productTrayWrapper.getProductID(), productTrayWrapper.getCount(),
                productTrayWrapper.getItemType());
    }

    public boolean isInCart(User user, ProductTrayWrapper productTrayWrapper)
    {
        String sql = ProductTrayWrapperMapper.BASE_SQL + user.getTray() + " where ProductID = ? AND ItemType = ?";
        Object[] params = new Object[] { productTrayWrapper.getProductID(), productTrayWrapper.getItemType() };
        ProductTrayWrapperMapper mapper = new ProductTrayWrapperMapper();
        try {
            productTrayWrapper = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public void alterProductAmount(User user, ProductTrayWrapper productTrayWrapper)
    {
        this.getJdbcTemplate().update("UPDATE " + user.getTray() + " SET Count = "
                + (int)(this.getCartItemAmount(user, productTrayWrapper) + productTrayWrapper.getCount())
                + " where ProductID = " + productTrayWrapper.getProductID() +
            " AND ItemType = '" + productTrayWrapper.getItemType() + "'");
    }

    private int getCartItemAmount(User user, ProductTrayWrapper productTrayWrapper)
    {
        String sql = ProductTrayWrapperMapper.BASE_SQL + user.getTray() + " where ProductID = ? AND ItemType = ?";
        Object[] params = new Object[] { productTrayWrapper.getProductID(), productTrayWrapper.getItemType() };
        ProductTrayWrapperMapper mapper = new ProductTrayWrapperMapper();
        try {
            productTrayWrapper = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return productTrayWrapper.getCount();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public void removeFromCart(User user, int id) throws IDNotFoundException
    {
        String sql = "DELETE FROM " + user.getTray() + " WHERE ID=" + id;
        this.getJdbcTemplate().update(sql);
    }

    public ProductTrayWrapper getItem(User user, int id) throws IDNotFoundException
    {
        String sql = ProductTrayWrapperMapper.BASE_SQL + user.getTray() + " where ID = ?";
        Object[] params = new Object[] { id };
        ProductTrayWrapperMapper mapper = new ProductTrayWrapperMapper();
        try {
            ProductTrayWrapper productTrayWrapper = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return productTrayWrapper;
        } catch (EmptyResultDataAccessException e) {
            throw new IDNotFoundException(id);
        }
    }
}
