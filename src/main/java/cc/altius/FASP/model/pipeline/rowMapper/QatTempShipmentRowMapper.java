/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline.rowMapper;

import cc.altius.FASP.model.pipeline.QatTempShipment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author ekta
 */
public class QatTempShipmentRowMapper implements RowMapper<QatTempShipment>{

    @Override
    public QatTempShipment mapRow(ResultSet rs, int i) throws SQLException {
        QatTempShipment s = new QatTempShipment();
        s.setShipmentId(rs.getInt("SHIPMENT_ID"));
        s.setPlanningUnit(rs.getString("PLANNING_UNIT_ID"));
        s.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
        s.setSuggestedQty(rs.getDouble("SUGGESTED_QTY"));
        s.setProcurementAgent(rs.getString("PROCUREMENT_AGENT_ID"));
        s.setProcurementUnit(rs.getString("PROCUREMENT_UNIT_ID"));
        s.setSupplier(rs.getString("SUPPLIER_ID"));
        s.setQuantity(rs.getDouble("QUANTITY"));
        s.setRate(rs.getDouble("RATE"));
        s.setProductCost(rs.getDouble("PRODUCT_COST"));
        s.setShipmentMode(rs.getString("SHIPPING_MODE"));
        s.setFreightCost(rs.getDouble("FREIGHT_COST"));
        s.setOrderedDate(rs.getDate("ORDERED_DATE"));
        s.setShippedDate(rs.getDate("SHIPPED_DATE"));
        s.setReceivedDate(rs.getDate("RECEIVED_DATE"));
        s.setShipmentStatus(rs.getString("SHIPMENT_STATUS_ID"));
        s.setNotes(rs.getString("NOTES"));
        s.setDataSource(rs.getString("DATA_SOURCE_ID"));
        s.setAccountFlag(rs.getBoolean("ACCOUNT_FLAG"));
        s.setErpFlag(rs.getBoolean("ERP_FLAG"));
        s.setVersionId(rs.getInt("VERSION_ID"));
        s.setFundingSource(rs.getString("FUNDING_SOURCE_ID"));
        s.setActive(rs.getBoolean("ACTIVE"));
        return s;
    }}
