/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsRowMapper;
import cc.altius.FASP.model.rowMapper.RoleRowMapper;
import cc.altius.FASP.model.rowMapper.UserRowMapper;
import cc.altius.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Map<String, Object> checkIfUserExists(String username, String password) {
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.GMT);
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        c.add(Calendar.HOUR, 4);

        CustomUserDetails customUserDetails = null;
        Map<String, Object> responseMap = new HashMap<String, Object>();
        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM us_user `user`"
                + " LEFT JOIN us_user_role user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN us_role role ON user_role.ROLE_ID=role.ROLE_ID "
                + " WHERE user.EMAIL_ID=?";
        try {
            customUserDetails = this.jdbcTemplate.query(sql, new CustomUserDetailsRowMapper(), username).get(0);
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(password, customUserDetails.getPassword())) {
                if (!customUserDetails.isActive()) {
                    responseMap.put("customUserDetails", null);
                    responseMap.put("message", "User is inactive");
//                    responseMap.put("failedValue", ErrorConstants.USER_INACTIVE);
                    return responseMap;
                } else if (customUserDetails.getFailedAttempts() >= 3) {
//                    logService.accessLog("0.0.0.0", username, user.getUserId(), false, "Account Locked");
//                    LogUtils.debugLogger.debug(LogUtils.buildStringForSystemLog("Account Locked"));
//                    LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog("Account Locked"));
                    responseMap.put("customUserDetails", null);
                    responseMap.put("message", "User account is locked");
//                    responseMap.put("failedValue", ErrorConstants.ACCOUNT_LOCKED);
                    return responseMap;
                } else {
                    responseMap.put("customUserDetails", customUserDetails);
                    responseMap.put("message", "Logined successfully");
                    this.resetFailedAttemptsByUserId(customUserDetails.getUserId());
                }
            } else {
                this.updateFailedAttemptsByUserId(username);
                responseMap.put("customUserDetails", null);
                responseMap.put("message", "Password is invalid");
//                responseMap.put("failedValue", ErrorConstants.INVALID_PASSWORD);
                return responseMap;
            }
        } catch (IncorrectResultSizeDataAccessException i) {
            responseMap.put("customUserDetails", null);
            responseMap.put("message", "User does not exists");
//            responseMap.put("failedValue", ErrorConstants.USER_DOES_NOT_EXIST);
            return responseMap;
        } catch (IndexOutOfBoundsException i) {
            responseMap.put("customUserDetails", null);
            responseMap.put("message", "User does not exists");
//            responseMap.put("failedValue", ErrorConstants.USER_DOES_NOT_EXIST);
            return responseMap;
        }
        return responseMap;
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        try {
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
            String sqlreset = "UPDATE `user` SET FAILED_ATTEMPTS=0,LAST_LOGIN_DATE=? WHERE USER_ID=?";
            return this.jdbcTemplate.update(sqlreset, curDt, userId);
        } catch (DataAccessException e) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, e));
            return 0;
        }
    }

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        try {
            String sqlQuery = "UPDATE `user` SET FAILED_ATTEMPTS=FAILED_ATTEMPTS+1 WHERE USERNAME=?";
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, sqlQuery));
            return this.jdbcTemplate.update(sqlQuery, username);
        } catch (DataAccessException e) {
//            LogUtils.systemLogger.info(LogUtils.buildStringForSystemLog(GlobalConstants.TAG_SYSTEM, "Could not update failed attempts :" + e));
            return 0;
        }
    }

    @Override
    public List<Role> getRoleList() {
        String sql = "SELECT * FROM us_role;";
        return this.jdbcTemplate.query(sql, new RoleRowMapper());
    }

    @Override
    @Transactional
    public int addNewUser(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource).withTableName("us_user").usingGeneratedKeyColumns("USER_ID");
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        Map<String, Object> map = new HashedMap<>();
        map.put("REALM_ID", user.getRealm().getRealmId());
        map.put("USERNAME", user.getEmailId());
        map.put("PASSWORD", user.getPassword());
        map.put("EMAIL_ID", user.getEmailId());
        map.put("PHONE_NO", user.getPhoneNumber());
        map.put("LANGUAGE_ID", user.getLanguage().getLanguageId());
        map.put("ACTIVE", 1);
        map.put("EXPIRED", 0);
        map.put("FAILED_ATTEMPTS", 0);
        map.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("SYNC_EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("LAST_LOGIN_DATE", null);
        map.put("CREATED_BY", 1);
        map.put("CREATED_DATE", curDate);
        map.put("LAST_MODIFIED_BY", 1);
        map.put("LAST_MODIFIED_DATE", curDate);
        int userId = insert.executeAndReturnKey(map).intValue();
        String sqlString = "INSERT INTO us_user_role (USER_ID, ROLE_ID) VALUES(?,?)";
        this.jdbcTemplate.update(sqlString, userId, user.getRole().getRoleId());
        return userId;
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT u.`USER_ID`,u.`EMAIL_ID`,u.`PHONE_NO`,"
                + "u.`REALM_ID`,rl.`REALM_CODE`,u.`LANGUAGE_ID`,"
                + "l.`LANGUAGE_NAME`,ur.`ROLE_ID`,r.`ROLE_NAME`,"
                + "u.`LAST_LOGIN_DATE`,u.`FAILED_ATTEMPTS`,u.`ACTIVE`"
                + " FROM us_user u "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID`"
                + "LEFT JOIN us_role r ON r.`ROLE_ID`=ur.`ROLE_ID`"
                + "LEFT JOIN rm_realm rl ON rl.`REALM_ID`=u.`REALM_ID`"
                + "LEFT JOIN lc_language l ON l.`LANGUAGE_ID`=u.`LANGUAGE_ID`";
        return this.jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User getUserByUserId(int userId) {
        String sql = "SELECT u.`USER_ID`,u.`EMAIL_ID`,u.`PHONE_NO`,"
                + "u.`REALM_ID`,rl.`REALM_CODE`,u.`LANGUAGE_ID`,"
                + "l.`LANGUAGE_NAME`,ur.`ROLE_ID`,r.`ROLE_NAME`,"
                + "u.`LAST_LOGIN_DATE`,u.`FAILED_ATTEMPTS`,u.`ACTIVE`"
                + " FROM us_user u "
                + "LEFT JOIN us_user_role ur ON ur.`USER_ID`=u.`USER_ID`"
                + "LEFT JOIN us_role r ON r.`ROLE_ID`=ur.`ROLE_ID`"
                + "LEFT JOIN rm_realm rl ON rl.`REALM_ID`=u.`REALM_ID`"
                + "LEFT JOIN lc_language l ON l.`LANGUAGE_ID`=u.`LANGUAGE_ID` WHERE u.`USER_ID`=?;";
        return this.jdbcTemplate.queryForObject(sql, new UserRowMapper(), userId);
    }

    @Override
    public int updateUser(User user) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "";
        sql = "UPDATE us_user u "
                + "SET u.`REALM_ID`=:realmId, "
                + "u.`USERNAME`=:userName, "
                + "u.`EMAIL_ID`=:emailId, "
                + "u.`PHONE_NO`=:phoneNo, "
                + "u.`LANGUAGE_ID`=:languageId, "
                + "u.`ACTIVE`=:active, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy, "
                + "u.`LAST_MODIFIED_DATE`=:lastModifiedDate "
                + "WHERE  u.`USER_ID`=:userId;";
        Map<String, Object> map = new HashMap<>();
        map.put("realmId", user.getRealm().getRealmId());
        map.put("userName", user.getEmailId());
        map.put("emailId", user.getEmailId());
        map.put("phoneNo", user.getPhoneNumber());
        map.put("languageId", user.getLanguage().getLanguageId());
        map.put("active", user.isActive());
        map.put("lastModifiedBy", 1);
        map.put("lastModifiedDate", curDate);
        map.put("userId", user.getUserId());
        int row = namedParameterJdbcTemplate.update(sql, map);
        sql = "DELETE FROM us_user_role WHERE  USER_ID=?;";
        this.jdbcTemplate.update(sql, user.getUserId());
        sql = "INSERT INTO us_user_role (USER_ID, ROLE_ID) VALUES(?,?)";
        this.jdbcTemplate.update(sql, user.getUserId(), user.getRole().getRoleId());
        return row;
    }

    @Override
    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page) {
        String message = "", sql, emailId = user.getEmailId(), phoneNo = user.getPhoneNumber();
        int userId = 0;
        if (page == 1) {
            sql = "SELECT COUNT(*) FROM us_user u WHERE u.`EMAIL_ID`=?;";
            if ((this.jdbcTemplate.queryForObject(sql, Integer.class, emailId) > 0 ? true : false)) {
                message += "Email id already exists.";
            }
            sql = "SELECT COUNT(*) FROM us_user u WHERE u.`PHONE_NO`=?;";
            if ((this.jdbcTemplate.queryForObject(sql, Integer.class, phoneNo) > 0 ? true : false)) {
                message += "Phone no. already exists.";
            }
        } else if (page == 2) {
            sql = "SELECT u.`USER_ID` FROM us_user u WHERE u.`EMAIL_ID`=?;";
            try {
                userId = this.jdbcTemplate.queryForObject(sql, Integer.class, emailId);
            } catch (EmptyResultDataAccessException e) {
                userId = 0;
            }
            if (userId > 0 && userId != user.getUserId() ? true : false) {
                message += "Email id already exists.";
            }
            sql = "SELECT u.`USER_ID` FROM us_user u WHERE u.`PHONE_NO`=?;";
            try {
                userId = this.jdbcTemplate.queryForObject(sql, Integer.class, phoneNo);
            } catch (EmptyResultDataAccessException e) {
                userId = 0;
            }
            if (userId > 0 && userId != user.getUserId() ? true : false) {
                message += "Phone no. already exists.";
            }
        }
        return message;
    }

    @Override
    public int unlockAccount(User user) {
        String curDate = DateUtils.getCurrentDateString(DateUtils.EST, DateUtils.YMDHMS);
        String sql = "UPDATE us_user u "
                + "SET "
                + "u.`FAILED_ATTEMPTS`=0, "
                + "u.`EXPIRES_ON`=:expiresOn, "
                + "u.`PASSWORD`=:pwd, "
                + "u.`LAST_LOGIN_DATE`=:lastModifiedDate, "
                + "u.`LAST_MODIFIED_BY`=:lastModifiedBy "
                + "WHERE u.`USER_ID`=:userId;";
        Map<String, Object> map = new HashMap<>();
        map.put("expiresOn", DateUtils.getOffsetFromCurrentDateObject(DateUtils.EST, -1));
        map.put("pwd", user.getPassword());
        map.put("lastModifiedBy", 1);
        map.put("lastModifiedDate", curDate);
        map.put("userId", user.getUserId());
        return namedParameterJdbcTemplate.update(sql, map);
    }

}
