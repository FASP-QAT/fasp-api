/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.ProgramDataDao;
import cc.altius.FASP.dao.RealmCountryDao;
import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.RealmCountryHealthArea;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.rowMapper.RealmCountryHealthAreaResultSetExtractor;
import cc.altius.FASP.model.rowMapper.RealmCountryPlanningUnitRowMapper;
import cc.altius.FASP.model.rowMapper.RealmCountryRowMapper;
import cc.altius.FASP.model.rowMapper.VersionRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.LogUtils;
import cc.altius.utils.DateUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class RealmCountryDaoImpl implements RealmCountryDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;
    @Autowired
    private ProgramDataDao programDataDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListStringBase = "SELECT "
            + "    rc.REALM_COUNTRY_ID, "
            + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, r.REALM_CODE, "
            + "    c.COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`,c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`, "
            + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, cu.LABEL_ID `CURRENCY_LABEL_ID`, cu.LABEL_EN `CURRENCY_LABEL_EN`, cu.LABEL_FR `CURRENCY_LABEL_FR`, cu.LABEL_PR `CURRENCY_LABEL_PR`, cu.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "    un.UNIT_ID, un.UNIT_CODE, un.LABEL_ID `UNIT_LABEL_ID`, un.LABEL_EN `UNIT_LABEL_EN`, un.LABEL_FR `UNIT_LABEL_FR`, un.LABEL_PR `UNIT_LABEL_PR`, un.LABEL_SP `UNIT_LABEL_SP`, "
            + "    rc.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rc.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rc.LAST_MODIFIED_DATE "
            + "  FROM rm_realm_country rc "
            + " LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID "
            + " LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + " LEFT JOIN vw_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID "
            + " LEFT JOIN vw_unit un ON rc.PALLET_UNIT_ID=un.UNIT_ID "
            + " LEFT JOIN us_user cb ON rc.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON rc.LAST_MODIFIED_BY=lmb.USER_ID ";

    private final String sqlListString = this.sqlListStringBase + " WHERE TRUE AND c.ACTIVE ";

    private final String sqlListForProgramString = "SELECT  "
            + "     rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, c.LABEL_ID `COUNTRY_LABEL_ID`, c.COUNTRY_CODE, c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_SP `COUNTRY_LABEL_SP`, c.LABEL_PR `COUNTRY_LABEL_PR`, "
            + "     ha.HEALTH_AREA_ID, ha.HEALTH_AREA_CODE, ha.LABEL_ID `HEALTH_AREA_LABEL_ID`, ha.LABEL_EN `HEALTH_AREA_LABEL_EN`, ha.LABEL_FR `HEALTH_AREA_LABEL_FR`, ha.LABEL_SP `HEALTH_AREA_LABEL_SP`, ha.LABEL_PR `HEALTH_AREA_LABEL_PR` "
            + "FROM vw_realm r  "
            + "LEFT JOIN rm_realm_country rc ON r.REALM_ID=rc.REALM_ID "
            + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN vw_program p ON rc.REALM_COUNTRY_ID=p.REALM_COUNTRY_ID "
            + "LEFT JOIN vw_health_area ha ON FIND_IN_SET(ha.HEALTH_AREA_ID, p.HEALTH_AREA_ID) "
            + "LEFT JOIN rm_health_area_country hac ON ha.HEALTH_AREA_ID = hac.HEALTH_AREA_ID AND rc.REALM_COUNTRY_ID=hac.REALM_COUNTRY_ID "
            + "LEFT JOIN rm_organisation o ON p.ORGANISATION_ID=o.ORGANISATION_ID "
            + "LEFT JOIN us_user cb ON ha.CREATED_BY=cb.USER_ID  "
            + "LEFT JOIN us_user lmb ON ha.LAST_MODIFIED_BY=lmb.USER_ID  "
            + "WHERE r.REALM_ID=:realmId AND r.ACTIVE AND c.ACTIVE AND rc.ACTIVE AND p.ACTIVE AND ha.ACTIVE AND hac.ACTIVE ";

    private final String sqlListStringForRealmCountryPlanningUnit = "SELECT rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,   "
            + "      rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`,  "
            + "      pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`,  "
            + "      rcpu.SKU_CODE, rcpu.MULTIPLIER, "
            + "      rcpu.LABEL_ID, rcpu.LABEL_EN, rcpu.LABEL_FR, rcpu.LABEL_SP, rcpu.LABEL_PR, "
            + "      u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP  `UNIT_LABEL_SP`, u.LABEL_PR  `UNIT_LABEL_PR`, "
            + "      rcpu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rcpu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rcpu.LAST_MODIFIED_DATE "
            + "FROM vw_realm_country_planning_unit rcpu "
            + "LEFT JOIN rm_realm_country rc  ON rc.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID "
            + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID  "
            + "LEFT JOIN vw_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID  "
            + "LEFT JOIN vw_unit u ON rcpu.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN us_user cb ON rcpu.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON rcpu.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE AND c.ACTIVE AND rc.ACTIVE ";

    private final String sqlListStringForRealmCountryPlanningUnitByProgram = "SELECT rcpu.REALM_COUNTRY_PLANNING_UNIT_ID, "
            + "              rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, "
            + "              pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
            + "              rcpu.SKU_CODE, rcpu.MULTIPLIER, "
            + "              rcpu.LABEL_ID, rcpu.LABEL_EN, rcpu.LABEL_FR, rcpu.LABEL_SP, rcpu.LABEL_PR, "
            + "              u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP  `UNIT_LABEL_SP`, u.LABEL_PR  `UNIT_LABEL_PR`, "
            + "              rcpu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rcpu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rcpu.LAST_MODIFIED_DATE "
            + "FROM vw_program p "
            + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
            + "LEFT JOIN vw_realm_country_planning_unit rcpu ON ppu.PLANNING_UNIT_ID=rcpu.PLANNING_UNIT_ID AND p.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID "
            + "LEFT JOIN rm_realm_country rc  ON rc.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID "
            + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + "LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID "
            + "LEFT JOIN vw_planning_unit pu ON rcpu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
            + "LEFT JOIN vw_unit u ON rcpu.UNIT_ID=u.UNIT_ID "
            + "LEFT JOIN us_user cb ON rcpu.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON rcpu.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE AND c.ACTIVE AND rc.ACTIVE AND ppu.ACTIVE AND p.ACTIVE ";

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int addRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_realm_country").usingGeneratedKeyColumns("REALM_COUNTRY_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", realmCountry.getRealm().getRealmId());
        params.put("COUNTRY_ID", realmCountry.getCountry().getCountryId());
        params.put("DEFAULT_CURRENCY_ID", realmCountry.getDefaultCurrency().getCurrencyId());
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("ACTIVE", true);
        return si.executeAndReturnKey(params).intValue();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public int updateRealmCountry(RealmCountry realmCountry, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE rm_realm_country rc SET "
                + "DEFAULT_CURRENCY_ID=:defaultCurrencyId, "
                + "ACTIVE=:active, "
                + "LAST_MODIFIED_BY=:curUser, "
                + "LAST_MODIFIED_DATE=:curDate "
                + "WHERE REALM_COUNTRY_ID=:realmCountryId "
                + "AND ("
                + "DEFAULT_CURRENCY_ID!=:defaultCurrencyId OR "
                + "ACTIVE!=:active) ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountry.getRealmCountryId());
        params.put("defaultCurrencyId", realmCountry.getDefaultCurrency().getCurrencyId());
        params.put("lastModifiedDate", curDate);
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("active", realmCountry.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<RealmCountry> getRealmCountryList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addUserAclForRealmCountry(sqlStringBuilder, params, "rc", curUser);
        sqlStringBuilder.append(" ORDER BY c.COUNTRY_CODE ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
    }

    @Override
    public RealmCountry getRealmCountryById(int realmCountryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND rc.REALM_COUNTRY_ID=:realmCountryId ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmCountryId", realmCountryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        try {
            return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            return null;
        }
    }

    @Override
    public RealmCountry getRealmCountryByRealmAndCountry(int realmId, int countryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND rc.COUNTRY_ID=:countryId ");
        Map<String, Object> params = new HashMap<>();
        params.put("countryId", countryId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", realmId, curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", realmId, curUser);
        sqlStringBuilder.append(" ORDER BY c.COUNTRY_CODE ");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountryPlanningUnit> getPlanningUnitListForRealmCountryId(int realmCountryId, boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForRealmCountryPlanningUnit).append("    AND rcpu.REALM_COUNTRY_ID=:realmCountryId ");
        Map<String, Object> params = new HashMap<>();
        if (active) {
            sqlStringBuilder = sqlStringBuilder.append(" AND rcpu.ACTIVE ");
        }

        params.put("realmCountryId", realmCountryId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryPlanningUnitRowMapper());
    }

    @Override
    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForProgramList(String[] programIds, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForRealmCountryPlanningUnitByProgram).append(" AND p.PROGRAM_ID IN (").append(getProgramIdString(programIds)).append(")");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        System.out.println(sqlStringBuilder.toString());
        System.out.println(params);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryPlanningUnitRowMapper());
    }

    public String getProgramIdString(String[] programIds) {
        if (programIds == null) {
            return "";
        } else {
            String opt = String.join("','", programIds);
            if (programIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    @Override
    public List<RealmCountryHealthArea> getRealmCountryListByRealmIdForActivePrograms(int realmId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListForProgramString);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", realmId, curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY rc.REALM_COUNTRY_ID, ha.HEALTH_AREA_ID ORDER BY c.COUNTRY_CODE, ha.HEALTH_AREA_CODE");
        System.out.println(LogUtils.buildStringForLog(sqlStringBuilder.toString(), params));
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryHealthAreaResultSetExtractor());
    }

    @Override
    @Transactional
    public int savePlanningUnitForCountry(RealmCountryPlanningUnit[] realmCountryPlanningUnits, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_realm_country_planning_unit");
        List<SqlParameterSource> insertList = new ArrayList<>();
        List<SqlParameterSource> updateList = new ArrayList<>();
        int rowsEffected = 0;
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params;
        Version version = null;
        for (RealmCountryPlanningUnit rcpu : realmCountryPlanningUnits) {
            System.out.println("------------rcpu-------------" + rcpu);
            if (rcpu.getRealmCountryPlanningUnitId() == 0) {
                // Insert
                params = new HashMap<>();
                params.put("PLANNING_UNIT_ID", rcpu.getPlanningUnit().getId());
                params.put("REALM_COUNTRY_ID", rcpu.getRealmCountry().getId());
                params.put("SKU_CODE", rcpu.getSkuCode());
                int labelId = this.labelDao.addLabel(rcpu.getLabel(), LabelConstants.RM_REALM_COUNTRY_PLANNING_UNIT, curUser.getUserId());
                params.put("LABEL_ID", labelId);
                params.put("MULTIPLIER", rcpu.getMultiplier());
//                params.put("GTIN", rcpu.getGtin());
                params.put("UNIT_ID", rcpu.getUnit().getUnitId());
                params.put("CREATED_DATE", curDate);
                params.put("CREATED_BY", curUser.getUserId());
                params.put("LAST_MODIFIED_DATE", curDate);
                params.put("LAST_MODIFIED_BY", curUser.getUserId());
                params.put("ACTIVE", true);
                insertList.add(new MapSqlParameterSource(params));
            } else {
                // Update
                params = new HashMap<>();
                params.put("realmCountryPlanningUnitId", rcpu.getRealmCountryPlanningUnitId());
                params.put("skuCode", rcpu.getSkuCode());
                params.put("multiplier", rcpu.getMultiplier());
//                params.put("gtin", rcpu.getGtin());
                params.put("label_en", rcpu.getLabel().getLabel_en());
                params.put("unitId", rcpu.getUnit().getUnitId());
                params.put("curDate", curDate);
                params.put("curUser", curUser.getUserId());
                params.put("active", rcpu.isActive());
                updateList.add(new MapSqlParameterSource(params));
            }
        }
        if (insertList.size() > 0) {
            SqlParameterSource[] insertParams = new SqlParameterSource[insertList.size()];
            rowsEffected += si.executeBatch(insertList.toArray(insertParams)).length;
        }
        if (updateList.size() > 0) {
            String sql = "DROP TEMPORARY TABLE IF EXISTS `tmp_realm_country_planning_unit`";
            params = new HashMap<>();
            this.namedParameterJdbcTemplate.update(sql, params);
            sql = "CREATE TEMPORARY TABLE `tmp_realm_country_planning_unit` ( "
                    + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT UNSIGNED NULL) ";
            this.namedParameterJdbcTemplate.update(sql, params);
            sql = "INSERT INTO tmp_realm_country_planning_unit SELECT p.`REALM_COUNTRY_PLANNING_UNIT_ID` from rm_realm_country_planning_unit p WHERE p.MULTIPLIER!=:multiplier AND p.`REALM_COUNTRY_PLANNING_UNIT_ID`=:realmCountryPlanningUnitId";
            SqlParameterSource[] updateParams = new SqlParameterSource[updateList.size()];
            int rowsInserted = this.namedParameterJdbcTemplate.batchUpdate(sql, updateList.toArray(updateParams)).length;
            String sqlString = "UPDATE "
                    + "rm_realm_country_planning_unit rcpu "
                    + "LEFT JOIN ap_label rcpul ON rcpu.LABEL_ID=rcpul.LABEL_ID "
                    + "SET "
                    + "rcpu.SKU_CODE=:skuCode, "
                    + "rcpu.MULTIPLIER=:multiplier, "
                    //                    + "rcpu.GTIN=:gtin, "
                    + "rcpul.LABEL_EN=:label_en, "
                    + "rcpu.UNIT_ID=:unitId, "
                    + "rcpu.ACTIVE=:active, "
                    + "rcpu.LAST_MODIFIED_DATE=IF(rcpu.ACTIVE!=:active OR rcpu.SKU_CODE!=:skuCode OR rcpu.MULTIPLIER!=:multiplier OR "
                    //                    + "OR rcpu.GTIN!=:gtin OR "
                    + "rcpul.LABEL_EN!=:label_en OR rcpu.UNIT_ID!=:unitId, :curDate, rcpu.LAST_MODIFIED_DATE), "
                    + "rcpu.LAST_MODIFIED_BY=IF(rcpu.ACTIVE!=:active OR rcpu.SKU_CODE!=:skuCode OR rcpu.MULTIPLIER!=:multiplier "
                    //                    + "OR rcpu.GTIN!=:gtin "
                    + "OR rcpul.LABEL_EN!=:label_en OR rcpu.UNIT_ID!=:unitId, :curUser, rcpu.LAST_MODIFIED_BY), "
                    + "rcpul.LAST_MODIFIED_DATE=IF(rcpul.LABEL_EN!=:label_en, :curDate, rcpul.LAST_MODIFIED_DATE), "
                    + "rcpul.LAST_MODIFIED_BY=IF(rcpul.LABEL_EN!=:label_en, :curUser, rcpul.LAST_MODIFIED_BY) "
                    + "WHERE rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=:realmCountryPlanningUnitId";
            rowsEffected += this.namedParameterJdbcTemplate.batchUpdate(sqlString, updateList.toArray(updateParams)).length;
            if (rowsInserted > 0) {
                sql = "select p.PROGRAM_ID\n"
                        + "from vw_realm_country_planning_unit rcpu \n"
                        + "left join rm_program_planning_unit ppu on rcpu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID\n"
                        + "left join rm_program p on p.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID and p.PROGRAM_ID=ppu.PROGRAM_ID\n"
                        + "where rcpu.REALM_COUNTRY_PLANNING_UNIT_ID in (select trcpu.REALM_COUNTRY_PLANNING_UNIT_ID from tmp_realm_country_planning_unit trcpu) and p.PROGRAM_ID is not null;";
                List<Integer> programIds = this.namedParameterJdbcTemplate.queryForList(sql, params, Integer.class);
                for (Integer pId : programIds) {
                    params.put("programId", pId);
                    params.put("curUser", curUser.getUserId());
                    params.put("curDate", curDate);
                    params.put("versionTypeId", 1);
                    params.put("versionStatusId", 1);
                    params.put("notes", null);
                    sqlString = "CALL getVersionId(:programId, :versionTypeId, :versionStatusId, :notes, null, null, :curUser, :curDate)";
                    version = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new VersionRowMapper());
                    sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
                    this.namedParameterJdbcTemplate.update(sqlString, params);
                    sqlString = "CREATE TEMPORARY TABLE `tmp_consumption` ( "
                            //        sqlString = "CREATE TABLE `tmp_consumption` ( "
                            + "  `ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, "
                            + "  `CONSUMPTION_ID` INT UNSIGNED NULL, "
                            + "  `REGION_ID` INT(10) UNSIGNED NOT NULL, "
                            + "  `PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                            + "  `REALM_COUNTRY_PLANNING_UNIT_ID` INT(10) UNSIGNED NOT NULL, "
                            + "  `CONSUMPTION_DATE` DATE NOT NULL, "
                            + "  `ACTUAL_FLAG` TINYINT UNSIGNED NOT NULL, "
                            + "  `RCPU_QTY` DOUBLE UNSIGNED NOT NULL, "
                            + "  `QTY` DOUBLE UNSIGNED NOT NULL, "
                            + "  `DAYS_OF_STOCK_OUT` INT UNSIGNED NOT NULL, "
                            + "  `DATA_SOURCE_ID` INT(10) UNSIGNED NOT NULL, "
                            + "  `NOTES` TEXT NULL, "
                            + "  `CREATED_BY` INT UNSIGNED NOT NULL, "
                            + "  `CREATED_DATE` DATETIME NOT NULL, "
                            + "  `LAST_MODIFIED_BY` INT UNSIGNED NOT NULL, "
                            + "  `LAST_MODIFIED_DATE` DATETIME NOT NULL, "
                            + "  `ACTIVE` TINYINT UNSIGNED NOT NULL DEFAULT 1, "
                            + "  `VERSION_ID` INT(10) NULL, "
                            + "  PRIMARY KEY (`ID`), "
                            + "  INDEX `fk_tmp_consumption_1_idx` (`CONSUMPTION_ID` ASC), "
                            + "  INDEX `fk_tmp_consumption_2_idx` (`REGION_ID` ASC), "
                            + "  INDEX `fk_tmp_consumption_3_idx` (`PLANNING_UNIT_ID` ASC), "
                            + "  INDEX `fk_tmp_consumption_4_idx` (`DATA_SOURCE_ID` ASC),"
                            + "  INDEX `fk_tmp_consumption_5_idx` (`VERSION_ID` ASC))";
                    this.namedParameterJdbcTemplate.update(sqlString, params);
                    sql = "INSERT INTO tmp_consumption select null,c.CONSUMPTION_ID,ct.REGION_ID,ct.PLANNING_UNIT_ID,ct.REALM_COUNTRY_PLANNING_UNIT_ID,ct.CONSUMPTION_DATE,ct.ACTUAL_FLAG,ct.CONSUMPTION_RCPU_QTY,ct.CONSUMPTION_RCPU_QTY*rcpu.MULTIPLIER,ct.DAYS_OF_STOCK_OUT,ct.DATA_SOURCE_ID,ct.NOTES,:curUser,:curDate,:curUser,:curDate,ct.ACTIVE,:versionId from rm_consumption c \n"
                            + "left join rm_consumption_trans ct on c.CONSUMPTION_ID=ct.CONSUMPTION_ID and c.MAX_VERSION_ID=ct.VERSION_ID\n"
                            + "left join rm_realm_country_planning_unit rcpu on rcpu.REALM_COUNTRY_PLANNING_UNIT_ID=ct.REALM_COUNTRY_PLANNING_UNIT_ID\n"
                            + "where ct.REALM_COUNTRY_PLANNING_UNIT_ID in (select trcpu.REALM_COUNTRY_PLANNING_UNIT_ID from tmp_realm_country_planning_unit trcpu) and c.PROGRAM_ID=:programId;";
                    params.put("versionId", version.getVersionId());
                    this.namedParameterJdbcTemplate.update(sql, params);
                    sqlString = "INSERT INTO rm_consumption_trans SELECT null, tc.CONSUMPTION_ID, tc.REGION_ID, tc.PLANNING_UNIT_ID, tc.CONSUMPTION_DATE, tc.REALM_COUNTRY_PLANNING_UNIT_ID, tc.ACTUAL_FLAG, tc.QTY, tc.RCPU_QTY, tc.DAYS_OF_STOCK_OUT, tc.DATA_SOURCE_ID, tc.NOTES, tc.ACTIVE, tc.LAST_MODIFIED_BY, tc.LAST_MODIFIED_DATE, :versionId"
                            + " FROM fasp.tmp_consumption tc ";
                    int consumptionRows = this.namedParameterJdbcTemplate.update(sqlString, params);
                    sqlString = "UPDATE tmp_consumption tc LEFT JOIN rm_consumption c ON c.CONSUMPTION_ID=tc.CONSUMPTION_ID SET c.MAX_VERSION_ID=:versionId, c.LAST_MODIFIED_BY=tc.LAST_MODIFIED_BY, c.LAST_MODIFIED_DATE=tc.LAST_MODIFIED_DATE";
                    this.namedParameterJdbcTemplate.update(sqlString, params);
                    sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_consumption`";
                    this.namedParameterJdbcTemplate.update(sqlString, params);

                    try {
                        this.programDataDao.getNewSupplyPlanList(pId, version.getVersionId(), true, false);
                    } catch (ParseException ex) {
                        Logger.getLogger(RealmCountryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
            sqlString = "DROP TEMPORARY TABLE IF EXISTS `tmp_realm_country_planning_unit`";
            this.namedParameterJdbcTemplate.update(sqlString, params);
        }
        return rowsEffected;
    }

    @Override
    public List<RealmCountry> getRealmCountryListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND rc.LAST_MODIFIED_DATE>:lastSyncDate ORDER BY c.COUNTRY_CODE");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT  "
                + "    rc.REALM_COUNTRY_ID,  "
                + "    r.REALM_ID, r.LABEL_ID `REALM_LABEL_ID`, r.LABEL_EN `REALM_LABEL_EN`, r.LABEL_FR `REALM_LABEL_FR`, r.LABEL_PR `REALM_LABEL_PR`, r.LABEL_SP `REALM_LABEL_SP`, r.REALM_CODE,  "
                + "    c.COUNTRY_ID, c.COUNTRY_CODE, c.LABEL_ID `COUNTRY_LABEL_ID`,c.LABEL_EN `COUNTRY_LABEL_EN`, c.LABEL_FR `COUNTRY_LABEL_FR`, c.LABEL_PR `COUNTRY_LABEL_PR`, c.LABEL_SP `COUNTRY_LABEL_SP`,  "
                + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, cu.LABEL_ID `CURRENCY_LABEL_ID`, cu.LABEL_EN `CURRENCY_LABEL_EN`, cu.LABEL_FR `CURRENCY_LABEL_FR`, cu.LABEL_PR `CURRENCY_LABEL_PR`, cu.LABEL_SP `CURRENCY_LABEL_SP`,  "
                + "    un.UNIT_ID, un.UNIT_CODE, un.LABEL_ID `UNIT_LABEL_ID`, un.LABEL_EN `UNIT_LABEL_EN`, un.LABEL_FR `UNIT_LABEL_FR`, un.LABEL_PR `UNIT_LABEL_PR`, un.LABEL_SP `UNIT_LABEL_SP`,  "
                + "    rc.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rc.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rc.LAST_MODIFIED_DATE  "
                + "FROM vw_program p "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + " LEFT JOIN vw_realm r ON rc.REALM_ID=r.REALM_ID  "
                + " LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
                + " LEFT JOIN vw_currency cu ON rc.DEFAULT_CURRENCY_ID=cu.CURRENCY_ID  "
                + " LEFT JOIN vw_unit un ON rc.PALLET_UNIT_ID=un.UNIT_ID  "
                + " LEFT JOIN us_user cb ON rc.CREATED_BY=cb.USER_ID  "
                + " LEFT JOIN us_user lmb ON rc.LAST_MODIFIED_BY=lmb.USER_ID "
                + " WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND rc.`REALM_COUNTRY_ID`  IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY c.COUNTRY_CODE");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListStringForRealmCountryPlanningUnit).append(" AND rcpu.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryPlanningUnitRowMapper());
    }

    @Override
    public List<RealmCountryPlanningUnit> getRealmCountryPlanningUnitListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT rcpu.REALM_COUNTRY_PLANNING_UNIT_ID,  "
                + "      rc.REALM_COUNTRY_ID, c.LABEL_ID `REALM_COUNTRY_LABEL_ID`, c.LABEL_EN `REALM_COUNTRY_LABEL_EN`, c.LABEL_FR `REALM_COUNTRY_LABEL_FR`, c.LABEL_PR `REALM_COUNTRY_LABEL_PR`, c.LABEL_SP `REALM_COUNTRY_LABEL_SP`, "
                + "      pu.PLANNING_UNIT_ID, pu.LABEL_ID `PLANNING_UNIT_LABEL_ID`, pu.LABEL_EN `PLANNING_UNIT_LABEL_EN`, pu.LABEL_FR `PLANNING_UNIT_LABEL_FR`, pu.LABEL_PR `PLANNING_UNIT_LABEL_PR`, pu.LABEL_SP `PLANNING_UNIT_LABEL_SP`, "
                + "      rcpu.SKU_CODE, rcpu.MULTIPLIER,  "
                + "      rcpu.LABEL_ID, rcpu.LABEL_EN, rcpu.LABEL_FR, rcpu.LABEL_SP, rcpu.LABEL_PR,  "
                + "      u.UNIT_ID, u.UNIT_CODE, u.LABEL_ID `UNIT_LABEL_ID`, u.LABEL_EN `UNIT_LABEL_EN`, u.LABEL_FR `UNIT_LABEL_FR`, u.LABEL_SP  `UNIT_LABEL_SP`, u.LABEL_PR  `UNIT_LABEL_PR`,  "
                + "      rcpu.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, rcpu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, rcpu.LAST_MODIFIED_DATE  "
                + "FROM vw_program p  "
                + "LEFT JOIN rm_program_planning_unit ppu ON p.PROGRAM_ID=ppu.PROGRAM_ID "
                + "LEFT JOIN rm_realm_country rc  ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID  "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID  "
                + "LEFT JOIN vw_realm_country_planning_unit rcpu ON rc.REALM_COUNTRY_ID=rcpu.REALM_COUNTRY_ID AND rcpu.PLANNING_UNIT_ID=ppu.PLANNING_UNIT_ID "
                + "LEFT JOIN vw_unit u ON rcpu.UNIT_ID=u.UNIT_ID  "
                + "LEFT JOIN vw_planning_unit pu ON ppu.PLANNING_UNIT_ID=pu.PLANNING_UNIT_ID "
                + "LEFT JOIN us_user cb ON rcpu.CREATED_BY=cb.USER_ID  "
                + "LEFT JOIN us_user lmb ON rcpu.LAST_MODIFIED_BY=lmb.USER_ID  "
                + " WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND rcpu.`REALM_COUNTRY_PLANNING_UNIT_ID`  IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "rc", curUser);
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        sqlStringBuilder.append(" GROUP BY rcpu.REALM_COUNTRY_PLANNING_UNIT_ID");
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new RealmCountryPlanningUnitRowMapper());
    }

}
