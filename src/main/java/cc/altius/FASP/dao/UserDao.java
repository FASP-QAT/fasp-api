/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.dao;

import cc.altius.FASP.model.CustomUserDetails;
import java.util.Map;

/**
 *
 * @author altius
 */
public interface UserDao {

    public Map<String, Object> checkIfUserExists(String username, String password);
    
    public int resetFailedAttemptsByUserId(int userId);

    public int updateFailedAttemptsByUserId(String username);

}
