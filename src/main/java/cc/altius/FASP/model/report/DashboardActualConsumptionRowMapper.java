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
                new DashboardActualConsumptionDetails(rs.getDate("CONSUMPTION_DATE"), rs.getInt("ACTUAL_COUNT"))
        );
    }
}
