/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.BatchInventory;
import cc.altius.FASP.model.BatchQty;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class BatchInventoryListResultSetExtractor implements ResultSetExtractor<List<BatchInventory>> {

    @Override
    public List<BatchInventory> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<BatchInventory> batchInventoryList = new LinkedList<>();
        while (rs.next()) {
            BatchInventory bi = new BatchInventory();
            bi.setBatchInventoryId(rs.getInt("BATCH_INVENTORY_ID"));
            int idx = batchInventoryList.indexOf(bi);
            if (idx == -1) {
                bi.setInventoryDate(rs.getString("INVENTORY_DATE"));
                bi.setPlanningUnit(new SimpleObject(
                        rs.getInt("PLANNING_UNIT_ID"),
                        new LabelRowMapper("PU_").mapRow(rs, 1)
                ));
                bi.setVersionId(rs.getInt("VERSION_ID"));
                bi.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
                bi.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
                bi.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
                bi.setLastModifiedBy(new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")));
                batchInventoryList.add(bi);
            } else {
                bi = batchInventoryList.get(idx);
            }
            bi.getBatchList().add(
                    new BatchQty(
                            rs.getInt("BATCH_INVENTORY_TRANS_ID"),
                            new Batch(
                                    rs.getInt("BATCH_ID"),
                                    rs.getInt("PLANNING_UNIT_ID"),
                                    rs.getString("BATCH_NO"),
                                    rs.getBoolean("AUTO_GENERATED"),
                                    rs.getDate("EXPIRY_DATE"),
                                    rs.getTimestamp("BATCH_CREATED_DATE")),
                            rs.getInt("QTY")
                    )
            );
        }
        return batchInventoryList;
    }

}
