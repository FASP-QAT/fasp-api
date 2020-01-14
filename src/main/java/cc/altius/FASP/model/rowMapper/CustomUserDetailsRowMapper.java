/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Priority;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author shrutika
 */
public class CustomUserDetailsRowMapper implements ResultSetExtractor<List<CustomUserDetails>> {

    @Override
    public List<CustomUserDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
        CustomUserDetails customUserDetails;
        Role role;
        List<CustomUserDetails> userList = new LinkedList<CustomUserDetails>();
        while (rs.next()) {
            customUserDetails = new CustomUserDetails();
            customUserDetails.setUserId(rs.getInt("USER_ID"));
            if (userList.indexOf(customUserDetails) == -1) {
                customUserDetails.setUsername(rs.getString("USERNAME"));
                customUserDetails.setPassword(rs.getString("PASSWORD"));
                customUserDetails.setActive(rs.getBoolean("ACTIVE"));
                customUserDetails.setExpired(rs.getBoolean("EXPIRED"));
                customUserDetails.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
                customUserDetails.setExpiresOn(rs.getDate("EXPIRES_ON"));
                customUserDetails.setOutsideAccess(rs.getBoolean("OUTSIDE_ACCESS"));
                customUserDetails.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                customUserDetails.setEmailId(rs.getString("EMAIL_ID"));
                userList.add(customUserDetails);
            }
            customUserDetails = userList.get(userList.indexOf(customUserDetails));

            role = new Role();
            role.setRoleId(rs.getString("ROLE_ID"));
            if (customUserDetails.getRoleList().indexOf(role) == -1) {
                role.setRoleName(rs.getString("ROLE_NAME"));
                customUserDetails.getRoleList().add(role);
            }
        }
        return userList;
    }
}
