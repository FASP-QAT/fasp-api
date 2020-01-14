/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.rowMapper.CustomUserDetailsRowMapper;
import cc.altius.FASP.service.UserService;
import cc.altius.utils.DateUtils;
import cc.altius.utils.PassPhrase;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author altius
 */
@Repository
public class UserDaoImpl implements UserDao {
    
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
            System.out.println("in catch");
            responseMap.put("customUserDetails", null);
            responseMap.put("message", "User does not exists");
//            responseMap.put("failedValue", ErrorConstants.USER_DOES_NOT_EXIST);
            return responseMap;
        } catch (IndexOutOfBoundsException i) {
            System.out.println("in catch");
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
    
}
