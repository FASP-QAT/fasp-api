/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class CanCreateRole {

    @JsonView(Views.ReportView.class)
    private String roleId;
    @JsonView(Views.ReportView.class)
    private Label label;

    public CanCreateRole(String roleId) {
        this.roleId = roleId;
    }

    public CanCreateRole() {
    }

    public String getRoleId() {
        return roleId;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.roleId);
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
        final CanCreateRole other = (CanCreateRole) obj;
        if (!Objects.equals(this.roleId, other.roleId)) {
            return false;
        }
        return true;
    }

}
