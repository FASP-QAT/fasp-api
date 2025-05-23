/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.EmailUser;
import cc.altius.FASP.model.ForgotPasswordToken;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.SecurityRequestMatcher;
import cc.altius.FASP.model.User;
import cc.altius.FASP.model.UserAcl;
import java.util.List;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface UserDao {

    public CustomUserDetails getCustomUserByUsername(String username);

    public CustomUserDetails getCustomUserByEmailId(String emailId);

    public CustomUserDetails getCustomUserByUserId(int userId);
    
    public CustomUserDetails getCustomUserByUserIdForApi(int userId, int method, String apiUrl);

//    public Map<String, Object> checkIfUserExists(String username, String password);
    public List<String> getBusinessFunctionsForUserId(int userId);

    public int resetFailedAttemptsByUsername(String emailId);

    public int updateFailedAttemptsByUserId(String emailId);

    public int addNewUser(User user, CustomUserDetails curUser) throws AccessControlFailedException;

    public List<User> getUserList(CustomUserDetails curUser);
    
    public List<BasicUser> getUserDropDownList(CustomUserDetails curUser);

    public List<User> getUserListForRealm(int realmId, CustomUserDetails curUser);
    
    public List<BasicUser> getUserListForProgram(int programId, CustomUserDetails curUser);

    public User getUserByUserId(int userId, CustomUserDetails curUser) throws AccessControlFailedException;

    public int updateUser(User user, CustomUserDetails curUser) throws AccessControlFailedException;

    public String checkIfUserExistsByEmail(User user, int page); // 1 add User , 2 Edit User

    public int unlockAccount(int userId, String password);

    public List<BusinessFunction> getBusinessFunctionList();

    public int updatePassword(int userId, String newPassword, int offset);

    public int updatePassword(String emailId, String token, String newPassword, int offset);

    public boolean confirmPassword(String emailId, String password);

    public int addRole(Role role, CustomUserDetails curUser);

    public int updateRole(Role role, CustomUserDetails curUser);

    public Role getRoleById(String roleId);

    public List<Role> getRoleList(CustomUserDetails curUser);
    
    public boolean checkCanCreateRole(String roleId, CustomUserDetails curUser);

    public String generateTokenForUserId(int userId);

    public EmailUser getEmailUserByEmailId(String emailId);

    public ForgotPasswordToken getForgotPasswordToken(String emailId, String token);

    public void updateTriggeredDateForForgotPasswordToken(String username, String token);

    public void updateCompletionDateForForgotPasswordToken(String emailId, String token);

    public boolean isTokenLogout(String token);

    public void addTokenToLogout(String token);

    public List<UserAcl> getAccessControls(CustomUserDetails curUser);
    
    public int mapAccessControls(User user, CustomUserDetails curUser);

    public int updateSuncExpiresOn(String emailId);

    public int updateUserLanguage(int userId, String languageCode);

    public int updateUserLanguageByEmailId(String emailId, String languageCode);

    public int updateUserModule(int userId, int moduleId) throws CouldNotSaveException;
    
    public int updateUserTheme(int userId, int themeId) throws CouldNotSaveException;
    
    public int updateUserDecimalPreference(int userId, boolean showDecimals);

    public int acceptUserAgreement(int userId);

    public int addUserJiraAccountId(int userId, String jiraCustomerAccountId);

    public String getUserJiraAccountId(int userId);

    public List<String> getUserListForUpdateJiraAccountId();

    public void updateUserJiraAccountId(String emailAddress, String jiraAccountId);

    public String getEmailByUserId(int userId);
    
    public List<SecurityRequestMatcher> getSecurityList();
    
    public Map<String, List<String>> getAclRoleBfList(int userId, CustomUserDetails curUser);
}
