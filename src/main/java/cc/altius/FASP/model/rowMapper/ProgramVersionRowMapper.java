/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.ProgramVersion;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ProgramVersionRowMapper implements RowMapper<ProgramVersion>{

    @Override
    public ProgramVersion mapRow(ResultSet rs, int i) throws SQLException {
        ProgramVersion pv = new ProgramVersion(
                new SimpleObject(rs.getInt("PROGRAM_ID"), new LabelRowMapper("PROGRAM_").mapRow(rs, i)), 
                new SimpleCodeObject(rs.getInt("REALM_COUNTRY_ID"), new LabelRowMapper("COUNTRY_").mapRow(rs, i), rs.getString("COUNTRY_CODE")), 
                new SimpleObject(rs.getInt("HEALTH_AREA_ID"), new LabelRowMapper("HEALTH_AREA_").mapRow(rs, i)), 
                new SimpleCodeObject(rs.getInt("ORGANISATION_ID"), new LabelRowMapper("ORGANISATION_").mapRow(rs, i), rs.getString("ORGANISATION_CODE")), 
                rs.getInt("VERSION_ID"), 
                new SimpleObject(rs.getInt("VERSION_TYPE_ID"), new LabelRowMapper("VERSION_TYPE_").mapRow(rs, i)), 
                new SimpleObject(rs.getInt("VERSION_STATUS_ID"), new LabelRowMapper("VERSION_STATUS_").mapRow(rs, i)), 
                rs.getString("NOTES"), 
                new BasicUser(rs.getInt("CB_USER_ID"), rs.getString("CB_USERNAME")),
                rs.getTimestamp("CREATED_DATE"),
                new BasicUser(rs.getInt("LMB_USER_ID"), rs.getString("LMB_USERNAME")),
                rs.getTimestamp("LAST_MODIFIED_DATE"));
        return pv;
    }
    
}
