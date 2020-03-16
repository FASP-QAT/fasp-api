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
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.rowMapper.HealthAreaListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.HealthAreaResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    LabelDao labelDao;

    @Override
    public List<PrgHealthAreaDTO> getHealthAreaListForSync(String lastSyncDate,int realmId) {
        String sql = "SELECT  ha.`ACTIVE`,ha.`HEALTH_AREA_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`,ha.`REALM_ID`\n"
                + "FROM rm_health_area ha \n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=ha.`LABEL_ID` WHERE ha.`REALM_ID`=:realmId";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " AND ha.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        params.put("realmId", realmId);
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgHealthAreaDTORowMapper());
    }

    @Override
    @Transactional
    public int addHealthArea(HealthArea h, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_health_area").usingGeneratedKeyColumns("HEALTH_AREA_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", h.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(h.getLabel(), curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int healthAreaId = si.executeAndReturnKey(params).intValue();
        si = new SimpleJdbcInsert(dataSource).withTableName("rm_health_area_country");
        SqlParameterSource[] paramList = new SqlParameterSource[h.getRealmCountryArray().length];
        int i = 0;
        for (String realmCountryId : h.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("HEALTH_AREA_ID", healthAreaId);
            params.put("REALM_COUNTRY_ID", realmCountryId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return healthAreaId;
    }

    @Override
    @Transactional
    public int updateHealthArea(HealthArea h, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("healthAreaId", h.getHealthAreaId());
        params.put("labelEn", h.getLabel().getLabel_en());
        params.put("active", h.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_health_area ha LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID SET ha.ACTIVE=:active, ha.LAST_MODIFIED_BY=IF(ha.ACTIVE!=:active,:curUser,ha.LAST_MODIFIED_BY), ha.LAST_MODIFIED_DATE=IF(ha.ACTIVE!=:active, :curDate, ha.LAST_MODIFIED_DATE), hal.LABEL_EN=:labelEn, hal.LAST_MODIFIED_BY=IF(hal.LABEL_EN!=:labelEn,:curUser,hal.LAST_MODIFIED_BY), hal.LAST_MODIFIED_DATE=IF(hal.LABEL_EN!=:labelEn, :curDate, hal.LAST_MODIFIED_DATE)  WHERE ha.HEALTH_AREA_ID=:healthAreaId", params);
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_health_area_country WHERE HEALTH_AREA_ID=?", params);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_health_area_country");
        SqlParameterSource[] paramList = new SqlParameterSource[h.getRealmCountryArray().length];
        int i = 0;
        for (String realmCountryId : h.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("HEALTH_AREA_ID", h.getHealthAreaId());
            params.put("REALM_COUNTRY_ID", realmCountryId);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return rows;
    }

    @Override
    public List<HealthArea> getHealthAreaList(CustomUserDetails curUser) {
        String sqlString = "SELECT "
                + "	ha.HEALTH_AREA_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "     r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "	ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE, "
                + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + "FROM rm_health_area ha "
                + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID "
                + "LEFT JOIN rm_realm_country rc ON hac.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND ha.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
//        for (UserAcl acl : curUser.getAclList()) {
//            if (acl.getRealmCountryId() != -1) {
//                sqlString += "AND hac.REALM_COUNTRY_ID=:realmCountryId";
//                params.put("realmCountryId", acl.getRealmCountryId());
//            }
//            if (acl.getHealthAreaId() != -1) {
//                sqlString += "AND ha.HEALTH_AREA_ID=:healthAreaId";
//                params.put("healthAreaId", acl.getHealthAreaId());
//            }
//            if (acl.getOrganisationId() != -1) {
//
//            }
//            if (acl.getProgramId() != -1) {
//
//            }
//        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public List<HealthArea> getHealthAreaListByRealmId(int realmId, CustomUserDetails curUser) {
        String sqlString = "SELECT "
                + "	ha.HEALTH_AREA_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "     r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "	ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE, "
                + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + "FROM rm_health_area ha "
                + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID "
                + "LEFT JOIN rm_realm_country rc ON hac.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                    + "WHERE ha.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND ha.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public HealthArea getHealthAreaById(int healthAreaId, CustomUserDetails curUser) {
        String sqlString = "SELECT "
                + "	ha.HEALTH_AREA_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "     r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "	ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE, "
                + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + "FROM rm_health_area ha "
                + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
                + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID "
                + "LEFT JOIN rm_realm_country rc ON hac.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "WHERE ha.HEALTH_AREA_ID=:haId ";
        Map<String, Object> params = new HashMap<>();
        params.put("haId", healthAreaId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND ha.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
//        for (UserAcl acl : curUser.getAclList()) {
//            if (acl.getRealmCountryId() != -1) {
//                sqlString += "AND hac.REALM_COUNTRY_ID=:realmCountryId";
//                params.put("realmCountryId", acl.getRealmCountryId());
//            }
//            if (acl.getHealthAreaId() != -1) {
//                sqlString += "AND ha.HEALTH_AREA_ID=:healthAreaId";
//                params.put("healthAreaId", acl.getHealthAreaId());
//            }
//            if (acl.getOrganisationId() != -1) {
//
//            }
//            if (acl.getProgramId() != -1) {
//
//            }
//        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new HealthAreaResultSetExtractor());
    }

}
