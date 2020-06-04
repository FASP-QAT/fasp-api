/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Registration;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class RegistrationRowMapper implements RowMapper<Registration> {
    
    @Override
    public Registration mapRow(ResultSet rs, int i) throws SQLException {
        Registration r = new Registration();
        r.setEmailId(rs.getString("EMAIL_ID"));
        r.setFirstName(rs.getString("FIRST_NAME"));
        r.setLastName(rs.getString("LAST_NAME"));
        r.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        r.setRegistrationId(rs.getInt("REGISTRATION_ID"));
        r.setStatus(rs.getBoolean("STATUS"));
        r.setNotes(rs.getString("NOTES"));
        return r;
    }
    
}
