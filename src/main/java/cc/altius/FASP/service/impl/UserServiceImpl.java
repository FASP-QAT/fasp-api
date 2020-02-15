/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.BusinessFunction;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Role;
import cc.altius.FASP.model.User;
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
    UserDao userDao;

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
    public int addNewUser(User user) {
        return this.userDao.addNewUser(user);
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
    public int updateUser(User user) {
        return this.userDao.updateUser(user);
    }

    @Override
    public String checkIfUserExistsByEmailIdAndPhoneNumber(User user, int page) {
        return this.userDao.checkIfUserExistsByEmailIdAndPhoneNumber(user, page);
    }

    @Override
    public int unlockAccount(User user) {
        return this.userDao.unlockAccount(user);
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
    public boolean confirmPassword(int userId, String password) {
        return this.userDao.confirmPassword(userId, password);
    }

}
