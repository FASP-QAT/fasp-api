/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.rowMapper.BasicUserRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleCodeObjectRowMapper;
import cc.altius.FASP.model.rowMapper.SimpleObjectRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class UpdateProgramInfoOutputRowMapper implements RowMapper<UpdateProgramInfoOutput> {

    @Override
    public UpdateProgramInfoOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        UpdateProgramInfoOutput upi = new UpdateProgramInfoOutput();
        upi.setProgram(new SimpleCodeObjectRowMapper("").mapRow(rs, 1));
        upi.setRealm(new SimpleObjectRowMapper("R_").mapRow(rs, 1));
        upi.setRealmCountry(new SimpleObjectRowMapper("RC_").mapRow(rs, 1));
        upi.setOrganisation(new SimpleObjectRowMapper("O_").mapRow(rs, 1));
        upi.setHealthAreas(new SimpleObjectRowMapper("HA_").mapRow(rs, 1));
        upi.setRegions(new SimpleObjectRowMapper("REG_").mapRow(rs, 1));
        upi.setProgramManager(rs.getString("PM_USERNAME"));
        upi.setLastUpdatedBy(new BasicUserRowMapper("LMB_").mapRow(rs, 1));
        upi.setLastUpdatedDate(rs.getDate("LAST_MODIFIED_DATE"));
        upi.setProgramNotes(rs.getString("PROGRAM_NOTES"));
        upi.setActive(rs.getBoolean("ACTIVE"));
        return upi;
    }

}
