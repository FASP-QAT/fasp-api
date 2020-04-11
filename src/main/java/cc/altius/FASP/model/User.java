/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author altius
 */
public class User extends BaseModel implements Serializable {

    private int userId;
    private String username;
    private String password;
    private String emailId;
    private String phoneNumber;
    private Realm realm;
    private List<Role> roleList;
    private String[] roles;
    private Language language;
    private int faildAttempts;
    private Date lastLoginDate;
    private List<UserAcl> userAclList;
    private UserAcl[] userAcls;

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
        for (String r : roles) {
            this.getRoleList().add(new Role(r));
        }
    }

    public User() {
        this.roleList = new LinkedList<>();
        this.userAclList = new LinkedList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getFaildAttempts() {
        return faildAttempts;
    }

    public void setFaildAttempts(int faildAttempts) {
        this.faildAttempts = faildAttempts;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public List<UserAcl> getUserAclList() {
        return userAclList;
    }

    public void setUserAclList(List<UserAcl> userAclList) {
        this.userAclList = userAclList;
    }

    public UserAcl[] getUserAcls() {
        return userAcls;
    }

    public void setUserAcls(UserAcl[] userAcls) {
        this.userAcls = userAcls;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", emailId=" + emailId + ", phoneNumber=" + phoneNumber + ", realm=" + realm + ", roleList=" + roleList + ", roles=" + roles + ", language=" + language + ", faildAttempts=" + faildAttempts + ", lastLoginDate=" + lastLoginDate + ", userAclList=" + userAclList + ", userAcls=" + userAcls + '}';
    }



}
