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
        Label l = new Label();
        l.setLabelId(rs.getInt("LABEL_ID"));
        l.setEngLabel(rs.getString("LABEL_EN"));
        l.setFreLabel(rs.getString("LABEL_FR"));
        l.setSpaLabel(rs.getString("LABEL_SP"));
        l.setPorLabel(rs.getString("LABEL_PR"));
        c.setLabel(l);
        return c;
    }
    
}
