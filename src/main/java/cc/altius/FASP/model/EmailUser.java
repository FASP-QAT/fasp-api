/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author akil
 */
public class EmailUser extends BasicUser {

    private String emailId;
    private String token;
    private String password;

    public EmailUser() {
        super();
    }

    public EmailUser(String emailId, int userId, String username) {
        super(userId, username);
        this.emailId = emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashPassword() {
        if(this.password != null) {
            BCryptPasswordEncoder b = new BCryptPasswordEncoder();
            return b.encode(password);
        } else {
            return null;
        }
        
    }
}
