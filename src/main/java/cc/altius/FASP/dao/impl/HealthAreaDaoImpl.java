/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.HealthAreaDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.HealthAreaListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.HealthAreaResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.ArrayUtils;
import cc.altius.FASP.utils.SuggestedDisplayName;
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
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = "SELECT "
            + "	ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
            + "     r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
            + "     ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE, "
            + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
            + "FROM rm_health_area ha "
            + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
            + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID "
            + "LEFT JOIN rm_realm_country rc ON hac.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID AND rc.ACTIVE AND hac.ACTIVE "
            + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + "WHERE TRUE ";
    private final String sqlListProgramString = "SELECT "
            + "	ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
            + "     r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
            + "     ha.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ha.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ha.LAST_MODIFIED_DATE, "
            + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
            + "FROM rm_health_area ha "
            + "LEFT JOIN rm_program p ON ha.HEALTH_AREA_ID=p.HEALTH_AREA_ID AND p.REALM_COUNTRY_ID=:realmCountryId "
            + "LEFT JOIN rm_realm r ON ha.REALM_ID=r.REALM_ID "
            + "LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID "
            + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID "
            + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID=hac.HEALTH_AREA_ID AND hac.REALM_COUNTRY_ID=:realmCountryId "
            + "LEFT JOIN rm_realm_country rc ON hac.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + "LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + "WHERE TRUE AND rc.REALM_COUNTRY_ID=:realmCountryId AND p.PROGRAM_ID IS NOT NULL AND p.ACTIVE  ";

    @Override
    @Transactional
    public int addHealthArea(HealthArea h, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_health_area").usingGeneratedKeyColumns("HEALTH_AREA_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(h.getLabel(), LabelConstants.RM_HEALTH_AREA, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("HEALTH_AREA_CODE", h.getHealthAreaCode());
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
        params.put("healthAreaCode", h.getHealthAreaCode());
        params.put("active", h.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_health_area ha LEFT JOIN ap_label hal ON ha.LABEL_ID=hal.LABEL_ID SET ha.ACTIVE=:active, ha.HEALTH_AREA_CODE=:healthAreaCode, ha.LAST_MODIFIED_BY=:curUser, ha.LAST_MODIFIED_DATE=:curDate, hal.LABEL_EN=:labelEn, hal.LAST_MODIFIED_BY=:curUser, hal.LAST_MODIFIED_DATE=:curDate  WHERE ha.HEALTH_AREA_ID=:healthAreaId", params);
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_health_area_country WHERE HEALTH_AREA_ID=:healthAreaId", params);
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
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForHealthArea(sqlStringBuilder, params, "ha", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public List<SimpleCodeObject> getHealthAreaDropdownList(int realmId, boolean aclFilter, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ha.HEALTH_AREA_ID `ID`, ha.LABEL_ID, ha.LABEL_EN, ha.LABEL_FR, ha.LABEL_SP, ha.LABEL_PR, ha.HEALTH_AREA_CODE `CODE` FROM vw_health_area ha WHERE ha.ACTIVE AND (ha.REALM_ID=:realmId OR :realmId=-1) ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(stringBuilder, params, "ha", curUser);
        if (aclFilter) {
            this.aclService.addUserAclForHealthArea(stringBuilder, params, "ha", curUser);
        }
        stringBuilder.append(" ORDER BY ha.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<HealthArea> getHealthAreaListByRealmCountry(int realmCountryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForHealthArea(sqlStringBuilder, params, "ha", curUser);
        this.aclService.addUserAclForRealmCountry(sqlStringBuilder, params, "rc", curUser);
        sqlStringBuilder.append(" AND hac.ACTIVE AND ha.ACTIVE AND hac.REALM_COUNTRY_ID=:realmCountryId");
        params.put("realmCountryId", realmCountryId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public List<SimpleCodeObject> getHealthAreaListByRealmCountryIds(String[] realmCountryIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR` FROM vw_program p LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID LEFT JOIN rm_realm r ON rc.REALM_ID=r.REALM_ID LEFT JOIN vw_health_area ha on FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) WHERE FIND_IN_SET(p.REALM_COUNTRY_ID, :realmCountryIds) AND p.ACTIVE");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY ha.HEALTH_AREA_ID ORDER BY ha.HEALTH_AREA_CODE ");
        params.put("realmCountryIds", ArrayUtils.convertArrayToString(realmCountryIds));
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleCodeObjectRowMapper("HEALTH_AREA_"));
    }

    @Override
    public List<HealthArea> getHealthAreaForActiveProgramsList(int realmCountryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListProgramString);
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealmCountry(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addUserAclForHealthArea(sqlStringBuilder, params, "ha", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public List<HealthArea> getHealthAreaListByRealmId(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public HealthArea getHealthAreaById(int healthAreaId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND ha.HEALTH_AREA_ID=:haId ");
        Map<String, Object> params = new HashMap<>();
        params.put("haId", healthAreaId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForHealthArea(sqlStringBuilder, params, "ha", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaResultSetExtractor());
    }

    @Override
    public List<HealthArea> getHealthAreaListForProgramByRealmId(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", realmId, curUser);
        sqlStringBuilder.append(" AND ha.HEALTH_AREA_ID IN (SELECT p.HEALTH_AREA_ID FROM vw_program p WHERE p.ACTIVE) ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        String extractedName = SuggestedDisplayName.getAlphaNumericString(name, SuggestedDisplayName.HEALTH_AREA_LENGTH);
        String sqlString = "SELECT COUNT(*) CNT FROM rm_health_area pa WHERE pa.REALM_ID=:realmId AND UPPER(LEFT(pa.HEALTH_AREA_CODE,:len))=:extractedName";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("len", extractedName.length());
        params.put("extractedName", extractedName);
        int cnt = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        return SuggestedDisplayName.getFinalDisplayName(extractedName, cnt);
    }

    @Override
    public List<HealthArea> getHealthAreaListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append("AND ha.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "r", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new HealthAreaListResultSetExtractor());
    }

}
