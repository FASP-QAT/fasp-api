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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

/**
 *
 * @author akil
 */
public class UserListResultSetExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        List<User> userList = new LinkedList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("USER_ID"));
            int idx = userList.indexOf(user);
            if (idx == -1) {
                userList.add(user);
                user.setUsername(rs.getString("USERNAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setOrgAndCountry(rs.getString("ORG_AND_COUNTRY"));
                user.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                user.setLanguage(new Language(rs.getInt("LANGUAGE_ID"), new LabelRowMapper("LANGUAGE_").mapRow(rs, 1), rs.getString("LANGUAGE_CODE"), rs.getString("COUNTRY_CODE")));
                user.setFaildAttempts(rs.getInt("FAILED_ATTEMPTS"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                user.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                user.setRoleList(new LinkedList<>());
//                user.setUserAclList(new LinkedList<>());
            } else {
                user = userList.get(idx);
            }
            Role r = new Role(rs.getString("ROLE_ID"), new LabelRowMapper("ROLE_").mapRow(rs, 1));
            if (user.getRoleList().indexOf(r) == -1) {
                user.getRoleList().add(r);
            }
//            if (rs.getInt("USER_ACL_ID") != 0) {
//                UserAcl acl = new UserAcl(
//                        user.getUserId(),
//                        rs.getString("ACL_ROLE_ID"), new LabelRowMapper("ACL_ROLE_").mapRow(rs, 1),
//                        rs.getInt("ACL_REALM_COUNTRY_ID"), new LabelRowMapper("ACL_REALM_").mapRow(rs, 1),
//                        rs.getInt("ACL_HEALTH_AREA_ID"), new LabelRowMapper("ACL_HEALTH_AREA_").mapRow(rs, 1),
//                        rs.getInt("ACL_ORGANISATION_ID"), new LabelRowMapper("ACL_ORGANISATION_").mapRow(rs, 1),
//                        rs.getInt("ACL_PROGRAM_ID"), new LabelRowMapper("ACL_PROGRAM_").mapRow(rs, 1),
//                        rs.getString("ACL_LAST_MODIFIED_DATE"));
//                if (user.getUserAclList().indexOf(acl) == -1) {
//                    user.getUserAclList().add(acl);
//                }
//            }
        }
        return userList;
    }

}
