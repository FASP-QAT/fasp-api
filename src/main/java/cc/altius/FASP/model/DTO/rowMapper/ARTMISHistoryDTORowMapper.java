/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class ARTMISHistoryDTORowMapper implements RowMapper<ManualTaggingDTO> {

    @Override
    public ManualTaggingDTO mapRow(ResultSet rs, int rows) throws SQLException {
        ManualTaggingDTO m = new ManualTaggingDTO();
        m.setRoNo(rs.getString("RO_NO"));
        m.setRoPrimeLineNo(rs.getInt("RO_PRIME_LINE_NO"));
        m.setOrderNo(rs.getString("ORDER_NO"));
        m.setPrimeLineNo(rs.getInt("PRIME_LINE_NO"));
        m.setExpectedDeliveryDate(rs.getTimestamp("EXPECTED_DELIVERY_DATE"));
        m.setErpStatus(rs.getString("STATUS"));
        m.setShipmentQty(rs.getInt("QTY"));
        m.setReceivedOn(rs.getTimestamp("LAST_MODIFIED_DATE"));
        m.setErpPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, rows)));
        return m;
    }

}
