/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgUnitDTORowMapper;
import cc.altius.FASP.dao.UnitDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.model.rowMapper.UnitRowMapper;
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
public class UnitDaoImpl implements UnitDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    LabelDao labelDao;

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

    @Override
    @Transactional
    public int addUnit(Unit h, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ap_unit").usingGeneratedKeyColumns("UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("UNIT_TYPE_ID", h.getUnitType().getUnitTypeId());
        params.put("UNIT_CODE", h.getUnitCode());
        int labelId = this.labelDao.addLabel(h.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateUnit(Unit h, CustomUserDetails curUser) {
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, h.getLabel().getLabel_en(), curUser, DateUtils.getCurrentDateObject(DateUtils.EST), h.getLabel().getLabelId());
        Map<String, Object> params = new HashMap<>();
        params.put("unitId", h.getUnitId());
        params.put("active", h.isActive());
        params.put("unitCode", h.getUnitCode());
        params.put("unitTypeId", h.getUnitType().getUnitTypeId());
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.update("UPDATE ap_unit u SET u.UNIT_TYPE_ID=:unitTypeId,u.UNIT_CODE=:unitCode,u.ACTIVE=:active, u.LAST_MODIFIED_BY=:curUser, u.LAST_MODIFIED_DATE=:curDate WHERE u.UNIT_ID=:unitId", params);
    }

    @Override
    public List<Unit> getUnitList() {
        String sqlString = "SELECT "
                + "	u.UNIT_ID, ul.LABEL_ID, ul.LABEL_EN, ul.LABEL_FR, ul.LABEL_SP, ul.LABEL_PR, u.UNIT_CODE, "
                + "    ut.UNIT_TYPE_ID, utl.LABEL_ID `UNIT_TYPE_LABEL_ID`, utl.LABEL_EN `UNIT_TYPE_LABEL_EN`, utl.LABEL_FR `UNIT_TYPE_LABEL_FR`, utl.LABEL_SP `UNIT_TYPE_LABEL_SP`, utl.LABEL_PR `UNIT_TYPE_LABEL_PR`, "
                + "	u.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, u.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, u.LAST_MODIFIED_DATE "
                + "FROM ap_unit u "
                + "LEFT JOIN ap_unit_type ut ON u.UNIT_TYPE_ID=ut.UNIT_TYPE_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
                + "LEFT JOIN us_user cb ON u.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON u.LAST_MODIFIED_BY=lmb.USER_ID";
        return this.jdbcTemplate.query(sqlString, new UnitRowMapper());
    }

    @Override
    public Unit getUnitById(int unitId) {
        String sqlString = "SELECT "
                + "	u.UNIT_ID, ul.LABEL_ID, ul.LABEL_EN, ul.LABEL_FR, ul.LABEL_SP, ul.LABEL_PR, u.UNIT_CODE, "
                + "    ut.UNIT_TYPE_ID, utl.LABEL_ID `UNIT_TYPE_LABEL_ID`, utl.LABEL_EN `UNIT_TYPE_LABEL_EN`, utl.LABEL_FR `UNIT_TYPE_LABEL_FR`, utl.LABEL_SP `UNIT_TYPE_LABEL_SP`, utl.LABEL_PR `UNIT_TYPE_LABEL_PR`, "
                + "	u.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, u.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, u.LAST_MODIFIED_DATE "
                + "FROM ap_unit u "
                + "LEFT JOIN ap_unit_type ut ON u.UNIT_TYPE_ID=ut.UNIT_TYPE_ID "
                + "LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
                + "LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
                + "LEFT JOIN us_user cb ON u.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON u.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE u.UNIT_ID=?";
        return this.jdbcTemplate.queryForObject(sqlString, new UnitRowMapper(), unitId);
    }

}
