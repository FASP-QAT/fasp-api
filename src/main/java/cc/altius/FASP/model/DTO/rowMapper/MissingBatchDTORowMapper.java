/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.MissingBatchDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class MissingBatchDTORowMapper implements RowMapper<MissingBatchDTO> {

    @Override
    public MissingBatchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        MissingBatchDTO m = new MissingBatchDTO();
        m.setProgramId(rs.getInt("PROGRAM_ID"));
        m.setShipmentId(rs.getInt("SHIPMENT_ID"));
        m.setShipmentTransId(rs.getInt("SHIPMENT_TRANS_ID"));
        m.setShipmentTransBatchInfoId(rs.getInt("SHIPMENT_TRANS_BATCH_INFO_ID"));
        m.setProjectedExpiryDate(rs.getDate("PROJECTED_EXPIRY_DATE"));
        m.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
        m.setShipmentRcpuQty(rs.getLong("SHIPMENT_RCPU_QTY"));
        m.setShipmentCreatedDate(rs.getTimestamp("CREATED_DATE"));
        m.setBatchNo(rs.getString("BATCH_NO"));
        return m;
    }

}
