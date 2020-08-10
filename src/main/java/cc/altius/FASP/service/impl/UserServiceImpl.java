/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
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
    @Autowired
    private RealmDao realmDao;
    @Autowired
    private AclService aclService;

//    @Value("${urlHost}")
//    private static String HOST_URL = "http://localhost:4202/#";
//    private static String HOST_URL = "https://uat.quantificationanalytics.org/#";
    @Value("${urlHost}")
    private static String HOST_URL;
    @Value("${urlPasswordReset}")
    private static String PASSWORD_RESET_URL = "resetPassword";

    @Override
    public CustomUserDetails getCustomUserByUsername(String username) {
        return this.userDao.getCustomUserByUsername(username);
    }

    @Override
    public CustomUserDetails getCustomUserByEmailId(String emailId) {
        return this.userDao.getCustomUserByEmailId(emailId);
    }

    @Override
    public CustomUserDetails getCustomUserByUserId(int userId) {
        return this.userDao.getCustomUserByUserId(userId);
    }

    @Override
    public Map<String, Object> checkIfUserExists(String username, String password) {
        return this.userDao.checkIfUserExists(username, password);
    }

    @Override
    public int resetFailedAttemptsByUsername(String emailId) {
        return this.userDao.resetFailedAttemptsByUsername(emailId);
    }

    @Override
    public int updateFailedAttemptsByUserId(String emailId) {
        return this.userDao.updateFailedAttemptsByUserId(emailId);
    }

    @Override
    public Role getRoleById(String roleId) {
        return this.userDao.getRoleById(roleId);
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
    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser) {
        Realm r = this.realmDao.getRealmById(realmId, curUser);
        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
            return this.userDao.getUserListForRealm(realmId, curUser);
        } else {
            throw new AccessDeniedException("Access denied");
        }
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
    public int updatePassword(String emailId, String token, String newPassword, int offset) {
        int r = this.userDao.updatePassword(emailId, token, newPassword, offset);
        this.userDao.updateCompletionDateForForgotPasswordToken(emailId, token);
        return r;
    }

    @Override
    public boolean confirmPassword(String username, String password) {
        return this.userDao.confirmPassword(username, password);
    }

    @Override
    public int addRole(Role role, CustomUserDetails curUser) {
        return this.userDao.addRole(role, curUser);
    }

    @Override
    public int updateRole(Role role, CustomUserDetails curUser) {
        return this.userDao.updateRole(role, curUser);
    }

    @Override
    public String generateTokenForEmailId(String emailId, int emailTemplateId) {
        EmailUser user = this.userDao.getEmailUserByEmailId(emailId);
        if (user == null) {
            return null;
        }
        String token = this.userDao.generateTokenForUserId(user.getUserId());
        if (token != null && !token.isEmpty()) {
            EmailTemplate emailTemplate = this.emailService.getEmailTemplateByEmailTemplateId(emailTemplateId);
            String[] subjectParam = new String[]{};
            String[] bodyParam = null;
//            if (emailTemplateId == 1) {
//                bodyParam = new String[]{HOST_URL, PASSWORD_RESET_URL, user.getUsername(), token};
//            } else if (emailTemplateId == 2) {
            bodyParam = new String[]{user.getUsername(), HOST_URL, PASSWORD_RESET_URL, emailId, token};
//            }
            System.out.println("emailId---" + emailId);
            Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), user.getEmailId(), emailTemplate.getCcTo(), subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            this.emailService.sendMail(emailer);
        }
        return token;
    }

    @Override
    public ForgotPasswordToken getForgotPasswordToken(String emailId, String token) {
        return this.userDao.getForgotPasswordToken(emailId, token);
    }

    @Override
    public void updateTriggeredDateForForgotPasswordToken(String username, String token) {
        this.userDao.updateTriggeredDateForForgotPasswordToken(username, token);
    }

    @Override
    public void updateCompletionDateForForgotPasswordToken(String emailId, String token) {
        this.userDao.updateCompletionDateForForgotPasswordToken(emailId, token);
    }

    @Override
    public boolean isTokenLogout(String token) {
        return this.userDao.isTokenLogout(token);
    }

    @Override
    public void addTokenToLogout(String token) {
        this.userDao.addTokenToLogout(token);
    }

    @Override
    public int mapAccessControls(User user, CustomUserDetails curUser) {
        return this.userDao.mapAccessControls(user, curUser);
    }

    @Override
    public int updateSuncExpiresOn(String emailId) {
        return this.userDao.updateSuncExpiresOn(emailId);
    }

    @Override
    public int updateUserLanguage(int userId, String languageCode) {
        return this.userDao.updateUserLanguage(userId, languageCode);
    }

    @Override
    public int acceptUserAgreement(int userId) {
        return this.userDao.acceptUserAgreement(userId);
    }

}
