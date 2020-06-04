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
import cc.altius.FASP.model.rowMapper.CountryRowMapper;
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

    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String sqlListString = "SELECT c.COUNTRY_ID, c.COUNTRY_CODE, "
            + "	cl.LABEL_ID, cl.LABEL_EN, cl.LABEL_FR, cl.LABEL_PR, cl.LABEL_SP, "
            + "    cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, "
            + "    cul.LABEL_ID `CURRENCY_LABEL_ID`, cul.LABEL_EN `CURRENCY_LABEL_EN`, cul.LABEL_FR `CURRENCY_LABEL_FR`, cul.LABEL_PR `CURRENCY_LABEL_PR`, cul.LABEL_SP `CURRENCY_LABEL_SP`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, c.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, c.LAST_MODIFIED_DATE, c.ACTIVE  "
            + "FROM ap_country c  "
            + "LEFT JOIN ap_label cl ON c.LABEL_ID=cl.LABEL_ID "
            + "LEFT JOIN ap_currency cu ON c.CURRENCY_ID=cu.CURRENCY_ID "
            + "LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
            + "LEFT JOIN us_user cb ON c.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON c.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Transactional
    @Override
    public int addCountry(Country country, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        int insertedLabelRowId = this.labelDao.addLabel(country.getLabel(), curUser.getUserId());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_country").usingGeneratedKeyColumns("COUNTRY_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_ID", country.getCurrency().getId());
        map.put("COUNTRY_CODE", country.getCountryCode());
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
                + "    c.COUNTRY_CODE=:countryCode, c.CURRENCY_ID=:currencyId, c.ACTIVE=:active, "
                + "    c.LAST_MODIFIED_BY=IF(c.COUNTRY_CODE!=:countryCode OR c.CURRENCY_ID!=:currencyId OR c.ACTIVE!=:active,:curUser,c.LAST_MODIFIED_BY), "
                + "    c.LAST_MODIFIED_DATE=IF(c.COUNTRY_CODE!=:countryCode OR c.CURRENCY_ID!=:currencyId OR c.ACTIVE!=:active,:curDate,c.LAST_MODIFIED_DATE), "
                + "    cl.LABEL_EN=:label_en,  "
                + "    cl.LAST_MODIFIED_BY=IF(cl.LABEL_EN!=:label_en, :curUser, cl.LAST_MODIFIED_BY), "
                + "    c.LAST_MODIFIED_DATE=IF(cl.LABEL_EN!=:label_en, :curDate, cl.LAST_MODIFIED_DATE) "
                + "WHERE c.COUNTRY_ID=:countryId";
        Map<String, Object> params = new HashMap<>();
        params.put("countryId", country.getCountryId());
        params.put("countryCode", country.getCountryCode());
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
            sqlStringBuilder.append(" AND c.ACTIVE");
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

}
