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
public class ForecastMetricsOutputRowMapper implements RowMapper<ForecastMetricsOutput> {

    @Override
    public ForecastMetricsOutput mapRow(ResultSet rs, int i) throws SQLException {
        ForecastMetricsOutput fmo = new ForecastMetricsOutput();
        fmo.setRealmCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        fmo.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        fmo.setPlanningUnit(new SimpleCodeObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i), rs.getString("SKU_CODE")));
        fmo.setHistoricalConsumptionActual(rs.getDouble("HISTORICAL_ACTUAL_CONSUMPTION"));
        fmo.setHistoricalConsumptionDiff(rs.getDouble("HISTORICAL_DIFF_CONSUMPTION"));
        fmo.setForecastError(rs.getDouble("FORECAST_ERROR"));
        fmo.setMonths(rs.getInt("MONTHS_COUNT"));
        return fmo;
    }

}
