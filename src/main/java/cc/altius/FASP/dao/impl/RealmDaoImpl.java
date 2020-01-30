/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.rowMapper.RealmRowMapper;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class RealmDaoImpl implements RealmDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Realm> getRealmList(boolean active) {
        String sql = "SELECT * FROM rm_realm r";
        if (active) {
            sql += " WHERE r.`ACTIVE`";
        }
        return this.jdbcTemplate.query(sql, new RealmRowMapper());
    }

}
