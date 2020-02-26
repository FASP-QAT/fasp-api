/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLanguageDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgLanguageDTORowMapper implements RowMapper<PrgLanguageDTO> {
    
    @Override
    public PrgLanguageDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgLanguageDTO l = new PrgLanguageDTO();
        l.setActive(rs.getBoolean("ACTIVE"));
        l.setLanguageId(rs.getInt("LANGUAGE_ID"));
        l.setLanguageName(rs.getString("LANGUAGE_NAME"));
        return l;
        
    }
    
}
