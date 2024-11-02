/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ConsumptionForecastVsActualOutputRowMapper implements RowMapper<ConsumptionForecastVsActualOutput> {
    
    @Override
    public ConsumptionForecastVsActualOutput mapRow(ResultSet rs, int i) throws SQLException {
        ConsumptionForecastVsActualOutput cfa = new ConsumptionForecastVsActualOutput();
        cfa.setTransDate(rs.getDate("MONTH"));
        cfa.setForecastedConsumption(rs.getDouble("FORECASTED_CONSUMPTION"));
        if (rs.wasNull()) {
            cfa.setForecastedConsumption(null);
        }
        cfa.setActualConsumption(rs.getDouble("ACTUAL_CONSUMPTION"));
        if (rs.wasNull()) {
            cfa.setActualConsumption(null);
        }
        return cfa;
    }
    
}
