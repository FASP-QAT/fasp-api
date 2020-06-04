/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.rowMapper.LabelRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class GlobalConsumptionOutputRowMapper implements RowMapper<GlobalConsumptionOutput> {

    @Override
    public GlobalConsumptionOutput mapRow(ResultSet rs, int i) throws SQLException {
        GlobalConsumptionOutput gco = new GlobalConsumptionOutput();
        gco.setRealmCountry(new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")));
        gco.setConsumptionDate(rs.getDate("CONSUMPTION_DATE"));
        gco.setPlanningUnitQty(rs.getDouble("PLANNING_UNIT_CONSUMPTION_QTY"));
        gco.setForecastingUnitQty(rs.getDouble("FORECASTING_UNIT_CONSUMPTION_QTY"));
        return gco;
    }

}
