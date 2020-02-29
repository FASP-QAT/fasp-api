/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.OrganisationDao;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.rowMapper.OrganisationRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
        return si.executeAndReturnKey(params).intValue();

    }

    @Override
    public int updateOrganisation(Organisation o, int curUser) {
        Map<String, Object> params = new HashMap<>();
        params.put("organisationId", o.getOrganisationId());
        params.put("active", o.isActive());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        return nm.update("UPDATE rm_organisation o SET o.ACTIVE=:active, o.LAST_MODIFIED_BY=:curUser, o.LAST_MODIFIED_DATE=:curDate WHERE o.ORGANISATION_ID=:organisationId", params);
    }

    @Override
    public List<Organisation> getOrganisationList() {
        String sqlString = " SELECT "
                + "o.ORGANISATION_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE "
                + "FROM rm_organisation o "
                + "LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON o.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID";
        return this.jdbcTemplate.query(sqlString, new OrganisationRowMapper());
    }

    @Override
    public Organisation getOrganisationById(int organisationId) {
        String sqlString = " SELECT "
                + "o.ORGANISATION_ID, hal.LABEL_ID, hal.LABEL_EN, hal.LABEL_FR, hal.LABEL_SP, hal.LABEL_PR, "
                + "r.REALM_ID, r.REALM_CODE, rl.LABEL_ID `REALM_LABEL_ID`, rl.LABEL_EN `REALM_LABEL_EN`, rl.LABEL_FR `REALM_LABEL_FR`, rl.LABEL_SP `REALM_LABEL_SP`, rl.LABEL_PR `REALM_LABEL_PR`, "
                + "o.ACTIVE, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, o.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, o.LAST_MODIFIED_DATE "
                + "FROM rm_organisation o "
                + "LEFT JOIN rm_realm r ON o.REALM_ID=r.REALM_ID "
                + "LEFT JOIN ap_label hal ON o.LABEL_ID=hal.LABEL_ID "
                + "LEFT JOIN ap_label rl ON r.LABEL_ID=rl.LABEL_ID "
                + "LEFT JOIN us_user cb ON o.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON o.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE o.ORGANISATION_ID=?";
        return this.jdbcTemplate.queryForObject(sqlString, new OrganisationRowMapper(), organisationId);
    }

}
