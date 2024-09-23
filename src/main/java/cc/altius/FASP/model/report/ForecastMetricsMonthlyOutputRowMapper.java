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
public class ForecastMetricsMonthlyOutputRowMapper implements RowMapper<ForecastMetricsMonthlyOutput> {

    @Override
    public ForecastMetricsMonthlyOutput mapRow(ResultSet rs, int i) throws SQLException {
        ForecastMetricsMonthlyOutput fmo = new ForecastMetricsMonthlyOutput();
        fmo.setMonth(rs.getDate("MONTH"));
        fmo.setMonthCount(rs.getInt("MONTH_COUNT"));
        fmo.setActualConsumptionHistory(rs.getDouble("ACTUAL_CONSUMPTION_HISTORY"));
        if (rs.wasNull()) {
            fmo.setActualConsumptionHistory(null);
        }
        fmo.setDiffConsumptionHistory(rs.getDouble("DIFF_CONSUMPTION_HISTORY"));
        if (rs.wasNull()) {
            fmo.setDiffConsumptionHistory(null);
        }
        fmo.setForecastError(rs.getDouble("FORECAST_ERROR"));
        if (rs.wasNull()) {
            fmo.setForecastError(null);
        }
        fmo.setActualConsumption(rs.getDouble("ACTUAL_CONSUMPTION"));
        if (rs.wasNull()) {
            fmo.setActualConsumption(null);
        }
        fmo.setForecastedConsumption(rs.getDouble("FORECASTED_CONSUMPTION"));
        if (rs.wasNull()) {
            fmo.setForecastedConsumption(null);
        }
        fmo.setActual(rs.getBoolean("ACTUAL"));
        if(rs.wasNull()) {
            fmo.setActual(null);
        }
        return fmo;
    }

}
