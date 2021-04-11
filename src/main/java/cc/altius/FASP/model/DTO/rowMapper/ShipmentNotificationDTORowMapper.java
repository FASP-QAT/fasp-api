/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ShipmentNotificationDTORowMapper implements RowMapper<ERPNotificationDTO>{

    @Override
    public ERPNotificationDTO mapRow(ResultSet rs, int rows) throws SQLException {
        ERPNotificationDTO m = new ERPNotificationDTO();
        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
        m.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)));
        m.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rows), rs.getString("PROCUREMENT_AGENT_CODE")));
        m.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
        m.setShipmentId(rs.getInt("SHIPMENT_ID"));
        m.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        m.setOrderNo(rs.getString("ORDER_NO"));
        m.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
        m.setRoNo(rs.getString("RO_NO"));
        m.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
        m.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rows)));
        m.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PLANNING_UNIT_ID"), new LabelRowMapper("ERP_PLANNING_UNIT_").mapRow(rs, rows)));
        m.setNotes(rs.getString("NOTES"));
        m.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        m.setNotificationType(new SimpleObject(rs.getInt("NOTIFICATION_TYPE_ID"), new LabelRowMapper("").mapRow(rs, rows)));
        m.setNotificationId(rs.getInt("NOTIFICATION_ID"));
        m.setAddressed(rs.getBoolean("ADDRESSED"));
        return m;
    }
    
}
