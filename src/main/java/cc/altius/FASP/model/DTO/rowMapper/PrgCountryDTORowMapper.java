/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgCountryDTO;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import cc.altius.FASP.model.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgCountryDTORowMapper implements RowMapper<PrgCountryDTO> {
    
    @Override
    public PrgCountryDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgCountryDTO country = new PrgCountryDTO();
        country.setCountryId(rs.getInt("COUNTRY_ID"));
        PrgCurrencyDTO currency = new PrgCurrencyDTO();
        currency.setCurrencyId(rs.getInt("CURRENCY_ID"));
        country.setCurrency(currency);
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        country.setLabel(label);
        PrgLanguageDTO language = new PrgLanguageDTO();
        language.setLanguageId(rs.getInt("LANGUAGE_ID"));
        country.setLanguage(language);
        country.setActive(rs.getBoolean("ACTIVE"));
        return country;
    }
    
}
