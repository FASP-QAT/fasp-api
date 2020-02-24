/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.CurrencyDao;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.rowMapper.CurrencyRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private javax.sql.DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional
    @Override
    public int addCurrency(Currency currency) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", currency.getLabel().getEngLabel());
//        params.put("LABEL_FR", currency.getLabel().getFreLabel());
//        params.put("LABEL_SP", currency.getLabel().getSpaLabel());//alreday scanned
//        params.put("LABEL_PR", currency.getLabel().getPorLabel());
        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        int insertedLabelRowId = labelInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_currency").usingGeneratedKeyColumns("CURRENCY_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_CODE", currency.getCurrencyCode());
        map.put("CURRENCY_SYMBOL", currency.getCurrencySymbol());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("CONVERSION_RATE_TO_USD", currency.getConversionRateToUsd());
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int currencyId = insert.executeAndReturnKey(map).intValue();
        return currencyId;
    }

    @Override
    public List<Currency> getCurrencyList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ac.* ,al.`LABEL_EN`,al.`LABEL_FR`,al.`LABEL_SP`,al.`LABEL_PR`,al.`LABEL_ID` \n"
                + "FROM ap_currency ac \n"
                + "LEFT JOIN ap_label al ON al.`LABEL_ID`=ac.`LABEL_ID`;");
        return this.jdbcTemplate.query(sb.toString(), new CurrencyRowMapper());
    }

    @Transactional
    @Override
    public int updateCurrency(Currency currency) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, currency.getLabel().getEngLabel(), 1, curDt, currency.getLabel().getLabelId());
        String sqlTwo = "UPDATE ap_currency c SET  c.`CURRENCY_CODE`=?,c.`CURRENCY_SYMBOL`=?,c.`CONVERSION_RATE_TO_USD`=?,c.`LAST_MODIFIED_BY`=?,c.`LAST_MODIFIED_DATE`=?"
                + " WHERE c.`CURRENCY_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, currency.getCurrencyCode(), currency.getCurrencySymbol(), currency.getConversionRateToUsd(), 1, curDt, currency.getCurrencyId());
    }

    @Override
    public String getAllCurrencyCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT GROUP_CONCAT(ac.`CURRENCY_CODE`) FROM `ap_currency` ac ");
        return this.jdbcTemplate.queryForObject(sb.toString(), String.class);
    }

    @Override
    public void updateCurrencyConversionrate(Map<String, Double> currencyConversions) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        for (Map.Entry<String, Double> entry : currencyConversions.entrySet()) {
            StringBuilder sb = new StringBuilder();
            String k = entry.getKey();
            Double v = entry.getValue();
            System.out.println("Key: " + k + ", Value: " + v);
            sb.append("update ap_currency set  CONVERSION_RATE_TO_USD=").append(v).append(" ,LAST_MODIFIED_DATE=? where CURRENCY_CODE ='").append(k.substring(3)).append("';");
            System.out.println("sb" + sb.toString());
            this.jdbcTemplate.update(sb.toString(), curDt);
        }

    }

}
