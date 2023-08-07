/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BatchDetails;
import cc.altius.FASP.model.LinkedShipmentBatchDetails;
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
public class LinkedShipmentBatchDetailsListResultSetExtractor implements ResultSetExtractor<List<LinkedShipmentBatchDetails>> {

    @Override
    public List<LinkedShipmentBatchDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<LinkedShipmentBatchDetails> linkedShipmentBatchDetailsList = new LinkedList<>();
        while (rs.next()) {
            LinkedShipmentBatchDetails l = new LinkedShipmentBatchDetails();
            l.setRoNo(rs.getString("RO_NO"));
            l.setRoPrimeLineNo(rs.getString("RO_PRIME_LINE_NO"));
            l.setQuantity(rs.getInt("QTY"));
            int idx = -1;
            idx = linkedShipmentBatchDetailsList.indexOf(l);
            if (idx == -1) {
                linkedShipmentBatchDetailsList.add(l);
            } else {
                l = linkedShipmentBatchDetailsList.get(idx);
            }
            BatchDetails bd = new BatchDetails();
            bd.setQuantity(rs.getInt("SHIPPED_QTY"));
            bd.setBatchNo(rs.getString("BATCH_NO"));
            l.getBatchDetailsList().add(bd);
        }
        return linkedShipmentBatchDetailsList;
    }

}
