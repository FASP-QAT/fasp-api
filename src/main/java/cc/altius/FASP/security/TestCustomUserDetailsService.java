package cc.altius.FASP.security;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Test-specific implementation of the CustomUserDetailsService.
 */
@Service
@Primary
@Profile("test")
public class TestCustomUserDetailsService extends CustomUserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(TestCustomUserDetailsService.class);
    @Autowired
    private UserDao userDao;
    @Override
    public CustomUserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        logger.info("Test environment: Loading user by email {}", emailId);
        try {
            CustomUserDetails user = userDao.getCustomUserByEmailId(emailId);
            if (user == null || !user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            user = userDao.getCustomUserByUserId(user.getUserId());
            if (user == null || !user.isPresent()) {
                throw new UsernameNotFoundException("User not found");
            }
            return user;
        } catch (Exception e) {
            logger.warn("Error occurred", e);
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
