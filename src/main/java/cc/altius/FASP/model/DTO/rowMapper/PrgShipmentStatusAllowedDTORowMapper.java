/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.PrgShipmentStatusAllowedDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PrgShipmentStatusAllowedDTORowMapper implements RowMapper<PrgShipmentStatusAllowedDTO>{

    @Override
    public PrgShipmentStatusAllowedDTO mapRow(ResultSet rs, int i) throws SQLException {
        PrgShipmentStatusAllowedDTO ssa=new PrgShipmentStatusAllowedDTO();
        ssa.setNextShipmentStatusId(rs.getInt("NEXT_SHIPMENT_STATUS_ID"));
        ssa.setShipmentStatusAllowedId(rs.getInt("SHIPMENT_STATUS_ALLOWED_ID"));
        ssa.setShipmentStatusId(rs.getInt("SHIPMENT_STATUS_ID"));
        return ssa;
    }
    
    
    
}
