/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ManualTaggingDTO;
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
public class ERPLinkedShipmentsDTORowMapper implements RowMapper<ManualTaggingDTO> {

    @Override
    public ManualTaggingDTO mapRow(ResultSet rs, int rows) throws SQLException {
        ManualTaggingDTO m = new ManualTaggingDTO();
        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
        m.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)));
        m.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rows), rs.getString("PROCUREMENT_AGENT_CODE")));
        m.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, rows), rs.getString("FUNDING_SOURCE_CODE")));
        m.setBudget(new SimpleCodeObject(rs.getInt("BUDGET_ID"), new LabelRowMapper("BUDGET_").mapRow(rs, rows), rs.getString("BUDGET_CODE")));
        m.setShipmentQty(rs.getLong("QTY"));
        m.setProductCost(rs.getDouble("PRODUCT_COST"));
        m.setShipmentId(rs.getInt("SHIPMENT_ID"));
        m.setShipmentTransId(rs.getInt("SHIPMENT_TRANS_ID"));
        m.setOrderNo(rs.getString("ORDER_NO"));
        m.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
        m.setRoNo(rs.getString("RO_NO"));
        m.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
        m.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rows)));
        m.setErpPlanningUnit(new SimpleObject(rs.getInt("ERP_PRODUCT_ID"), new LabelRowMapper("ERP_PRODUCT_").mapRow(rs, rows)));
        m.setSkuCode(rs.getString("SKU_CODE"));
        m.setNotes(rs.getString("NOTES"));
        m.setConversionFactor(rs.getDouble("CONVERSION_FACTOR"));
        m.setParentShipmentId(rs.getInt("PARENT_SHIPMENT_ID"));
        m.setErpStatus(rs.getString("STATUS"));
        return m;
    }

}
