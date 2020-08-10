/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ExportOrderDataDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ExportOrderDataDTORowMapper implements RowMapper<ExportOrderDataDTO> {
    
    @Override
    public ExportOrderDataDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ExportOrderDataDTO e = new ExportOrderDataDTO();
        e.setShipmentId(rs.getInt("SHIPMENT_ID"));
        e.setSkuCode(rs.getString("SKU_CODE"));
        e.setProgramId(rs.getInt("PROGRAM_ID"));
        e.setProcurementAgentCode(rs.getString("PROCUREMENT_AGENT_CODE"));
        e.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
        e.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
        return e;
    }
    
}
