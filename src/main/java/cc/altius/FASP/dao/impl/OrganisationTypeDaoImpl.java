/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.utils.DateUtils;
import java.util.Date;
import cc.altius.FASP.dao.OrganisationTypeDao;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.OrganisationType;
import cc.altius.FASP.model.rowMapper.OrganisationTypeRowMapper;
import cc.altius.FASP.service.AclService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class OrganisationTypeDaoImpl implements OrganisationTypeDao {

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

    private final String sqlListString = " SELECT "
            + " ot.ORGANISATION_TYPE_ID,  ol.LABEL_ID, ol.LABEL_EN, ol.LABEL_FR, ol.LABEL_SP, ol.LABEL_PR, "
            + " r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
            + " ot.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, ot.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, ot.LAST_MODIFIED_DATE "
            + " FROM rm_organisation_type ot "
            + " LEFT JOIN rm_realm r ON ot.REALM_ID=r.REALM_ID "
            + " LEFT JOIN ap_label ol ON ot.LABEL_ID=ol.LABEL_ID "
            + " LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
            + " LEFT JOIN us_user cb ON ot.CREATED_BY=cb.USER_ID "
            + " LEFT JOIN us_user lmb ON ot.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Override
    @Transactional
    public int addOrganisationType(OrganisationType o, CustomUserDetails curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.dataSource).withTableName("rm_organisation_type").usingGeneratedKeyColumns("ORGANISATION_TYPE_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_ID", curUser.getRealm().getRealmId());
        int labelId = this.labelDao.addLabel(o.getLabel(), LabelConstants.RM_ORGANISATION_TYPE, curUser.getUserId());
        params.put("LABEL_ID", labelId);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int organisationTypeId = si.executeAndReturnKey(params).intValue();
        return organisationTypeId;
    }

    @Override
    public int updateOrganisationType(OrganisationType ot, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("organisationTypeId", ot.getOrganisationTypeId());
        params.put("labelEn", ot.getLabel().getLabel_en());
        params.put("active", ot.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        int rows = this.namedParameterJdbcTemplate.update("UPDATE rm_organisation_type ot LEFT JOIN ap_label ol ON ot.LABEL_ID=ol.LABEL_ID "
                + "SET "
                + "ot.ACTIVE=:active, "
                + "ot.LAST_MODIFIED_BY=:curUser, "
                + "ot.LAST_MODIFIED_DATE=:curDate, "
                + "ol.LABEL_EN=:labelEn, "
                + "ol.LAST_MODIFIED_BY=:curUser, "
                + "ol.LAST_MODIFIED_DATE=:curDate "
                + "WHERE ot.ORGANISATION_TYPE_ID=:organisationTypeId", params);

        return rows;
    }

    @Override
    public List<OrganisationType> getOrganisationTypeList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "ot", curUser);
        if (active) {
            sqlStringBuilder.append(" AND ot.ACTIVE ");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationTypeRowMapper());
    }

    @Override
    public List<OrganisationType> getOrganisationTypeListByRealmId(int realmId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "ot", curUser);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "ot", realmId, curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationTypeRowMapper());
    }

    @Override
    public OrganisationType getOrganisationTypeById(int organisationTypeId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND ot.ORGANISATION_TYPE_ID=:organisationTypeId ");
        Map<String, Object> params = new HashMap<>();
        params.put("organisationTypeId", organisationTypeId);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "ot", curUser);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new OrganisationTypeRowMapper());
    }

    @Override
    public List<OrganisationType> getOrganisationTypeListForSync(String lastSyncDate, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND ot.LAST_MODIFIED_DATE>:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        this.aclService.addUserAclForRealm(sqlStringBuilder, params, "ot", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new OrganisationTypeRowMapper());
    }
}
