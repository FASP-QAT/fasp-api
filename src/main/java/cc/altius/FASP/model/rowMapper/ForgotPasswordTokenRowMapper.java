/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.ForgotPasswordToken;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class ForgotPasswordTokenRowMapper implements RowMapper<ForgotPasswordToken> {

    @Override
    public ForgotPasswordToken mapRow(ResultSet rs, int rowNum) throws SQLException {
        ForgotPasswordToken fpt = new ForgotPasswordToken();
        fpt.setUserId(rs.getInt("USER_ID"));
        fpt.setUsername(rs.getString("USERNAME"));
        fpt.setToken(rs.getString("TOKEN"));
        fpt.setTokenGenerationDate(rs.getTimestamp("TOKEN_GENERATION_DATE"));
        fpt.setTokenTriggeredDate(rs.getTimestamp("TOKEN_TRIGGERED_DATE"));
        fpt.setTokenCompletionDate(rs.getTimestamp("TOKEN_COMPLETION_DATE"));
        return fpt;
    }
    
}
