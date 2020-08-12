/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ManualTaggingDTORowMapper implements RowMapper<ManualTaggingDTO> {

    @Override
    public ManualTaggingDTO mapRow(ResultSet rs, int arg1) throws SQLException {
        ManualTaggingDTO m = new ManualTaggingDTO();
        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
        m.setShipmentStatusDesc(rs.getString("SHIPMENT_STATUS_DESC"));
        m.setProcurementAgentName(rs.getString("PROCUREMENT_AGENT_NAME"));
        m.setBudgetDesc(rs.getString("BUDGET_DESC"));
        m.setShipmentQty(rs.getInt("SHIPMENT_QTY"));
        m.setProductCost(rs.getDouble("PRODUCT_COST"));
        m.setShipmentId(rs.getInt("SHIPMENT_ID"));
        m.setShipmentTransId(rs.getInt("SHIPMENT_TRANS_ID"));
        return m;
    }

}
