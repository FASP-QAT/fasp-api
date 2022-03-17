/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.erpLinking.rowMapper;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.erpLinking.ErpShipmentsOutput;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ErpShipmentsOutputRowMapper implements RowMapper<ErpShipmentsOutput> {

    @Override
    public ErpShipmentsOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        ErpShipmentsOutput e = new ErpShipmentsOutput();
        e.setShipmentId(rs.getInt("SHIPMENT_ID"));
        e.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        e.setShipmentTransId(rs.getInt("SHIPMENT_TRANS_ID"));
        e.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
        e.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SS_").mapRow(rs, rowNum)));
        e.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")));
        e.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FS_").mapRow(rs, rowNum), rs.getString("FUNDING_SOURCE_CODE")));
        e.setBudget(new SimpleCodeObject(rs.getInt("BUDGET_ID"), new LabelRowMapper("B_").mapRow(rs, rowNum), rs.getString("BUDGET_CODE")));
        e.setShipmentQty(rs.getLong("SHIPMENT_QTY"));
        e.setProductCost(rs.getDouble("PRODUCT_COST"));
        e.setOrderNo(rs.getString("ORDER_NO"));
        e.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
        e.setRoNo(rs.getString("RO_NO"));
        e.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
        e.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum)));
        e.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PLANNING_UNIT_ID"), new LabelRowMapper("EPU_").mapRow(rs, rowNum)));
        e.setSkuCode(rs.getString("SKU_CODE"));
        e.setNotes(rs.getString("NOTES"));
        e.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        e.setErpStatus(rs.getString("ERP_STATUS"));
        e.setReceivedOn(rs.getDate("RECEIVED_ON"));
        return e;
    }

}
