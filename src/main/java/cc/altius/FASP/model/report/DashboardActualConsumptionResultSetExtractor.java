/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DashboardActualConsumptionResultSetExtractor implements ResultSetExtractor<Map<Integer, List<DashboardActualConsumptionDetails>>> {

    @Override
    public Map<Integer, List<DashboardActualConsumptionDetails>> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, List<DashboardActualConsumptionDetails>> db = new HashMap<>();
        while (rs.next()) {
            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
            if (!db.containsKey(planningUnitId)) {
                db.put(planningUnitId, new LinkedList<>());
            }
            db.get(planningUnitId).add(new DashboardActualConsumptionDetails(rs.getDate("CONSUMPTION_DATE"), rs.getInt("ACTUAL_COUNT")));
        }
        return db;
    }

}
