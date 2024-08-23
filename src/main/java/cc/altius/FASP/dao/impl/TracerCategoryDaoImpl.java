/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.rowMapper.TracerCategoryRowMapper;
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
import cc.altius.FASP.dao.TracerCategoryDao;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import cc.altius.FASP.service.AclService;

/**
 *
 * @author altius
 */
@Repository
public class TracerCategoryDaoImpl implements TracerCategoryDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = "SELECT  "
            + "    tc.TRACER_CATEGORY_ID,  "
            + "    tc.LABEL_ID, tc.LABEL_EN, tc.LABEL_FR, tc.LABEL_SP, tc.LABEL_PR, "
            + "    r.REALM_ID, r.`LABEL_ID` `REALM_LABEL_ID`, r.`LABEL_EN` `REALM_LABEL_EN` , r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_PR` `REALM_LABEL_PR`, r.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
            + "    ha.HEALTH_AREA_ID, ha.`LABEL_ID` `HA_LABEL_ID`, ha.`LABEL_EN` `HA_LABEL_EN` , ha.`LABEL_FR` `HA_LABEL_FR`, ha.`LABEL_PR` `HA_LABEL_PR`, ha.`LABEL_SP` `HA_LABEL_SP`, ha.HEALTH_AREA_CODE, "
            + "    tc.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, tc.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, tc.LAST_MODIFIED_DATE "
            + "FROM vw_tracer_category tc  "
            + "LEFT JOIN vw_realm r ON tc.REALM_ID=r.REALM_ID "
            + "LEFT JOIN vw_health_area ha ON tc.HEALTH_AREA_ID=ha.HEALTH_AREA_ID "
            + "LEFT JOIN us_user cb ON tc.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON tc.LAST_MODIFIED_BY=lmb.USER_ID ";

    @Override
    @Transactional
    public int addTracerCategory(TracerCategory m, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_tracer_category").usingColumns("REALM_ID", "LABEL_ID", "HEALTH_AREA_ID", "ACTIVE", "CREATED_BY", "CREATED_DATE", "LAST_MODIFIED_BY", "LAST_MODIFIED_DATE").usingGeneratedKeyColumns("TRACER_CATEGORY_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", m.getRealm().getId());
        int labelId = this.labelDao.addLabel(m.getLabel(), LabelConstants.RM_TRACER_CATEGORY, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("HEALTH_AREA_ID", m.getHealthArea().getId());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    public int updateTracerCategory(TracerCategory m, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_tracer_category m LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
                + "SET  "
                + "m.`ACTIVE`=:active, "
                + "m.`HEALTH_AREA_ID`=:healthAreaId, "
                + "m.`LAST_MODIFIED_BY`=:curUser, "
                + "m.`LAST_MODIFIED_DATE`=:curDate, "
                + "ml.LABEL_EN=:labelEn, "
                + "ml.`LAST_MODIFIED_BY`=:curUser, "
                + "ml.`LAST_MODIFIED_DATE`=:curDate "
                + " WHERE m.`TRACER_CATEGORY_ID`=:tracerCategoryId";
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", m.getTracerCategoryId());
        params.put("healthAreaId", m.getHealthArea().getId());
        params.put("active", m.isActive());
        params.put("curDate", curDate);
        params.put("curUser", curUser.getUserId());
        params.put("labelEn", m.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<TracerCategory> getTracerCategoryList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE TRUE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        if (active) {
            sqlStringBuilder.append(" AND tc.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<SimpleObject> getTracerCategoryDropdownList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT tc.TRACER_CATEGORY_ID `ID`, tc.LABEL_ID, tc.LABEL_EN, tc.LABEL_FR, tc.LABEL_SP, tc.LABEL_PR FROM vw_tracer_category tc WHERE tc.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "tc", curUser);
        sqlStringBuilder.append(" ORDER BY tc.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<SimpleObject> getTracerCategoryDropdownListForFilterMultiplerPrograms(String programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT tc.TRACER_CATEGORY_ID `ID`, tc.LABEL_ID, tc.LABEL_EN, tc.LABEL_FR, tc.LABEL_SP, tc.LABEL_PR FROM rm_program_planning_unit ppu LEFT JOIN vw_program p ON ppu.PROGRAM_ID=p.PROGRAM_ID LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID LEFT JOIN vw_tracer_category tc ON fu.TRACER_CATEGORY_ID=tc.TRACER_CATEGORY_ID WHERE ppu.ACTIVE AND tc.ACTIVE AND pu.ACTIVE AND fu.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        if (programIds.length() > 0) {
            sqlStringBuilder.append(" AND FIND_IN_SET(ppu.PROGRAM_ID, :programIds) ");
            params.put("programIds", programIds);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "tc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY tc.TRACER_CATEGORY_ID ORDER BY tc.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleObjectRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE TRUE AND tc.REALM_ID=:realmId ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND tc.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, int programId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE tc.TRACER_CATEGORY_ID IN ("
                + "SELECT DISTINCT(fu.TRACER_CATEGORY_ID) "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN rm_planning_unit pu on ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE ppu.PROGRAM_ID=:programId AND ppu.ACTIVE) AND tc.REALM_ID=:realmId ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("programId", programId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND tc.ACTIVE ");
        }
        sqlStringBuilder.append(" GROUP BY tc.TRACER_CATEGORY_ID ORDER BY tc.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, String[] programIds, boolean active, CustomUserDetails curUser) {
        String programIdsString = "";
        if (programIds == null) {
            programIdsString = "";
        } else {
            String opt = String.join("','", programIds);
            if (programIds.length > 0) {
                programIdsString = "'" + opt + "'";
            } else {
                programIdsString = opt;
            }
        }
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE tc.TRACER_CATEGORY_ID IN ("
                + "SELECT DISTINCT(fu.TRACER_CATEGORY_ID) "
                + "FROM rm_program_planning_unit ppu "
                + "LEFT JOIN rm_planning_unit pu on ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE ppu.PROGRAM_ID IN (").append(programIdsString).append(") AND ppu.ACTIVE"
                + " UNION "
                + "SELECT DISTINCT(fu.TRACER_CATEGORY_ID) "
                + "FROM rm_dataset_planning_unit ppu "
                + "LEFT JOIN rm_planning_unit pu on ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID "
                + "WHERE ppu.PROGRAM_ID IN (").append(programIdsString).append(") AND ppu.ACTIVE) AND tc.REALM_ID=:realmId ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND tc.ACTIVE ");
        }
        sqlStringBuilder.append(" GROUP BY tc.TRACER_CATEGORY_ID ORDER BY tc.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public TracerCategory getTracerCategoryById(int tracerCategoryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE TRUE AND tc.`TRACER_CATEGORY_ID`=:tracerCategoryId ");
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", tracerCategoryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" WHERE TRUE AND tc.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

}
