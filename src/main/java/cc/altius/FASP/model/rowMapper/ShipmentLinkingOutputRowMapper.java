/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ShipmentLinkingOutput;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentLinkingOutputRowMapper implements RowMapper<ShipmentLinkingOutput> {

    @Override
    public ShipmentLinkingOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        ShipmentLinkingOutput so = new ShipmentLinkingOutput();
        so.setRoNo(rs.getString("RO_NO"));
        so.setRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
        so.setOrderNo(rs.getString("ORDER_NO"));
        so.setPrimeLineNo(rs.getString("PRIME_LINE_NO"));
        so.setErpShipmentStatus(rs.getString("ERP_SHIPMENT_STATUS"));
        so.setErpQty(rs.getInt("ERP_QTY"));
        so.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
        so.setKnShipmentNo(rs.getString("KN_SHIPMENT_NO"));
        so.setBatchNo(rs.getString("BATCH_NO"));
        so.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        so.setErpPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        so.setPrice(rs.getDouble("PRICE"));
        if (rs.wasNull()) {
            so.setPrice(null);
        }
        so.setShippingCost(rs.getDouble("SHIPPING_COST"));
        if (rs.wasNull()) {
            so.setShippingCost(null);
        }
        so.setQatEquivalentShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SS_").mapRow(rs, rowNum)));
        return so;
    }

}
