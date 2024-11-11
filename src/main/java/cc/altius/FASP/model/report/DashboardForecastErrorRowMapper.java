/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class DashboardForecastErrorRowMapper implements RowMapper<DashboardForecastError> {

    @Override
    public DashboardForecastError mapRow(ResultSet rs, int rowNum) throws SQLException {
        DashboardForecastError fe = new DashboardForecastError();
        fe.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("").mapRow(rs, rowNum)));
        fe.setCount(rs.getInt("NO_OF_MONTHS"));
        fe.setErrorPerc(rs.getDouble("ERROR_PERC"));
        if (rs.wasNull()) {
            fe.setErrorPerc(null);
        }
        fe.setForecastErrorThreshold(rs.getDouble("FORECAST_ERROR_THRESHOLD"));
        if (rs.wasNull()) {
            fe.setForecastErrorThreshold(null);
        }
        return fe;
    }

}
