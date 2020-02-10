/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.security;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author akil
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserDao userDao;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Inside loadUserByUsername" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
        try {
            CustomUserDetails user = this.userDao.getCustomUserByUsername(username);
            if (user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            if (!user.isActive()) {
                logger.warn("Account disabled" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
//                this.logDao.accessLog(ipAddress, username, null, false, "Account disabled");
            } else if (!user.isAccountNonLocked()) {
                logger.warn("Account locked" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
//                this.logDao.accessLog(ipAddress, username, null, false, "Account locked");
//            } else if (!(user.isOutsideAccess() || checkIfIpIsFromAllowedRange(ipAddress))) {
//                user.setActive(false);
//                logger.warn("Outside access" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
//                this.logDao.accessLog(ipAddress, username, null, false, "Outside access");
            } else {
//                if (user.isPasswordExpired()) {
                // only insert the ROLE_BF_PASSWORD_EXPIRED
//                    logger.info("Credentials are Expired so only put in ROLE_BF_PASSWORD_EXPIRED into Authoirites" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
//                    List<String> businessFunctions = new LinkedList<>();
//                    businessFunctions.add("ROLE_BF_PASSWORD_EXPIRED");
//                    user.setBusinessFunction(businessFunctions);
//                } else {
                user.setBusinessFunction(this.userDao.getBusinessFunctionsForUserId(user.getUserId()));
//                }
            }
            return user;
        } catch (NullPointerException ne) {
            logger.warn("Error occurred", ne);
            throw new UsernameNotFoundException(ne.getMessage());
        } catch (Exception e) {
            logger.warn("Error occurred", e);
            throw new UsernameNotFoundException(e.getMessage());
        }
    }

    private boolean checkIfIpIsFromAllowedRange(String ipToCheck) {
//        for (String curRange : this.allowedIpRange) {
//            IPUtils curIpRange = new IPUtils(curRange);
//            if(curIpRange.checkIP(ipToCheck)) {
//                return true;
//            }
//        }
//        return false;
        return true;
    }

}
