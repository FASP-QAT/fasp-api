/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class Role implements Serializable {

    private String roleId;
    private Label label;
    private List<BusinessFunction> businessFunctionList;
    private String[] businessFunctions;
    private List<CanCreateRole> canCreateRoles;
    private String[] canCreateRole;

    public Role() {
        this.businessFunctionList = new LinkedList<BusinessFunction>();
        this.canCreateRoles = new LinkedList<CanCreateRole>();
    }

    public Role(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleId() {
        return roleId;
    }

    public Role(String roleId, Label label) {
        this.roleId = roleId;
        this.label = label;
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
        return businessFunctions;
    }

    public void setBusinessFunctions(String[] businessFunctions) {
        this.businessFunctions = businessFunctions;
    }

    public List<CanCreateRole> getCanCreateRoles() {
        return canCreateRoles;
    }

    public void setCanCreateRoles(List<CanCreateRole> canCreateRoles) {
        this.canCreateRoles = canCreateRoles;
    }

    public String[] getCanCreateRole() {
        return canCreateRole;
    }

    public void setCanCreateRole(String[] canCreateRole) {
        this.canCreateRole = canCreateRole;
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
        return "Role{" + "roleId=" + roleId + ", label=" + label + ", businessFunctionList=" + businessFunctionList + ", businessFunctions=" + businessFunctions + ", canCreateRoles=" + canCreateRoles + ", canCreateRole=" + canCreateRole + '}';
    }

}
