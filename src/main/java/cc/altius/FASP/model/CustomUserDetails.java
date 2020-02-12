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
    private boolean expired;
    private int failedAttempts;
    private boolean outsideAccess;
    private Date expiresOn;
    private Date lastLoginDate;
    private List<Role> roleList;
    private List<SimpleGrantedAuthority> businessFunction;
    private String emailId;
    private Date sessionExpiresOn;
    private Language language;

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public CustomUserDetails() {
        this.roleList = new LinkedList<Role>();
    }

    public List<SimpleGrantedAuthority> getBusinessFunction() {
        return businessFunction;
    }

    public void setBusinessFunction(List<String> businessFunction) {
        List<SimpleGrantedAuthority> finalBusinessFunction = new ArrayList<SimpleGrantedAuthority>();
        for (String bf : businessFunction) {
            finalBusinessFunction.add((new SimpleGrantedAuthority(bf)));
        }
        this.businessFunction = finalBusinessFunction;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
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

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isOutsideAccess() {
        return outsideAccess;
    }

    public void setOutsideAccess(boolean outsideAccess) {
        this.outsideAccess = outsideAccess;
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

    public Date getSessionExpiresOn() {
        return sessionExpiresOn;
    }

    public void setSessionExpiresOn(Date sessionExpiresOn) {
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

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (isExpired()) {
            return false;
        } else {
            return true;
        }
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
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (isActive()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordExpired() {
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        if (DateUtils.compareDates(DateUtils.formatDate(this.expiresOn, DateUtils.YMD), curDate) > 0) {
            return false;
        } else {
            return true;
        }
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
        return "CustomUserDetails{" + "userId=" + userId + ", username=" + username + ", password=" + password + ", active=" + active + ", expired=" + expired + ", failedAttempts=" + failedAttempts + ", outsideAccess=" + outsideAccess + ", expiresOn=" + expiresOn + ", lastLoginDate=" + lastLoginDate + ", roleList=" + roleList + ", businessFunction=" + businessFunction + '}';
    }
}
