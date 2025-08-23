/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class UserWithSimpleAcl implements Serializable {

    @JsonView({Views.UserListView.class})
    private int userId;
    @JsonView({Views.UserListView.class})
    private String username;
    @JsonView({Views.UserListView.class})
    private String orgAndCountry;
    @JsonView({Views.UserListView.class})
    private List<SimpleUserAcl> aclList;

    public UserWithSimpleAcl() {
        this.aclList = new LinkedList<>();
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

    public String getOrgAndCountry() {
        return orgAndCountry;
    }

    public void setOrgAndCountry(String orgAndCountry) {
        this.orgAndCountry = orgAndCountry;
    }

    public List<SimpleUserAcl> getAclList() {
        return aclList;
    }

    public void setAclList(List<SimpleUserAcl> aclList) {
        this.aclList = aclList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.userId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserWithSimpleAcl other = (UserWithSimpleAcl) obj;
        return this.userId == other.userId;
    }

}
