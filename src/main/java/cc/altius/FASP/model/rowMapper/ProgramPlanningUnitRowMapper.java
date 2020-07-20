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
                rs.getInt("REORDER_FREQUENCY_IN_MONTHS"),
                rs.getInt("MIN_MONTHS_OF_STOCK"),
                rs.getDouble("LOCAL_PROCUREMENT_LEAD_TIME"),
                rs.getInt("SHELF_LIFE"),
                rs.getDouble("CATALOG_PRICE"),
                rs.getInt("MONTHS_IN_PAST_FOR_AMC"),
                rs.getInt("MONTHS_IN_FUTURE_FOR_AMC")
        );
        ppu.setBaseModel(new BaseModelRowMapper().mapRow(rs, rowNum));
        return ppu;
    }
}
