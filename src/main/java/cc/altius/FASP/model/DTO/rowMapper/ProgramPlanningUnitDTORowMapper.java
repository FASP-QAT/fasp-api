/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO.rowMapper;

import cc.altius.FASP.model.DTO.ProgramPlanningUnitDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnitDTORowMapper implements RowMapper<ProgramPlanningUnitDTO> {

    @Override
    public ProgramPlanningUnitDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProgramPlanningUnitDTO(rs.getInt("PROGRAM_ID"), rs.getInt("PLANNING_UNIT_ID"));
    }

}
