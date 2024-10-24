/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleEquivalencyUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimpleEquivalencyUnitRowMapper implements RowMapper<SimpleEquivalencyUnit> {

    @Override
    public SimpleEquivalencyUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        SimpleEquivalencyUnit eu = new SimpleEquivalencyUnit(rs.getInt("EU_ID"), new LabelRowMapper("EU_").mapRow(rs, rowNum));
        String forecastingUnitIds = rs.getString("FORECASTING_UNIT_IDS");
        if (forecastingUnitIds != null && !forecastingUnitIds.isEmpty()) {
            eu.setForecastingUnitIds(Arrays.asList(forecastingUnitIds.split(",")));
        }
        return eu;
    }

}
