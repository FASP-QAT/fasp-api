/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.Realm;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author shrutika
 */
public class CustomUserDetailsResultSetExtractorBasic implements ResultSetExtractor<CustomUserDetails> {
    
    @Override
    public CustomUserDetails extractData(ResultSet rs) throws SQLException, DataAccessException {
        CustomUserDetails user = new CustomUserDetails();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                user.setLanguage(new Language(rs.getInt("LANGUAGE_ID"), new LabelRowMapper("LANGUAGE_").mapRow(rs, 1), rs.getString("LANGUAGE_CODE"), rs.getString("COUNTRY_CODE")));
                user.setFailedAttempts(rs.getInt("FAILED_ATTEMPTS"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setSyncExpiresOn(rs.getTimestamp("SYNC_EXPIRES_ON"));
                user.setActive(rs.getBoolean("ACTIVE"));
                user.setExpiresOn(rs.getTimestamp("EXPIRES_ON"));
                user.setAgreementAccepted(rs.getBoolean("AGREEMENT_ACCEPTED"));
                user.setRoles(new LinkedList<>());
                user.setAclList(new LinkedList<>());
            }
//            Role r = new Role(rs.getString("ROLE_ID"), new LabelRowMapper("ROLE_").mapRow(rs, 1));
//            if (user.getRoles().indexOf(r) == -1) {
//                user.getRoles().add(r);
//            }
//            UserAcl acl = new UserAcl(
//                    user.getUserId(),
//                    rs.getInt("ACL_REALM_COUNTRY_ID"), new LabelRowMapper("ACL_REALM_").mapRow(rs, 1),
//                    rs.getInt("ACL_HEALTH_AREA_ID"), new LabelRowMapper("ACL_HEALTH_AREA_").mapRow(rs, 1),
//                    rs.getInt("ACL_ORGANISATION_ID"), new LabelRowMapper("ACL_ORGANISATION_").mapRow(rs, 1),
//                    rs.getInt("ACL_PROGRAM_ID"), new LabelRowMapper("ACL_PROGRAM_").mapRow(rs, 1));
//            if (user.getAclList().indexOf(acl) == -1) {
//                user.getAclList().add(acl);
//            }
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
