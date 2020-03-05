/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author altius
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private EmailService emailService;
//    @Value("${urlHost}")
    private static String HOST_URL = "http://localhost:4202";
//    private static String HOST_URL = "https://faspdeveloper.github.io/fasp";
//    @Value("${urlPasswordReset}")
    private static String PASSWORD_RESET_URL = "resetPassword";

    @Override
    public CustomUserDetails getCustomUserByUsername(String username) {
        return this.userDao.getCustomUserByUsername(username);
    }

    @Override
    public Map<String, Object> checkIfUserExists(String username, String password) {
        return this.userDao.checkIfUserExists(username, password);
    }

    @Override
    public int resetFailedAttemptsByUsername(String username) {
        return this.userDao.resetFailedAttemptsByUsername(username);
    }

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        return this.userDao.updateFailedAttemptsByUserId(username);
    }

    @Override
    public List<Role> getRoleList() {
        return this.userDao.getRoleList();
    }

    @Override
    public int addNewUser(User user, int curUser) {
        return this.userDao.addNewUser(user, curUser);
    }

    @Override
    public List<User> getUserList() {
        return this.userDao.getUserList();
    }

    @Override
    public User getUserByUserId(int userId) {
        return this.userDao.getUserByUserId(userId);
    }

    @Override
    public int updateUser(User user, int curUser) {
        return this.userDao.updateUser(user, curUser);
    }

    @Override
    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page) {
        return this.userDao.checkIfUserExistsByEmailIdAndPhoneNumber(user, page);
    }

    @Override
    public int unlockAccount(int userId, String password) {
        return this.userDao.unlockAccount(userId, password);
    }

    @Override
    public List<BusinessFunction> getBusinessFunctionList() {
        return this.userDao.getBusinessFunctionList();
    }

    @Override
    public int updatePassword(int userId, String newPassword, int offset) {
        return this.userDao.updatePassword(userId, newPassword, offset);
    }

    @Override
    public int updatePassword(String username, String token, String newPassword, int offset) {
        int r = this.userDao.updatePassword(username, token, newPassword, offset);
        this.userDao.updateCompletionDateForForgotPasswordToken(username, token);
        return r;
    }

    @Override
    public boolean confirmPassword(String username, String password) {
        return this.userDao.confirmPassword(username, password);
    }

    @Override
    public int addRole(Role role) {
        return this.userDao.addRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return this.userDao.updateRole(role);
    }

    @Override
    public String generateTokenForUsername(String username, int emailTemplateId) {
        EmailUser user = this.userDao.getEmailUserByUsername(username);
        if (user == null) {
            return null;
        }
        String token = this.userDao.generateTokenForUserId(user.getUserId());
        if (token != null && !token.isEmpty()) {
            try {
                EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(emailTemplateId);
                String[] subjectParam = new String[]{};
                String[] bodyParam = new String[]{HOST_URL, PASSWORD_RESET_URL, user.getUsername(), token};
                Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), user.getEmailId(), emailTemplate.getCcTo(), subjectParam, bodyParam);
                try {
                    int emailerId = this.emailService.saveEmail(emailer);
                    emailer.setEmailerId(emailerId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.emailService.sendMail(emailer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return token;
    }

    @Override
    public ForgotPasswordToken getForgotPasswordToken(String username, String token) {
        return this.userDao.getForgotPasswordToken(username, token);
    }

    @Override
    public void updateTriggeredDateForForgotPasswordToken(String username, String token) {
        this.userDao.updateTriggeredDateForForgotPasswordToken(username, token);
    }

    @Override
    public void updateCompletionDateForForgotPasswordToken(String username, String token) {
        this.userDao.updateCompletionDateForForgotPasswordToken(username, token);
    }

}
