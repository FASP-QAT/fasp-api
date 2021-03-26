/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProcurementAgentPlanningUnitProgramPrice;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProcurementAgentPlanningUnitProgramPriceRowMapper implements RowMapper<ProcurementAgentPlanningUnitProgramPrice> {

    @Override
    public ProcurementAgentPlanningUnitProgramPrice mapRow(ResultSet rs, int i) throws SQLException {
        ProcurementAgentPlanningUnitProgramPrice papup = new ProcurementAgentPlanningUnitProgramPrice();
        papup.setProcurementAgentPlanningUnitProgramId(rs.getInt("PROCUREMENT_AGENT_PLANNING_UNIT_PROGRAM_ID"));
        papup.setProcurementAgentPlanningUnitId(rs.getInt("PROCUREMENT_AGENT_PLANNING_UNIT_ID"));
        papup.setProcurementAgent(new SimpleCodeObject(rs.getInt("PROCUREMENT_AGENT_ID"), new LabelRowMapper("PROCUREMENT_AGENT_").mapRow(rs, i), rs.getString("PROCUREMENT_AGENT_CODE")));
        papup.setPlanningUnit(new SimpleObject(rs.getInt("PLANNING_UNIT_ID"), new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, i)));
        papup.setProgram(new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)));
        papup.setPrice(rs.getDouble("PROGRAM_PRICE"));
        papup.setBaseModel(new BaseModelRowMapper("PAPUP_").mapRow(rs, i));
        return papup;
    }

}
