/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ArtmisShipmentDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ArtmisShipmentDTORowMapper implements RowMapper<ArtmisShipmentDTO> {

    @Override
    public ArtmisShipmentDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ArtmisShipmentDTO d = new ArtmisShipmentDTO();
        d.setShipmentId(rs.getInt("SHIPMENT_ID"));
        d.setFundingSourceId(rs.getInt("FUNDING_SOURCE_ID"));
        d.setBudgetId(rs.getInt("BUDGET_ID"));
        d.setAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
        d.setEmergencyOrder(rs.getBoolean("EMERGENCY_ORDER"));
        return d;
    }

}
