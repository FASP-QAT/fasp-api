/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ShipmentLinkingOutput;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
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
        so.setOrderActive(rs.getBoolean("ORDER_ACTIVE"));
        so.setShipmentActive(rs.getBoolean("SHIPMENT_ACTIVE"));
        so.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PLANNING_UNIT_ID"), new LabelRowMapper("ERP_PU_").mapRow(rs, rowNum)));
        try {
            int tmpPuId = rs.getInt("QAT_PLANNING_UNIT_ID");
            if (rs.wasNull() || tmpPuId == 0) {
                so.setQatPlanningUnit(null);
            } else {
                so.setQatPlanningUnit(new SimpleObject(rs.getInt("QAT_PLANNING_UNIT_ID"), new LabelRowMapper("QAT_PU_").mapRow(rs, rowNum)));
            }
        } catch (SQLException s) {
            so.setQatPlanningUnit(null);
        }
        try {
            int tmpPuId = rs.getInt("QAT_RCPU_ID");
            if (rs.wasNull() || tmpPuId == 0) {
                so.setQatRealmCountryPlanningUnit(null);
            } else {
                so.setQatRealmCountryPlanningUnit(new SimpleObjectWithMultiplier(rs.getInt("QAT_RCPU_ID"), new LabelRowMapper("QAT_RCPU_").mapRow(rs, rowNum), rs.getDouble("QAT_RCPU_MULTIPLIER")));
            }
        } catch (SQLException s) {
            so.setQatPlanningUnit(null);
        }
        so.setPrice(rs.getDouble("PRICE"));
        if (rs.wasNull()) {
            so.setPrice(null);
        }
        so.setShippingCost(rs.getDouble("SHIPPING_COST"));
        if (rs.wasNull()) {
            so.setShippingCost(null);
        }
        so.setQatEquivalentShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SS_").mapRow(rs, rowNum)));
        so.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        if (rs.wasNull()) {
            so.setParentShipmentId(null);
        }
        so.setChildShipmentId(rs.getInt("CHILD_SHIPMENT_ID"));
        if (rs.wasNull()) {
            so.setChildShipmentId(null);
        }
        so.setParentLinkedShipmentId(rs.getString("PARENT_LINKED_SHIPMENT_ID"));
        if (rs.wasNull()) {
            so.setParentLinkedShipmentId(null);
        }
        so.setNotes(rs.getString("NOTES"));
        so.setShipBy(rs.getString("SHIP_BY"));
        so.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        so.setTracerCategoryId(rs.getInt("TRACER_CATEGORY_ID"));
        return so;
    }

}
