/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service.impl;

import cc.altius.FASP.dao.UserDao;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.service.UserService;
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
    public Map<String, Object> checkIfUserExists(String username, String password) {
        return this.userDao.checkIfUserExists(username, password);
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        return this.userDao.resetFailedAttemptsByUserId(userId);
    }

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        return this.userDao.updateFailedAttemptsByUserId(username);
    }

}
