/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForecastMetricsComparisionOutputRowMapper implements RowMapper<ForecastMetricsComparisionOutput> {

    @Override
    public ForecastMetricsComparisionOutput mapRow(ResultSet rs, int i) throws SQLException {
        ForecastMetricsComparisionOutput fmo = new ForecastMetricsComparisionOutput();
        fmo.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i), rs.getString("PROGRAM_CODE")));
        fmo.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        fmo.setDiffConsumptionTotal(rs.getDouble("DIFF_CONSUMPTION_TOTAL"));
        if (rs.wasNull()) {
            fmo.setDiffConsumptionTotal(null);
        }
        fmo.setActualConsumptionTotal(rs.getDouble("ACTUAL_CONSUMPTION_TOTAL"));
        if (rs.wasNull()) {
            fmo.setActualConsumptionTotal(null);
        }
        fmo.setActualConsumption(rs.getDouble("ACTUAL_CONSUMPTION"));
        if (rs.wasNull()) {
            fmo.setActualConsumption(null);
        }
        fmo.setForecastedConsumption(rs.getDouble("FORECASTED_CONSUMPTION"));
        if (rs.wasNull()) {
            fmo.setForecastedConsumption(null);
        }
        fmo.setForecastError(rs.getDouble("FORECAST_ERROR"));
        if (rs.wasNull()) {
            fmo.setForecastError(null);
        }
        fmo.setMonthCount(rs.getInt("MONTH_COUNT"));
        fmo.setActual(rs.getBoolean("ACTUAL"));
        if (rs.wasNull()) {
            fmo.setActual(null);
        }
        fmo.setForecastErrorThreshold(rs.getDouble("FORECAST_ERROR_THRESHOLD"));
        if (rs.wasNull()) {
            fmo.setForecastErrorThreshold(null);
        }
        return fmo;
    }

}
