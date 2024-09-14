/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DashboardActualConsumptionRowMapper implements RowMapper<DashboardActualConsumption> {

    @Override
    public DashboardActualConsumption mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DashboardActualConsumption(
                rs.getInt("PLANNING_UNIT_ID"),
                new DashboardActualConsumptionDetails(rs.getDate("CONSUMPTION_DATE"), rs.getInt("REGION_COUNT"), rs.getInt("ACTUAL_COUNT"))
        );
    }

//    @Override
//    public DashboardQpl extractData(ResultSet rs) throws SQLException, DataAccessException {
//        Map<Integer, List<DashboardActualConsumptionDetails>> dacMap = new HashMap<>();
//        List<DashboardActualConsumptionDetails> dacdList;
//        boolean isFirst = true;
//        while (rs.next()) {
//            isFirst = false;
//            int planningUnitId = rs.getInt("PLANNING_UNIT_ID");
//            System.out.println("PlanningUnitId - " + planningUnitId);
//            if (dacMap.containsKey(planningUnitId)) {
//                System.out.println("PlanningUnit found in Map");
//                dacdList = dacMap.get(planningUnitId);
//            } else {
//                System.out.println("PlanningUnit not found in Map");
//                dacdList = new LinkedList<>();
//                dacMap.put(planningUnitId, dacdList);
//                System.out.println("dacMap after adding empty list --> " + dacMap);
//            }
//            DashboardActualConsumptionDetails dacd = new DashboardActualConsumptionDetails();
//            dacd.setConsumptionDate(rs.getDate("CONSUMPTION_DATE"));
//            System.out.println("dacd before searching in the dacList = " + dacd);
//            if (dacdList.indexOf(dacd) == -1) {
//                dacd.setRegionCount(rs.getInt("REGION_COUNT"));
//                dacd.setActualCount(rs.getInt("ACTUAL_CONSUMPTION"));
//                dacdList.add(dacd);
//            }
//            System.out.println("dacMap at the end of the loop --> " + dacMap);
//        }
//        
//        return ac;
//    }
}
