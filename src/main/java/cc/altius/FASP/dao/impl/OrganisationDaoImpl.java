/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.rowMapper.OrganisationListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.OrganisationResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
public class OrganisationDaoImpl implements OrganisationDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    @Transactional
    public int addOrganisation(Organisation o, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_organisation").usingGeneratedKeyColumns("ORGANISATION_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", o.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(o.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int organisationId = si.executeAndReturnKey(params).intValue();

        si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_organisation_country");
        SqlParameterSource[] paramList = new SqlParameterSource[o.getRealmCountryArray().length];
        int i = 0;
        for (int realmCountryId : o.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("ORGANISATION_ID", organisationId);
            params.put("REALM_COUNTRY_ID", realmCountryId);
            params.put("ACTIVE", true);
            params.put("CREATED_BY", curUser);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return organisationId;

    }

    @Override
    public int updateOrganisation(Organisation o, int curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("organisationId", o.getOrganisationId());
        params.put("active", o.isActive());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        int rows = nm.update("UPDATE rm_organisation o SET o.ACTIVE=:active, o.LAST_MODIFIED_BY=:curUser, o.LAST_MODIFIED_DATE=:curDate WHERE o.ORGANISATION_ID=:organisationId", params);
        this.jdbcTemplate.update("DELETE FROM rm_organisation_country WHERE ORGANISATION_ID=?", o.getOrganisationId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("rm_organisation_country");
        SqlParameterSource[] paramList = new SqlParameterSource[o.getRealmCountryArray().length];
        int i = 0;
        for (int realmCountryId : o.getRealmCountryArray()) {
            params = new HashMap<>();
            params.put("ORGANISATION_ID", o.getOrganisationId());
            params.put("REALM_COUNTRY_ID", realmCountryId);
            params.put("CREATED_BY", curUser);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("ACTIVE", true);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return rows;
    }

    @Override
    public List<Organisation> getOrganisationList() {
        String sqlString = " SELECT "
                + "                 o.ORGANISATION_ID, o.CODE, ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
                + "                 r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "                 o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
                + "                 rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + "                 FROM rm_organisation o "
                + "                 LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + "                 LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "                 LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "                 LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + "                 LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + "                 LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + "                 LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "                 LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "                 LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID; ";
        return this.jdbcTemplate.query(sqlString, new OrganisationListResultSetExtractor());
    }

    @Override
    public Organisation getOrganisationById(int organisationId) {

        String sqlString = " SELECT "
                + "                 o.ORGANISATION_ID, o.CODE, ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
                + "                 r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "                 o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE, "
                + "                 rc.REALM_COUNTRY_ID, rc.COUNTRY_ID, cl.LABEL_ID `COUNTRY_LABEL_ID`, cl.LABEL_EN `COUNTRY_LABEL_EN`, cl.LABEL_FR `COUNTRY_LABEL_FR`, cl.LABEL_SP `COUNTRY_LABEL_SP`, cl.LABEL_PR `COUNTRY_LABEL_PR` "
                + "                 FROM rm_organisation o "
                + "                 LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + "                 LEFT JOIN ap_label ol ON o.LABEL_ID=ol.LABEL_ID "
                + "                 LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "                 LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + "                 LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + "                 LEFT JOIN rm_organisation_country oc ON o.ORGANISATION_ID=oc.ORGANISATION_ID "
                + "                 LEFT JOIN rm_realm_country rc ON oc.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "                 LEFT JOIN ap_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "                 LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "                 WHERE o.ORGANISATION_ID=?; ";

        return this.jdbcTemplate.query(sqlString, new OrganisationResultSetExtractor(), organisationId);
    }

}
