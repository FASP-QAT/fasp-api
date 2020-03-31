/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.CurrencyDao;
import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.rowMapper.CurrencyRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author palash
 */
@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    private LabelDao labelDao;

    @Transactional
    @Override
    public int addCurrency(Currency currency, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int insertedLabelRowId = this.labelDao.addLabel(currency.getLabel(), curUser.getUserId());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_currency").usingGeneratedKeyColumns("CURRENCY_ID");
        System.out.println("Before Inserting="+currency.getCurrencySymbol());
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_CODE", currency.getCurrencyCode());
        map.put("CURRENCY_SYMBOL", currency.getCurrencySymbol());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("CONVERSION_RATE_TO_USD", currency.getConversionRateToUsd());
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        return insert.executeAndReturnKey(map).intValue();
    }

    @Override
    public List<Currency> getCurrencyList(boolean active, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "	cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD, "
                + "    cul.`LABEL_ID`, cul.`LABEL_EN`, cul.`LABEL_FR`, cul.`LABEL_SP`, cul.`LABEL_PR`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, cu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, cu.LAST_MODIFIED_DATE, cu.ACTIVE  "
                + "FROM ap_currency cu   "
                + "LEFT JOIN ap_label cul ON cu.`LABEL_ID`=cul.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON cu.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON cu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE TRUE ";
        Map<String, Object> params = new HashMap<>();
        params.put("active", active);
        if (active) {
            sqlString += " AND cu.ACTIVE=TRUE ";
        }
        return this.namedParameterJdbcTemplate.query(sqlString, params, new CurrencyRowMapper());
    }
    
    @Override
    public Currency getCurrencyById(int currencyId, CustomUserDetails curUser) {
        String sqlString = "SELECT  "
                + "	cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD, "
                + "    cul.`LABEL_ID`, cul.`LABEL_EN`, cul.`LABEL_FR`, cul.`LABEL_SP`, cul.`LABEL_PR`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, cu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, cu.LAST_MODIFIED_DATE, cu.ACTIVE  "
                + "FROM ap_currency cu   "
                + "LEFT JOIN ap_label cul ON cu.`LABEL_ID`=cul.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON cu.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON cu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE cu.CURRENCY_ID=:currencyId ";
        Map<String, Object> params = new HashMap<>();
        params.put("currencyId", currencyId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlString, params, new CurrencyRowMapper());
    }

    @Transactional
    @Override
    public int updateCurrency(Currency currency, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE ap_currency cu LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
                + "SET  "
                + "	cu.CURRENCY_CODE=:currencyCode, cu.CURRENCY_SYMBOL=:currencySymbol, cu.CONVERSION_RATE_TO_USD=:conversionRateToUsd, cu.ACTIVE=:active, "
                + "	cu.LAST_MODIFIED_BY = IF(cu.CURRENCY_CODE!=:currencyCode OR cu.CURRENCY_SYMBOL!=:currencySymbol OR cu.CONVERSION_RATE_TO_USD!=:conversionRateToUsd OR cu.ACTIVE!=:active, :curUser, cu.LAST_MODIFIED_BY), "
                + "    cu.LAST_MODIFIED_DATE = IF(cu.CURRENCY_CODE!=:currencyCode OR cu.CURRENCY_SYMBOL!=:currencySymbol OR cu.CONVERSION_RATE_TO_USD!=:conversionRateToUsd OR cu.ACTIVE!=:active, :curDate, cu.LAST_MODIFIED_DATE), "
                + "    cul.LABEL_EN=:label_en,  "
                + "    cu.LAST_MODIFIED_BY = IF(cul.LABEL_EN!=:label_en, :curUser, cu.LAST_MODIFIED_BY), "
                + "    cu.LAST_MODIFIED_DATE = IF(cul.LABEL_EN!=:label_en, :curDate, cu.LAST_MODIFIED_DATE) "
                + "WHERE cu.CURRENCY_ID=:currencyId";
        Map<String, Object> params = new HashMap<>();
        params.put("active", currency.isActive());
        params.put("currencyCode", currency.getCurrencyCode());
        params.put("currencySymbol", currency.getCurrencySymbol());
        params.put("conversionRateToUsd", currency.getConversionRateToUsd());
        params.put("label_en", currency.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("currencyId", currency.getCurrencyId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public String getAllCurrencyCode() {
        String sql = "SELECT GROUP_CONCAT(ac.`CURRENCY_CODE`) FROM `ap_currency` ac ";
        Map<String, Object> params = new HashMap<>();
        return this.namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
    }

    @Override
    public void updateCurrencyConversionRate(Map<String, Double> currencyConversions) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        for (Map.Entry<String, Double> entry : currencyConversions.entrySet()) {
            Map<String, Object> params = new HashMap<>();
            params.put("currencyCode", entry.getKey());
            params.put("conversionRate", entry.getValue());
            params.put("curDate", curDate);
            String sql = "UPDATE ap_currency SET CONVERSION_RATE_TO_USD=:conversionRate ,LAST_MODIFIED_DATE=:curDate, LAST_MODIFIED_BY=1 where CURRENCY_CODE =:currencyCode";
            this.namedParameterJdbcTemplate.update(sql, params);
        }
    }

    @Override
    public List<Currency> getCurrencyListForSync(String lastSyncDate) {
        String sqlString = "SELECT  "
                + "	cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CURRENCY_SYMBOL, cu.CONVERSION_RATE_TO_USD, "
                + "    cul.`LABEL_ID`, cul.`LABEL_EN`, cul.`LABEL_FR`, cul.`LABEL_SP`, cul.`LABEL_PR`, "
                + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, cu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, cu.LAST_MODIFIED_DATE, cu.ACTIVE  "
                + "FROM ap_currency cu   "
                + "LEFT JOIN ap_label cul ON cu.`LABEL_ID`=cul.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON cu.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON cu.LAST_MODIFIED_BY=lmb.USER_ID "
                + "WHERE cu.LAST_MODIFIED_DATE>=:lastSyncDate";
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new CurrencyRowMapper());
    }

}
