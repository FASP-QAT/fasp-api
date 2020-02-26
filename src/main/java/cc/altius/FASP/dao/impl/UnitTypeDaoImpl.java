/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.UnitTypeDao;
import cc.altius.FASP.model.UnitType;
import cc.altius.FASP.model.rowMapper.UnitTypeRowMapper;
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
public class UnitTypeDaoImpl implements UnitTypeDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<UnitType> getUnitTypeListForSync() {
        String sql = "SELECT u.`UNIT_TYPE_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM ap_unit_type u\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=u.`LABEL_ID`";
        return this.jdbcTemplate.query(sql, new UnitTypeRowMapper());
    }

}
