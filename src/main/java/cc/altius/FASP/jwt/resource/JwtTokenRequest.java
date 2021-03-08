/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.jwt.resource;

import java.io.Serializable;

public class JwtTokenRequest implements Serializable {
    
    private static final long serialVersionUID = -5616176897013108345L;
    
    private String username;
    private String password;
    private String languageCode;
    private boolean languageChanged;
    
    public JwtTokenRequest() {
        super();
    }
    
    public JwtTokenRequest(String username, String password, String laguageCode,boolean languageChanged) {
        this.setUsername(username);
        this.setPassword(password);
        this.setLanguageCode(languageCode);
        this.setLanguageChanged(languageChanged);
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getLanguageCode() {
        return languageCode;
    }
    
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public boolean isLanguageChanged() {
        return languageChanged;
    }

    public void setLanguageChanged(boolean languageChanged) {
        this.languageChanged = languageChanged;
    }
    
}
