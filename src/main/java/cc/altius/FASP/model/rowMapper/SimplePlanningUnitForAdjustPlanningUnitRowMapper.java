/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleForecastingUnitForAdjustPlanningUnit;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForAdjustPlanningUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitForAdjustPlanningUnitRowMapper implements RowMapper<SimplePlanningUnitForAdjustPlanningUnit> {

    @Override
    public SimplePlanningUnitForAdjustPlanningUnit mapRow(ResultSet rs, int i) throws SQLException {    
        SimplePlanningUnitForAdjustPlanningUnit pu = new SimplePlanningUnitForAdjustPlanningUnit();
        pu.setId(rs.getInt("PLANNING_UNIT_ID"));
        pu.setLabel(new LabelRowMapper("PU_").mapRow(rs, i));
        pu.setForecastingUnit(
                new SimpleForecastingUnitForAdjustPlanningUnit(
                        new SimpleObject(
                                rs.getInt("PRODUCT_CATEGORY_ID"),
                                new LabelRowMapper("PC_").mapRow(rs, i)
                        ),
                        rs.getInt("FORECASTING_UNIT_ID"),
                        new LabelRowMapper("FU_").mapRow(rs, i)
                )
        );
        return pu;
    }

}
