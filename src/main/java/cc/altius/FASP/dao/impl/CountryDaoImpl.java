/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.CountryDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.rowMapper.CountryRowMapper;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.utils.LogUtils;
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
public class CountryDaoImpl implements CountryDao {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT c.COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, "
            + "	c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_PR, c.LABEL_SP, "
            + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, "
            + "    cu.LABEL_ID `CURRENCY_LABEL_ID`, cu.LABEL_EN `CURRENCY_LABEL_EN`, cu.LABEL_FR `CURRENCY_LABEL_FR`, cu.LABEL_PR `CURRENCY_LABEL_PR`, cu.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, c.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, c.LAST_MODIFIED_DATE, c.ACTIVE  "
            + "FROM vw_country c  "
            + "LEFT JOIN vw_currency cu ON c.CURRENCY_ID=cu.CURRENCY_ID "
            + "LEFT JOIN us_user cb ON c.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON c.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Transactional
    @Override
    public int addCountry(Country country, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedLabelRowId = this.labelDao.addLabel(country.getLabel(), LabelConstants.AP_COUNTRY, curUser.getUserId());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_country").usingGeneratedKeyColumns("COUNTRY_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_ID", country.getCurrency().getId());
        map.put("COUNTRY_CODE", country.getCountryCode());
        map.put("COUNTRY_CODE2", country.getCountryCode2());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        return insert.executeAndReturnKey(map).intValue();
    }

    @Override
    public int updateCountry(Country country, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE ap_country c LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
                + "SET  "
                + "    c.COUNTRY_CODE=:countryCode, c.COUNTRY_CODE2=:countryCode2, c.CURRENCY_ID=:currencyId, c.ACTIVE=:active, "
                + "    c.LAST_MODIFIED_BY=:curUser, "
                + "    c.LAST_MODIFIED_DATE=:curDate, "
                + "    cl.LABEL_EN=:label_en,  "
                + "    cl.LAST_MODIFIED_BY=:curUser, "
                + "    cl.LAST_MODIFIED_DATE=:curDate "
                + "WHERE c.COUNTRY_ID=:countryId";
        Map<String, Object> params = new HashMap<>();
        params.put("countryId", country.getCountryId());
        params.put("countryCode", country.getCountryCode());
        params.put("countryCode2", country.getCountryCode2());
        params.put("currencyId", country.getCurrency().getId());
        params.put("label_en", country.getLabel().getLabel_en());
        params.put("active", country.isActive());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Country> getCountryList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        if (active) {
            sqlStringBuilder.append(" AND c.ACTIVE ORDER BY cl.`LABEL_EN`");
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), new CountryRowMapper());
    }

    @Override
    public Country getCountryById(int countryId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND c.COUNTRY_ID=:countryId");
        Map<String, Object> params = new HashMap<>();
        params.put("countryId", countryId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new CountryRowMapper());
    }

    @Override
    public List<Country> getCountryListForSync(String lastSyncDate) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND c.LAST_MODIFIED_DATE>=:lastSyncDate");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new CountryRowMapper());
    }

    @Override
    public List<Country> getCountryListForSyncProgram(String programIdsString, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder("SELECT c.COUNTRY_ID, c.COUNTRY_CODE, c.COUNTRY_CODE2, "
                + "	c.LABEL_ID, c.LABEL_EN, c.LABEL_FR, c.LABEL_PR, c.LABEL_SP, "
                + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, "
                + "    cu.LABEL_ID `CURRENCY_LABEL_ID`, cu.LABEL_EN `CURRENCY_LABEL_EN`, cu.LABEL_FR `CURRENCY_LABEL_FR`, cu.LABEL_PR `CURRENCY_LABEL_PR`, cu.LABEL_SP `CURRENCY_LABEL_SP`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, c.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, c.LAST_MODIFIED_DATE, c.ACTIVE  "
                + "FROM rm_program p "
                + "LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID "
                + "LEFT JOIN vw_country c ON rc.COUNTRY_ID=c.COUNTRY_ID "
                + "LEFT JOIN vw_currency cu ON c.CURRENCY_ID=cu.CURRENCY_ID "
                + "LEFT JOIN us_user cb ON c.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON c.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE p.PROGRAM_ID IN (").append(programIdsString).append(") AND c.COUNTRY_ID IS NOT NULL ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addFullAclForProgram(sqlStringBuilder, params, "p", curUser);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new CountryRowMapper());
    }

}
