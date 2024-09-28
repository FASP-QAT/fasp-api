/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.rowMapper.OrganisationListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.OrganisationResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.util.Date;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.SuggestedDisplayName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class OrganisationDaoImpl implements OrganisationDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private final String sqlListString = " SELECT "
            + " o.ORGANISATION_ID, o.ORGANISATION_CODE, o.LABEL_ID, o.LABEL_EN, o.LABEL_FR, o.LABEL_SP, o.LABEL_PR, "
            + " r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
            + " o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
            + " rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR`, "
            + " o.ORGANISATION_TYPE_ID, o.TYPE_LABEL_ID, o.TYPE_LABEL_EN, o.TYPE_LABEL_FR, o.TYPE_LABEL_SP, o.TYPE_LABEL_PR"
            + " FROM vw_organisation o "
            + " LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
            + " LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
            + " LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
            + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
            + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addOrganisation(Organisation o, CustomUserDetails curUser) {
        SimpleJdbcInsert si;
        String sql = "INSERT INTO `fasp`.`rm_organisation` "
                + "(`REALM_ID`, `LABEL_ID`, `ORGANISATION_CODE`, `ORGANISATION_TYPE_ID`, `ACTIVE`, "
                + "`CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) "
                + "VALUES "
                + "(:REALM_ID, :LABEL_ID, :ORGANISATION_CODE, :ORGANISATION_TYPE_ID, :ACTIVE, "
                + ":CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE)";
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        params.put("ORGANISATION_TYPE_ID", o.getOrganisationType().getId());
        int labelId = this.labelDao.addLabel(o.getLabel(), LabelConstants.RM_ORGANISATION, curUser.getUserId());
        params.put("ORGANISATION_CODE", o.getOrganisationCode());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        this.namedParameterJdbcTemplate.update(sql, params);
        int organisationId = this.jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_organisation_country");
        SqlParameterSource[] paramList = new SqlParameterSource[o.getRealmCountryArray().length];
        int i = 0;
        for (String realmCountryId : o.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("ORGANISATION_ID", organisationId);
            params.put("REALM_COUNTRY_ID", realmCountryId);
            params.put("ACTIVE", true);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return organisationId;
    }

    @Override
    public int updateOrganisation(Organisation o, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("organisationId", o.getOrganisationId());
        params.put("organisationCode", o.getOrganisationCode());
        params.put("organisationTypeId", o.getOrganisationType().getId());
        params.put("labelEn", o.getLabel().getLabel_en());
        params.put("active", o.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_organisation o LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "SET "
                + "o.ACTIVE=:active, "
                + "o.ORGANISATION_CODE=:organisationCode, "
                + "o.ORGANISATION_TYPE_ID=:organisationTypeId, "
                + "o.LAST_MODIFIED_BY=:curUser, "
                + "o.LAST_MODIFIED_DATE=:curDate, "
                + "ol.LABEL_EN=:labelEn, "
                + "ol.LAST_MODIFIED_BY=:curUser, "
                + "ol.LAST_MODIFIED_DATE=:curDate "
                + "WHERE o.ORGANISATION_ID=:organisationId", params);
        this.namedParameterJdbcTemplate.update("DELETE FROM rm_organisation_country WHERE ORGANISATION_ID=:organisationId", params);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("rm_organisation_country");
        SqlParameterSource[] paramList = new SqlParameterSource[o.getRealmCountryArray().length];
        int i = 0;
        for (String realmCountryId : o.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("ORGANISATION_ID", o.getOrganisationId());
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
    public List<Organisation> getOrganisationList(CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlListString, params, "o", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationListResultSetExtractor());
    }

    @Override
    public List<SimpleCodeObject> getOrganisationDropdownList(int realmId, CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT o.ORGANISATION_ID `ID`, o.LABEL_ID, o.LABEL_EN, o.LABEL_FR, o.LABEL_SP, o.LABEL_PR, o.ORGANISATION_CODE `CODE` FROM vw_organisation o WHERE o.ACTIVE AND (o.REALM_ID=:realmId OR :realmId=-1) ");
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(stringBuilder, params, "o", curUser);
        this.aclService.addUserAclForOrganisation(stringBuilder, params, "o", curUser);
        stringBuilder.append(" ORDER BY o.LABEL_EN");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<SimpleCodeObject> getOrganisationDropdownListForRealmCountryId(int realmCountryId, CustomUserDetails curUser) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT o.ORGANISATION_ID `ID`, o.ORGANISATION_CODE `CODE`, o.LABEL_ID, o.LABEL_EN, o.LABEL_FR, o.LABEL_SP, o.LABEL_PR "
                + "FROM vw_organisation o "
                + "LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + "LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + "LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "WHERE :realmCountryId=-1 OR oc.REALM_COUNTRY_ID=:realmCountryId AND o.ACTIVE AND oc.ACTIVE ");
        this.aclService.addUserAclForOrganisation(sqlStringBuilder, params, "o", curUser);
        this.aclService.addUserAclForRealmCountry(sqlStringBuilder, params, "rc", curUser);
        sqlStringBuilder.append("GROUP BY o.ORGANISATION_ID ORDER BY o.ORGANISATION_CODE");
        params.put("realmCountryId", realmCountryId);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new SimpleCodeObjectRowMapper(""));
    }

    @Override
    public List<Organisation> getOrganisationListByRealmId(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlListString, params, "o", curUser);
        this.aclService.addUserAclForRealm(sqlListString, params, "o", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationListResultSetExtractor());
    }

    @Override
    public Organisation getOrganisationById(int organisationId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND o.ORGANISATION_ID=:organisationId ");
        Map<String, Object> params = new HashMap<>();
        params.put("organisationId", organisationId);
        this.aclService.addUserAclForRealm(sqlListString, params, "o", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationResultSetExtractor());
    }

    @Override
    public String getDisplayName(int realmId, String name, CustomUserDetails curUser) {
        String extractedName = SuggestedDisplayName.getAlphaNumericString(name, SuggestedDisplayName.ORGANISATION_LENGTH);
        String sqlString = "SELECT COUNT(*) CNT FROM rm_organisation pa WHERE pa.REALM_ID=:realmId AND UPPER(LEFT(pa.ORGANISATION_CODE,:len))=:extractedName";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        params.put("len", extractedName.length());
        params.put("extractedName", extractedName);
        int cnt = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class);
        return SuggestedDisplayName.getFinalDisplayName(extractedName, cnt);
    }

    @Override
    public List<Organisation> getOrganisationListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND o.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlListString, params, "o", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationListResultSetExtractor());
    }

}
