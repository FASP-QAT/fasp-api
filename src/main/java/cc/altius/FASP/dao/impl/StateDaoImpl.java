/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.StateDao;
import cc.altius.FASP.model.State;
import cc.altius.FASP.model.rowMapper.StateRowMapper;
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
public class StateDaoImpl implements StateDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<State> getStateList(int countryId) {
        String sql = "SELECT * FROM state s WHERE s.`COUNTRY_ID`=?;";
        return this.jdbcTemplate.query(sql, new StateRowMapper(), countryId);
    }

    @Override
    public List<State> getAllStateList() {
        String sql = "SELECT * FROM state;";
        return this.jdbcTemplate.query(sql, new StateRowMapper());
    }

}
