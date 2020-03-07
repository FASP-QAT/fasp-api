/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Emailer;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author altius
 */
public class EmailerRowMapper implements RowMapper<Emailer> {

    @Override
    public Emailer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Emailer emailer = new Emailer();
        emailer.setEmailerId(rs.getInt("EMAILER_ID"));
        emailer.setToSend(rs.getString("TO_SEND"));
        emailer.setCcToSend(rs.getString("CC_SEND_TO"));
        emailer.setSubject(rs.getString("SUBJECT"));
        emailer.setBody(rs.getString("BODY"));
        emailer.setStatus(rs.getInt("STATUS"));
        emailer.setCcToSend(rs.getString("CC_SEND_TO"));
        emailer.setReason(rs.getString("REASON"));
        emailer.setBccToSend(rs.getString("BCC"));
        emailer.setAttempts(rs.getInt("ATTEMPTS"));
        return emailer;
    }

}
