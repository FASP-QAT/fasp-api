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
    public CustomUserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        logger.info("Inside loadUserByUsername" + LogUtils.getArgsString(), LogUtils.getIpAddress(), LogUtils.getUsername());
        try {
            CustomUserDetails user = this.userDao.getCustomUserByEmailId(emailId);
            if (user == null || !user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
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

}
