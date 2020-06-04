/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.EmailUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class EmailUserRowMapper implements RowMapper<EmailUser>{

    
    @Override
    public EmailUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EmailUser(rs.getString("EMAIL_ID"), rs.getInt("USER_ID"), rs.getString("USERNAME"));
    }
    
}
