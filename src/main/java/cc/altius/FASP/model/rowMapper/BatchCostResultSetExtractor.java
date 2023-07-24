/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class BatchCostResultSetExtractor implements ResultSetExtractor<Map<Integer, Double>> {

    @Override
    public Map<Integer, Double> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, Double> batchMap = new HashMap<>();
        while (rs.next()) {
            int batchId = rs.getInt("BATCH_ID");
            if (batchMap.get(batchId) == null) {
                Double cost = rs.getDouble("COST");
                if (rs.wasNull()) {
                    cost = null;
                }
                batchMap.put(batchId, cost);
            }
        }
        return batchMap;
    }

}
