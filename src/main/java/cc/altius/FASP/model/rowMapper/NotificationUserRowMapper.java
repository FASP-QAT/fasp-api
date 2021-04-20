/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.NotificationUser;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author akil
 */
public class NotificationUserRowMapper implements RowMapper<NotificationUser> {

    @Override
    public NotificationUser mapRow(ResultSet rs, int i) throws SQLException {
        return new NotificationUser(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("EMAIL_ID"));
    }

}
