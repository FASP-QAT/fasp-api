/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.CityDao;
import cc.altius.FASP.model.City;
import cc.altius.FASP.model.rowMapper.CityRowMapper;
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
public class CityDaoImpl implements CityDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<City> getCityList(int countryId, int stateId) {
        String sql = "SELECT * FROM city c WHERE c.`COUNTRY_ID`=? AND (c.`STATE_ID`=? OR ?=0)";
        return this.jdbcTemplate.query(sql, new CityRowMapper(), countryId, stateId, stateId);
    }

}
