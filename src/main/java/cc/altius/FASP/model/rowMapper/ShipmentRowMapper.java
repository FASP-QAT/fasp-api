/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Shipment;
import cc.altius.FASP.model.SimpleForecastingUnitObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitObject;
import cc.altius.FASP.model.SimpleProcurementAgentObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentRowMapper implements RowMapper<Shipment> {

    @Override
    public Shipment mapRow(ResultSet rs, int i) throws SQLException {
        Shipment s = new Shipment();
        s.setShipmentId(rs.getInt("SHIPMENT_ID"));
        s.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        if (rs.wasNull()) {
            s.setParentShipmentId(null);
        }
        s.setPlanningUnit(
                new SimplePlanningUnitObject(
                        rs.getInt("PLANNING_UNIT_ID"),
                        new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i),
                        new SimpleForecastingUnitObject(
                                rs.getInt("FORECASTING_UNIT_ID"),
                                new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, i),
                                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, i))))
        );
        s.setExpectedDeliveryDate(rs.getString("EXPECTED_DELIVERY_DATE"));
        s.setSuggestedQty(rs.getInt("SUGGESTED_QTY"));
        s.setProcurementAgent(new SimpleProcurementAgentObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE"), rs.getString("COLOR_HTML_CODE")));
        s.setProcurementUnit(new SimpleObject(rs.getInt("PROCUREMENT_UNIT_ID"), new LabelRowMapper("PROCUREMENT_UNIT_").mapRow(rs, i)));
        s.setSupplier(new SimpleObject(rs.getInt("SUPPLIER_ID"), new LabelRowMapper("SUPPLIER_").mapRow(rs, i)));
        s.setShipmentQty(rs.getInt("QUANTITY"));
        s.setRate(rs.getDouble("RATE"));
        s.setProductCost(rs.getDouble("PRODUCT_COST"));
        s.setShipmentMode(rs.getString("SHIPPING_MODE"));
        s.setFreightCost(rs.getDouble("FREIGHT_COST"));
        s.setPlannedDate(rs.getString("PLANNED_DATE"));
        s.setSubmittedDate(rs.getString("SUBMITTED_DATE"));
        s.setApprovedDate(rs.getString("APPROVED_DATE"));
        s.setShippedDate(rs.getString("SHIPPED_DATE"));
        s.setArrivedDate(rs.getString("ARRIVED_DATE"));
        s.setReceivedDate(rs.getString("RECEIVED_DATE"));
        s.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, i)));
        s.setNotes(rs.getString("NOTES"));
        s.setDataSource(new SimpleObject(rs.getInt("DATA_SOURCE_ID"), new LabelRowMapper("DATA_SOURCE_").mapRow(rs, i)));
        s.setAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
        s.setErpFlag(rs.getBoolean("ERP_FLAG"));
        s.setVersionId(rs.getInt("VERSION_ID"));
        s.setBaseModel(new BaseModelRowMapper().mapRow(rs, i));
        s.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        return s;
    }

}
