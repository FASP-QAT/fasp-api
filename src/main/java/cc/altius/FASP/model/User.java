/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Arrays;
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
    private String orgAndCountry;
    private Realm realm;
    private List<Role> roleList;
    private String[] roles;
    private Language language;
    private int defaultModuleId;
    private int defaultThemeId;
    private int faildAttempts;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date lastLoginDate;
    private List<UserAcl> userAclList;
    private UserAcl[] userAcls;
    private List<String> businessFunctionList;

    public String[] getRoles() {
        if (this.roleList.isEmpty()) {
            return new String[0];
        } else {
            return roleList.stream().map(Role::getRoleId).toArray(String[]::new);
        }
    }

    public void setRoles(String[] roles) {
        this.roleList.clear();
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

    public String getOrgAndCountry() {
        return orgAndCountry;
    }

    public void setOrgAndCountry(String orgAndCountry) {
        this.orgAndCountry = orgAndCountry;
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

    public int getDefaultModuleId() {
        return defaultModuleId;
    }

    public void setDefaultModuleId(int defaultModuleId) {
        this.defaultModuleId = defaultModuleId;
    }

    public int getDefaultThemeId() {
        return defaultThemeId;
    }

    public void setDefaultThemeId(int defaultThemeId) {
        this.defaultThemeId = defaultThemeId;
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
        if (this.userAclList.isEmpty()) {
            return new UserAcl[0];
        } else {
            return userAclList.stream().toArray(UserAcl[]::new);
        }
    }

    public void setUserAcls(UserAcl[] userAcls) {
        this.userAcls = userAcls;
        this.userAclList = Arrays.asList(userAcls);
    }

    public List<String> getBusinessFunctionList() {
        return businessFunctionList;
    }

    public void setBusinessFunctionList(List<String> businessFunctionList) {
        this.businessFunctionList = businessFunctionList;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", emailId=" + emailId + ", orgAndCountry=" + orgAndCountry + ", realm=" + realm + ", roleList=" + roleList + ", roles=" + roles + ", language=" + language + ", faildAttempts=" + faildAttempts + ", lastLoginDate=" + lastLoginDate + ", userAclList=" + userAclList + ", userAcls=" + userAcls + '}';
    }

}
