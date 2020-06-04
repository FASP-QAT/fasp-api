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

    private final String sqlListString = "SELECT  "
            + "	cu.CURRENCY_ID, cu.CURRENCY_CODE, cu.CONVERSION_RATE_TO_USD, "
            + "    cul.`LABEL_ID`, cul.`LABEL_EN`, cul.`LABEL_FR`, cul.`LABEL_SP`, cul.`LABEL_PR`, "
            + "    cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, cu.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, cu.LAST_MODIFIED_DATE, cu.ACTIVE ,cu.IS_SYNC "
            + "FROM ap_currency cu   "
            + "LEFT JOIN ap_label cul ON cu.`LABEL_ID`=cul.`LABEL_ID` "
            + "LEFT JOIN us_user cb ON cu.CREATED_BY=cb.USER_ID "
            + "LEFT JOIN us_user lmb ON cu.LAST_MODIFIED_BY=lmb.USER_ID "
            + "WHERE TRUE ";

    @Transactional
    @Override
    public int addCurrency(Currency currency, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        int insertedLabelRowId = this.labelDao.addLabel(currency.getLabel(), curUser.getUserId());
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_currency").usingGeneratedKeyColumns("CURRENCY_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_CODE", currency.getCurrencyCode());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("CONVERSION_RATE_TO_USD", currency.getConversionRateToUsd());
        map.put("IS_Sync", currency.isIsSync());
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        return insert.executeAndReturnKey(map).intValue();
    }

    @Transactional
    @Override
    public int updateCurrency(Currency currency, CustomUserDetails curUser) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
        String sqlString = "UPDATE ap_currency cu LEFT JOIN ap_label cul ON cu.LABEL_ID=cul.LABEL_ID "
                + "SET  "
                + "	cu.CURRENCY_CODE=:currencyCode, cu.CONVERSION_RATE_TO_USD=:conversionRateToUsd, cu.ACTIVE=:active,cu.IS_SYNC=:isSync, "
                + "	cu.LAST_MODIFIED_BY = IF(cu.CURRENCY_CODE!=:currencyCode OR cu.CONVERSION_RATE_TO_USD!=:conversionRateToUsd OR cu.ACTIVE!=:active OR cu.IS_SYNC=:isSync, :curUser, cu.LAST_MODIFIED_BY), "
                + "    cu.LAST_MODIFIED_DATE = IF(cu.CURRENCY_CODE!=:currencyCode OR cu.CONVERSION_RATE_TO_USD!=:conversionRateToUsd OR cu.ACTIVE!=:active OR cu.IS_SYNC=:isSync, :curDate, cu.LAST_MODIFIED_DATE), "
                + "    cul.LABEL_EN=:label_en,  "
                + "    cu.LAST_MODIFIED_BY = IF(cul.LABEL_EN!=:label_en, :curUser, cu.LAST_MODIFIED_BY), "
                + "    cu.LAST_MODIFIED_DATE = IF(cul.LABEL_EN!=:label_en, :curDate, cu.LAST_MODIFIED_DATE) "
                + "WHERE cu.CURRENCY_ID=:currencyId";
        Map<String, Object> params = new HashMap<>();
        params.put("isSync", currency.isIsSync());
        params.put("active", currency.isActive());
        params.put("currencyCode", currency.getCurrencyCode());
        params.put("conversionRateToUsd", currency.getConversionRateToUsd());
        params.put("label_en", currency.getLabel().getLabel_en());
        params.put("curUser", curUser.getUserId());
        params.put("curDate", curDate);
        params.put("currencyId", currency.getCurrencyId());
        return this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<Currency> getCurrencyList(boolean active, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString);
        Map<String, Object> params = new HashMap<>();

        if (active) {
            sqlStringBuilder.append(" AND cu.ACTIVE=TRUE ");
            params.put("active", active);
        }
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new CurrencyRowMapper());
    }

    @Override
    public Currency getCurrencyById(int currencyId, CustomUserDetails curUser) {
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND cu.CURRENCY_ID=:currencyId ");
        Map<String, Object> params = new HashMap<>();
        params.put("currencyId", currencyId);
        return this.namedParameterJdbcTemplate.queryForObject(sqlStringBuilder.toString(), params, new CurrencyRowMapper());
    }

    @Override
    public String getAllCurrencyCode() {
        String sql = "SELECT GROUP_CONCAT(ac.`CURRENCY_CODE`) FROM `ap_currency` ac where ac.IS_SYNC=1";
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
        StringBuilder sqlStringBuilder = new StringBuilder(this.sqlListString).append(" AND cu.LAST_MODIFIED_DATE>=:lastSyncDate ");
        Map<String, Object> params = new HashMap<>();
        params.put("lastSyncDate", lastSyncDate);
        return this.namedParameterJdbcTemplate.query(sqlStringBuilder.toString(), params, new CurrencyRowMapper());
    }

}
