/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.PplPrograminfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PplPrograminfoRowMapper implements RowMapper<PplPrograminfo> {
    
    @Override
    public PplPrograminfo mapRow(ResultSet rs, int arg1) throws SQLException {
        PplPrograminfo pi = new PplPrograminfo();
        pi.setCountryname(rs.getString("CountryName"));
        pi.setNote(rs.getString("Note"));
        pi.setProgramname(rs.getString("ProgramName"));
        return pi;
    }
    
}
