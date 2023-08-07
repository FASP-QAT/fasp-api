/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ArtmisHistoryErpShipment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ArtmisHistoryErpShipmentRowMapper implements RowMapper<ArtmisHistoryErpShipment> {

    @Override
    public ArtmisHistoryErpShipment mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArtmisHistoryErpShipment s = new ArtmisHistoryErpShipment();
        s.setProcurementAgentShipmentNo(rs.getString("PROCUREMENT_AGENT_SHIPMENT_NO"));
        s.setDeliveryDate(rs.getDate("DELIVERY_DATE"));
        s.setQty(rs.getInt("QTY"));
        s.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        s.setBatchNo(rs.getString("BATCH_NO"));
        s.setChangeCode(rs.getString("CHANGE_CODE"));
        s.setDataReceivedOn(rs.getDate("DATA_RECEIVED_DATE"));
        return s;
    }

}
