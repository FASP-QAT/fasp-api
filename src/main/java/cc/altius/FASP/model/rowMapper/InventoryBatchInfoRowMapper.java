/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.InventoryBatchInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class InventoryBatchInfoRowMapper implements RowMapper<InventoryBatchInfo> {

    @Override
    public InventoryBatchInfo mapRow(ResultSet rs, int i) throws SQLException {
        InventoryBatchInfo ib = new InventoryBatchInfo();
        ib.setInventoryTransBatchInfoId(rs.getInt("INVENTORY_TRANS_BATCH_INFO_ID"));
        if (rs.wasNull()) {
            return null;
        }
        ib.setBatchNo(rs.getString("BATCH_NO"));
        ib.setExpiryDate(rs.getDate("EXPIRY_DATE"));
        ib.setAdjustmentQty(rs.getInt("BATCH_ADJUSTMENT_QTY"));
        if (rs.getObject("BATCH_ACTUAL_QTY") == null) {
            ib.setActualQty(null);
        } else {
            ib.setActualQty(rs.getInt("BATCH_ACTUAL_QTY"));
        }
        return ib;
    }

}
