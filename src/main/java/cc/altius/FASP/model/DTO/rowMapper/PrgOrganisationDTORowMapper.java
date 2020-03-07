/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgLabelDTO;
import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgOrganisationDTORowMapper implements RowMapper<PrgOrganisationDTO>{

    @Override
    public PrgOrganisationDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgOrganisationDTO o=new PrgOrganisationDTO();
        PrgLabelDTO label = new PrgLabelDTO();
        label.setLabelEn(rs.getString("LABEL_EN"));
        label.setLabelFr(rs.getString("LABEL_FR"));
        label.setLabelPr(rs.getString("LABEL_PR"));
        label.setLabelSp(rs.getString("LABEL_SP"));
        o.setLabel(label);
        o.setOrganisationId(rs.getInt("ORGANISATION_ID"));
        o.setActive(rs.getBoolean("ACTIVE"));
        return o;
    }
    
    
    
}
