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
            + "    m.TRACER_CATEGORY_ID,  "
            + "    ml.LABEL_ID, ml.LABEL_EN, ml.LABEL_FR, ml.LABEL_SP, ml.LABEL_PR, "
            + "    r.REALM_ID, rl.`LABEL_ID` `REALM_LABEL_ID`, rl.`LABEL_EN` `REALM_LABEL_EN` , rl.`LABEL_FR` `REALM_LABEL_FR`, rl.`LABEL_PR` `REALM_LABEL_PR`, rl.`LABEL_SP` `REALM_LABEL_SP`, r.REALM_CODE, "
            + "    m.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, m.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, m.LAST_MODIFIED_DATE "
            + "FROM rm_tracer_category m  "
            + "LEFT JOIN ap_label ml ON m.LABEL_ID=ml.LABEL_ID "
            + "LEFT JOIN rm_realm r ON m.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN us_user cb ON m.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON m.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addTracerCategory(TracerCategory m, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_tracer_category").usingGeneratedKeyColumns("TRACER_CATEGORY_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", m.getRealm().getId());
        int labelId = this.labelDao.addLabel(m.getLabel(), LabelConstants.RM_TRACER_CATEGORY, curUser.getUserId());
        params.put("LABEL_ID", labelId);
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
                + "m.`LAST_MODIFIED_BY`=:curUser, "
                + "m.`LAST_MODIFIED_DATE`=:curDate, "
                + "ml.LABEL_EN=:labelEn, "
                + "ml.`LAST_MODIFIED_BY`=:curUser, "
                + "ml.`LAST_MODIFIED_DATE`=:curDate "
                + " WHERE m.`TRACER_CATEGORY_ID`=:tracerCategoryId";
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", m.getTracerCategoryId());
        params.put("active", m.isActive());
        params.put("curDate", curDate);
        params.put("curUser", curUser.getUserId());
        params.put("labelEn", m.getLabel().getLabel_en());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<TracerCategory> getTracerCategoryList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        if (active) {
            sqlStringBuilder.append(" AND m.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND m.REALM_ID=:realmId ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND m.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForRealm(int realmId, int programId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND m.REALM_ID=:realmId ")
                .append("AND m.TRACER_CATEGORY_ID IN (SELECT fu.TRACER_CATEGORY_ID FROM rm_program_planning_unit ppu LEFT JOIN rm_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID LEFT JOIN rm_forecasting_unit fu ON pu.FORECASTING_UNIT_ID=fu.FORECASTING_UNIT_ID WHERE ppu.PROGRAM_ID=:programId GROUP BY fu.TRACER_CATEGORY_ID) ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("programId", programId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        if (active) {
            sqlStringBuilder.append(" AND m.ACTIVE ");
        }
        sqlStringBuilder.append(" ORDER BY ml.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public TracerCategory getTracerCategoryById(int tracerCategoryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND m.`TRACER_CATEGORY_ID`=:tracerCategoryId ");
        Map<String, Object> params = new HashMap<>();
        params.put("tracerCategoryId", tracerCategoryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

    @Override
    public List<TracerCategory> getTracerCategoryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND m.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new TracerCategoryRowMapper());
    }

}
