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
        Label label=new Label();
        label.setEngLabel(rs.getString("LABEL_EN"));
        label.setFreLabel(rs.getString("LABEL_FR"));
        label.setLabelId(rs.getInt("LABEL_ID"));
        label.setPorLabel(rs.getString("LABEL_PR"));
        label.setSpaLabel(rs.getString("LABEL_SP"));
        p.setLabel(label);
        p.setProgramId(rs.getInt("PROGRAM_ID"));
        return p;
    }

}
