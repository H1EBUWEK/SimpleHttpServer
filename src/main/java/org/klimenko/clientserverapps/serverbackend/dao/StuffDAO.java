package org.klimenko.clientserverapps.serverbackend.dao;

import org.klimenko.clientserverapps.serverbackend.models.Stuff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.klimenko.clientserverapps.serverbackend.mappers.StuffMapper;

import javax.sql.DataSource;
import java.util.List;

@Repository
@Transactional
public class StuffDAO extends JdbcDaoSupport {

    @Autowired
    public StuffDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public Stuff getStuff(int id) {
        String sql = StuffMapper.BASE_SQL + " where p.id = ?";

        Object[] params = new Object[] { id };
        StuffMapper mapper = new StuffMapper();
        try {
            Stuff pet = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return pet;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void buyStuff(int id, int newCount)
    {
        String sql = "UPDATE stuff SET Count = " + newCount + " WHERE ID = " + id;
        this.getJdbcTemplate().update(sql);
    }

    public List<Stuff> getStuff()
    {
        String sql = StuffMapper.BASE_SQL;
        StuffMapper mapper = new StuffMapper();
        try {
            List<Stuff> list = this.getJdbcTemplate().query(sql, mapper);
            return list;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
