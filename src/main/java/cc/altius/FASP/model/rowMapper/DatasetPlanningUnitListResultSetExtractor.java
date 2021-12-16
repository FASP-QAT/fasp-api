/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.DatasetPlanningUnit;
import cc.altius.FASP.model.SelectedForecast;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleForecastingUnitTracerCategoryObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import cc.altius.FASP.model.SimplePlanningUnitTracerCategoryObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class DatasetPlanningUnitListResultSetExtractor implements ResultSetExtractor<List<DatasetPlanningUnit>> {

    @Override
    public List<DatasetPlanningUnit> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<DatasetPlanningUnit> dpuList = new LinkedList<>();
        while (rs.next()) {
            DatasetPlanningUnit dpu = new DatasetPlanningUnit();
            dpu.setProgramPlanningUnitId(rs.getInt("PROGRAM_PLANNING_UNIT_ID"));
            int idx = dpuList.indexOf(dpu);
            if (idx == -1) {
                dpu.setPlanningUnit(
                        new SimplePlanningUnitTracerCategoryObject(
                                new SimpleCodeObject(rs.getInt("PUU_UNIT_ID"), new LabelRowMapper("PUU_").mapRow(rs, 1), rs.getString("PUU_UNIT_CODE")),
                                rs.getInt("PLANNING_UNIT_ID"),
                                new LabelRowMapper("PU_").mapRow(rs, 1),
                                rs.getDouble("PU_MULTIPLIER_FOR_FU"),
                                new SimpleForecastingUnitTracerCategoryObject(
                                        new SimpleCodeObject(rs.getInt("FUU_UNIT_ID"), new LabelRowMapper("FUU_").mapRow(rs, 1), rs.getString("FUU_UNIT_CODE")),
                                        rs.getInt("FORECASTING_UNIT_ID"),
                                        new LabelRowMapper("FU_").mapRow(rs, 1),
                                        new SimpleObject(rs.getInt("TRACER_CATEGORY_ID"), new LabelRowMapper("TC_").mapRow(rs, 1))
                                )
                        )
                );
                dpu.setConsumptionDataType(rs.getInt("CONSUMPTION_DATA_TYPE_ID"));
                if (rs.wasNull()) {
                    dpu.setConsumptionDataType(null);
                }
                if (dpu.getConsumptionDataType() != null && dpu.getConsumptionDataType().equals(3)) {
                    SimpleObjectWithMultiplier otherUnit = new SimpleObjectWithMultiplier(0, new LabelRowMapper("OU_").mapRow(rs, 1), rs.getDouble("OU_MULTIPLIER_FOR_FU"));
                    if (otherUnit.getLabel().getLabelId() != 0) {
                        dpu.setOtherUnit(otherUnit);
                    }
                }

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
                dpu.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PA_").mapRow(rs, 1), rs.getString("PROCUREMENT_AGENT_CODE")));
                if (dpu.getProcurementAgent().getId() == 0) {
                    dpu.setProcurementAgent(null);
                }
                dpu.setPrice(rs.getDouble("PRICE"));
                if (rs.wasNull()) {
                    dpu.setPrice(null);
                }
                dpu.setConsumptionNotes(rs.getString("CONSUMPTION_NOTES"));
                dpuList.add(dpu);
                dpu.setSelectedForecastMap(new HashMap<>());
            } else {
                dpu = dpuList.get(idx);
            }
            SelectedForecast sf = new SelectedForecast();
            sf.setScenarioId(rs.getInt("SCENARIO_ID"));
            if (rs.wasNull()) {
                sf.setScenarioId(null);
            }
            sf.setConsumptionExtrapolationId(rs.getInt("CONSUMPTION_EXTRAPOLATION_ID"));
            if (rs.wasNull()) {
                sf.setConsumptionExtrapolationId(null);
            }
            sf.setTotalForecast(rs.getInt("TOTAL_FORECAST"));
            if (rs.wasNull()) {
                sf.setTotalForecast(null);
            }
            Integer regionId = rs.getInt("REGION_ID");
            if (!rs.wasNull()) {
                dpu.getSelectedForecastMap().put(regionId, sf);
            }
        }
        return dpuList;
    }

}
