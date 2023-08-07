/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ProgramCount;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramCountRowMapper implements RowMapper<ProgramCount> {

    @Override
    public ProgramCount mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ProgramCount(rs.getInt("PROGRAM_COUNT"), rs.getInt("DATASET_COUNT"));
    }

}
