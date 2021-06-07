/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ARTMISHistoryDTO;
import cc.altius.FASP.model.DTO.ErpShipmentDTO;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class ARTMISHistoryDTORowMapper implements ResultSetExtractor<List<ARTMISHistoryDTO>> {

    @Override
    public List<ARTMISHistoryDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<ARTMISHistoryDTO> orderList = new LinkedList<>();
        int oldErpOrderId = 0, newErpOrderId;
        ARTMISHistoryDTO order = new ARTMISHistoryDTO();
        ErpShipmentDTO s = new ErpShipmentDTO();
        while (rs.next()) {
            newErpOrderId = rs.getInt("ERP_ORDER_ID");
            if (oldErpOrderId != newErpOrderId) {
                if (oldErpOrderId != 0) {
                    orderList.add(order);
                }
                order = new ARTMISHistoryDTO();
                order.setErpOrderId(rs.getInt("ERP_ORDER_ID"));
                order.setTotalCost(rs.getDouble("TOTAL_COST"));
                order.setRoNo(rs.getString("RO_NO"));
                order.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
                order.setOrderNo(rs.getString("ORDER_NO"));
                order.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
                order.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
                order.setErpStatus(rs.getString("STATUS"));
                order.setShipmentQty(rs.getLong("QTY"));
                order.setReceivedOn(rs.getTimestamp("LAST_MODIFIED_DATE"));
                order.setErpPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, 1)));
            }
            s = new ErpShipmentDTO();
            s.setBatchNo(rs.getString("BATCH_NO"));
            if (order.getShipmentList().indexOf(s) == -1) {
                s.setExpiryDate(rs.getTimestamp("EXPIRY_DATE"));
                order.getShipmentList().add(s);
            }

            oldErpOrderId = newErpOrderId;

        }
        if (order.getErpOrderId() != 0) {
            orderList.add(order);
        }
        return orderList;
    }

//    @Override
//    public ManualTaggingDTO mapRow(ResultSet rs, int rows) throws SQLException {
//        ManualTaggingDTO m = new ManualTaggingDTO();
//        m.setRoNo(rs.getString("RO_NO"));
//        m.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
//        m.setOrderNo(rs.getString("ORDER_NO"));
//        m.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
//        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
//        m.setErpStatus(rs.getString("STATUS"));
//        m.setShipmentQty(rs.getInt("QTY"));
//        m.setReceivedOn(rs.getTimestamp("LAST_MODIFIED_DATE"));
//        m.setErpPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, rows)));
//        return m;
//}
}
