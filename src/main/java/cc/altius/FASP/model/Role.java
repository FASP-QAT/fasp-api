/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class Role implements Serializable {

    @JsonView({Views.IgnoreView.class, Views.UserListView.class})
    private String roleId;
    @JsonView({Views.IgnoreView.class, Views.UserListView.class})
    private Label label;
    private List<BusinessFunction> businessFunctionList;
    private String[] businessFunctions;
    private List<CanCreateRole> canCreateRoleList;
    private String[] canCreateRoles;

    public Role() {
        this.businessFunctionList = new LinkedList<BusinessFunction>();
        this.canCreateRoleList = new LinkedList<CanCreateRole>();
    }

    public Role(String roleId) {
        this.roleId = roleId;
        this.businessFunctionList = new LinkedList<BusinessFunction>();
        this.canCreateRoleList = new LinkedList<CanCreateRole>();
    }

    public String getRoleId() {
        return roleId;
    }

    public Role(String roleId, Label label) {
        this.roleId = roleId;
        this.label = label;
        this.businessFunctionList = new LinkedList<BusinessFunction>();
        this.canCreateRoleList = new LinkedList<CanCreateRole>();
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<BusinessFunction> getBusinessFunctionList() {
        return businessFunctionList;
    }

    public void setBusinessFunctionList(List<BusinessFunction> businessFunctionList) {
        this.businessFunctionList = businessFunctionList;
    }

    public String[] getBusinessFunctions() {
        if (this.businessFunctionList.isEmpty()) {
            return new String[0];
        } else {
            return this.businessFunctionList.stream().map(BusinessFunction::getBusinessFunctionId).toArray(String[]::new);
        }
    }

    public void setBusinessFunctions(String[] businessFunctions) {
        this.businessFunctions = businessFunctions;
        this.businessFunctionList.clear();
        for (String bf : businessFunctions) {
            this.businessFunctionList.add(new BusinessFunction(bf));
        }
    }

    public List<CanCreateRole> getCanCreateRoleList() {
        return canCreateRoleList;
    }

    public void setCanCreateRoleList(List<CanCreateRole> canCreateRoleList) {
        this.canCreateRoleList = canCreateRoleList;
    }

    public String[] getCanCreateRoles() {
        if (this.canCreateRoleList.isEmpty()) {
            return new String[0];
        } else {
            return this.canCreateRoleList.stream().map(CanCreateRole::getRoleId).toArray(String[]::new);
        }
    }

    public void setCanCreateRoles(String[] canCreateRoles) {
        this.canCreateRoles = canCreateRoles;
        this.canCreateRoleList.clear();
        for (String c: canCreateRoles) {
            this.canCreateRoleList.add(new CanCreateRole(c));
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.roleId);
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
        final Role other = (Role) obj;
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", label=" + label + ", businessFunctionList=" + businessFunctionList + ", businessFunctions=" + businessFunctions + ", canCreateRoles=" + canCreateRoles + ", canCreateRole=" + canCreateRoleList + '}';
    }

}
