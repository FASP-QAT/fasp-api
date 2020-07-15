/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

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
        fmo.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, i)));
        fmo.setDiffConsumptionTotal(rs.getInt("DIFF_CONSUMPTION_TOTAL"));
        if (rs.wasNull()) {
            fmo.setDiffConsumptionTotal(null);
        }
        fmo.setActualConsumptionTotal(rs.getInt("ACTUAL_CONSUMPTION_TOTAL"));
        if (rs.wasNull()) {
            fmo.setActualConsumptionTotal(null);
        }
        fmo.setForecastError(rs.getDouble("FORECAST_ERROR"));
        if (rs.wasNull()) {
            fmo.setForecastError(null);
        }
        fmo.setMonthCount(rs.getInt("MONTH_COUNT"));
        return fmo;
    }

}
