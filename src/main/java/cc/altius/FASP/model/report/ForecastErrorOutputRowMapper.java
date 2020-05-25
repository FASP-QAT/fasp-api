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
public class ForecastErrorOutputRowMapper implements RowMapper<ForecastErrorOutput> {

    @Override
    public ForecastErrorOutput mapRow(ResultSet rs, int i) throws SQLException {
        return new ForecastErrorOutput(
                rs.getDate("ACTUAL_DATE"),
                rs.getDouble("FORECASTED_CONSUMPTION"),
                rs.getDouble("ACTUAL_CONSUMPTION"),
                rs.getDouble("FORECAST_ERROR"),
                rs.getInt("MONTHS_COUNT")
        );
    }

}
