/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ProgramDTO;
import cc.altius.FASP.model.Label;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ProgramDTORowMapper implements RowMapper<ProgramDTO> {

    @Override
    public ProgramDTO mapRow(ResultSet rs, int i) throws SQLException {
        ProgramDTO p = new ProgramDTO();
        p.setLabel(new Label(rs.getInt("LABEL_ID"), rs.getString("LABEL_EN"), rs.getString("LABEL_SP"), rs.getString("LABEL_FR"), rs.getString("LABEL_PR")));
        p.setProgramId(rs.getInt("PROGRAM_ID"));
        return p;
    }

}
