/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.DTO.PrgLabelDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgHealthAreaDTORowMapper implements RowMapper<PrgHealthAreaDTO>{

    @Override
    public PrgHealthAreaDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgHealthAreaDTO ha=new PrgHealthAreaDTO();
        ha.setHealthAreaId(rs.getInt("HEALTH_AREA_ID"));
        ha.setActive(rs.getBoolean("ACTIVE"));
        ha.setLabel(new PrgLabelDTORowMapper().mapRow(rs, i));
        ha.setRealmId(rs.getInt("REALM_ID"));
        return ha;
    }
    
    
    
}
