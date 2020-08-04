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
import cc.altius.FASP.rest.controller.UserRestController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author altius
 */
public class UserResultSetExtractor implements ResultSetExtractor<User> {
private final Logger auditLogger = LoggerFactory.getLogger(UserRestController.class);
    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = new User();
        boolean isFirst = true;
        while (rs.next()) {
            if (isFirst) {
                auditLogger.info("-------------------------1-------------------------------");
                user.setUserId(rs.getInt("USER_ID"));
                auditLogger.info("-------------------------2-------------------------------");
                user.setUsername(rs.getString("USERNAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setPhoneNumber(rs.getString("PHONE"));
                user.setPassword(rs.getString("PASSWORD"));
                auditLogger.info("-------------------------3-------------------------------");
                user.setRealm(new Realm(rs.getInt("REALM_ID"), new LabelRowMapper("REALM_").mapRow(rs, 1), rs.getString("REALM_CODE")));
                user.setLanguage(new Language(rs.getInt("LANGUAGE_ID"), rs.getString("LANGUAGE_NAME"), rs.getString("LANGUAGE_CODE")));
                user.setFaildAttempts(rs.getInt("FAILED_ATTEMPTS"));
                user.setLastLoginDate(rs.getTimestamp("LAST_LOGIN_DATE"));
                auditLogger.info("-------------------------4-------------------------------");
                user.setBaseModel(new BaseModelRowMapper().mapRow(rs, 1));
                user.setRoleList(new LinkedList<>());
                user.setUserAclList(new LinkedList<>());
                user.setBusinessFunctionList(new LinkedList<>());
            }
            Role r = new Role(rs.getString("ROLE_ID"), new LabelRowMapper("ROLE_").mapRow(rs, 1));
            auditLogger.info("-------------------------5-------------------------------");
            if (user.getRoleList().indexOf(r) == -1) {
                user.getRoleList().add(r);
            }
            if (rs.getInt("USER_ACL_ID") != 0) {
                auditLogger.info("-------------------------6-------------------------------");
                UserAcl acl = new UserAcl(
                        user.getUserId(),
                        rs.getInt("ACL_REALM_COUNTRY_ID"), new LabelRowMapper("ACL_REALM_").mapRow(rs, 1),
                        rs.getInt("ACL_HEALTH_AREA_ID"), new LabelRowMapper("ACL_HEALTH_AREA_").mapRow(rs, 1),
                        rs.getInt("ACL_ORGANISATION_ID"), new LabelRowMapper("ACL_ORGANISATION_").mapRow(rs, 1),
                        rs.getInt("ACL_PROGRAM_ID"), new LabelRowMapper("ACL_PROGRAM_").mapRow(rs, 1));
                if (user.getUserAclList().indexOf(acl) == -1) {
                    user.getUserAclList().add(acl);
                }
            }
            String bf = new String(rs.getString("BUSINESS_FUNCTION_ID"));
            auditLogger.info("-------------------------7-------------------------------");
            if (user.getBusinessFunctionList().indexOf(bf) == -1) {
                user.getBusinessFunctionList().add(bf);
            }
            isFirst = false;
        }
        auditLogger.info("-------------------------8-------------------------------");
        if (isFirst) {
            auditLogger.info("-------------------------9-------------------------------");
            return null;
        } else {
            auditLogger.info("-------------------------10-------------------------------"+user);
            auditLogger.info("-------------------------11-------------------------------");
            return user;
        }
    }

}
