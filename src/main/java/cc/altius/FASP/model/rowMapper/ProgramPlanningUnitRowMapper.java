/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitRowMapper implements RowMapper<ProgramPlanningUnit> {
    
    @Override
    public ProgramPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProgramPlanningUnit ppu = new ProgramPlanningUnit(
                rs.getInt("PROGRAM_PLANNING_UNIT_ID"),
                new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, rowNum)),
                rs.getDouble("MULTIPLIER"),
                new SimpleObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FORECASTING_UNIT_").mapRow(rs, rowNum)),
                new SimpleObject(rs.getInt("PRODUCT_CATEGORY_ID"), new LabelRowMapper("PRODUCT_CATEGORY_").mapRow(rs, rowNum)),
                rs.getInt("REORDER_FREQUENCY_IN_MONTHS"),
                rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"),
                rs.getInt("SHELF_LIFE"),
                rs.getDouble("CATALOG_PRICE"),
                rs.getInt("MONTHS_IN_PAST_FOR_AMC"),
                rs.getInt("MONTHS_IN_FUTURE_FOR_AMC")
        );
        ppu.setMinMonthsOfStock(rs.getInt("MIN_MONTHS_OF_STOCK"));
        if (rs.wasNull()) {
            ppu.setMinMonthsOfStock(null);
        }
        ppu.setPlanBasedOn(rs.getInt("PLAN_BASED_ON"));
        ppu.setMinQty(rs.getInt("MIN_QTY"));
        if (rs.wasNull()) {
            ppu.setMinQty(null);
        }
        ppu.setDistributionLeadTime(rs.getDouble("DISTRIBUTION_LEAD_TIME"));
        if (rs.wasNull()) {
            ppu.setDistributionLeadTime(null);
        }
        ppu.setForecastErrorThreshold(rs.getDouble("FORECAST_ERROR_THRESHOLD"));
        if (rs.wasNull()) {
            ppu.setForecastErrorThreshold(null);
        }
        ppu.setNotes(rs.getString("NOTES"));
        ppu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return ppu;
    }
}
