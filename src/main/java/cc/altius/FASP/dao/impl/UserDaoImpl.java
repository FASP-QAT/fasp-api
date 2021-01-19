/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.rowMapper.BusinessFunctionRowMapper;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsResultSetExtractorBasic;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsResultSetExtractorFull;
import cc.altius.FASP.model.rowMapper.EmailUserRowMapper;
import cc.altius.FASP.model.rowMapper.ForgotPasswordTokenRowMapper;
import cc.altius.FASP.model.rowMapper.RoleListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.RoleResultSetExtractor;
import cc.altius.FASP.model.rowMapper.UserListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.UserResultSetExtractor;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author altius
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Value("${syncExpiresOn}")
    private int syncExpiresOn;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final static String customUserString = "SELECT "
            + "      `user`.`USER_ID`,`user`.`AGREEMENT_ACCEPTED`, `user`.`USERNAME`, `user`.`PASSWORD`,`user`.`SYNC_EXPIRES_ON`, "
            + "      `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
            + "      realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
            + "      lang.`LANGUAGE_ID`, lang.`LANGUAGE_NAME`, lang.`LANGUAGE_CODE`, "
            + "      `user`.`ACTIVE`, `user`.`EMAIL_ID`, `user`.`EXPIRES_ON`, "
            + "      role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
            + "      bf.`BUSINESS_FUNCTION_ID`, "
            + "      acl.USER_ACL_ID, "
            + "      acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
            + "      acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
            + "      acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
            + "      acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR`, "
            + "      DATE_FORMAT(acl.`LAST_MODIFIED_DATE`, '%Y-%m-%d %h:%i:%s') `ACL_LAST_MODIFIED_DATE` "
            + "  FROM us_user `user` "
            + "      LEFT JOIN rm_realm `realm` ON realm.`REALM_ID`=user.`REALM_ID` "
            + "      LEFT JOIN ap_label `realm_lb` ON realm.`LABEL_ID`=realm_lb.`LABEL_ID` "
            + "      LEFT JOIN ap_language lang ON lang.`LANGUAGE_ID`=`user`.`LANGUAGE_ID` "
            + "      LEFT JOIN us_user_role user_role ON user_role.`USER_ID`=`user`.`USER_ID` "
            + "      LEFT JOIN us_role role ON user_role.`ROLE_ID`=role.`ROLE_ID` "
            + "      LEFT JOIN ap_label role_lb ON role.`LABEL_ID`=role_lb.`LABEL_ID` "
            + "      LEFT JOIN us_role_business_function rbf ON role.`ROLE_ID`=rbf.`ROLE_ID` "
            + "      LEFT JOIN us_business_function bf ON rbf.`BUSINESS_FUNCTION_ID`=bf.`BUSINESS_FUNCTION_ID` "
            + "      LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID` "
            + "      LEFT JOIN rm_realm_country acl_realm_country ON acl.`REALM_COUNTRY_ID`=acl_realm_country.`REALM_COUNTRY_ID` "
            + "      LEFT JOIN ap_country acl_country ON acl_realm_country.`COUNTRY_ID`=acl_country.`COUNTRY_ID` "
            + "      LEFT JOIN ap_label acl_country_lb ON acl_country.`LABEL_ID`=acl_country_lb.`LABEL_ID` "
            + "      LEFT JOIN rm_health_area acl_health_area ON acl.`HEALTH_AREA_ID`=acl_health_area.`HEALTH_AREA_ID` "
            + "      LEFT JOIN ap_label acl_health_area_lb ON acl_health_area.`LABEL_ID`=acl_health_area_lb.`LABEL_ID` "
            + "      LEFT JOIN rm_organisation acl_organisation ON acl.`ORGANISATION_ID`=acl_organisation.`ORGANISATION_ID` "
            + "      LEFT JOIN ap_label acl_organisation_lb ON acl_organisation.`LABEL_ID`=acl_organisation_lb.`LABEL_ID` "
            + "      LEFT JOIN rm_program acl_program ON acl.`PROGRAM_ID`=acl_program.`PROGRAM_ID` "
            + "      LEFT JOIN ap_label acl_program_lb on acl_program.`LABEL_ID`=acl_program_lb.`LABEL_ID` "
            + " WHERE TRUE ";

    private static final String customUserOrderBy = "  ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,bf.`BUSINESS_FUNCTION_ID`,acl.`USER_ACL_ID`";

    private static final String userString = "SELECT "
            + "    `user`.`USER_ID`, `user`.`USERNAME`, `user`.`EMAIL_ID`, `user`.`PHONE`, `user`.`PASSWORD`, "
            + "    `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
            + "    realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
            + "    lang.`LANGUAGE_ID`, lang.`LANGUAGE_NAME`, lang.`LANGUAGE_CODE`, "
            + "    `user`.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, `user`.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, `user`.`ACTIVE`, "
            + "    role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
            + "    rbf.BUSINESS_FUNCTION_ID, "
            + "    acl.USER_ACL_ID, "
            + "    acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
            + "    acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
            + "    acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
            + "    acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR`, "
            + "    DATE_FORMAT(acl.`LAST_MODIFIED_DATE`, '%Y-%m-%d %h:%i:%s') `ACL_LAST_MODIFIED_DATE` "
            + " FROM us_user `user` "
            + "    LEFT JOIN rm_realm `realm` ON realm.`REALM_ID`=user.`REALM_ID` "
            + "    LEFT JOIN ap_label `realm_lb` ON realm.`LABEL_ID`=realm_lb.`LABEL_ID` "
            + "    LEFT JOIN ap_language lang ON lang.`LANGUAGE_ID`=`user`.`LANGUAGE_ID` "
            + "    LEFT JOIN us_user cb ON cb.`USER_ID`=`user`.`CREATED_BY` "
            + "    LEFT JOIN us_user lmb ON lmb.`USER_ID`=`user`.`LAST_MODIFIED_BY` "
            + "    LEFT JOIN us_user_role user_role ON user_role.`USER_ID`=`user`.`USER_ID` "
            + "    LEFT JOIN us_role role ON user_role.`ROLE_ID`=role.`ROLE_ID` "
            + "    LEFT JOIN ap_label role_lb ON role.`LABEL_ID`=role_lb.`LABEL_ID` "
            + "    LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID` "
            + "    LEFT JOIN rm_realm_country acl_realm_country ON acl.`REALM_COUNTRY_ID`=acl_realm_country.`REALM_COUNTRY_ID` "
            + "    LEFT JOIN ap_country acl_country ON acl_realm_country.`COUNTRY_ID`=acl_country.`COUNTRY_ID` "
            + "    LEFT JOIN ap_label acl_country_lb ON acl_country.`LABEL_ID`=acl_country_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_health_area acl_health_area ON acl.`HEALTH_AREA_ID`=acl_health_area.`HEALTH_AREA_ID` "
            + "    LEFT JOIN ap_label acl_health_area_lb ON acl_health_area.`LABEL_ID`=acl_health_area_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_organisation acl_organisation ON acl.`ORGANISATION_ID`=acl_organisation.`ORGANISATION_ID` "
            + "    LEFT JOIN ap_label acl_organisation_lb ON acl_organisation.`LABEL_ID`=acl_organisation_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_program acl_program ON acl.`PROGRAM_ID`=acl_program.`PROGRAM_ID` "
            + "    LEFT JOIN ap_label acl_program_lb on acl_program.`LABEL_ID`=acl_program_lb.`LABEL_ID` "
            + "    LEFT JOIN us_role_business_function rbf ON role.ROLE_ID=rbf.ROLE_ID "
            + "    LEFT JOIN us_user cu ON user.REALM_ID=cu.REALM_ID OR cu.REALM_ID IS NULL "
            + "    WHERE cu.USER_ID=:curUser ";

    private static final String userOrderBy = " ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,acl.`USER_ACL_ID`";

    /**
     * Method to get Customer object from username
     *
     * @param username Username used to login
     * @return Returns the Customer object, null if no object could be found
     */
    @Override
    public CustomUserDetails getCustomUserByUsername(String username) {
        logger.info("Inside the getCustomerUserByUsername method - " + username);
        String sqlString = this.customUserString
                + "  AND LOWER(`user`.`USERNAME`)=LOWER(:username) "
                + this.customUserOrderBy;

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            return this.namedParameterJdbcTemplate.query(sqlString, params, new CustomUserDetailsResultSetExtractorBasic());
        } catch (Exception e) {
            return null;
        }
    }

//    /**
//     * Method to get Customer object from email id
//     *
//     * @param emailId
//     * @return Returns the Customer object, null if no object could be found
//     */
    @Override
    public CustomUserDetails getCustomUserByEmailId(String emailId) {
        logger.info("Inside the getCustomUserByEmailId method - " + emailId);
        String sqlString = this.customUserString + "  AND LOWER(`user`.`EMAIL_ID`)=LOWER(:emailId) " + this.customUserOrderBy;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("emailId", emailId);
            CustomUserDetails user = this.namedParameterJdbcTemplate.query(sqlString, params, new CustomUserDetailsResultSetExtractorBasic());
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CustomUserDetails getCustomUserByUserId(int userId) {
        String sqlString = this.customUserString + "  AND `user`.`USER_ID`=:userId " + this.customUserOrderBy;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            CustomUserDetails user = this.namedParameterJdbcTemplate.query(sqlString, params, new CustomUserDetailsResultSetExtractorFull());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to get the list of Business functions that a userId has access to
     *
     * @param userId userId that you want to get the Business functions for
     * @return Returns a list of Business functions that the userId has access
     * to
     */
    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        logger.info("Inside the getBusinessFunctionsForUserId method - " + userId);
        String sqlString = "SELECT BUSINESS_FUNCTION_ID FROM us_user_role LEFT JOIN us_role_business_function ON us_user_role.ROLE_ID=us_role_business_function.ROLE_ID WHERE us_user_role.USER_ID=:userId AND BUSINESS_FUNCTION_ID IS NOT NULL";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return this.namedParameterJdbcTemplate.queryForList(sqlString, params, String.class);
    }

    @Override
    public Map<String, Object> checkIfUserExists(String username, String password) {
        CustomUserDetails customUserDetails = null;
        Map<String, Object> responseMap = new HashMap<>();
        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM us_user `user`"
                + " LEFT JOIN us_user_role user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN us_role role ON user_role.ROLE_ID=role.ROLE_ID "
                + " WHERE user.USERNAME=:username";
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("username", username);
            customUserDetails = this.namedParameterJdbcTemplate.query(sql, params, new CustomUserDetailsResultSetExtractorBasic());
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, customUserDetails.getPassword())) {
                if (!customUserDetails.isActive()) {
                    responseMap.put("customUserDetails", null);
                    responseMap.put("message", "User is not active");
                    return responseMap;
                } else if (customUserDetails.getFailedAttempts() >= 3) {
                    responseMap.put("customUserDetails", null);
                    responseMap.put("message", "User account is locked");
                    return responseMap;
                } else {
                    customUserDetails.setBusinessFunction(this.getBusinessFunctionsForUserId(customUserDetails.getUserId()));
                    responseMap.put("customUserDetails", customUserDetails);
                    responseMap.put("message", "Login successful");
                    this.resetFailedAttemptsByUsername(username);
                }
            } else {
                this.updateFailedAttemptsByUserId(username);
                responseMap.put("customUserDetails", null);
                responseMap.put("message", "Password is invalid");
                return responseMap;
            }
        } catch (Exception i) {
            logger.error("Error", i);
            responseMap.put("customUserDetails", null);
            responseMap.put("message", "User does not exists");
            return responseMap;
        }
        return responseMap;
    }

    @Override
    public int resetFailedAttemptsByUsername(String emailId) {
        try {
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.EST);
            Map<String, Object> params = new HashMap<>();
            params.put("emailId", emailId);
            params.put("curDate", curDate);
            String sqlString = "UPDATE `us_user` SET FAILED_ATTEMPTS=0,LAST_LOGIN_DATE=:curDate WHERE LOWER(EMAIL_ID)=LOWER(:emailId)";
            return this.namedParameterJdbcTemplate.update(sqlString, params);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public int updateFailedAttemptsByUserId(String emailId) {
        try {
            String sqlQuery = "UPDATE `us_user` SET FAILED_ATTEMPTS=FAILED_ATTEMPTS+1 WHERE LOWER(EMAIL_ID)=LOWER(:emailId)";
            Map<String, Object> params = new HashMap<>();
            params.put("emailId", emailId);
            return this.namedParameterJdbcTemplate.update(sqlQuery, params);
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public Role getRoleById(String roleId) {
        String sql = "SELECT us_role.*,lb.`LABEL_ID`,lb.`LABEL_EN`,lb.`LABEL_FR`,lb.`LABEL_PR`,lb.`LABEL_SP`, rb.`BUSINESS_FUNCTION_ID`,c.`CAN_CREATE_ROLE` FROM us_role "
                + "LEFT JOIN ap_label lb ON lb.`LABEL_ID`=us_role.`LABEL_ID` "
                + "LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=us_role.`ROLE_ID` "
                + "LEFT JOIN us_can_create_role c ON c.`ROLE_ID`=us_role.`ROLE_ID`"
                + "WHERE us_role.ROLE_ID=:roleId";
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        return this.namedParameterJdbcTemplate.query(sql, params, new RoleResultSetExtractor());
    }

    @Override
    public List<Role> getRoleList(CustomUserDetails curUser) {
        StringBuilder sb = new StringBuilder();
//        sb.append(" SELECT us_role.*,lb.`LABEL_ID`,lb.`LABEL_EN`,lb.`LABEL_FR`,lb.`LABEL_PR`,lb.`LABEL_SP`, rb.`BUSINESS_FUNCTION_ID`,c.`CAN_CREATE_ROLE` FROM us_role "
//                + "LEFT JOIN ap_label lb ON lb.`LABEL_ID`=us_role.`LABEL_ID` "
//                + "LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=us_role.`ROLE_ID` "
//                + "LEFT JOIN us_can_create_role c ON c.`ROLE_ID`=us_role.`ROLE_ID` WHERE 1 ");
//        sb.append("SELECT c.`CAN_CREATE_ROLE`,r.`ROLE_ID`,lb.*,rb.`BUSINESS_FUNCTION_ID` "
//                + " FROM us_can_create_role c "
//                + " LEFT JOIN us_role r ON r.`ROLE_ID`=c.`CAN_CREATE_ROLE` "
//                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` "
//                + " LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=r.`ROLE_ID` WHERE 1 ");
        sb.append("SELECT c.`CAN_CREATE_ROLE`,r.`ROLE_ID`,lb.*,rb.`BUSINESS_FUNCTION_ID` FROM us_role r "
                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` "
                + " LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=r.`ROLE_ID` "
                + " LEFT JOIN us_can_create_role c ON c.`CAN_CREATE_ROLE`=r.`ROLE_ID` "
                + " WHERE 1 ");
        String role[] = new String[curUser.getRoles().size()];
        for (int i = 0; i < curUser.getRoles().size(); i++) {
            role[i] = curUser.getRoles().get(i).getRoleId();
        }
//        if (Arrays.asList(role).contains("ROLE_APPLICATION_ADMIN")) {
//            sb.append("AND c.`ROLE_ID`=\"ROLE_APPLICATION_ADMIN\"");
//
//        } else 
        if (Arrays.asList(role).contains("ROLE_REALM_ADMIN")) {
            sb.append("AND c.`ROLE_ID`=\"ROLE_REALM_ADMIN\"");
        }
        sb.append(" ORDER BY lb.`LABEL_EN` ASC ");
        return this.namedParameterJdbcTemplate.query(sb.toString(), new RoleListResultSetExtractor());
    }

    @Override
    @Transactional
    public int addNewUser(User user, int curUser) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("us_user").usingGeneratedKeyColumns("USER_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("REALM_ID", ((user.getRealm() == null || user.getRealm().getRealmId() == null ? null : (user.getRealm().getRealmId() != -1 ? user.getRealm().getRealmId() : null))));
        map.put("AGREEMENT_ACCEPTED", false);
        map.put("USERNAME", user.getUsername());
        map.put("PASSWORD", user.getPassword());
//        map.put("PASSWORD", null);
        map.put("EMAIL_ID", user.getEmailId());
        map.put("PHONE", user.getPhoneNumber());
        map.put("LANGUAGE_ID", user.getLanguage().getLanguageId());
        map.put("ACTIVE", true);
        map.put("EXPIRED", false);
        map.put("FAILED_ATTEMPTS", 0);
        map.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("SYNC_EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, syncExpiresOn));
        map.put("LAST_LOGIN_DATE", null);
        map.put("CREATED_BY", curUser);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser);
        map.put("LAST_MODIFIED_DATE", curDate);
        int userId = insert.executeAndReturnKey(map).intValue();
        String sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";
        Map<String, Object>[] paramArray = new HashMap[user.getRoles().length];
        Map<String, Object> params = new HashMap<>();
        int x = 0;
        for (String role : user.getRoles()) {
            params = new HashMap<>();
            params.put("userId", userId);
            params.put("roleId", role);
            params.put("curUser", curUser);
            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramArray);
        return userId;
    }

    @Override
    public List<User> getUserList(CustomUserDetails curUser) {
        String sql = this.userString + this.userOrderBy;
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        return this.namedParameterJdbcTemplate.query(sql, params, new UserListResultSetExtractor());
    }

    @Override
    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser) {
        String sql = this.userString + " AND user.REALM_ID=:realmId ";

        Map<String, Object> params = new HashMap<>();
        params.put("realmId", realmId);
        if (curUser.getRealm().getRealmId() != -1) {
            params.put("userRealmId", curUser.getRealm().getRealmId());
            sql += " AND user.REALM_ID=:userRealmId ";
        }
        params.put("curUser", curUser.getUserId());
        sql += this.userOrderBy;
        return this.namedParameterJdbcTemplate.query(sql, params, new UserListResultSetExtractor());
    }

    @Override
    public User getUserByUserId(int userId, CustomUserDetails curUser) {
        String sql = this.userString + " AND `user`.`USER_ID`=:userId " + this.userOrderBy;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("curUser", curUser.getUserId());
        User u = this.namedParameterJdbcTemplate.query(sql, params, new UserResultSetExtractor());
        if (u == null) {
            throw new EmptyResultDataAccessException(1);
        } else {
            return u;
        }
    }

    @Override
    @Transactional
    public int updateUser(User user, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlString = "";
        sqlString = "UPDATE us_user u "
                + "SET "
                + "u.`USERNAME`=:userName, "
                + "u.`EMAIL_ID`=:emailId, "
                + "u.`PHONE`=:phoneNo, "
                + "u.`LANGUAGE_ID`=:languageId, "
                + "u.`ACTIVE`=:active, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "u.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + "WHERE  u.`USER_ID`=:userId;";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", user.getUsername());
        params.put("emailId", user.getEmailId());
        params.put("phoneNo", user.getPhoneNumber());
        params.put("languageId", user.getLanguage().getLanguageId());
        params.put("active", user.isActive());
        params.put("lastModifiedBy", curUser);
        params.put("lastModifiedDate", curDate);
        params.put("userId", user.getUserId());
        int row = this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DELETE FROM us_user_role WHERE  USER_ID=:userId";
        row = this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";
        Map<String, Object>[] paramArray = new HashMap[user.getRoles().length];
        params.clear();
        int x = 0;
        for (String role : user.getRoles()) {
            params = new HashMap<>();
            params.put("userId", user.getUserId());
            params.put("roleId", role);
            params.put("curUser", curUser);
            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramArray);
        return row;
    }

    @Override
    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page) {
        String message = "", sql;
        int result2 = 0;
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("emailId", user.getEmailId());
        if (page == 1) {
            sql = "SELECT COUNT(*) FROM us_user u WHERE LOWER(u.`EMAIL_ID`)=LOWER(:emailId)";
            result2 = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            if (result2 > 0) {
                message = "static.message.user.emailIdExists";
            }
        } else if (page == 2) {
            sql = "SELECT u.`USER_ID` FROM us_user u WHERE LOWER(u.`EMAIL_ID`)=LOWER(:emailId)";
            try {
                result2 = this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
            } catch (EmptyResultDataAccessException e) {
            }
            if (result2 > 0 && result2 != user.getUserId()) {
                message = "static.message.user.emailIdExists";
            }
        }
        return message;
    }

    @Override
    public int unlockAccount(int userId, String password) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE us_user u "
                + "SET "
                + "u.`FAILED_ATTEMPTS`=0, "
                + "u.`EXPIRES_ON`=:expiresOn, "
                + "u.`PASSWORD`=:pwd, "
                + "u.`LAST_MODIFIED_DATE`=:lastModifiedDate, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy "
                + "WHERE u.`USER_ID`=:userId;";
        Map<String, Object> params = new HashMap<>();
        params.put("expiresOn", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        params.put("pwd", password);
        params.put("lastModifiedBy", 1);
        params.put("lastModifiedDate", curDate);
        params.put("userId", userId);
        try {
            return namedParameterJdbcTemplate.update(sql, params);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<BusinessFunction> getBusinessFunctionList() {
        String sqlString = "SELECT bf.BUSINESS_FUNCTION_ID, "
                + "	bfl.LABEL_ID, bfl.LABEL_EN, bfl.LABEL_FR, bfl.LABEL_PR, bfl.LABEL_SP, "
                + "    TRUE `ACTIVE`, cb.USER_ID `CB_USER_ID`, cb.USERNAME `CB_USERNAME`, bf.CREATED_DATE, lmb.USER_ID `LMB_USER_ID`, lmb.USERNAME `LMB_USERNAME`, bf.LAST_MODIFIED_DATE "
                + "FROM us_business_function bf  "
                + "LEFT JOIN ap_label bfl ON bf.LABEL_ID=bfl.LABEL_ID "
                + "LEFT JOIN us_user cb ON bf.CREATED_BY=cb.USER_ID "
                + "LEFT JOIN us_user lmb ON bf.LAST_MODIFIED_BY=lmb.USER_ID";
        return this.namedParameterJdbcTemplate.query(sqlString, new BusinessFunctionRowMapper());
    }

    @Override
    public int updatePassword(int userId, String newPassword, int offset) {
        Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, offset);
        String sqlString = "UPDATE us_user SET PASSWORD=:hash, EXPIRES_ON=:expiresOn,FAILED_ATTEMPTS=0 WHERE us_user.USER_ID=:userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("hash", newPassword);
        params.put("expiresOn", offsetDate);
        return namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public int updatePassword(String emailId, String token, String newPassword, int offset) {
        Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, offset);
        String sqlString = "UPDATE us_user SET PASSWORD=:hash, EXPIRES_ON=:expiresOn, FAILED_ATTEMPTS=0 WHERE LOWER(us_user.EMAIL_ID)=LOWER(:emailId)";
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("hash", newPassword);
        params.put("expiresOn", offsetDate);
        return namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public boolean confirmPassword(String emailId, String password) {
        String sqlString = "SELECT us_user.PASSWORD FROM us_user WHERE LOWER(us_user.`EMAIL_ID`)=LOWER(:emailId)";
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        String hash = namedParameterJdbcTemplate.queryForObject(sqlString, params, String.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }

    @Override
    @Transactional
    public int addRole(Role role, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("us_role");
        Map<String, Object> params = new HashMap<>();
        String roleId = "ROLE";
        int labelId = 0;
        String[] splited = role.getLabel().getLabel().split("\\s+");
        for (int i = 0; i < splited.length; i++) {
            roleId = roleId + "_" + splited[i].toUpperCase();
        }
        labelId = this.labelDao.addLabel(role.getLabel(), LabelConstants.US_ROLE, 1);
        params.put("ROLE_ID", roleId);
        params.put("LABEL_ID", labelId);
        params.put("CREATED_BY", curUser.getUserId());
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());
        params.put("LAST_MODIFIED_DATE", curDate);
        int rows1 = si.execute(params);
        si = new SimpleJdbcInsert(dataSource).withTableName("us_role_business_function");
        SqlParameterSource[] paramList = new SqlParameterSource[role.getBusinessFunctions().length];
        int i = 0;
        for (String bf : role.getBusinessFunctions()) {
            params = new HashMap<>();
            params.put("ROLE_ID", roleId);
            params.put("BUSINESS_FUNCTION_ID", bf);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        int result[] = si.executeBatch(paramList);
        si = new SimpleJdbcInsert(dataSource).withTableName("us_can_create_role");
        if (role.getCanCreateRoles().length > 0) {
            paramList = new SqlParameterSource[role.getCanCreateRoles().length];
            i = 0;
            for (String r : role.getCanCreateRoles()) {
                params = new HashMap<>();
                params.put("ROLE_ID", roleId);
                params.put("CAN_CREATE_ROLE", r);
                paramList[i] = new MapSqlParameterSource(params);
                i++;
            }

            si.executeBatch(paramList);

        }
        String sql = "INSERT INTO us_can_create_role  VALUES (\"ROLE_APPLICATION_ADMIN\",?);";
        this.jdbcTemplate.update(sql, roleId);
        return (rows1 == 1 && result.length > 0 ? 1 : 0);
    }

    @Override
    @Transactional
    public int updateRole(Role role, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "";
        Map<String, Object> params = new HashMap<>();
        params.put("roleId", role.getRoleId());
        params.put("label_en", role.getLabel().getLabel_en());
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("lastModifiedDate", curDate);
        this.namedParameterJdbcTemplate.update("DELETE rbf.* FROM us_role_business_function rbf where rbf.ROLE_ID=:roleId", params);
        this.namedParameterJdbcTemplate.update("DELETE r.* FROM us_can_create_role r WHERE r.ROLE_ID=:roleId", params);
        sql = "UPDATE us_role r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "SET "
                + "l.`LABEL_EN`=:label_en, "
                + "l.`LAST_MODIFIED_BY`=IF(l.`LABEL_EN`!=:label_en, :lastModifiedBy, l.LAST_MODIFIED_BY), "
                + "l.`LAST_MODIFIED_DATE`=IF(l.`LABEL_EN`!=:label_en, :lastModifiedDate, l.LAST_MODIFIED_DATE) "
                + "WHERE r.`ROLE_ID`=:roleId";
        this.namedParameterJdbcTemplate.update(sql, params);
        params.clear();
        sql = "DELETE rbf.* FROM us_role_business_function rbf WHERE rbf.ROLE_ID=:roleId";
        params.put("roleId", role.getRoleId());
        this.namedParameterJdbcTemplate.update(sql, params);

        SimpleJdbcInsert si = new SimpleJdbcInsert(dataSource).withTableName("us_role_business_function");
        SqlParameterSource[] paramList = new SqlParameterSource[role.getBusinessFunctions().length];
        int i = 0;
        for (String bf : role.getBusinessFunctions()) {
            params = new HashMap<>();
            params.put("ROLE_ID", role.getRoleId());
            params.put("BUSINESS_FUNCTION_ID", bf);
            params.put("CREATED_BY", curUser.getUserId());
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser.getUserId());
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        params.clear();

        sql = "DELETE ccr.* FROM us_can_create_role ccr WHERE ccr.ROLE_ID=:roleId";
        params.put("roleId", role.getRoleId());
        this.namedParameterJdbcTemplate.update(sql, params);

        si = new SimpleJdbcInsert(dataSource).withTableName("us_can_create_role");
        if (role.getCanCreateRoles().length > 0) {
            paramList = new SqlParameterSource[role.getCanCreateRoles().length];
            i = 0;
            for (String canCreateRole : role.getCanCreateRoles()) {
                params = new HashMap<>();
                params.put("ROLE_ID", role.getRoleId());
                params.put("CAN_CREATE_ROLE", canCreateRole);
                paramList[i] = new MapSqlParameterSource(params);
                i++;
            }
            si.executeBatch(paramList);

        }

        if (!role.getRoleId().equals("ROLE_APPLICATION_ADMIN")) {
            sql = "INSERT IGNORE INTO us_can_create_role  VALUES (\"ROLE_APPLICATION_ADMIN\",?);";
            this.jdbcTemplate.update(sql, role.getRoleId());

        }
        return 1;
    }

    @Override
    public String generateTokenForUserId(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        return this.namedParameterJdbcTemplate.queryForObject("CALL generateForgotPasswordToken(:userId,:curDate)", params, String.class);
    }

    @Override
    public EmailUser getEmailUserByEmailId(String emailId) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        return this.namedParameterJdbcTemplate.queryForObject("SELECT USERNAME, USER_ID, EMAIL_ID FROM us_user WHERE LOWER(EMAIL_ID)=LOWER(:emailId)", params, new EmailUserRowMapper());
    }

    @Override
    public ForgotPasswordToken getForgotPasswordToken(String emailId, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("token", token);
        return this.namedParameterJdbcTemplate.queryForObject("SELECT fpt.*, u.USERNAME FROM us_forgot_password_token fpt LEFT JOIN us_user u on fpt.USER_ID=u.USER_ID WHERE fpt.token=:token AND LOWER(u.EMAIL_ID)=LOWER(:emailId)", params, new ForgotPasswordTokenRowMapper());
    }

    @Override
    public void updateTriggeredDateForForgotPasswordToken(String username, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("token", token);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        this.namedParameterJdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_TRIGGERED_DATE=:curDate WHERE LOWER(u.EMAIL_ID)=LOWER(:username) AND fpt.TOKEN=:token", params);
    }

    @Override
    public void updateCompletionDateForForgotPasswordToken(String emailId, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("token", token);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        this.namedParameterJdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_COMPLETION_DATE=:curDate WHERE LOWER(u.EMAIL_ID)=LOWER(:emailId) AND fpt.TOKEN=:token", params);
    }

    @Override
    public boolean isTokenLogout(String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        String sqlString = "SELECT COUNT(*) FROM us_token_logout WHERE TOKEN=:token";
        return (this.namedParameterJdbcTemplate.queryForObject(sqlString, params, Integer.class) > 0);
    }

    @Override
    public void addTokenToLogout(String token) {
        String sqlString = "INSERT IGNORE INTO us_token_logout (TOKEN, LOGOUT_DATE) VALUES (:token, :curDate)";
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.IST));
        this.namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public int mapAccessControls(User user, CustomUserDetails curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlString = "";
        int row = 0, x = 0, count;
        Map<String, Object> params = new HashMap<>();
        Map<String, Object>[] paramArray = new HashMap[user.getUserAcls().length];
        if (user.getUserAcls() != null && user.getUserAcls().length > 1) {
            for (UserAcl userAcl : user.getUserAcls()) {
                count = 0;
                if (userAcl.getRealmCountryId() == -1) {
                    count++;
                }
                if (userAcl.getHealthAreaId() == -1) {
                    count++;
                }
                if (userAcl.getOrganisationId() == -1) {
                    count++;
                }
                if (userAcl.getProgramId() == -1) {
                    count++;
                }
                if (count == 4) {
                    return -2;
                }
            }

        }
        if (user.getUserAcls() != null && user.getUserAcls().length > 0) {
            sqlString = "DELETE FROM us_user_acl WHERE  USER_ID=:userId";
            params.put("userId", user.getUserId());
            this.namedParameterJdbcTemplate.update(sqlString, params);
            sqlString = "INSERT INTO us_user_acl (USER_ID, REALM_COUNTRY_ID, HEALTH_AREA_ID, ORGANISATION_ID, PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:userId, :realmCountryId, :healthAreaId, :organisationId, :programId, :curUser, :curDate, :curUser, :curDate)";
            paramArray = new HashMap[user.getUserAcls().length];
            for (UserAcl userAcl : user.getUserAcls()) {
                params = new HashMap<>();
                params.put("userId", user.getUserId());
                params.put("realmCountryId", (userAcl.getRealmCountryId() == -1 ? null : userAcl.getRealmCountryId()));
                params.put("healthAreaId", (userAcl.getHealthAreaId() == -1 ? null : userAcl.getHealthAreaId()));
                params.put("organisationId", (userAcl.getOrganisationId() == -1 ? null : userAcl.getOrganisationId()));
                params.put("programId", (userAcl.getProgramId() == -1 ? null : userAcl.getProgramId()));
                params.put("curUser", curUser.getUserId());
                params.put("curDate", curDate);
                paramArray[x] = params;
                x++;
            }
            row = this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramArray).length;
        }
        return row;
    }

    @Override
    public int updateSuncExpiresOn(String emailId) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("syncexpiresOn", DateUtils.getCurrentDateObject(DateUtils.EST));
        return this.namedParameterJdbcTemplate.update("update us_user u set u.SYNC_EXPIRES_ON=:syncexpiresOn where LOWER(u.EMAIL_ID)=LOWER(:emailId)", params);
    }

    @Override
    public int updateUserLanguage(int userId, String languageCode) {
        String sql;
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        sql = "SELECT l.`LANGUAGE_ID` FROM ap_language l WHERE l.`LANGUAGE_CODE`=?;";
        int languageId = this.jdbcTemplate.queryForObject(sql, Integer.class, languageCode);
        sql = "UPDATE us_user u SET u.`LANGUAGE_ID`=?,u.`LAST_LOGIN_DATE`=?,u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, languageId, curDate, userId, userId);
    }

    @Override
    public int acceptUserAgreement(int userId) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE us_user u SET u.`AGREEMENT_ACCEPTED`=1,u.`LAST_LOGIN_DATE`=?,u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, curDate, userId, userId);
    }

    @Override
    public int addUserJiraAccountId(int userId, String jiraCustomerAccountId) {
        String sql = "UPDATE us_user u SET u.`JIRA_ACCOUNT_ID`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, jiraCustomerAccountId, userId);
    }

    @Override
    public String getUserJiraAccountId(int userId) {
        String sql = "SELECT u.`JIRA_ACCOUNT_ID` FROM us_user u WHERE u.`USER_ID`=?;";
        try {
            return this.jdbcTemplate.queryForObject(sql, String.class, userId);
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getUserListForUpdateJiraAccountId() {
        String sql = "SELECT DISTINCT(u.`EMAIL_ID`) FROM us_user u WHERE u.`JIRA_ACCOUNT_ID` IS NULL OR u.`JIRA_ACCOUNT_ID` = '';";
        try {
            return this.jdbcTemplate.queryForList(sql, String.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateUserJiraAccountId(String emailAddress, String jiraAccountId) {
        String sql = "UPDATE us_user u SET u.`JIRA_ACCOUNT_ID`=? WHERE u.`EMAIL_ID`=?;";
        this.jdbcTemplate.update(sql, jiraAccountId, emailAddress);
    }

    @Override
    public String getEmailByUserId(int userId) {
        String sql = "select u.EMAIL_ID from us_user u where u.USER_ID=?;";
        return this.jdbcTemplate.queryForObject(sql,String.class ,userId);
    }

}
