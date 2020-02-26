/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LogisticsUnitDao;
import cc.altius.FASP.model.DTO.PrgLogisticsUnitDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgLogisticsUnitDTORowMapper;
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
public class LogisticsUnitDaoImpl implements LogisticsUnitDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgLogisticsUnitDTO> getLogisticsUnitListForSync(String lastSyncDate) {
        String sql = "SELECT lu.`ACTIVE`,lu.`HEIGHT_QTY`,lu.`HEIGHT_UNIT_ID`,lu.`LABEL_ID`,\n"
                + "l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,lu.`LENGTH_QTY`,lu.`LENGTH_UNIT_ID`,lu.`LOGISTICS_UNIT_ID`\n"
                + ",lu.`MANUFACTURER_ID`,lu.`PLANNING_UNIT_ID`,lu.`QTY_IN_EURO1`,lu.`QTY_IN_EURO2`,lu.`QTY_OF_PLANNING_UNITS`,lu.`UNIT_ID`\n"
                + ",lu.`VARIANT`,lu.`WEIGHT_QTY`,lu.`WEIGHT_UNIT_ID`,lu.`WIDTH_QTY`,lu.`WIDTH_UNIT_ID`\n"
                + "FROM rm_logistics_unit lu\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=lu.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE lu.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgLogisticsUnitDTORowMapper());
    }

}
