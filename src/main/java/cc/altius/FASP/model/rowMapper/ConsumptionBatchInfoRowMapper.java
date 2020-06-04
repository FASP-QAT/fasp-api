/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ConsumptionBatchInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ConsumptionBatchInfoRowMapper implements RowMapper<ConsumptionBatchInfo> {

    @Override
    public ConsumptionBatchInfo mapRow(ResultSet rs, int i) throws SQLException {
        ConsumptionBatchInfo cb = new ConsumptionBatchInfo();
        cb.setConsumptionTransBatchInfoId(rs.getInt("CONSUMPTION_TRANS_BATCH_INFO_ID"));
        if (rs.wasNull()) {
            return null;
        }
        cb.setBatchNo(rs.getString("BATCH_NO"));
        cb.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        cb.setConsumptionQty(rs.getInt("BATCH_QTY"));
        return cb;
    }
    
}
