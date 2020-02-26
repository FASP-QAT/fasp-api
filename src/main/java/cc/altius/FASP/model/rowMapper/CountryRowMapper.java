/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Country;
import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Label;
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
        Country c = new Country();
        c.setCountryId(rs.getInt("COUNTRY_ID"));
        c.setLabel(new LabelRowMapper("CU_").mapRow(rs, i));
        Currency cu = new Currency();
        cu.setCurrencyId(rs.getInt("CURRENCY_ID"));
        c.setCurrency(cu);
        c.setLanguage(new Language(rs.getInt("LANGUAGE_ID")));
        c.setActive(rs.getBoolean("ACTIVE"));
        return c;
    }

}
