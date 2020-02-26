/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.UnitDao;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgUnitDTORowMapper;
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
public class UnitDaoImpl implements UnitDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<PrgUnitDTO> getUnitListForSync(String lastSyncDate) {
        String sql = "SELECT u.`ACTIVE`,u.`LABEL_ID`,u.`UNIT_CODE`,u.`UNIT_ID`,u.`UNIT_TYPE_ID`,\n"
                + "l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM ap_unit u \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=u.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE u.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new PrgUnitDTORowMapper());
    }

}
