/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.report.ProgramAndPlanningUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramAndPlanningUnitRowMapper implements RowMapper<ProgramAndPlanningUnit> {

    @Override
    public ProgramAndPlanningUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProgramAndPlanningUnit(rs.getInt("PROGRAM_ID"), rs.getInt("PLANNING_UNIT_ID"));
    }

}
