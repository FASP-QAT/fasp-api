/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.PlanningUnitForProgramMapping;
import cc.altius.FASP.model.ProgramPlanningUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class PlanningUnitForProgramResultSetExtractor implements ResultSetExtractor<ProgramPlanningUnit> {

    @Override
    public ProgramPlanningUnit extractData(ResultSet rs) throws SQLException, DataAccessException {
        ProgramPlanningUnit ppu = new ProgramPlanningUnit();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                ppu.setProgramId(rs.getInt("PROGRAM_ID"));
                ppu.setLabel(new LabelRowMapper().mapRow(rs, 1));
                ppu.setPlanningUnitList(new LinkedList<>());
            }
            if (rs.getInt("PLANNING_UNIT_ID") > 0) {
                PlanningUnitForProgramMapping sp = new PlanningUnitForProgramMapping();
                sp.setPlanningUnitId(rs.getInt("PLANNING_UNIT_ID"));
                sp.setLabel(new LabelRowMapper("PLANNING_UNIT_").mapRow(rs, 1));
                sp.setReorderFrequencyInMonths(rs.getInt("REORDER_FREQUENCY_IN_MONTHS"));
                sp.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                if (ppu.getPlanningUnitList().indexOf(sp) == -1) {
                    ppu.getPlanningUnitList().add(sp);
                }
            }
            isFirst = false;
        }
        return ppu;
    }
}
