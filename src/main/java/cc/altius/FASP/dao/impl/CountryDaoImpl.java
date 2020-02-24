/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.CountryDao;
import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.rowMapper.CountryRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class CountryDaoImpl implements CountryDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Country> getCountryList(boolean active) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT c.*,l.`LABEL_ID`,l.`LABEL_EN`,l.`LABEL_FR`,l.`LABEL_SP`,l.`LABEL_PR` \n"
                + "FROM ap_country c LEFT JOIN ap_label l ON l.`LABEL_ID`=c.`LABEL_ID`");
        if (active) {
            sb.append(" WHERE c.`ACTIVE` ");
        }
        return this.jdbcTemplate.query(sb.toString(), new CountryRowMapper());
    }

    @Transactional
    @Override
    public int addCountry(Country country) {
       
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);

        SimpleJdbcInsert labelInsert = new SimpleJdbcInsert(dataSource).withTableName("ap_label").usingGeneratedKeyColumns("LABEL_ID");
        Map<String, Object> params = new HashMap<>();
        params.put("LABEL_EN", country.getLabel().getEngLabel());
//        params.put("LABEL_FR", country.getLabel().getFreLabel());
//        params.put("LABEL_SP", country.getLabel().getSpaLabel());
//        params.put("LABEL_PR", country.getLabel().getPorLabel());
        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        int insertedLabelRowId = labelInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("ap_country").usingGeneratedKeyColumns("COUNTRY_ID");
        Map<String, Object> map = new HashMap<>();
        map.put("CURRENCY_ID", country.getCurrency().getCurrencyId());
        map.put("LANGUAGE_ID", country.getLanguage().getLanguageId());
        map.put("LABEL_ID", insertedLabelRowId);
        map.put("ACTIVE", 1);
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int countryId = insert.executeAndReturnKey(map).intValue();
        return countryId;
    }

    @Transactional
    @Override
    public int updateCountry(Country country) {
        Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
        String sqlOne = "UPDATE ap_label al SET al.`LABEL_EN`=?,al.`LAST_MODIFIED_BY`=?,al.`LAST_MODIFIED_DATE`=? WHERE al.`LABEL_ID`=?";
        this.jdbcTemplate.update(sqlOne, country.getLabel().getEngLabel(),1, curDt, country.getLabel().getLabelId());
        String sqlTwo = "UPDATE ap_country dt SET  dt.`LANGUAGE_ID`=?,dt.`CURRENCY_ID`=?,dt.`ACTIVE`=?,dt.`LAST_MODIFIED_BY`=?,dt.`LAST_MODIFIED_DATE`=?"
                + " WHERE dt.`COUNTRY_ID`=?;";
        return this.jdbcTemplate.update(sqlTwo, country.getLanguage().getLanguageId(),country.getCurrency().getCurrencyId(),country.isActive(), 1, curDt, country.getCountryId());
    }

}
