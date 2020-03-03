/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.rowMapper.RealmCountryRowMapper;
import cc.altius.FASP.model.rowMapper.RealmRowMapper;
import cc.altius.FASP.rest.controller.RealmRestController;
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
public class RealmDaoImpl implements RealmDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Override
    public List<Realm> getRealmList(boolean active) {
        String sql = " SELECT r.*,lb.`LABEL_ID` AS RM_LABEL_ID,lb.`LABEL_EN` RM_LABEL_EN,lb.`LABEL_FR` RM_LABEL_FR,lb.`LABEL_PR` RM_LABEL_PR,lb.`LABEL_SP` AS RM_LABEL_SP,cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` FROM rm_realm r "
                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` "
                + " LEFT JOIN us_user cb ON r.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON r.LAST_MODIFIED_BY=lmb.USER_ID ";
        return this.jdbcTemplate.query(sql, new RealmRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryList(boolean active) {
        String sql = "SELECT rc.`REALM_COUNTRY_ID`, "
                + "lc.`LABEL_ID` AS CU_LABEL_ID, "
                + "lc.`LABEL_EN` AS CU_LABEL_EN  "
                + ",lc.`LABEL_FR` AS CU_LABEL_FR  "
                + ",lc.`LABEL_SP` AS CU_LABEL_SP  "
                + ",lc.`LABEL_PR` AS CU_LABEL_PR, "
                + "lr.`LABEL_ID` AS RM_LABEL_ID,  "
                + "lr.`LABEL_EN` AS RM_LABEL_EN  "
                + ",lr.`LABEL_FR` AS RM_LABEL_FR "
                + ",lr.`LABEL_SP` AS RM_LABEL_SP "
                + ",lr.`LABEL_PR` AS RM_LABEL_PR, c.*,r.* "
                + " FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON c.`COUNTRY_ID`=rc.`COUNTRY_ID` "
                + "LEFT JOIN ap_label lc ON lc.`LABEL_ID`=c.`LABEL_ID` "
                + "LEFT JOIN rm_realm r ON r.`REALM_ID`=rc.`REALM_ID` "
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=r.`LABEL_ID` ";
        if (active) {
            sql += "WHERE rc.`ACTIVE`;";
        }
        return this.jdbcTemplate.query(sql, new RealmCountryRowMapper());
    }

    @Override
    public List<RealmCountry> getRealmCountryListByRealmId(int realmId) {
        String sql = "SELECT rc.`REALM_COUNTRY_ID`, "
                + "lc.`LABEL_ID` AS CU_LABEL_ID, "
                + "lc.`LABEL_EN` AS CU_LABEL_EN  "
                + ",lc.`LABEL_FR` AS CU_LABEL_FR  "
                + ",lc.`LABEL_SP` AS CU_LABEL_SP  "
                + ",lc.`LABEL_PR` AS CU_LABEL_PR, "
                + "lr.`LABEL_ID` AS RM_LABEL_ID,  "
                + "lr.`LABEL_EN` AS RM_LABEL_EN  "
                + ",lr.`LABEL_FR` AS RM_LABEL_FR "
                + ",lr.`LABEL_SP` AS RM_LABEL_SP "
                + ",lr.`LABEL_PR` AS RM_LABEL_PR, c.*,r.* "
                + " FROM rm_realm_country rc "
                + "LEFT JOIN ap_country c ON c.`COUNTRY_ID`=rc.`COUNTRY_ID` "
                + "LEFT JOIN ap_label lc ON lc.`LABEL_ID`=c.`LABEL_ID` "
                + "LEFT JOIN rm_realm r ON r.`REALM_ID`=rc.`REALM_ID` "
                + "LEFT JOIN ap_label lr ON lr.`LABEL_ID`=r.`LABEL_ID` WHERE rc.`ACTIVE` AND rc.`REALM_ID`=? ";

        return this.jdbcTemplate.query(sql, new RealmCountryRowMapper(), realmId);
    }

    @Override
    @Transactional
    public int addRealm(Realm r, int curUser) {
        SimpleJdbcInsert si = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("rm_realm").usingGeneratedKeyColumns("REALM_ID");
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("REALM_CODE", r.getRealmCode());
        int labelId = this.labelDao.addLabel(r.getLabel(), curUser);
        params.put("LABEL_ID", labelId);
        params.put("MONTHS_IN_PAST_FOR_AMC", r.getMonthInPastForAmc());
        params.put("MONTHS_IN_FUTURE_FOR_AMC", r.getMonthInFutureForAmc());
        params.put("ORDER_FREQUENCY", r.getOrderFrequency());
        params.put("DEFAULT_REALM", false);
        params.put("ACTIVE", true);
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int realmId = si.executeAndReturnKey(params).intValue();
        return realmId;
    }

    @Override
    @Transactional
    public int updateRealm(Realm r, int curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        Map<String, Object> params = new HashMap<>();
        params.put("realmId", r.getRealmId());
        params.put("realmCode", r.getRealmCode());
        params.put("monthInPastForAmc", r.getMonthInPastForAmc());
        params.put("monthInFutureForAmc", r.getMonthInFutureForAmc());
        params.put("orgerFrequency", r.getOrderFrequency());
        params.put("default_realm", r.isDefaultRealm());
        params.put("active", r.isActive());
        params.put("curUser", curUser);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(this.jdbcTemplate);
        int rows = nm.update("UPDATE rm_realm r SET r.REALM_CODE=:realmCode ,r.MONTHS_IN_PAST_FOR_AMC=:monthInPastForAmc ,r.MONTHS_IN_FUTURE_FOR_AMC=:monthInFutureForAmc ,r.ORDER_FREQUENCY=:orgerFrequency ,r.DEFAULT_REALM=:default_realm ,r.ACTIVE=:active ,r.LAST_MODIFIED_BY=:curUser, r.LAST_MODIFIED_DATE=:curDate WHERE r.REALM_ID=:realmId", params);
        return rows;
    }

    @Override
    public Realm getRealmById(int realmId) {
        String sql = " SELECT r.*,lb.`LABEL_ID` AS RM_LABEL_ID,lb.`LABEL_EN` RM_LABEL_EN,lb.`LABEL_FR` RM_LABEL_FR,lb.`LABEL_PR` RM_LABEL_PR,lb.`LABEL_SP` AS RM_LABEL_SP,cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`,lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME` FROM rm_realm r "
                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` "
                + " LEFT JOIN us_user cb ON r.CREATED_BY=cb.USER_ID "
                + " LEFT JOIN us_user lmb ON r.LAST_MODIFIED_BY=lmb.USER_ID ";
        sql += " WHERE r.REALM_ID=?; ";
        return this.jdbcTemplate.queryForObject(sql, new RealmRowMapper(), realmId);
    }

}
