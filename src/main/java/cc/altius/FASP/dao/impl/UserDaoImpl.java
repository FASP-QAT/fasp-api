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
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.rowMapper.BusinessFunctionRowMapper;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsResultSetExtractor;
import cc.altius.FASP.model.rowMapper.EmailUserRowMapper;
import cc.altius.FASP.model.rowMapper.ForgotPasswordTokenRowMapper;
import cc.altius.FASP.model.rowMapper.RoleRowMapper;
import cc.altius.FASP.model.rowMapper.UserListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.UserResultSetExtractor;
import cc.altius.utils.DateUtils;
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
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Method to get Customer object from username
     *
     * @param username Username used to login
     * @return Returns the Customer object, null if no object could be found
     */
    @Override
    public CustomUserDetails getCustomUserByUsername(String username) {
        logger.info("Inside the getCustomerUserByUsername method - " + username);
        String sqlString = "SELECT "
                + "      `user`.`USER_ID`, `user`.`USERNAME`, `user`.`PASSWORD`, "
                + "      `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
                + "      realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
                + "      lang.`LANGUAGE_ID`, lang.`LANGUAGE_NAME`, "
                + "      `user`.`ACTIVE`, `user`.`EMAIL_ID`, `user`.`EXPIRES_ON`, "
                + "      role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
                + "      bf.`BUSINESS_FUNCTION_ID`, "
                + "      acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
                + "      acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
                + "      acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
                + "      acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR` "
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
                + "      WHERE `user`.`USERNAME`=? "
                + "  ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,bf.`BUSINESS_FUNCTION_ID`,acl.`USER_ACL_ID`";
        try {
            return this.jdbcTemplate.query(sqlString, new CustomUserDetailsResultSetExtractor(), username);
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
        String sqlString = "SELECT BUSINESS_FUNCTION_ID FROM us_user_role LEFT JOIN us_role_business_function ON us_user_role.ROLE_ID=us_role_business_function.ROLE_ID WHERE us_user_role.USER_ID=? AND BUSINESS_FUNCTION_ID IS NOT NULL";
        return this.jdbcTemplate.queryForList(sqlString, String.class, userId);
    }

    @Override
    public Map<String, Object> checkIfUserExists(String username, String password) {
        CustomUserDetails customUserDetails = null;
        Map<String, Object> responseMap = new HashMap<>();
        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM us_user `user`"
                + " LEFT JOIN us_user_role user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN us_role role ON user_role.ROLE_ID=role.ROLE_ID "
                + " WHERE user.USERNAME=?";
        try {
            customUserDetails = this.jdbcTemplate.query(sql, new CustomUserDetailsResultSetExtractor(), username);
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
    public int resetFailedAttemptsByUsername(String username) {
        try {
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.EST);
            String sqlreset = "UPDATE `us_user` SET FAILED_ATTEMPTS=0,LAST_LOGIN_DATE=? WHERE USERNAME=?";
            return this.jdbcTemplate.update(sqlreset, curDt, username);
        } catch (DataAccessException e) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
            return 0;
        }
    }

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        try {
            String sqlQuery = "UPDATE `us_user` SET FAILED_ATTEMPTS=FAILED_ATTEMPTS+1 WHERE USERNAME=?";
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlQuery));
            return this.jdbcTemplate.update(sqlQuery, username);
        } catch (DataAccessException e) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "Could not update failed attempts :" + e));
            return 0;
        }
    }

    @Override
    public List<Role> getRoleList() {
        String sql = " SELECT us_role.*,lb.`LABEL_ID`,lb.`LABEL_EN`,lb.`LABEL_FR`,lb.`LABEL_PR`,lb.`LABEL_SP`,c.`ROLE_ID` AS  CAN_CREATE_ROLE,rb.`BUSINESS_FUNCTION_ID` FROM us_role "
                + "LEFT JOIN ap_label lb ON lb.`LABEL_ID`=us_role.`LABEL_ID` "
                + "LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=us_role.`ROLE_ID` "
                + "LEFT JOIN us_can_create_role c ON c.`CAN_CREATE_ROLE`=us_role.`ROLE_ID`; ";
        return this.jdbcTemplate.query(sql, new RoleRowMapper());
    }

    @Override
    @Transactional
    public int addNewUser(User user, int curUser) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("us_user").usingGeneratedKeyColumns("USER_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("REALM_ID", user.getRealm().getRealmId());
        map.put("USERNAME", user.getUsername());
        map.put("PASSWORD", user.getPassword());
        map.put("EMAIL_ID", user.getEmailId());
        map.put("PHONE", user.getPhoneNumber());
        map.put("LANGUAGE_ID", user.getLanguage().getLanguageId());
        map.put("ACTIVE", true);
        map.put("EXPIRED", false);
        map.put("FAILED_ATTEMPTS", 0);
        map.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("SYNC_EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("LAST_LOGIN_DATE", null);
        map.put("CREATED_BY", curUser);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser);
        map.put("LAST_MODIFIED_DATE", curDate);
        int userId = insert.executeAndReturnKey(map).intValue();
        String sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object>[] paramArray = new HashMap[user.getRoleList().length];
        Map<String, Object> params = new HashMap<>();
        int x = 0;
        for (String role : user.getRoleList()) {
            params = new HashMap<>();
            params.put("userId", userId);
            params.put("roleId", role);
            params.put("curUser", curUser);
            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        nm.batchUpdate(sqlString, paramArray);
        if (user.getUserAclList() != null) {
            sqlString = "INSERT INTO us_user_acl (USER_ID, REALM_COUNTRY_ID, HEALTH_AREA_ID, ORGANISATION_ID, PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:userId, :realmCountryId, :healthAreaId, :organisationId, :programId, :curUser, :curDate, :curUser, :curDate)";
            paramArray = new HashMap[user.getUserAclList().size()];
            x = 0;
            for (UserAcl userAcl : user.getUserAclList()) {
                params = new HashMap<>();
                params.put("userId", userId);
                params.put("realmCountryId", (userAcl.getRealmCountryId() == -1 ? null : userAcl.getRealmCountryId()));
                params.put("healthAreaId", (userAcl.getRealmCountryId() == -1 ? null : userAcl.getRealmCountryId()));
                params.put("organisationId", (userAcl.getOrganisationId() == -1 ? null : userAcl.getOrganisationId()));
                params.put("programId", (userAcl.getProgramId() == -1 ? null : userAcl.getProgramId()));
                params.put("curUser", curUser);
                params.put("curDate", curDate);
                paramArray[x] = params;
                x++;
            }
            nm.batchUpdate(sqlString, paramArray);
        }
        return userId;
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT "
                + "    `user`.`USER_ID`, `user`.`USERNAME`, `user`.`EMAIL_ID`, `user`.`PHONE`, `user`.`PASSWORD`, "
                + "    `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
                + "    realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
                + "    lang.`LANGUAGE_ID`, lang.`LANGUAGE_NAME`, "
                + "    `user`.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, `user`.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, `user`.`ACTIVE`, "
                + "    role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
                + "    acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
                + "    acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
                + "    acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
                + "    acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR` "
                + "FROM us_user `user` "
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
                + "ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,acl.`USER_ACL_ID`";
        return this.jdbcTemplate.query(sql, new UserListResultSetExtractor());
    }

    @Override
    public User getUserByUserId(int userId) {
        String sql = "SELECT "
                + "    `user`.`USER_ID`, `user`.`USERNAME`, `user`.`EMAIL_ID`, `user`.`PHONE`, `user`.`PASSWORD`, "
                + "    `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
                + "    realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
                + "    lang.`LANGUAGE_ID`, lang.`LANGUAGE_NAME`, "
                + "    `user`.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, `user`.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, `user`.`ACTIVE`, "
                + "    role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
                + "    acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
                + "    acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
                + "    acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
                + "    acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR` "
                + "FROM us_user `user` "
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
                + "    WHERE `user`.`USER_ID`=? "
                + "ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,acl.`USER_ACL_ID`";
        return this.jdbcTemplate.query(sql, new UserResultSetExtractor(), userId);
    }

    @Override
    @Transactional
    public int updateUser(User user, int curUser) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlString = "";
        sqlString = "UPDATE us_user u "
                + "SET u.`REALM_ID`=:realmId, "
                + "u.`USERNAME`=:userName, "
                + "u.`EMAIL_ID`=:emailId, "
                + "u.`PHONE`=:phoneNo, "
                + "u.`LANGUAGE_ID`=:languageId, "
                + "u.`ACTIVE`=:active, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "u.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + "WHERE  u.`USER_ID`=:userId;";
        Map<String, Object> map = new HashMap<>();
        map.put("realmId", user.getRealm().getRealmId());
        map.put("userName", user.getUsername());
        map.put("emailId", user.getEmailId());
        map.put("phoneNo", user.getPhoneNumber());
        map.put("languageId", user.getLanguage().getLanguageId());
        map.put("active", user.isActive());
        map.put("lastModifiedBy", curUser);
        map.put("lastModifiedDate", curDate);
        map.put("userId", user.getUserId());
        int row = namedParameterJdbcTemplate.update(sqlString, map);
        sqlString = "DELETE FROM us_user_role WHERE  USER_ID=?;";
        row = this.jdbcTemplate.update(sqlString, user.getUserId());
        sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object>[] paramArray = new HashMap[user.getRoleList().length];
        Map<String, Object> params = new HashMap<>();
        int x = 0;
        for (String role : user.getRoleList()) {
            params = new HashMap<>();
            params.put("userId", user.getUserId());
            params.put("roleId", role);
            params.put("curUser", curUser);
            params.put("curDate", curDate);
//            params.put("curUser", curUser);
//            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        nm.batchUpdate(sqlString, paramArray);
//        if (user.getUserAclList() != null && user.getUserAclList().size()>0) {
//            sqlString = "DELETE FROM us_user_acl WHERE  USER_ID=?;";
//            this.jdbcTemplate.update(sqlString, user.getUserId());
//            sqlString = "INSERT INTO us_user_acl (USER_ID, REALM_COUNTRY_ID, HEALTH_AREA_ID, ORGANISATION_ID, PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:userId, :realmCountryId, :healthAreaId, :organisationId, :programId, :curUser, :curDate, :curUser, :curDate)";
//            paramArray = new HashMap[user.getUserAclList().size()];
//            x = 0;
//            for (UserAcl userAcl : user.getUserAclList()) {
//                params = new HashMap<>();
//                params.put("userId", user.getUserId());
//                params.put("realmCountryId", (userAcl.getRealmCountryId() == -1 ? null : userAcl.getRealmCountryId()));
//                params.put("healthAreaId", (userAcl.getRealmCountryId() == -1 ? null : userAcl.getRealmCountryId()));
//                params.put("organisationId", (userAcl.getOrganisationId() == -1 ? null : userAcl.getOrganisationId()));
//                params.put("programId", (userAcl.getProgramId() == -1 ? null : userAcl.getProgramId()));
//                params.put("curUser", curUser);
//                params.put("curDate", curDate);
//                paramArray[x] = params;
//                x++;
//            }
//            nm.batchUpdate(sqlString, paramArray);
//        }
        return row;
    }

    @Override
    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page) {
        String message = "", sql, username = user.getUsername(), phoneNo = user.getPhoneNumber();
        int userId = 0;
        if (page == 1) {
            sql = "SELECT COUNT(*) FROM us_user u WHERE u.`USERNAME`=?;";
            if ((this.jdbcTemplate.queryForObject(sql, Integer.class, username) > 0 ? true : false)) {
                message += "User already exists.";
            }
        } else if (page == 2) {
            sql = "SELECT u.`USER_ID` FROM us_user u WHERE u.`USERNAME`=?;";
            try {
                userId = this.jdbcTemplate.queryForObject(sql, Integer.class, username);
            } catch (EmptyResultDataAccessException e) {
                userId = 0;
            }
            if (userId > 0 && userId != user.getUserId() ? true : false) {
                message += "User already exists.";
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
        Map<String, Object> map = new HashMap<>();
        map.put("expiresOn", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("pwd", password);
        map.put("lastModifiedBy", 1);
        map.put("lastModifiedDate", curDate);
        map.put("userId", userId);
        try {
            return namedParameterJdbcTemplate.update(sql, map);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<BusinessFunction> getBusinessFunctionList() {
        String sqlString = "SELECT b.*,l.* FROM us_business_function b "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=b.`LABEL_ID`; ";
        try {
            return this.jdbcTemplate.query(sqlString, new BusinessFunctionRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public int updatePassword(String username, String token, String newPassword, int offset) {
        Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, offset);
        String sqlString = "UPDATE us_user SET PASSWORD=:hash, EXPIRES_ON=:expiresOn, FAILED_ATTEMPTS=0 WHERE us_user.USERNAME=:username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("hash", newPassword);
        params.put("expiresOn", offsetDate);
        return namedParameterJdbcTemplate.update(sqlString, params);
    }

    @Override
    public boolean confirmPassword(String username, String password) {
        String sqlString = "SELECT us_user.PASSWORD FROM us_user WHERE us_user.`USERNAME`=:username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        String hash = namedParameterJdbcTemplate.queryForObject(sqlString, params, String.class);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, hash);
    }

    @Override
    @Transactional
    public int addRole(Role role) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("us_role");
        Map<String, Object> params = new HashMap<>();
        String roleId = "ROLE";
        int labelId = 0;
        String[] splited = role.getLabel().getLabel().split("\\s+");
        for (int i = 0; i < splited.length; i++) {
            roleId = roleId + "_" + splited[i].toUpperCase();
        }
        labelId = this.labelDao.addLabel(role.getLabel(), 1);
        params.put("ROLE_ID", roleId);
        params.put("LABEL_ID", labelId);
        params.put("CREATED_BY", 1);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", 1);
        params.put("LAST_MODIFIED_DATE", curDate);
        int rows1 = si.execute(params);
        si = new SimpleJdbcInsert(jdbcTemplate).withTableName("us_role_business_function");
        SqlParameterSource[] paramList = new SqlParameterSource[role.getBusinessFunctions().length];
        int i = 0;
        for (String bf : role.getBusinessFunctions()) {
            params = new HashMap<>();
            params.put("ROLE_ID", roleId);
            params.put("BUSINESS_FUNCTION_ID", bf);
            params.put("CREATED_BY", 1);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", 1);
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        int result[] = si.executeBatch(paramList);
        si = new SimpleJdbcInsert(jdbcTemplate).withTableName("us_can_create_role");
        paramList = new SqlParameterSource[role.getCanCreateRole().length];
        i = 0;
        for (String r : role.getCanCreateRole()) {
            params = new HashMap<>();
            params.put("ROLE_ID", r);
            params.put("CAN_CREATE_ROLE", roleId);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);

        return (rows1 == 1 && result.length > 0 ? 1 : 0);
    }

    @Override
    @Transactional
    public int updateRole(Role role) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "";
        String roleId = "ROLE";
        String[] splited = role.getLabel().getLabel().split("\\s+");
        for (int i = 0; i < splited.length; i++) {
            roleId = roleId + "_" + splited[i].toUpperCase();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("newRoleId", roleId);
        params.put("roleId", role.getRoleId());
        params.put("label_en", role.getLabel().getLabel_en());
        params.put("label_sp", role.getLabel().getLabel_sp());
        params.put("label_fr", role.getLabel().getLabel_fr());
        params.put("labelpr", role.getLabel().getLabel_pr());
        params.put("lastModifiedBy", 1);
        params.put("lastModifiedDate", curDate);
        sql = "UPDATE us_role r "
                + "LEFT JOIN ap_label l ON l.`LABEL_ID`=r.`LABEL_ID` "
                + "SET r.`ROLE_ID`=:newRoleId, "
                + "l.`LABEL_EN`=:label_en, "
                + "l.`LABEL_FR`=:label_fr, "
                + "l.`LABEL_PR`=:label_pr, "
                + "l.`LABEL_SP`=:label_sp, "
                + "l.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "l.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + "WHERE r.`ROLE_ID`=:roleId;";
        namedParameterJdbcTemplate.update(sql, params);
        params.clear();
        this.jdbcTemplate.update("DELETE rbf.* FROM us_role_business_function rbf where rbf.ROLE_ID=?", role.getRoleId());
        SimpleJdbcInsert si = new SimpleJdbcInsert(jdbcTemplate).withTableName("us_role_business_function");
        SqlParameterSource[] paramList = new SqlParameterSource[role.getBusinessFunctions().length];
        int i = 0;
        for (String bf : role.getBusinessFunctions()) {
            params = new HashMap<>();
            params.put("ROLE_ID", roleId);
            params.put("BUSINESS_FUNCTION_ID", bf);
            params.put("CREATED_BY", 1);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", 1);
            params.put("LAST_MODIFIED_DATE", curDate);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        params.clear();
        this.jdbcTemplate.update("DELETE r.* FROM us_can_create_role r WHERE r.CAN_CREATE_ROLE=?;", role.getRoleId());
        si = new SimpleJdbcInsert(jdbcTemplate).withTableName("us_can_create_role");
        paramList = new SqlParameterSource[role.getCanCreateRole().length];
        i = 0;
        for (String r : role.getCanCreateRole()) {
            params = new HashMap<>();
            params.put("ROLE_ID", r);
            params.put("CAN_CREATE_ROLE", roleId);
            paramList[i] = new MapSqlParameterSource(params);
            i++;
        }
        si.executeBatch(paramList);
        return 1;
    }

    @Override
    public String generateTokenForUserId(int userId) {
        return this.jdbcTemplate.queryForObject("CALL generateForgotPasswordToken(?,?)", String.class, userId, DateUtils.getCurrentDateObject(DateUtils.EST));
    }

    @Override
    public EmailUser getEmailUserByUsername(String username) {
        return this.jdbcTemplate.queryForObject("SELECT USERNAME, USER_ID, EMAIL_ID FROM us_user WHERE USERNAME=?", new EmailUserRowMapper(), username);
    }

    @Override
    public ForgotPasswordToken getForgotPasswordToken(String username, String token) {
        return this.jdbcTemplate.queryForObject("SELECT fpt.*, u.USERNAME FROM us_forgot_password_token fpt LEFT JOIN us_user u on fpt.USER_ID=u.USER_ID WHERE fpt.token=? and u.USERNAME=?", new ForgotPasswordTokenRowMapper(), token, username);
    }

    @Override
    public void updateTriggeredDateForForgotPasswordToken(String username, String token) {
        this.jdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_TRIGGERED_DATE=? WHERE u.USERNAME=? AND fpt.TOKEN=?", DateUtils.getCurrentDateObject(DateUtils.EST), username, token);
    }

    @Override
    public void updateCompletionDateForForgotPasswordToken(String username, String token) {
        this.jdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_COMPLETION_DATE=? WHERE u.USERNAME=? AND fpt.TOKEN=?", DateUtils.getCurrentDateObject(DateUtils.EST), username, token);
    }

}
