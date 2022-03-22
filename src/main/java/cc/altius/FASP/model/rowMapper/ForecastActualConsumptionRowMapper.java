/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ForecastActualConsumption;
import cc.altius.FASP.model.SimpleForecastingUnitProductCategoryObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitProductCategoryObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForecastActualConsumptionRowMapper implements RowMapper<ForecastActualConsumption> {

    @Override
    public ForecastActualConsumption mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForecastActualConsumption fc = new ForecastActualConsumption();
        fc.setActualConsumptionId(rs.getInt("ACTUAL_CONSUMPTION_ID"));
        fc.setPlanningUnit(
                new SimplePlanningUnitProductCategoryObject(
                        rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum),
                        new SimpleForecastingUnitProductCategoryObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, rowNum),
                                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PC_").mapRow(rs, rowNum))
                        )
                )
        );
        fc.setPuMultiplier(rs.getDouble("MULTIPLIER"));
        fc.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REG_").mapRow(rs, rowNum)));
        fc.setMonth(rs.getDate("MONTH"));
        fc.setAmount(rs.getDouble("AMOUNT"));
        if (rs.wasNull()) {
            fc.setAmount(null);
        }
        fc.setReportingRate(rs.getDouble("REPORTING_RATE"));
        if (rs.wasNull()) {
            fc.setReportingRate(null);
        }
        fc.setDaysOfStockOut(rs.getInt("DAYS_OF_STOCK_OUT"));
        if (rs.wasNull()) {
            fc.setDaysOfStockOut(null);
        }
        fc.setExclude(rs.getBoolean("EXCLUDE"));
        fc.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
        fc.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        return fc;
    }

}
