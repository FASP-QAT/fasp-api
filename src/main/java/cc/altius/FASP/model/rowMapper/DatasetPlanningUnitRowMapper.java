/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitTracerCategoryObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitTracerCategoryObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class DatasetPlanningUnitRowMapper implements RowMapper<DatasetPlanningUnit> {

    @Override
    public DatasetPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        DatasetPlanningUnit dpu = new DatasetPlanningUnit();
        dpu.setProgramPlanningUnitId(rs.getInt("PROGRAM_PLANNING_UNIT_ID"));
        dpu.setPlanningUnit(new SimplePlanningUnitTracerCategoryObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, rowNum), new SimpleForecastingUnitTracerCategoryObject(rs.getInt("FORECASTING_UNIT_ID"), new LabelRowMapper("FU_").mapRow(rs, rowNum), new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, rowNum)))));
        dpu.setConsuptionForecast(rs.getBoolean("CONSUMPTION_FORECAST"));
        dpu.setTreeForecast(rs.getBoolean("TREE_FORECAST"));
        dpu.setStock(rs.getInt("STOCK"));
        if (rs.wasNull()) {
            dpu.setStock(null);
        }
        dpu.setExistingShipments(rs.getInt("EXISTING_SHIPMENTS"));
        if (rs.wasNull()) {
            dpu.setExistingShipments(null);
        }
        dpu.setMonthsOfStock(rs.getInt("MONTHS_OF_STOCK"));
        if (rs.wasNull()) {
            dpu.setMonthsOfStock(null);
        }
        dpu.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, rowNum), rs.getString("PROCUREMENT_AGENT_CODE")));
        if (dpu.getProcurementAgent().getId() == 0) {
            dpu.setProcurementAgent(null);
        }
        dpu.setPrice(rs.getDouble("PRICE"));
        if (rs.wasNull()) {
            dpu.setPrice(null);
        }
        return dpu;
    }

}
