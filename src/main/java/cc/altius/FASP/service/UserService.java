/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface UserService {

    public CustomUserDetails getCustomUserByUsername(String username);

    public CustomUserDetails getCustomUserByEmailId(String emailId);

    public CustomUserDetails getCustomUserByUserId(int userId);

    public Map<String, Object> checkIfUserExists(String username, String password);

    public int resetFailedAttemptsByUsername(String emailId);

    public int updateFailedAttemptsByUserId(String emailId);

    public Role getRoleById(String roleId);

    public List<Role> getRoleList(CustomUserDetails curUser);

    public int addNewUser(User user, int curUser);

    public List<User> getUserList(CustomUserDetails curUser);

    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser);

    public User getUserByUserId(int userId, CustomUserDetails curUser);

    public int updateUser(User user, int curUser);

    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page);

    public int unlockAccount(int userId, String password);

    public List<BusinessFunction> getBusinessFunctionList();

    public int updatePassword(int userId, String newPassword, int offset);

    public int updatePassword(String emailId, String token, String newPassword, int offset);

    public boolean confirmPassword(String username, String password);

    public int addRole(Role role, CustomUserDetails curUser);

    public int updateRole(Role role, CustomUserDetails curUser);

    public String generateTokenForEmailId(String emailId, int emailTemplateId);

    public ForgotPasswordToken getForgotPasswordToken(String emailId, String token);

    public void updateTriggeredDateForForgotPasswordToken(String username, String token);

    public void updateCompletionDateForForgotPasswordToken(String emailId, String token);

    public boolean isTokenLogout(String token);

    public void addTokenToLogout(String token);

    public int mapAccessControls(User user, CustomUserDetails curUser);

    public int updateSuncExpiresOn(String emailId);

    public int updateUserLanguage(int userId, String languageCode);

    public int updateUserLanguageByEmailId(String emailId, String languageCode);

    public int acceptUserAgreement(int userId);

    public int addUserJiraAccountId(int userId, String jiraCustomerAccountId);

    public String getUserJiraAccountId(int userId);

    public List<String> getUserListForUpdateJiraAccountId();

    public void updateUserJiraAccountId(String emailAddress, String jiraAccountId);

    public String getEmailByUserId(int userId);

}
