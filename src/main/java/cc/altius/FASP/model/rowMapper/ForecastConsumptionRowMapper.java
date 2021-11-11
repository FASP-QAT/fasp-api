/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ForecastConsumption;
import cc.altius.FASP.model.ForecastConsumptionUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForecastConsumptionRowMapper implements RowMapper<ForecastConsumption> {

    @Override
    public ForecastConsumption mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForecastConsumption fc = new ForecastConsumption();
        fc.setForecastConsumptionId(rs.getInt("CONSUMPTION_ID"));
        fc.setProgram(new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, rowNum), rs.getString("PROGRAM_CODE")));
        ForecastConsumptionUnit fcu = new ForecastConsumptionUnit();
        fcu.setForecastConsumptionUnitId(rs.getInt("CONSUMPTION_UNIT_ID"));
        fcu.setDataType(rs.getInt("DATA_TYPE"));
        fcu.setForecastingUnit(new SimpleObjectWithMultiplier(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, rowNum), 1));
        fcu.setPlanningUnit(new SimpleObjectWithMultiplier(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PU_").mapRow(rs, rowNum), rs.getDouble("PU_MULTIPLIER_FOR_FU")));
        if (fcu.getDataType()==3) {
            fcu.setOtherUnit(new SimpleObjectWithMultiplier(0, new LabelRowMapper("OU_").mapRow(rs, rowNum), rs.getDouble("OU_MULTIPLIER_FOR_FU")));
        }
        fc.setConsumptionUnit(fcu);
        fc.setRegion(new SimpleObject(rs.getInt("REGION_ID"), new LabelRowMapper("REG_").mapRow(rs, rowNum)));
        fc.setMonth(rs.getDate("MONTH"));
        fc.setActualConsumption(rs.getDouble("ACTUAL_CONSUMPTION"));
        if (rs.wasNull()) {
            fc.setActualConsumption(null);
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
        fc.setVersionId(rs.getInt("VERSION_ID"));
        fc.setCreatedBy(new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")));
        fc.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        return fc;
    }

}
