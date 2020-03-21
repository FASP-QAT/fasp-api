/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class CountryRowMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet rs, int i) throws SQLException {
        Country c = new Country(rs.getInt("COUNTRY_ID"), rs.getString("COUNTRY_CODE"), new LabelRowMapper("").mapRow(rs, i));
        c.setCurrency(new Currency(rs.getInt("CURRENCY_ID"), rs.getString("CURRENCY_CODE"), rs.getString("CURRENCY_SYMBOL"), new LabelRowMapper("CURRENCY_").mapRow(rs, i), rs.getDouble("CONVERSION_RATE_TO_USD")));
        c.setLanguage(new Language(rs.getInt("LANGUAGE_ID"), rs.getString("LANGUAGE_NAME"), rs.getString("LANGUAGE_CODE")));
        c.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return c;
    }

}
