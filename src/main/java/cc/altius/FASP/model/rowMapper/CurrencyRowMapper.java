/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Currency;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author palash
 */
public class CurrencyRowMapper  implements RowMapper<Currency>{

    @Override
    public Currency mapRow(ResultSet rs, int i) throws SQLException {
        Currency c= new Currency();
        c.setCurrencyCode(rs.getString("CURRENCY_CODE"));
        c.setCurrencySymbol(rs.getString("CURRENCY_SYMBOL"));
        c.setConversionRateToUsd(rs.getDouble("CONVERSION_RATE_TO_USD"));
        c.setCurrencyId(rs.getInt("CURRENCY_ID"));
        c.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
        return c;
    }
    
}
