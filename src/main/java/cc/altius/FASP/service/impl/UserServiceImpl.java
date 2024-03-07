/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.RealmDao;
import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.exception.IncorrectAccessControlException;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.UserAclDTO;
import cc.altius.FASP.model.EmailTemplate;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.Emailer;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.EmailService;
import cc.altius.FASP.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${urlHost}")
    private String HOST_URL;
    @Value("${urlPasswordReset}")
    private String PASSWORD_RESET_URL;

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
    public List<Role> getRoleList(CustomUserDetails curUser) {
        return this.userDao.getRoleList(curUser);
    }

    @Override
    public int addNewUser(User user, CustomUserDetails curUser) throws IncorrectAccessControlException {
        return this.userDao.addNewUser(user, curUser);
    }

    @Override
    public List<User> getUserList(int realmId, CustomUserDetails curUser) {
        return this.userDao.getUserList(realmId, curUser);
    }

    @Override
    public List<UserAclDTO> getUserAclList(int realmId, CustomUserDetails curUser) {
        return this.userDao.getUserAclList(realmId, curUser);
    }

    @Override
    public List<BasicUser> getUserDropDownList(CustomUserDetails curUser) {
        return this.userDao.getUserDropDownList(curUser);
    }

    @Override
    public List<BasicUser> getUserDropDownList(int realmId, CustomUserDetails curUser) {
        return this.userDao.getUserDropDownList(realmId, curUser);
    }
    
//    @Override
//    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser) {
//        Realm r = this.realmDao.getRealmById(realmId, curUser);
//        if (this.aclService.checkRealmAccessForUser(curUser, realmId)) {
//            return this.userDao.getUserListForRealm(realmId, curUser);
//        } else {
//            throw new AccessDeniedException("Access denied");
//        }
//    }

    @Override
    public List<BasicUser> getUserDropDownListForProgram(int programId, CustomUserDetails curUser) {
        return this.userDao.getUserDropDownListForProgram(programId, curUser);
    }

    @Override
    public User getUserByUserId(int userId, CustomUserDetails curUser) {
        return this.userDao.getUserByUserId(userId, curUser);
    }

    @Override
    public int updateUser(User user, CustomUserDetails curUser) throws IncorrectAccessControlException {
        return this.userDao.updateUser(user, curUser);
    }

    @Override
    public String checkIfUserExistsByEmailId(User user, int page) {
        return this.userDao.checkIfUserExistsByEmail(user, page);
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
            bodyParam = new String[]{emailId, HOST_URL, PASSWORD_RESET_URL, emailId, token};
            Emailer emailer = this.emailService.buildEmail(emailTemplate.getEmailTemplateId(), user.getEmailId(), emailTemplate.getCcTo(), "", subjectParam, bodyParam);
            int emailerId = this.emailService.saveEmail(emailer);
            emailer.setEmailerId(emailerId);
            if (this.emailService.sendMail(emailer) == 1) {
                return token;
            } else {
                return null;
            }
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
    public int updateUserLanguageByEmailId(String emailId, String languageCode) {
        return this.userDao.updateUserLanguageByEmailId(emailId, languageCode);
    }

    @Override
    public int updateUserModule(int userId, int moduleId) throws CouldNotSaveException {
        return this.userDao.updateUserModule(userId, moduleId);
    }

    @Override
    public int acceptUserAgreement(int userId) {
        return this.userDao.acceptUserAgreement(userId);
    }

    @Override
    public int addUserJiraAccountId(int userId, String jiraCustomerAccountId) {
        return this.userDao.addUserJiraAccountId(userId, jiraCustomerAccountId);
    }

    @Override
    public String getUserJiraAccountId(int userId) {
        return this.userDao.getUserJiraAccountId(userId);
    }

    @Override
    public List<String> getUserListForUpdateJiraAccountId() {
        return this.userDao.getUserListForUpdateJiraAccountId();
    }

    @Override
    public void updateUserJiraAccountId(String emailAddress, String jiraAccountId) {
        this.userDao.updateUserJiraAccountId(emailAddress, jiraAccountId);
    }

    @Override
    public String getEmailByUserId(int userId) {
        return this.userDao.getEmailByUserId(userId);
    }

}
