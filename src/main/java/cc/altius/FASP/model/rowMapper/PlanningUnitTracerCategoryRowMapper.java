/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class PlanningUnitTracerCategoryRowMapper implements RowMapper<ProcurementAgentPlanningUnit> {

    @Override
    public ProcurementAgentPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProcurementAgentPlanningUnit pu = new ProcurementAgentPlanningUnit();
        pu.setSkuCode(rs.getString("SKU_CODE"));
        SimpleObject p = new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper().mapRow(rs, rowNum));
        pu.setPlanningUnit(p);
        return pu;
    }

}
