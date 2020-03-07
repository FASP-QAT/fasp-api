/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgCurrencyDTORowMapper implements RowMapper<PrgCurrencyDTO>{

    @Override
    public PrgCurrencyDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgCurrencyDTO c=new PrgCurrencyDTO();
        c.setConversionRateToUsd(rs.getDouble("CONVERSION_RATE_TO_USD"));
        c.setCurrencyCode(rs.getString("CURRENCY_CODE"));
        c.setCurrencyId(rs.getInt("CURRENCY_ID"));
        c.setCurrencySymbol(rs.getString("CURRENCY_SYMBOL"));
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        c.setLabel(label);
        return c;
    }
    
    
    
}
