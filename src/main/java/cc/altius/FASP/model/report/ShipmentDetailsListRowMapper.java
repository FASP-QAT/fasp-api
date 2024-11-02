/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ShipmentDetailsListRowMapper implements RowMapper<ShipmentDetailsList>{

    @Override
    public ShipmentDetailsList mapRow(ResultSet rs, int i) throws SQLException {
        ShipmentDetailsList sd = new ShipmentDetailsList();
        sd.setShipmentId(rs.getInt("SHIPMENT_ID"));
        sd.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        sd.setForecastingUnit(new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, i)));
        sd.setMultiplier(rs.getInt("MULTIPLIER"));
        sd.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        sd.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, i), rs.getString("FUNDING_SOURCE_CODE")));
        sd.setBudget(new SimpleCodeObject(rs.getInt("BUDGET_ID"), new LabelRowMapper("BUDGET_").mapRow(rs, i), rs.getString("BUDGET_CODE")));
        sd.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, i)));
        sd.setShipmentQty(rs.getDouble("SHIPMENT_QTY"));
        sd.setExpectedDeliveryDate(rs.getDate("EDD"));
        sd.setProductCost(rs.getDouble("PRODUCT_COST"));
        sd.setFreightCost(rs.getDouble("FREIGHT_COST"));
        sd.setTotalCost(rs.getDouble("TOTAL_COST"));
        sd.setOrderNo(rs.getString("ORDER_NO"));
        sd.setLocalProcurement(rs.getBoolean("LOCAL_PROCUREMENT"));
        sd.setEmergencyOrder(rs.getBoolean("EMERGENCY_ORDER"));
        sd.setErpFlag(rs.getBoolean("ERP_FLAG"));
        sd.setNotes(rs.getString("NOTES"));
        return sd;
    }
    
}
