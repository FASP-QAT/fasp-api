/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.rowMapper.RealmRowMapper;
import cc.altius.FASP.service.AclService;
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
public class RealmDaoImpl implements RealmDao {

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

    private final String sqlListString = "SELECT r.REALM_ID, r.REALM_CODE, r.DEFAULT_REALM, r.MIN_MOS_MIN_GAURDRAIL, r.MIN_MOS_MAX_GAURDRAIL, r.MAX_MOS_MAX_GAURDRAIL, r.MIN_QPL_TOLERANCE, r.MIN_QPL_TOLERANCE_CUT_OFF, r.MAX_QPL_TOLERANCE, r.ACTUAL_CONSUMPTION_MONTHS_IN_PAST, r.FORECAST_CONSUMPTION_MONTH_IN_PAST, r.INVENTORY_MONTHS_IN_PAST, r.`MIN_COUNT_FOR_MODE`, r.`MIN_PERC_FOR_MODE`, "
            + " rl.`LABEL_ID` ,rl.`LABEL_EN`, rl.`LABEL_FR`, rl.`LABEL_PR`, rl.`LABEL_SP`,"
            + " cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, r.ACTIVE, r.CREATED_DATE, r.LAST_MODIFIED_DATE "
            + " FROM rm_realm r "
            + " LEFT JOIN ap_label rl ON r.`LABEL_ID`=rl.`LABEL_ID` "
            + " LEFT JOIN us_user cb ON r.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON r.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addRealm(Realm r, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_realm").usingGeneratedKeyColumns("REALM_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_CODE", r.getRealmCode());
        int labelId = this.labelDao.addLabel(r.getLabel(), LabelConstants.AP_REALM, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("DEFAULT_REALM", r.isDefaultRealm());
        params.put("MIN_MOS_MIN_GAURDRAIL", r.getMinMosMinGaurdrail());
        params.put("MIN_MOS_MAX_GAURDRAIL", r.getMinMosMaxGaurdrail());
        params.put("MAX_MOS_MAX_GAURDRAIL", r.getMaxMosMaxGaurdrail());
        params.put("MIN_QPL_TOLERANCE", r.getMinQplTolerance());
        params.put("MIN_QPL_TOLERANCE_CUT_OFF", r.getMinQplToleranceCutOff());
        params.put("MAX_QPL_TOLERANCE", r.getMaxQplTolerance());
        params.put("ACTUAL_CONSUMPTION_MONTHS_IN_PAST", r.getActualConsumptionMonthsInPast());
        params.put("FORECAST_CONSUMPTION_MONTH_IN_PAST", r.getForecastConsumptionMonthsInPast());
        params.put("INVENTORY_MONTHS_IN_PAST", r.getInventoryMonthsInPast());
        params.put("MIN_COUNT_FOR_MODE", r.getMinCountForMode());
        params.put("MIN_PERC_FOR_MODE", r.getMinPercForMode());
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int realmId = si.executeAndReturnKey(params).intValue();
        if (r.isDefaultRealm()) {
            params.clear();
            params.put("realmId", realmId);
            this.namedParameterJdbcTemplate.update("UPDATE rm_realm SET DEFAULT_REALM=0 WHERE REALM_ID!=:realmId", params);
        }
        return realmId;
    }

    @Override
    @Transactional
    public int updateRealm(Realm r, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", r.getRealmId());
        params.put("labelEn", r.getLabel().getLabel_en());
        params.put("realmCode", r.getRealmCode());
        params.put("default", r.isDefaultRealm());
        params.put("minMosMinGaurdrail", r.getMinMosMinGaurdrail());
        params.put("minMosMaxGaurdrail", r.getMinMosMaxGaurdrail());
        params.put("maxMosMaxGaurdrail", r.getMaxMosMaxGaurdrail());
        params.put("minQplTolerance", r.getMinQplTolerance());
        params.put("minQplToleranceCutOff", r.getMinQplToleranceCutOff());
        params.put("maxQplTolerance", r.getMaxQplTolerance());
        params.put("actualConsumptionMonthsInPast", r.getActualConsumptionMonthsInPast());
        params.put("forecastConsumptionMonthsInPast", r.getForecastConsumptionMonthsInPast());
        params.put("inventoryMonthsInPast", r.getInventoryMonthsInPast());
        params.put("minCountForMode", r.getMinCountForMode());
        params.put("minPercForMode", r.getMinPercForMode());
        params.put("active", r.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_realm r LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID SET "
                + "r.REALM_CODE=:realmCode, "
                + "r.DEFAULT_REALM=:default,"
                + "r.MIN_MOS_MIN_GAURDRAIL=:minMosMinGaurdrail,"
                + "r.MIN_MOS_MAX_GAURDRAIL=:minMosMaxGaurdrail,"
                + "r.MAX_MOS_MAX_GAURDRAIL=:maxMosMaxGaurdrail,"
                + "r.MIN_QPL_TOLERANCE=:minQplTolerance,"
                + "r.MIN_QPL_TOLERANCE_CUT_OFF=:minQplToleranceCutOff,"
                + "r.MAX_QPL_TOLERANCE=:maxQplTolerance,"
                + "r.ACTUAL_CONSUMPTION_MONTHS_IN_PAST=:actualConsumptionMonthsInPast,"
                + "r.FORECAST_CONSUMPTION_MONTH_IN_PAST=:forecastConsumptionMonthsInPast,"
                + "r.INVENTORY_MONTHS_IN_PAST=:inventoryMonthsInPast,"
                + "r.MIN_COUNT_FOR_MODE=:minCountForMode,"
                + "r.MIN_PERC_FOR_MODE=:minPercForMode,"
                + "r.ACTIVE=:active, "
                + "r.LAST_MODIFIED_BY=:curUser, "
                + "r.LAST_MODIFIED_DATE=:curDate, "
                + "rl.LABEL_EN=:labelEn, "
                + "rl.LAST_MODIFIED_BY=:curUser, "
                + "rl.LAST_MODIFIED_DATE=:curDate "
                + "WHERE r.REALM_ID=:realmId", params);
        if (r.isDefaultRealm()) {
            this.namedParameterJdbcTemplate.update("UPDATE rm_realm SET DEFAULT_REALM=0 WHERE REALM_ID!=:realmId", params);
        }
        return rows;
    }

    @Override
    public List<Realm> getRealmList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder.append(" AND r.ACTIVE=:active ");
            params.put("active", active);
        }
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmRowMapper());
    }

    @Override
    public Realm getRealmById(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new RealmRowMapper());
    }

    @Override
    public List<Realm> getRealmListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND r.LAST_MODIFIED_DATE>:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmRowMapper());
    }
}
