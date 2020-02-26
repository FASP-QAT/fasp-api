/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgHealthAreaDTORowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class HealthAreaDaoImpl implements HealthAreaDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgHealthAreaDTO> getHealthAreaListForSync(String lastSyncDate) {
        String sql = "SELECT  ha.`ACTIVE`,ha.`HEALTH_AREA_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM rm_health_area ha \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=ha.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE ha.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgHealthAreaDTORowMapper());
    }

}
