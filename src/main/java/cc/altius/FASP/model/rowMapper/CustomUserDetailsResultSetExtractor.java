/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.Role;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author shrutika
 */
public class CustomUserDetailsResultSetExtractor implements ResultSetExtractor<CustomUserDetails> {

    @Override
    public CustomUserDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        CustomUserDetails user = new CustomUserDetails();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setActive(rs.getBoolean("ACTIVE"));
                user.setExpired(rs.getBoolean("EXPIRED"));
                user.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
                user.setExpiresOn(rs.getDate("EXPIRES_ON"));
//                user.setOutsideAccess(rs.getBoolean("OUTSIDE_ACCESS"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                Language l = new Language();
                l.setLanguageId(rs.getInt("LANGUAGE_ID"));
                user.setLanguage(l);
                user.setRoleList(new LinkedList<>());
                user.setBusinessFunction(new LinkedList<>());
            }
            Label l = new Label();
            l.setLabelId(rs.getInt("LABEL_ID"));
            l.setEngLabel(rs.getString("LABEL_EN"));
            l.setFreLabel(rs.getString("LABEL_FR"));
            l.setSpaLabel(rs.getString("LABEL_SP"));
            l.setPorLabel(rs.getString("LABEL_PR"));
            Role role = new Role(rs.getString("ROLE_ID"), l);
            if (user.getRoleList().indexOf(role) == -1) {
                user.getRoleList().add(role);
            }
//            SimpleGrantedAuthority bf = new SimpleGrantedAuthority(rs.getString("BUSINESS_FUNCTION_ID"));
//            if (user.getBusinessFunction().indexOf(bf) == -1) {
//                user.getBusinessFunction().add(bf);
//            }
            isFirst = false;
        }
        if (isFirst) {
            return null;
        } else {
            return user;
        }
    }
}
