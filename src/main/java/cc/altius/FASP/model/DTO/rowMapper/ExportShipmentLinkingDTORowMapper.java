/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ExportShipmentLinkingDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ExportShipmentLinkingDTORowMapper implements RowMapper<ExportShipmentLinkingDTO> {
    
    @Override
    public ExportShipmentLinkingDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ExportShipmentLinkingDTO e = new ExportShipmentLinkingDTO();
        e.setProgramId(rs.getInt(("PROGRAM_ID")));
        e.setProgramName(rs.getString("LABEL_EN"));
        e.setParentShipmentId(rs.getInt("SHIPMENT_ID"));
        e.setRoNo(rs.getString("RO_NO"));
        e.setOrderNo(rs.getString("ORDER_NO"));
        e.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
        e.setActive(rs.getBoolean("ACTIVE"));
        e.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        return e;
    }
    
}
