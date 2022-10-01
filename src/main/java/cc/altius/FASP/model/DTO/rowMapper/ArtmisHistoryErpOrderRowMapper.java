/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ArtmisHistoryErpOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ArtmisHistoryErpOrderRowMapper implements RowMapper<ArtmisHistoryErpOrder> {

    @Override
    public ArtmisHistoryErpOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArtmisHistoryErpOrder a = new ArtmisHistoryErpOrder();
        a.setProcurementAgentOrderNo(rs.getString("PROCUREMENT_AGENT_ORDER_NO"));
        a.setPlanningUnitName(rs.getString("PLANNING_UNIT_NAME"));
        a.setExpectedDeliveryDate(rs.getDate("EXPECTED_DELIVERY_DATE"));
        a.setStatus(rs.getString("STATUS"));
        a.setQty(rs.getInt("QTY"));
        a.setCost(rs.getDouble("TOTAL_COST"));
        a.setChangeCode(rs.getString("CHANGE_CODE"));
        a.setDataReceivedOn(rs.getDate("DATA_RECEIVED_DATE"));
        return a;
    }

}
