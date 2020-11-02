/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ErpBatchDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ErpBatchDTORowMapper implements RowMapper<ErpBatchDTO>{

    @Override
    public ErpBatchDTO mapRow(ResultSet rs, int i) throws SQLException {
        ErpBatchDTO eb = new ErpBatchDTO();
        eb.setBatchId(rs.getInt("BATCH_ID"));
        eb.setShipmentTransBatchInfoId(rs.getInt("SHIPMENT_TRANS_BATCH_INFO_ID"));
        eb.setBatchNo(rs.getString("BATCH_NO"));
        eb.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        eb.setQty(rs.getInt("BATCH_SHIPMENT_QTY"));
        eb.setStatus(-1);
        return eb;
    }
    
}
