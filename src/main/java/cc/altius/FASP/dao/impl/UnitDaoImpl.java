/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    LabelDao labelDao;

    private final String sqlListString = "SELECT "
            + "	u.UNIT_ID, ul.LABEL_ID, ul.LABEL_EN, ul.LABEL_FR, ul.LABEL_SP, ul.LABEL_PR, u.UNIT_CODE, "
            + " ut.DIMENSION_ID, utl.LABEL_ID `DIMENSION_LABEL_ID`, utl.LABEL_EN `DIMENSION_LABEL_EN`, utl.LABEL_FR `DIMENSION_LABEL_FR`, utl.LABEL_SP `DIMENSION_LABEL_SP`, utl.LABEL_PR `DIMENSION_LABEL_PR`, "
            + "	u.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, u.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, u.LAST_MODIFIED_DATE "
            + " FROM ap_unit u "
            + " LEFT JOIN ap_dimension ut ON u.DIMENSION_ID=ut.DIMENSION_ID "
            + " LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID "
            + " LEFT JOIN ap_label utl ON ut.LABEL_ID=utl.LABEL_ID "
            + " LEFT JOIN us_user cb ON u.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON u.LAST_MODIFIED_BY=lmb.USER_ID "
            + " WHERE TRUE ";

    @Override
    @Transactional
    public int addUnit(Unit u, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("ap_unit").usingGeneratedKeyColumns("UNIT_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("DIMENSION_ID", u.getDimension().getId());
        params.put("UNIT_CODE", u.getUnitCode());
        int labelId = this.labelDao.addLabel(u.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateUnit(Unit u, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("unitId", u.getUnitId());
        params.put("active", u.isActive());
        params.put("labelEn", u.getLabel().getLabel_en());
        params.put("unitCode", u.getUnitCode());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        return this.namedParameterJdbcTemplate.update("UPDATE ap_unit u LEFT JOIN ap_label ul ON u.LABEL_ID=ul.LABEL_ID SET "
                + "u.UNIT_CODE=:unitCode, "
                + "u.ACTIVE=:active, "
                + "u.LAST_MODIFIED_BY=IF(u.UNIT_CODE!=:unitCode OR u.ACTIVE!=:active, :curUser,u.LAST_MODIFIED_BY), "
                + "u.LAST_MODIFIED_DATE=IF(u.UNIT_CODE!=:unitCode OR u.ACTIVE!=:active, :curDate,u.LAST_MODIFIED_DATE), "
                + "ul.LABEL_EN=:labelEn,  "
                + "ul.LAST_MODIFIED_BY=IF(ul.LABEL_EN!=:labelEn, :curUser,ul.LAST_MODIFIED_BY), "
                + "ul.LAST_MODIFIED_DATE=IF(ul.LABEL_EN!=:labelEn, :curDate,ul.LAST_MODIFIED_DATE) "
                + "WHERE u.UNIT_ID=:unitId", params);
    }

    @Override
    public List<Unit> getUnitListForSync(String lastSyncDate) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND u.LAST_MODIFIED_DATE>=:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new UnitRowMapper());
    }

    @Override
    public List<Unit> getUnitList() {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), new UnitRowMapper());
    }

    @Override
    public Unit getUnitById(int unitId) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND u.UNIT_ID=:unitId");
        Map<String, Object> params = new HashMap<>();
        params.put("unitId", unitId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new UnitRowMapper());
    }

}
