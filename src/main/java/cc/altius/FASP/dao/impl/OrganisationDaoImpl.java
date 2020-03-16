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
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.model.DTO.rowMapper.PrgOrganisationDTORowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<PrgOrganisationDTO> getOrganisationListForSync(String lastSyncDate) {
        String sql = "SELECT o.`ACTIVE`,o.`LABEL_ID`,o.`ORGANISATION_ID`,o.`REALM_ID`\n"
                + ",l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_PR`,l.`LABEL_SP`\n"
                + "FROM rm_organisation o\n"
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=o.`LABEL_ID`";
        Map<String, Object> params = new HashMap<>();
        if (!lastSyncDate.equals("null")) {
            sql += " WHERE o.`LAST_MODIFIED_DATE`>:lastSyncDate;";
            params.put("lastSyncDate", lastSyncDate);
        }
        return this.namedParameterJdbcTemplate.query(sql, params, new PrgOrganisationDTORowMapper());
    }

    @Override
    @Transactional
    public int addOrganisation(Organisation o, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_organisation").usingGeneratedKeyColumns("ORGANISATION_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", o.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(o.getLabel(), curUser.getUserId());
        params.put("ORGANISATION_CODE", o.getOrganisationCode());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int organisationId = si.executeAndReturnKey(params).intValue();

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
        params.put("labelEn", o.getLabel().getLabel_en());
        params.put("active", o.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_organisation o LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "SET "
                + "o.ACTIVE=:active, "
                + "o.ORGANISATION_CODE=:organisationCode,"
                + "o.LAST_MODIFIED_BY=IF(o.ACTIVE!=:active OR o.ORGANISATION_CODE!=:organisationCode, :curUser, o.LAST_MODIFIED_BY), "
                + "o.LAST_MODIFIED_DATE=IF(o.ACTIVE!=:active OR o.ORGANISATION_CODE!=:organisationCode, :curDate, o.LAST_MODIFIED_DATE), "
                + "ol.LABEL_EN=:labelEn, "
                + "ol.LAST_MODIFIED_BY=IF(ol.LABEL_EN!=:labelEn, :curUser, ol.LAST_MODIFIED_BY), "
                + "ol.LAST_MODIFIED_DATE=IF(ol.LABEL_EN!=:labelEn, :curDate, ol.LAST_MODIFIED_DATE) "
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
        String sqlString = " SELECT "
                + " o.ORGANISATION_ID, o.ORGANISATION_CODE, ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
                + " r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + " o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
                + " rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + " FROM rm_organisation o "
                + " LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + " LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + " LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + " LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + " LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND o.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new OrganisationListResultSetExtractor());
    }
    
    @Override
    public List<Organisation> getOrganisationListByRealmId(int realmId, CustomUserDetails curUser) {
        String sqlString = " SELECT "
                + " o.ORGANISATION_ID, o.ORGANISATION_CODE, ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
                + " r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + " o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
                + " rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + " FROM rm_organisation o "
                + " LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + " LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + " LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + " LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + " LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "WHERE o.REALM_ID=:realmId ";
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND o.REALM_ID=:userRealmId ";
            params.put("userRealmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new OrganisationListResultSetExtractor());
    }

    @Override
    public Organisation getOrganisationById(int organisationId, CustomUserDetails curUser) {
            String sqlString = " SELECT "
                + " o.ORGANISATION_ID, o.ORGANISATION_CODE, ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
                + " r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + " o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
                + " rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + " FROM rm_organisation o "
                + " LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + " LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + " LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + " LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + " LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + " LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + " LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + " WHERE o.ORGANISATION_ID=:organisationId ";
        Map<String, Object> params = new HashMap<>();
        params.put("organisationId", organisationId);
        if (curUser.getRealm().getRealmId() != -1) {
            sqlString += "AND o.REALM_ID=:realmId ";
            params.put("realmId", curUser.getRealm().getRealmId());
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new OrganisationResultSetExtractor());
    }

}
