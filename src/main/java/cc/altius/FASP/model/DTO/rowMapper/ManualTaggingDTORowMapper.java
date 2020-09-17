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
public class ManualTaggingDTORowMapper implements RowMapper<ManualTaggingDTO> {

    @Override
    public ManualTaggingDTO mapRow(ResultSet rs, int rows) throws SQLException {
        ManualTaggingDTO m = new ManualTaggingDTO();
        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
        m.setShipmentStatus(new SimpleObject(rs.getInt("SHIPMENT_STATUS_ID"), new LabelRowMapper("SHIPMENT_STATUS_").mapRow(rs, 1)));
        m.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, rows), rs.getString("PROCUREMENT_AGENT_CODE")));
        m.setFundingSource(new SimpleCodeObject(rs.getInt("FUNDING_SOURCE_ID"), new LabelRowMapper("FUNDING_SOURCE_").mapRow(rs, rows), rs.getString("FUNDING_SOURCE_CODE")));
        m.setBudget(new SimpleCodeObject(rs.getInt("BUDGET_ID"), new LabelRowMapper("BUDGET_").mapRow(rs, rows), rs.getString("BUDGET_CODE")));
        m.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
        m.setProductCost(rs.getDouble("PRODUCT_COST"));
        m.setShipmentId(rs.getInt("SHIPMENT_ID"));
        m.setShipmentTransId(rs.getInt("SHIPMENT_TRANS_ID"));
        return m;
    }

}
