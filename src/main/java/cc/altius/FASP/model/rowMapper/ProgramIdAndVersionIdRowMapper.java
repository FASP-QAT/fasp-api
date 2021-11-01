/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramIdAndVersionId;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ProgramIdAndVersionIdRowMapper implements RowMapper<ProgramIdAndVersionId> {
    
    @Override
    public ProgramIdAndVersionId mapRow(ResultSet rs, int i) throws SQLException {
        ProgramIdAndVersionId p = new ProgramIdAndVersionId();
        p.setProgramId(rs.getInt("PROGRAM_ID"));
        p.setVersionId(rs.getInt("CURRENT_VERSION_ID"));
        return p;
    }
    
}
