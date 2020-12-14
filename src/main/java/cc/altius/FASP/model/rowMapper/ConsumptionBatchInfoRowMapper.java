/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Batch;
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
        cb.setBatch(new Batch(rs.getInt("BATCH_ID"), rs.getInt("BATCH_PLANNING_UNIT_ID"), rs.getString("BATCH_NO"), rs.getBoolean("AUTO_GENERATED"), rs.getDate("EXPIRY_DATE")));
        cb.getBatch().setCreatedDate(rs.getDate("BATCH_CREATED_DATE"));
        cb.setConsumptionQty(rs.getDouble("BATCH_QTY"));
        return cb;
    }
    
}
