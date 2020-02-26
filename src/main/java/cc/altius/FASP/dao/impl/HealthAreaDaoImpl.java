/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgHealthAreaDTORowMapper;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.rowMapper.HealthAreaRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class HealthAreaDaoImpl implements HealthAreaDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Autowired
    LabelDao labelDao;

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

    @Override
    @Transactional
    public int addHealthArea(HealthArea h, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_health_area").usingGeneratedKeyColumns("HEALTH_AREA_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", h.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(h.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateHealthArea(HealthArea h, int curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("healthAreaId", h.getHealthAreaId());
        params.put("active", h.isActive());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        return nm.update("UPDATE rm_health_area ha SET ha.ACTIVE=:active, ha.LAST_MODIFIED_BY=:curUser, ha.LAST_MODIFIED_DATE=:curDate WHERE ha.HEALTH_AREA_ID=:healthAreaId", params);
    }

    @Override
    public List<HealthArea> getHealthAreaList() {
        String sqlString = "SELECT "
                + "	ha.HEALTH_AREA_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "	ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE "
                + "FROM rm_health_area ha "
                + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID";
        return this.jdbcTemplate.query(sqlString, new HealthAreaRowMapper());
    }

    @Override
    public HealthArea getHealthAreaById(int healthAreaId) {
        String sqlString = "SELECT "
                + "	ha.HEALTH_AREA_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "    r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "	ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE "
                + "FROM rm_health_area ha "
                + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE ha.HEALTH_AREA_ID=?";
        return this.jdbcTemplate.queryForObject(sqlString, new HealthAreaRowMapper(), healthAreaId);
    }

}
