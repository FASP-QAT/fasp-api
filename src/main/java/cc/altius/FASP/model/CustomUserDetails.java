/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author shrutika
 */
public class CustomUserDetails implements UserDetails, Serializable {

    private int userId;
    private String username;
    private String password;
    private boolean active;
    private int failedAttempts;
    private Date expiresOn;
    private Date lastLoginDate;
    private Realm realm;
    private List<Role> roles;
    private List<UserAcl> aclList;
    private List<SimpleGrantedAuthority> businessFunction;
    private String emailId;
    private int sessionExpiresOn;
    private Language language;
    private Date syncExpiresOn;
    private boolean agreementAccepted;
    private int defaultThemeId;
    private boolean showDecimals;
    private int defaultModuleId;

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public List<UserAcl> getAclList() {
        return aclList;
    }

    public void setAclList(List<UserAcl> aclList) {
        this.aclList = aclList;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public CustomUserDetails() {
        this.roles = new LinkedList<>();
        this.aclList = new LinkedList<>();
        this.businessFunction = new LinkedList<>();
    }

    public List<SimpleGrantedAuthority> getBusinessFunction() {
        return businessFunction;
    }

    public void setBusinessFunction(List<String> bfList) {
        List<SimpleGrantedAuthority> finalBfList = new ArrayList<>();
        bfList.forEach((bf) -> {
            finalBfList.add((new SimpleGrantedAuthority(bf)));
        });
        this.businessFunction = finalBfList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public int getSessionExpiresOn() {
        return sessionExpiresOn;
    }

    public void setSessionExpiresOn(int sessionExpiresOn) {
        this.sessionExpiresOn = sessionExpiresOn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return businessFunction;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Date getSyncExpiresOn() {
        return syncExpiresOn;
    }

    public void setSyncExpiresOn(Date syncExpiresOn) {
        this.syncExpiresOn = syncExpiresOn;
    }

    public boolean isAgreementAccepted() {
        return agreementAccepted;
    }

    public void setAgreementAccepted(boolean agreementAccepted) {
        this.agreementAccepted = agreementAccepted;
    }

    public int getDefaultThemeId() {
        return defaultThemeId;
    }

    public void setDefaultThemeId(int defaultThemeId) {
        this.defaultThemeId = defaultThemeId;
    }

    public boolean isShowDecimals() {
        return showDecimals;
    }

    public void setShowDecimals(boolean showDecimals) {
        this.showDecimals = showDecimals;
    }

    public int isDefaultModuleId() {
        return defaultModuleId;
    }

    public void setDefaultModuleId(int defaultModuleId) {
        this.defaultModuleId = defaultModuleId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (failedAttempts <= 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        if (DateUtils.compareDates(DateUtils.formatDate(this.expiresOn, DateUtils.YMD), curDate) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    public boolean isPresent() {
        if (this.userId == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean hasBusinessFunction(String businessFunction) {
        SimpleGrantedAuthority s = new SimpleGrantedAuthority(businessFunction);
        return (this.getBusinessFunction().indexOf(s) >= 0);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.userId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CustomUserDetails other = (CustomUserDetails) obj;
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", active=" + active + ", failedAttempts=" + failedAttempts + ", expiresOn=" + expiresOn + ", lastLoginDate=" + lastLoginDate + ", realm=" + realm + ", roles=" + roles + ", aclList=" + aclList + ", businessFunction=" + businessFunction + ", emailId=" + emailId + ", sessionExpiresOn=" + sessionExpiresOn + ", language=" + language + ", syncExpiresOn=" + syncExpiresOn + '}';
    }

}
