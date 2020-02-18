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
        Label l = new Label();
        l.setEngLabel(rs.getString("LABEL_EN"));
        l.setFreLabel(rs.getString("LABEL_FR"));
        l.setSpaLabel(rs.getString("LABEL_SP"));
        l.setPorLabel(rs.getString("LABEL_PR"));
        l.setLabelId(rs.getInt("LABEL_ID"));
        c.setLabel(l);
        Currency cu= new Currency();
        cu.setCurrencyId(rs.getInt("CURRENCY_ID"));
        c.setCurrency(cu);
        Language la = new Language();
        la.setLanguageId(rs.getInt("LANGUAGE_ID"));
        c.setLanguage(la);
        c.setActive(rs.getBoolean("ACTIVE"));
        return c;
    }
    
}
