/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.SimpleCodeObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class LoadProgramRowMapper implements RowMapper<LoadProgram>{

    @Override
    public LoadProgram mapRow(ResultSet rs, int i) throws SQLException {
        return new LoadProgram(
                new SimpleCodeObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper().mapRow(rs, i), rs.getString("PROGRAM_CODE")),
                new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("REALM_COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")),
                new SimpleCodeObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, i), rs.getString("HEALTH_AREA_CODE")),
                new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, i), rs.getString("ORGANISATION_CODE")),
                rs.getInt("MAX_COUNT")
        );
    }
    
}
