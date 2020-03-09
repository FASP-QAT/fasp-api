/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgCountryDTO;
import cc.altius.FASP.model.DTO.PrgCurrencyDTO;
import cc.altius.FASP.model.DTO.PrgLanguageDTO;
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
        country.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        PrgLanguageDTO language = new PrgLanguageDTO();
        language.setLanguageId(rs.getInt("LANGUAGE_ID"));
        country.setLanguage(language);
        country.setActive(rs.getBoolean("ACTIVE"));
        return country;
    }

}
