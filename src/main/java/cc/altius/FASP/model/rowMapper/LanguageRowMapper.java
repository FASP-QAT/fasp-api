/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Language;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class LanguageRowMapper implements RowMapper<Language> {
    
    @Override
    public Language mapRow(ResultSet rs, int i) throws SQLException {
        Language l = new Language(rs.getInt("LANGUAGE_ID"), new LabelRowMapper("").mapRow(rs, i), rs.getString("LANGUAGE_CODE"), rs.getString("COUNTRY_CODE"));
        l.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        return l;
    }
    
}
