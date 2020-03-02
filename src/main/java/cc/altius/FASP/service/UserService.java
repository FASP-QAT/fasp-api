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

    public Map<String, Object> checkIfUserExists(String username, String password);

    public int resetFailedAttemptsByUsername(String username);

    public int updateFailedAttemptsByUserId(String username);

    public List<Role> getRoleList();

    public int addNewUser(User user, int curUser);

    public List<User> getUserList();

    public User getUserByUserId(int userId);

    public int updateUser(User user, int curUser);

    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page);

    public int unlockAccount(int userId, String password);

    public List<BusinessFunction> getBusinessFunctionList();

    public int updatePassword(int userId, String newPassword, int offset);

    public int updatePassword(String username, String token, String newPassword, int offset);

    public boolean confirmPassword(String username, String password);

    public int addRole(Role role);

    public int updateRole(Role role);

    public String generateTokenForUsername(String username);

    public ForgotPasswordToken getForgotPasswordToken(String username, String token);

    public void updateTriggeredDateForForgotPasswordToken(String username, String token);

    public void updateCompletionDateForForgotPasswordToken(String username, String token);

}
