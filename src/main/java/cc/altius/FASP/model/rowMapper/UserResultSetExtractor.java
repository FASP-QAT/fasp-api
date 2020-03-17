/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.Language;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author altius
 */
public class UserResultSetExtractor implements ResultSetExtractor<User> {

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                user.setUserId(rs.getInt("USER_ID"));
                user.setUsername(rs.getString("USERNAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setPhoneNumber(rs.getString("PHONE"));
                user.setPassword(rs.getString("PASSWORD"));
                user.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                user.setLanguage(new Language(rs.getInt("LANGUAGE_ID"), rs.getString("LANGUAGE_NAME")));
                user.setFaildAttempts(rs.getInt("FAILED_ATTEMPTS"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                user.setRoleList(new LinkedList<>());
                user.setUserAclList(new LinkedList<>());
            }
            Role r = new Role(rs.getString("ROLE_ID"), new LabelRowMapper("ROLE_").mapRow(rs, 1));
            if (user.getRoleList().indexOf(r) == -1) {
                user.getRoleList().add(r);
            }
            UserAcl acl = new UserAcl(
                    user.getUserId(),
                    rs.getInt("ACL_REALM_COUNTRY_ID"), new LabelRowMapper("ACL_REALM_").mapRow(rs, 1),
                    rs.getInt("ACL_HEALTH_AREA_ID"), new LabelRowMapper("ACL_HEALTH_AREA_").mapRow(rs, 1),
                    rs.getInt("ACL_ORGANISATION_ID"), new LabelRowMapper("ACL_ORGANISATION_").mapRow(rs, 1),
                    rs.getInt("ACL_PROGRAM_ID"), new LabelRowMapper("ACL_PROGRAM_").mapRow(rs, 1));
            if (user.getUserAclList().indexOf(acl) == -1) {
                user.getUserAclList().add(acl);
            }
            isFirst = false;
        }
        if (isFirst) {
            return null;
        } else {
            return user;
        }
    }

}
