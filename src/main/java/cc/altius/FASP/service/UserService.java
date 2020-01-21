/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.service;

import java.util.Map;

/**
 *
 * @author altius
 */
public interface UserService {

    public Map<String, Object> checkIfUserExists(String username, String password);

    public int resetFailedAttemptsByUserId(int userId);

    public int updateFailedAttemptsByUserId(String username);

}
