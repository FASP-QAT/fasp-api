/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.LabelDao;
import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.exception.IncorrectAccessControlException;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.LabelConstants;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.SecurityRequestMatcher;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import cc.altius.FASP.model.rowMapper.AclRoleBusinessFunctionResultSetExtractor;
import cc.altius.FASP.model.rowMapper.BasicUserRowMapper;
import cc.altius.FASP.model.rowMapper.BusinessFunctionRowMapper;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsResultSetExtractorBasic;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsResultSetExtractorFull;
import cc.altius.FASP.model.rowMapper.EmailUserRowMapper;
import cc.altius.FASP.model.rowMapper.ForgotPasswordTokenRowMapper;
import cc.altius.FASP.model.rowMapper.RoleListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.RoleResultSetExtractor;
import cc.altius.FASP.model.rowMapper.SecurityRequestMatcherRowMapper;
import cc.altius.FASP.model.rowMapper.UserAclRowMapper;
import cc.altius.FASP.model.rowMapper.UserListResultSetExtractor;
import cc.altius.FASP.model.rowMapper.UserResultSetExtractor;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.rest.controller.UserRestController;
import cc.altius.FASP.utils.LogUtils;
import cc.altius.utils.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private AclService aclService;

    @Value("${syncExpiresOn}")
    private int syncExpiresOn;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final static String userAccessString = "SELECT a1.USER_ID FROM (SELECT      "
            + "    `user`.`USER_ID`, "
            + "    BIT_or(IF( "
            + "            (cuacl.REALM_COUNTRY_ID=acl.REALM_COUNTRY_ID OR cuacl.REALM_COUNTRY_ID is null) AND  "
            + "            (cuacl.HEALTH_AREA_ID=acl.HEALTH_AREA_ID OR cuacl.HEALTH_AREA_ID is null) AND "
            + "            (cuacl.ORGANISATION_ID=acl.ORGANISATION_ID OR cuacl.ORGANISATION_ID is null) AND "
            + "            (cuacl.PROGRAM_ID=acl.PROGRAM_ID OR cuacl.PROGRAM_ID is null) "
            + "        , 1,0)) access "
            + "FROM us_user `user`      "
            + "LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID`      "
            + "LEFT JOIN us_user cu ON user.REALM_ID=cu.REALM_ID OR cu.REALM_ID IS NULL      "
            + "LEFT JOIN us_user_acl cuacl ON cu.USER_ID=cuacl.USER_ID "
            + "WHERE  "
            + "    cu.USER_ID=:curUser   "
            + "    AND (-1=:realmId OR user.REALM_ID=:realmId)   "
            + "    AND (-1=:userRealmId OR user.REALM_ID=:userRealmId)   "
            + "group by user.USER_ID "
            + "having access) a1";

    private final static String customUserString1 = " SELECT "
            + " `user`.`USER_ID`,`user`.`AGREEMENT_ACCEPTED`, `user`.`USERNAME`, `user`.`PASSWORD`,`user`.`SYNC_EXPIRES_ON`, "
            + " `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, "
            + " realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
            + " lang.`LANGUAGE_ID`, langLabel.`LABEL_ID` AS LANGUAGE_LABEL_ID,langLabel.`LABEL_EN` AS LANGUAGE_LABEL_EN,langLabel.`LABEL_FR` AS LANGUAGE_LABEL_FR,langLabel.`LABEL_PR` LANGUAGE_LABEL_PR,langLabel.`LABEL_SP` AS LANGUAGE_LABEL_SP , lang.`LANGUAGE_CODE`,lang.`COUNTRY_CODE`, "
            + " `user`.`ACTIVE`, `user`.`EMAIL_ID`, `user`.`EXPIRES_ON`, `user`.`DEFAULT_THEME_ID`, `user`.`SHOW_DECIMALS`, "
            + " role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
            + " bf.`BUSINESS_FUNCTION_ID`, "
            + " acl.USER_ACL_ID, acl.`ROLE_ID` `ACL_ROLE_ID`, acl_role_lb.`LABEL_ID` `ACL_ROLE_LABEL_ID`, acl_role_lb.`LABEL_EN` `ACL_ROLE_LABEL_EN`, acl_role_lb.`LABEL_FR` `ACL_ROLE_LABEL_FR`, acl_role_lb.`LABEL_SP` `ACL_ROLE_LABEL_SP`, acl_role_lb.`LABEL_PR` `ACL_ROLE_LABEL_PR`, "
            + " acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
            + " acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
            + " acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
            + " acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR`, "
            + " DATE_FORMAT(acl.`LAST_MODIFIED_DATE`, '%Y-%m-%d %h:%i:%s') `ACL_LAST_MODIFIED_DATE` "
            + " FROM us_user `user` "
            + " LEFT JOIN rm_realm `realm` ON realm.`REALM_ID`=user.`REALM_ID` "
            + " LEFT JOIN ap_label `realm_lb` ON realm.`LABEL_ID`=realm_lb.`LABEL_ID` "
            + " LEFT JOIN ap_language lang ON lang.`LANGUAGE_ID`=`user`.`LANGUAGE_ID` "
            + " LEFT JOIN ap_label langLabel ON langLabel.`LABEL_ID`=lang.`LABEL_ID` "
            + " LEFT JOIN us_user_role user_role ON user_role.`USER_ID`=`user`.`USER_ID` "
            + " LEFT JOIN us_role role ON user_role.`ROLE_ID`=role.`ROLE_ID` "
            + " LEFT JOIN ap_label role_lb ON role.`LABEL_ID`=role_lb.`LABEL_ID` "
            + " LEFT JOIN us_role_business_function rbf ON role.`ROLE_ID`=rbf.`ROLE_ID` "
            + " LEFT JOIN us_business_function bf ON rbf.`BUSINESS_FUNCTION_ID`=bf.`BUSINESS_FUNCTION_ID` ";
    private final static String customUserString2WithoutAclCheck = " LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID` ";
    private final static String customUserString2WithAclCheck = " LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID` AND (FIND_IN_SET(acl.ROLE_ID, :allowedRoleList) OR acl.ROLE_ID IS NULL) ";
    private final static String customUserString3 = " LEFT JOIN us_role acl_role ON acl.`ROLE_ID`=acl_role.`ROLE_ID` "
            + " LEFT JOIN ap_label acl_role_lb ON acl_role.`LABEL_ID`=acl_role_lb.`LABEL_ID` "
            + " LEFT JOIN rm_realm_country acl_realm_country ON acl.`REALM_COUNTRY_ID`=acl_realm_country.`REALM_COUNTRY_ID` "
            + " LEFT JOIN ap_country acl_country ON acl_realm_country.`COUNTRY_ID`=acl_country.`COUNTRY_ID` "
            + " LEFT JOIN ap_label acl_country_lb ON acl_country.`LABEL_ID`=acl_country_lb.`LABEL_ID` "
            + " LEFT JOIN rm_health_area acl_health_area ON acl.`HEALTH_AREA_ID`=acl_health_area.`HEALTH_AREA_ID` "
            + " LEFT JOIN ap_label acl_health_area_lb ON acl_health_area.`LABEL_ID`=acl_health_area_lb.`LABEL_ID` "
            + " LEFT JOIN rm_organisation acl_organisation ON acl.`ORGANISATION_ID`=acl_organisation.`ORGANISATION_ID` "
            + " LEFT JOIN ap_label acl_organisation_lb ON acl_organisation.`LABEL_ID`=acl_organisation_lb.`LABEL_ID` "
            + " LEFT JOIN rm_program acl_program ON acl.`PROGRAM_ID`=acl_program.`PROGRAM_ID` "
            + " LEFT JOIN ap_label acl_program_lb ON acl_program.`LABEL_ID`=acl_program_lb.`LABEL_ID` ";
//            + " WHERE TRUE ";

    private final static String customUserString = customUserString1 + customUserString2WithoutAclCheck + customUserString3;
    private final static String customUserStringWithAclCheck = customUserString1 + customUserString2WithAclCheck + customUserString3;
    private static final String customUserOrderBy = "  ORDER BY `user`.`USER_ID`, role.`ROLE_ID`,bf.`BUSINESS_FUNCTION_ID`,acl.`USER_ACL_ID`";

    private static final String userCommonString = "SELECT "
            + "    `user`.`USER_ID`, `user`.`USERNAME`, `user`.`EMAIL_ID`, `user`.`ORG_AND_COUNTRY`, `user`.`PASSWORD`, "
            + "    `user`.`FAILED_ATTEMPTS`, `user`.`LAST_LOGIN_DATE`, `user`.`DEFAULT_MODULE_ID`, `user`.`DEFAULT_THEME_ID`, `user`.`SHOW_DECIMALS`, "
            + "    realm.`REALM_ID`, realm.`REALM_CODE`, realm_lb.`LABEL_ID` `REALM_LABEL_ID`, realm_lb.`LABEL_EN` `REALM_LABEL_EN`, realm_lb.`LABEL_FR` `REALM_LABEL_FR`, realm_lb.`LABEL_SP` `REALM_LABEL_SP`, realm_lb.`LABEL_PR` `REALM_LABEL_PR`, "
            + "    lang.`LANGUAGE_ID`, langLabel.`LABEL_ID` AS LANGUAGE_LABEL_ID,langLabel.`LABEL_EN` AS LANGUAGE_LABEL_EN,langLabel.`LABEL_FR` AS LANGUAGE_LABEL_FR,langLabel.`LABEL_PR` LANGUAGE_LABEL_PR,langLabel.`LABEL_SP` AS LANGUAGE_LABEL_SP , lang.`LANGUAGE_CODE`,lang.`COUNTRY_CODE`, "
            + "    `user`.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, `user`.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, `user`.`ACTIVE`, "
            + "    role.`ROLE_ID`, role_lb.`LABEL_ID` `ROLE_LABEL_ID`, role_lb.`LABEL_EN` `ROLE_LABEL_EN`, role_lb.`LABEL_FR` `ROLE_LABEL_FR`, role_lb.`LABEL_SP` `ROLE_LABEL_SP`, role_lb.`LABEL_PR` `ROLE_LABEL_PR`, "
            + "    rbf.BUSINESS_FUNCTION_ID, "
            + "    acl.USER_ACL_ID, acl.`ROLE_ID` `ACL_ROLE_ID`, acl_role_lb.`LABEL_ID` `ACL_ROLE_LABEL_ID`, acl_role_lb.`LABEL_EN` `ACL_ROLE_LABEL_EN`, acl_role_lb.`LABEL_FR` `ACL_ROLE_LABEL_FR`, acl_role_lb.`LABEL_SP` `ACL_ROLE_LABEL_SP`, acl_role_lb.`LABEL_PR` `ACL_ROLE_LABEL_PR`, "
            + "    acl.`REALM_COUNTRY_ID` `ACL_REALM_COUNTRY_ID`, acl_country_lb.`LABEL_ID` `ACL_REALM_LABEL_ID`, acl_country_lb.`LABEL_EN` `ACL_REALM_LABEL_EN`, acl_country_lb.`LABEL_FR` `ACL_REALM_LABEL_FR`, acl_country_lb.`LABEL_SP` `ACL_REALM_LABEL_SP`, acl_country_lb.`LABEL_PR` `ACL_REALM_LABEL_PR`, "
            + "    acl.`HEALTH_AREA_ID` `ACL_HEALTH_AREA_ID`, acl_health_area_lb.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, acl_health_area_lb.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, acl_health_area_lb.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, acl_health_area_lb.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, acl_health_area_lb.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
            + "    acl.`ORGANISATION_ID` `ACL_ORGANISATION_ID`, acl_organisation_lb.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, acl_organisation_lb.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, acl_organisation_lb.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, acl_organisation_lb.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, acl_organisation_lb.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
            + "    acl.`PROGRAM_ID` `ACL_PROGRAM_ID`, acl_program_lb.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, acl_program_lb.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, acl_program_lb.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, acl_program_lb.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, acl_program_lb.`LABEL_PR` `ACL_PROGRAM_LABEL_PR`, "
            + "    DATE_FORMAT(acl.`LAST_MODIFIED_DATE`, '%Y-%m-%d %h:%i:%s') `ACL_LAST_MODIFIED_DATE` "
            + " FROM us_user `user` "
            + "    LEFT JOIN rm_realm `realm` ON realm.`REALM_ID`=user.`REALM_ID` "
            + "    LEFT JOIN ap_label `realm_lb` ON realm.`LABEL_ID`=realm_lb.`LABEL_ID` "
            + "    LEFT JOIN ap_language lang ON lang.`LANGUAGE_ID`=`user`.`LANGUAGE_ID` "
            + "    LEFT JOIN ap_label langLabel ON langLabel.`LABEL_ID`=lang.`LABEL_ID` "
            + "    LEFT JOIN us_user cb ON cb.`USER_ID`=`user`.`CREATED_BY` "
            + "    LEFT JOIN us_user lmb ON lmb.`USER_ID`=`user`.`LAST_MODIFIED_BY` "
            + "    LEFT JOIN us_user_role user_role ON user_role.`USER_ID`=`user`.`USER_ID` "
            + "    LEFT JOIN us_role role ON user_role.`ROLE_ID`=role.`ROLE_ID` "
            + "    LEFT JOIN ap_label role_lb ON role.`LABEL_ID`=role_lb.`LABEL_ID` "
            + "    LEFT JOIN us_user_acl acl ON `user`.`USER_ID`=acl.`USER_ID` "
            + "    LEFT JOIN us_role acl_role ON acl.`ROLE_ID`=acl_role.`ROLE_ID` "
            + "    LEFT JOIN ap_label acl_role_lb ON acl_role.`LABEL_ID`=acl_role_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_realm_country acl_realm_country ON acl.`REALM_COUNTRY_ID`=acl_realm_country.`REALM_COUNTRY_ID` "
            + "    LEFT JOIN ap_country acl_country ON acl_realm_country.`COUNTRY_ID`=acl_country.`COUNTRY_ID` "
            + "    LEFT JOIN ap_label acl_country_lb ON acl_country.`LABEL_ID`=acl_country_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_health_area acl_health_area ON acl.`HEALTH_AREA_ID`=acl_health_area.`HEALTH_AREA_ID` "
            + "    LEFT JOIN ap_label acl_health_area_lb ON acl_health_area.`LABEL_ID`=acl_health_area_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_organisation acl_organisation ON acl.`ORGANISATION_ID`=acl_organisation.`ORGANISATION_ID` "
            + "    LEFT JOIN ap_label acl_organisation_lb ON acl_organisation.`LABEL_ID`=acl_organisation_lb.`LABEL_ID` "
            + "    LEFT JOIN rm_program acl_program ON acl.`PROGRAM_ID`=acl_program.`PROGRAM_ID` "
            + "    LEFT JOIN ap_label acl_program_lb on acl_program.`LABEL_ID`=acl_program_lb.`LABEL_ID` "
            + "    LEFT JOIN us_role_business_function rbf ON role.ROLE_ID=rbf.ROLE_ID ";
    private static final String userByUserId = "    WHERE user.USER_ID=:userId ";
    private static final String userList = "    WHERE user_role.ROLE_ID IN (SELECT ccr.CAN_CREATE_ROLE FROM us_user_role ur LEFT JOIN us_can_create_role ccr ON ur.ROLE_ID=ccr.ROLE_ID where ur.USER_ID=:curUser) ";
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
        String sqlString = this.customUserString + " WHERE TRUE "
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
        String sqlString = this.customUserString + " WHERE TRUE " + "  AND LOWER(`user`.`EMAIL_ID`)=LOWER(:emailId) " + this.customUserOrderBy;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("emailId", emailId);
            CustomUserDetails user = this.namedParameterJdbcTemplate.query(sqlString, params, new CustomUserDetailsResultSetExtractorBasic());
            return user;
        } catch (Exception e) {
            logger.info("Error", e);
            return null;
        }
    }

    @Override
    public CustomUserDetails getCustomUserByUserId(int userId) {
        String sqlString = this.customUserString + " WHERE TRUE " + "  AND `user`.`USER_ID`=:userId " + this.customUserOrderBy;
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
        sb.append("SELECT c.`CAN_CREATE_ROLE`,r.`ROLE_ID`,lb.*,rb.`BUSINESS_FUNCTION_ID` FROM us_role r "
                + " LEFT JOIN ap_label lb ON lb.`LABEL_ID`=r.`LABEL_ID` "
                + " LEFT JOIN us_role_business_function rb ON rb.`ROLE_ID`=r.`ROLE_ID` "
                + " LEFT JOIN us_can_create_role c ON c.`CAN_CREATE_ROLE`=r.`ROLE_ID` "
                + " WHERE 1 ");
        String role[] = new String[curUser.getRoles().size()];
        for (int i = 0; i < curUser.getRoles().size(); i++) {
            role[i] = curUser.getRoles().get(i).getRoleId();
        }
//        if (Arrays.asList(role).contains("ROLE_REALM_ADMIN")) {
        sb.append("AND FIND_IN_SET(c.`ROLE_ID`,'"+String.join(",", role)+"')");
//        }
        sb.append(" ORDER BY lb.`LABEL_EN` ASC ");
        return this.namedParameterJdbcTemplate.query(sb.toString(), new RoleListResultSetExtractor());
    }

    @Override
    @Transactional(rollbackFor = IncorrectAccessControlException.class)
    public int addNewUser(User user, CustomUserDetails curUser) throws IncorrectAccessControlException {
        String sqlString = "INSERT INTO us_user (`REALM_ID`, `AGREEMENT_ACCEPTED`, `USERNAME`, `PASSWORD`, `EMAIL_ID`, `ORG_AND_COUNTRY`, `LANGUAGE_ID`, `ACTIVE`, `FAILED_ATTEMPTS`, `EXPIRES_ON`, `SYNC_EXPIRES_ON`, `LAST_LOGIN_DATE`, `CREATED_BY`, `CREATED_DATE`, `LAST_MODIFIED_BY`, `LAST_MODIFIED_DATE`) VALUES (:REALM_ID, :AGREEMENT_ACCEPTED, :USERNAME, :PASSWORD, :EMAIL_ID, :ORG_AND_COUNTRY, :LANGUAGE_ID, :ACTIVE, :FAILED_ATTEMPTS, :EXPIRES_ON, :SYNC_EXPIRES_ON, :LAST_LOGIN_DATE, :CREATED_BY, :CREATED_DATE, :LAST_MODIFIED_BY, :LAST_MODIFIED_DATE)";
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("REALM_ID", ((user.getRealm() == null || user.getRealm().getRealmId() == null ? null : (user.getRealm().getRealmId() != -1 ? user.getRealm().getRealmId() : null))));
        map.put("AGREEMENT_ACCEPTED", false);
        map.put("USERNAME", user.getUsername());
        map.put("PASSWORD", user.getPassword());
        map.put("EMAIL_ID", user.getEmailId());
        map.put("ORG_AND_COUNTRY", user.getOrgAndCountry());
        map.put("LANGUAGE_ID", user.getLanguage().getLanguageId());
        map.put("DEFAULT_MODULE_ID", 1); // SupplyPlan or Mod1
        map.put("DEFAULT_THEME_ID", 1); // SupplyPlan or Mod1
        map.put("SHOW_DECIMALS", true); // SupplyPlan or Mod1
        map.put("ACTIVE", true);
        map.put("FAILED_ATTEMPTS", 0);
        map.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("SYNC_EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, syncExpiresOn));
        map.put("LAST_LOGIN_DATE", null);
        map.put("CREATED_BY", curUser.getUserId());
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", curUser.getUserId());
        map.put("LAST_MODIFIED_DATE", curDate);
        this.namedParameterJdbcTemplate.update(sqlString, map);
        int userId = this.namedParameterJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", map, Integer.class);

        sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";

        Set<String> uniqueRoles = user.getUserAclList().stream().map(acl -> acl.getRoleId()).collect(Collectors.toSet());
        Map<String, Object>[] paramArray = new HashMap[uniqueRoles.size()];
        Map<String, Object> params = new HashMap<>();
        int x = 0;
        for (String role : uniqueRoles) {
            params = new HashMap<>();
            params.put("userId", userId);
            params.put("roleId", role);
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramArray);
        user.setUserId(userId);
        int row = 0, count;
        x = 0;
        params.clear();
        paramArray = null;
        paramArray = new HashMap[user.getUserAcls().length];
        sqlString = "INSERT INTO us_user_acl (USER_ID, ROLE_ID, REALM_COUNTRY_ID, HEALTH_AREA_ID, ORGANISATION_ID, PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:userId, :roleId, :realmCountryId, :healthAreaId, :organisationId, :programId, :curUser, :curDate, :curUser, :curDate)";
        paramArray = new HashMap[user.getUserAcls().length];
        for (UserAcl userAcl : user.getUserAcls()) {
            params = new HashMap<>();
            params.put("userId", user.getUserId());
            params.put("roleId", userAcl.getRoleId());
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
        if (row == 0) {
            throw new IncorrectAccessControlException();
        }
        return userId;
    }

    @Override
    public List<User> getUserList(CustomUserDetails curUser) {
        StringBuilder sb1 = new StringBuilder("SELECT DISTINCT(u.`USER_ID`) `USER_ID` FROM us_user u  "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID` "
                + "LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID AND ur.ROLE_ID=acl.ROLE_ID "
                + "WHERE     "
                + "    ur.ROLE_ID IN (SELECT ccr.CAN_CREATE_ROLE FROM us_user_role ur LEFT JOIN us_can_create_role ccr ON ur.ROLE_ID=ccr.ROLE_ID where ur.USER_ID=:curUser)  ");
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        this.aclService.addUserAclForRealm(sb1, params, "u", curUser);
        this.aclService.addFullAclAtUserLevel(sb1, params, "acl", curUser);
        StringBuilder sb = new StringBuilder("SELECT "
                + "    u.`USER_ID`, u.`USERNAME`, u.`EMAIL_ID`, u.`ORG_AND_COUNTRY`, u.`PASSWORD`, "
                + "    u.`FAILED_ATTEMPTS`, u.`LAST_LOGIN_DATE`, u.`DEFAULT_MODULE_ID`, u.`DEFAULT_THEME_ID`, "
                + "    r.`REALM_ID`, r.`REALM_CODE`, r.`LABEL_ID` `REALM_LABEL_ID`, r.`LABEL_EN` `REALM_LABEL_EN`, r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_SP` `REALM_LABEL_SP`, r.`LABEL_PR` `REALM_LABEL_PR`, "
                + "   l.`LANGUAGE_ID`, ll.`LABEL_ID` AS `LANGUAGE_LABEL_ID`, ll.`LABEL_EN` AS `LANGUAGE_LABEL_EN`, ll.`LABEL_FR` AS `LANGUAGE_LABEL_FR`, ll.`LABEL_PR` `LANGUAGE_LABEL_PR`, ll.`LABEL_SP` AS `LANGUAGE_LABEL_SP`, l.`LANGUAGE_CODE`, l.`COUNTRY_CODE`, "
                + "   u.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, u.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, u.`ACTIVE`, "
                + "   ro.`ROLE_ID`, rol.`LABEL_ID` `ROLE_LABEL_ID`, rol.`LABEL_EN` `ROLE_LABEL_EN`, rol.`LABEL_FR` `ROLE_LABEL_FR`, rol.`LABEL_SP` `ROLE_LABEL_SP`, rol.`LABEL_PR` `ROLE_LABEL_PR` "
                + "FROM us_user u "
                + "LEFT JOIN vw_realm r ON r.`REALM_ID`=u.`REALM_ID` "
                + "LEFT JOIN ap_language l ON l.`LANGUAGE_ID`=u.`LANGUAGE_ID` "
                + "LEFT JOIN ap_label ll ON ll.`LABEL_ID`=l.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON cb.`USER_ID`=u.`CREATED_BY` "
                + "LEFT JOIN us_user lmb ON lmb.`USER_ID`=u.`LAST_MODIFIED_BY` "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID` "
                + "LEFT JOIN us_role ro ON ur.`ROLE_ID`=ro.`ROLE_ID` "
                + "LEFT JOIN ap_label rol ON ro.`LABEL_ID`=rol.`LABEL_ID` "
                + "WHERE u.USER_ID IN (")
                .append(sb1)
                .append(")")
                .append("ORDER BY u.`USER_ID`, ur.`ROLE_ID`");
        logger.info(LogUtils.buildStringForLog(sb.toString(), params));
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new UserListResultSetExtractor());
    }

    @Override
    public List<BasicUser> getUserDropDownList(CustomUserDetails curUser) {
        StringBuilder stringBuilder = new StringBuilder("SELECT u.USER_ID, u.USERNAME FROM us_user u WHERE u.ACTIVE ");
        Map<String, Object> params = new HashMap<>();
        this.aclService.addUserAclForRealm(stringBuilder, params, "u", curUser);
        stringBuilder.append(" ORDER BY u.USERNAME");
        return this.namedParameterJdbcTemplate.query(stringBuilder.toString(), params, new BasicUserRowMapper());
    }

    @Override
    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser) {
        StringBuilder sb1 = new StringBuilder("SELECT DISTINCT(u.`USER_ID`) `USER_ID` FROM us_user u  "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID` "
                + "LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID AND ur.ROLE_ID=acl.ROLE_ID "
                + "WHERE     "
                + "    ur.ROLE_ID IN (SELECT ccr.CAN_CREATE_ROLE FROM us_user_role ur LEFT JOIN us_can_create_role ccr ON ur.ROLE_ID=ccr.ROLE_ID where ur.USER_ID=:curUser)  ");
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        params.put("realmId", realmId);
        this.aclService.addUserAclForRealm(sb1, params, "u", curUser);
        this.aclService.addFullAclAtUserLevel(sb1, params, "acl", curUser);
        StringBuilder sb = new StringBuilder("SELECT "
                + "    u.`USER_ID`, u.`USERNAME`, u.`EMAIL_ID`, u.`ORG_AND_COUNTRY`, u.`PASSWORD`, "
                + "    u.`FAILED_ATTEMPTS`, u.`LAST_LOGIN_DATE`, u.`DEFAULT_MODULE_ID`, u.`DEFAULT_THEME_ID`, "
                + "    r.`REALM_ID`, r.`REALM_CODE`, r.`LABEL_ID` `REALM_LABEL_ID`, r.`LABEL_EN` `REALM_LABEL_EN`, r.`LABEL_FR` `REALM_LABEL_FR`, r.`LABEL_SP` `REALM_LABEL_SP`, r.`LABEL_PR` `REALM_LABEL_PR`, "
                + "   l.`LANGUAGE_ID`, ll.`LABEL_ID` AS `LANGUAGE_LABEL_ID`, ll.`LABEL_EN` AS `LANGUAGE_LABEL_EN`, ll.`LABEL_FR` AS `LANGUAGE_LABEL_FR`, ll.`LABEL_PR` `LANGUAGE_LABEL_PR`, ll.`LABEL_SP` AS `LANGUAGE_LABEL_SP`, l.`LANGUAGE_CODE`, l.`COUNTRY_CODE`, "
                + "   u.`CREATED_DATE`, cb.`USER_ID` `CB_USER_ID`, cb.`USERNAME` `CB_USERNAME`, u.`LAST_MODIFIED_DATE`, lmb.`USER_ID` `LMB_USER_ID`, lmb.`USERNAME` `LMB_USERNAME`, u.`ACTIVE`, "
                + "   ro.`ROLE_ID`, rol.`LABEL_ID` `ROLE_LABEL_ID`, rol.`LABEL_EN` `ROLE_LABEL_EN`, rol.`LABEL_FR` `ROLE_LABEL_FR`, rol.`LABEL_SP` `ROLE_LABEL_SP`, rol.`LABEL_PR` `ROLE_LABEL_PR` "
                + "FROM us_user u "
                + "LEFT JOIN vw_realm r ON r.`REALM_ID`=u.`REALM_ID` "
                + "LEFT JOIN ap_language l ON l.`LANGUAGE_ID`=u.`LANGUAGE_ID` "
                + "LEFT JOIN ap_label ll ON ll.`LABEL_ID`=l.`LABEL_ID` "
                + "LEFT JOIN us_user cb ON cb.`USER_ID`=u.`CREATED_BY` "
                + "LEFT JOIN us_user lmb ON lmb.`USER_ID`=u.`LAST_MODIFIED_BY` "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID` "
                + "LEFT JOIN us_role ro ON ur.`ROLE_ID`=ro.`ROLE_ID` "
                + "LEFT JOIN ap_label rol ON ro.`LABEL_ID`=rol.`LABEL_ID` "
                + "WHERE u.REALM_ID=:realmId AND u.USER_ID IN (")
                .append(sb1).append(")")
                //                if (!curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_ADD_REALM"))) {
                //            sb.append(" AND u.REALM_ID=").append(curUser.getRealm().getRealmId());
                //        }
                .append(" ORDER BY u.`USER_ID`, ur.`ROLE_ID`");
        logger.info(LogUtils.buildStringForLog(sb.toString(), params));
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new UserListResultSetExtractor());
    }

    // Need to add RoleId condition here
    // Check if this method is being used anywhere
    // This is used to get the list of Users that can be a Program Admin for a particular Program
    @Override
    public List<BasicUser> getUserListForProgram(int programId, CustomUserDetails curUser) {
        String sb = "SELECT u.USER_ID, u.USERNAME FROM us_user u WHERE u.ACTIVE AND  u.USER_ID in (SELECT DISTINCT(u.USER_ID) FROM vw_all_program p LEFT JOIN rm_realm_country rc ON p.REALM_COUNTRY_ID=rc.REALM_COUNTRY_ID LEFT JOIN rm_program_health_area pha ON p.PROGRAM_ID=pha.PROGRAM_ID LEFT JOIN us_user u ON u.REALM_ID = rc.REALM_ID LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID WHERE p.PROGRAM_ID=:programId) ORDER BY u.USERNAME";
        Map<String, Object> params = new HashMap<>();
        params.put("programId", programId);
        return this.namedParameterJdbcTemplate.query(sb, params, new BasicUserRowMapper());
    }

    @Override
    public User getUserByUserId(int userId, CustomUserDetails curUser) {
        String sql = this.userCommonString + this.userByUserId + " AND `user`.`USER_ID`=:userId " + this.userOrderBy;
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("curUser", curUser.getUserId());
        logger.info(LogUtils.buildStringForLog(sql, params));
        User u = this.namedParameterJdbcTemplate.query(sql, params, new UserResultSetExtractor());
        if (u == null) {
            throw new EmptyResultDataAccessException(1);
        } else {
            return u;
        }
    }

    @Override
    @Transactional(rollbackFor = IncorrectAccessControlException.class)
    public int updateUser(User user, CustomUserDetails curUser) throws IncorrectAccessControlException {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sqlString = "";
        sqlString = "UPDATE us_user u "
                + "SET "
                + "u.`USERNAME`=:userName, "
                + "u.`EMAIL_ID`=:emailId, "
                + "u.`ORG_AND_COUNTRY`=:orgAndCountry, "
                + "u.`LANGUAGE_ID`=:languageId, "
                + "u.`ACTIVE`=:active, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "u.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + "WHERE  u.`USER_ID`=:userId;";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", user.getUsername());
        params.put("emailId", user.getEmailId());
        params.put("orgAndCountry", user.getOrgAndCountry());
        params.put("languageId", user.getLanguage().getLanguageId());
        params.put("active", user.isActive());
        params.put("lastModifiedBy", curUser.getUserId());
        params.put("lastModifiedDate", curDate);
        params.put("userId", user.getUserId());
        int row = this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "DELETE FROM us_user_role WHERE  USER_ID=:userId";
        row = this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID,CREATED_BY,CREATED_DATE,LAST_MODIFIED_BY,LAST_MODIFIED_DATE) VALUES(:userId,:roleId,:curUser,:curDate,:curUser,:curDate)";

        Set<String> uniqueRoles = user.getUserAclList().stream().map(acl -> acl.getRoleId()).collect(Collectors.toSet());
        Map<String, Object>[] paramArray = new HashMap[uniqueRoles.size()];
        params.clear();
        int x = 0;
        for (String role : uniqueRoles) {
            params = new HashMap<>();
            params.put("userId", user.getUserId());
            params.put("roleId", role);
            params.put("curUser", curUser.getUserId());
            params.put("curDate", curDate);
            paramArray[x] = params;
            x++;
        }
        this.namedParameterJdbcTemplate.batchUpdate(sqlString, paramArray);
        int count = 0;
        x = 0;
        params.clear();
        paramArray = null;
        paramArray = new HashMap[user.getUserAcls().length];

        sqlString = "DELETE FROM us_user_acl WHERE  USER_ID=:userId";
        params.put("userId", user.getUserId());
        this.namedParameterJdbcTemplate.update(sqlString, params);
        sqlString = "INSERT INTO us_user_acl (USER_ID, ROLE_ID, REALM_COUNTRY_ID, HEALTH_AREA_ID, ORGANISATION_ID, PROGRAM_ID, CREATED_BY, CREATED_DATE, LAST_MODIFIED_BY, LAST_MODIFIED_DATE) VALUES (:userId, :roleId, :realmCountryId, :healthAreaId, :organisationId, :programId, :curUser, :curDate, :curUser, :curDate)";
        paramArray = new HashMap[user.getUserAcls().length];
        for (UserAcl userAcl : user.getUserAcls()) {
            params = new HashMap<>();
            params.put("userId", user.getUserId());
            params.put("roleId", userAcl.getRoleId());
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

        if (row == 0) {
            throw new IncorrectAccessControlException();
        }
        return row;
    }

    @Override
    public String checkIfUserExistsByEmail(User user, int page) {
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
        logger.info("Reset password get forgot password token---getForgotPasswordToken---" + emailId + ", token---" + token);
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("token", token);
        return this.namedParameterJdbcTemplate.queryForObject("SELECT fpt.*, u.USERNAME FROM us_forgot_password_token fpt LEFT JOIN us_user u on fpt.USER_ID=u.USER_ID WHERE fpt.token=:token AND LOWER(u.EMAIL_ID)=LOWER(:emailId)", params, new ForgotPasswordTokenRowMapper());
    }

    @Override
    public void updateTriggeredDateForForgotPasswordToken(String emailId, String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("token", token);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        logger.info("Going to update the TokenTriggeredDate for EmailId:" + emailId + ", token:" + token);
        int rowsUpdated = this.namedParameterJdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_TRIGGERED_DATE=:curDate WHERE LOWER(u.EMAIL_ID)=LOWER(:emailId) AND fpt.TOKEN=:token", params);
        logger.info(rowsUpdated + " rows updated for the TokenTriggeredDate for EmailId:" + emailId + ", token:" + token);
    }

    @Override
    public void updateCompletionDateForForgotPasswordToken(String emailId, String token) {
        logger.info("Reset password updateCompletionDateForForgotPasswordToken---Email id---" + emailId + ", token---" + token);
        Map<String, Object> params = new HashMap<>();
        params.put("emailId", emailId);
        params.put("token", token);
        params.put("curDate", DateUtils.getCurrentDateObject(DateUtils.EST));
        logger.info("Going to update the TokenCompletionDate for EmailId:" + emailId + ", token:" + token);
        int rowsUpdated = this.namedParameterJdbcTemplate.update("UPDATE us_forgot_password_token fpt LEFT JOIN us_user u ON fpt.USER_ID=u.USER_ID SET fpt.TOKEN_COMPLETION_DATE=COALESCE(fpt.TOKEN_COMPLETION_DATE,:curDate) WHERE LOWER(u.EMAIL_ID)=LOWER(:emailId) AND fpt.TOKEN=:token", params);
        logger.info(rowsUpdated + " rows updated for the TokenCompletionDate for EmailId:" + emailId + ", token:" + token);
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
    public List<UserAcl> getAccessControls(CustomUserDetails curUser) {
        StringBuilder sb1 = new StringBuilder("SELECT DISTINCT(u.`USER_ID`) `USER_ID` FROM us_user u  "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID` "
                + "LEFT JOIN us_user_acl acl ON u.USER_ID=acl.USER_ID AND ur.ROLE_ID=acl.ROLE_ID "
                + "WHERE     "
                + "    ur.ROLE_ID IN (SELECT ccr.CAN_CREATE_ROLE FROM us_user_role ur LEFT JOIN us_can_create_role ccr ON ur.ROLE_ID=ccr.ROLE_ID where ur.USER_ID=:curUser)  ");
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        this.aclService.addUserAclForRealm(sb1, params, "u", curUser);
        this.aclService.addFullAclAtUserLevel(sb1, params, "acl", curUser);

        StringBuilder sb = new StringBuilder("SELECT "
                + "   acl.USER_ACL_ID, u.`USER_ID`, u.`USERNAME`, "
                + "   acl.`ROLE_ID`, aclrl.`LABEL_ID` `ACL_ROLE_LABEL_ID`, aclrl.`LABEL_EN` `ACL_ROLE_LABEL_EN`, aclrl.`LABEL_FR` `ACL_ROLE_LABEL_FR`, aclrl.`LABEL_SP` `ACL_ROLE_LABEL_SP`, aclrl.`LABEL_PR` `ACL_ROLE_LABEL_PR`, "
                + "   aclrc.`REALM_COUNTRY_ID` `REALM_COUNTRY_ID`, aclc.`LABEL_ID` `ACL_REALM_COUNTRY_LABEL_ID`, aclc.`LABEL_EN` `ACL_REALM_COUNTRY_LABEL_EN`, aclc.`LABEL_FR` `ACL_REALM_COUNTRY_LABEL_FR`, aclc.`LABEL_SP` `ACL_REALM_COUNTRY_LABEL_SP`, aclc.`LABEL_PR` `ACL_REALM_COUNTRY_LABEL_PR`, "
                + "   aclha.`HEALTH_AREA_ID` `HEALTH_AREA_ID`, aclha.`LABEL_ID` `ACL_HEALTH_AREA_LABEL_ID`, aclha.`LABEL_EN` `ACL_HEALTH_AREA_LABEL_EN`, aclha.`LABEL_FR` `ACL_HEALTH_AREA_LABEL_FR`, aclha.`LABEL_SP` `ACL_HEALTH_AREA_LABEL_SP`, aclha.`LABEL_PR` `ACL_HEALTH_AREA_LABEL_PR`, "
                + "   aclo.`ORGANISATION_ID` `ORGANISATION_ID`, aclo.`LABEL_ID` `ACL_ORGANISATION_LABEL_ID`, aclo.`LABEL_EN` `ACL_ORGANISATION_LABEL_EN`, aclo.`LABEL_FR` `ACL_ORGANISATION_LABEL_FR`, aclo.`LABEL_SP` `ACL_ORGANISATION_LABEL_SP`, aclo.`LABEL_PR` `ACL_ORGANISATION_LABEL_PR`, "
                + "   aclp.`PROGRAM_ID` `PROGRAM_ID`, aclp.`LABEL_ID` `ACL_PROGRAM_LABEL_ID`, aclp.`LABEL_EN` `ACL_PROGRAM_LABEL_EN`, aclp.`LABEL_FR` `ACL_PROGRAM_LABEL_FR`, aclp.`LABEL_SP` `ACL_PROGRAM_LABEL_SP`, aclp.`LABEL_PR` `ACL_PROGRAM_LABEL_PR`, "
                + "   acl.`LAST_MODIFIED_DATE` "
                + "FROM us_user u "
                + "LEFT JOIN rm_realm r ON u.REALM_ID=r.REALM_ID "
                + "LEFT JOIN us_user_role ur ON u.`USER_ID`=ur.`USER_ID` "
                + "LEFT JOIN us_user_acl acl ON u.`USER_ID`=acl.`USER_ID` "
                + "LEFT JOIN us_role aclr ON acl.`ROLE_ID`=aclr.`ROLE_ID` "
                + "LEFT JOIN ap_label aclrl ON aclr.`LABEL_ID`=aclrl.`LABEL_ID` "
                + "LEFT JOIN rm_realm_country aclrc ON acl.`REALM_COUNTRY_ID`=aclrc.`REALM_COUNTRY_ID` "
                + "LEFT JOIN vw_country aclc ON aclrc.`COUNTRY_ID`=aclc.`COUNTRY_ID` "
                + "LEFT JOIN vw_health_area aclha ON acl.`HEALTH_AREA_ID`=aclha.`HEALTH_AREA_ID` "
                + "LEFT JOIN vw_organisation aclo ON acl.`ORGANISATION_ID`=aclo.`ORGANISATION_ID` "
                + "LEFT JOIN vw_all_program aclp ON acl.`PROGRAM_ID`=aclp.`PROGRAM_ID` "
                + "WHERE u.USER_ID IN (")
                .append(sb1)
                .append(")");
//        if (!curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_ADD_REALM"))) {
//            sb.append(" AND u.REALM_ID=").append(curUser.getRealm().getRealmId());
//        }
        sb.append(" ORDER BY u.`USER_ID`, acl.`ROLE_ID` ");
        logger.info(LogUtils.buildStringForLog(sb.toString(), params));
        return this.namedParameterJdbcTemplate.query(sb.toString(), params, new UserAclRowMapper());
    }

    @Override
    @Transactional
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
        sql = "UPDATE us_user u SET u.`LANGUAGE_ID`=?,u.`LAST_MODIFIED_DATE`=?,u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, languageId, curDate, userId, userId);
    }

    @Override
    public int updateUserLanguageByEmailId(String emailId, String languageCode) {
        String sql;
        sql = "SELECT u.`USER_ID` FROM us_user u WHERE LOWER(u.`EMAIL_ID`)=LOWER(?);";
        int userId = this.jdbcTemplate.queryForObject(sql, Integer.class, emailId);
        return this.updateUserLanguage(userId, languageCode);
    }

    @Override
    public int updateUserModule(int userId, int moduleId) throws CouldNotSaveException {
        String sql;
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        if (moduleId != 1 && moduleId != 2) {
            throw new CouldNotSaveException("Incorrect Module Id");
        }
        sql = "UPDATE us_user u SET u.`DEFAULT_MODULE_ID`=?, u.`LAST_MODIFIED_DATE`=?, u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, moduleId, curDate, userId, userId);
    }

    @Override
    public int updateUserTheme(int userId, int themeId) throws CouldNotSaveException {
        String sql;
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        if (themeId != 1 && themeId != 2) {
            throw new CouldNotSaveException("Incorrect Module Id");
        }
        sql = "UPDATE us_user u SET u.`DEFAULT_THEME_ID`=?, u.`LAST_MODIFIED_DATE`=?, u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, themeId, curDate, userId, userId);
    }
    
    @Override
    public int updateUserDecimalPreference(int userId, boolean showDecimals) {
        String sql;
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        sql = "UPDATE us_user u SET u.`SHOW_DECIMALS`=?, u.`LAST_MODIFIED_DATE`=?, u.`LAST_MODIFIED_BY`=? WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.update(sql, showDecimals, curDate, userId, userId);
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
        return this.jdbcTemplate.queryForObject(sql, String.class, userId);
    }

    @Override
    public List<SecurityRequestMatcher> getSecurityList() {
        String sql = "SELECT MIN(s.`SECURITY_ID`) `SECURITY_ID`, s.`METHOD`, s.`URL` `URL_LIST`, group_concat(DISTINCT s.`BF`) `BF_LIST` FROM ap_security s GROUP BY s.`METHOD`, s.`URL` ORDER BY s.`SECURITY_ID`";
        return this.jdbcTemplate.query(sql, new SecurityRequestMatcherRowMapper());
    }

    @Override
    public CustomUserDetails getCustomUserByUserIdForApi(int userId, int method, String apiUri) {
        logger.info("Method:" + method + ", apiUri=" + apiUri);
        String sqlString = "SELECT GROUP_CONCAT(DISTINCT(rbf.ROLE_ID)) `ROLE_ID` FROM us_role_business_function rbf LEFT JOIN (SELECT GROUP_CONCAT(s.BF) `BF_LIST` FROM ap_security s WHERE (s.METHOD=:method OR s.METHOD=0) AND IF (s.URL=:apiUri, TRUE, IF (s.URL LIKE '%**%', SUBSTRING(:apiUri,1, length(REPLACE(s.URL, '**', ''))) LIKE CONCAT(REPLACE(s.URL, '**', ''),'%'), IF (s.URL LIKE '%*%', SUBSTRING(:apiUri,1, length(REPLACE(s.URL, '*', ''))) LIKE CONCAT(REPLACE(s.URL, '*', ''),'%'), FALSE)))) bf1 ON FIND_IN_SET(rbf.BUSINESS_FUNCTION_ID, bf1.BF_LIST) WHERE bf1.BF_LIST IS NOT NULL";
        Map<String, Object> params = new HashMap<>();
        params.put("method", method);
        params.put("apiUri", apiUri);
        params.put("userId", userId);
        String allowedRoleList = this.namedParameterJdbcTemplate.queryForObject(sqlString, params, String.class);
        logger.info("allowedRoleList=" + allowedRoleList);
        sqlString = this.customUserStringWithAclCheck + " WHERE TRUE AND `user`.`USER_ID`=:userId " + this.customUserOrderBy;
        try {
            params.clear();
            params.put("userId", userId);
            params.put("allowedRoleList", allowedRoleList);
            CustomUserDetails user = this.namedParameterJdbcTemplate.query(sqlString, params, new CustomUserDetailsResultSetExtractorFull());
            logger.info(user.getAclList().toString());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<String, List<String>> getAclRoleBfList(int userId, CustomUserDetails curUser) {
        String sqlString = "SELECT acl.ROLE_ID, rbf.BUSINESS_FUNCTION_ID FROM us_user_acl acl LEFT JOIN us_role_business_function rbf ON acl.ROlE_ID=rbf.ROLE_ID WHERE acl.USER_ID=:userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        return this.namedParameterJdbcTemplate.query(sqlString, params, new AclRoleBusinessFunctionResultSetExtractor(curUser.getBusinessFunction()));
    }

    @Override
    public boolean checkCanCreateRole(String roleId, CustomUserDetails curUser) {
        String sql = "SELECT COUNT(*) FROM us_user_role ur LEFT JOIN us_can_create_role ccr ON ur.ROLE_ID=ccr.ROLE_ID WHERE ur.USER_ID=:curUser AND ccr.CAN_CREATE_ROLE=:newRoleId";
        Map<String, Object> params = new HashMap<>();
        params.put("curUser", curUser.getUserId());
        params.put("newRoleId", roleId);
        return (this.namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class) >= 1);
    }

}
