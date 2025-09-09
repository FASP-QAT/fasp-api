/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class UserAcl implements Serializable {

    @JsonView(Views.UserListView.class)
    private int userAclId;
    @JsonView(Views.UserListView.class)
    private int userId;
    @JsonView(Views.UserListView.class)
    private String username;
    @JsonView(Views.UserListView.class)
    private String orgAndCountry;
    @JsonView(Views.UserListView.class)
    private int active;
    @JsonView(Views.UserListView.class)
    private String roleId;
    @JsonView(Views.UserListView.class)
    private Label roleDesc;
    @JsonView(Views.UserListView.class)
    private int realmCountryId;
    @JsonView(Views.UserListView.class)
    private Label countryName;
    @JsonView(Views.UserListView.class)
    private int healthAreaId;
    @JsonView(Views.UserListView.class)
    private Label healthAreaName;
    @JsonView(Views.UserListView.class)
    private int organisationId;
    @JsonView(Views.UserListView.class)
    private Label organisationName;
    @JsonView(Views.UserListView.class)
    private int fundingSourceId;
    @JsonView(Views.UserListView.class)
    private Label fundingSourceName;
    @JsonView(Views.UserListView.class)
    private int procurementAgentId;
    @JsonView(Views.UserListView.class)
    private Label procurementAgentName;
    @JsonView(Views.UserListView.class)
    private int programId;
    @JsonView(Views.UserListView.class)
    private Label programName;
    @JsonView(Views.UserListView.class)
    private String programCode;
    @JsonView(Views.UserListView.class)
    private int programTypeId;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.UserListView.class)
    private Date lastModifiedDate;

    public UserAcl() {
    }

    public UserAcl(int userId, String roleId, int realmCountryId, int healthAreaId, int organisationId, int programId, int fundingSourceId, int procurementAgentId, String lastModifiedDate) {
        this.userId = userId;
        this.roleId = roleId;
        this.setRealmCountryId(realmCountryId);
        this.setHealthAreaId(healthAreaId);
        this.setOrganisationId(organisationId);
        this.setProgramId(programId);
        this.setFundingSourceId(fundingSourceId);
        this.setProcurementAgentId(procurementAgentId);
        this.lastModifiedDate = this.lastModifiedDate;
    }

    public UserAcl(int userId, String roleId, Label roleDesc, int realmCountryId, Label countryName, int healthAreaId, Label healthAreaName, int organisationId, Label organisationName, int programId, Label programName, String programCode, int fundingSourceId, Label fundingSourceName, int procurementAgentId, Label procurementAgentName, int programTypeId, Date lastModifiedDate) {
        this.userId = userId;
        this.roleId = roleId;
        this.setRealmCountryId(realmCountryId);
        this.setHealthAreaId(healthAreaId);
        this.setOrganisationId(organisationId);
        this.setProgramId(programId);
        this.setFundingSourceId(fundingSourceId);
        this.setProcurementAgentId(procurementAgentId);
        this.roleDesc = roleDesc;
        this.countryName = countryName;
        this.healthAreaName = healthAreaName;
        this.organisationName = organisationName;
        this.programName = programName;
        this.programCode = programCode;
        this.fundingSourceName = fundingSourceName;
        this.procurementAgentName = procurementAgentName;
        this.programTypeId = programTypeId;
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getUserAclId() {
        return userAclId;
    }

    public void setUserAclId(int userAclId) {
        this.userAclId = userAclId;
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
    
    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Label getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(Label roleDesc) {
        this.roleDesc = roleDesc;
    }

    public int getRealmCountryId() {
        return realmCountryId;
    }

    public void setRealmCountryId(int realmCountryId) {
        this.realmCountryId = realmCountryId == 0 ? -1 : realmCountryId;
    }

    public Label getCountryName() {
        return countryName;
    }

    public void setCountryName(Label countryName) {
        this.countryName = countryName;
    }

    public int getHealthAreaId() {
        return healthAreaId;
    }

    public void setHealthAreaId(int healthAreaId) {
        this.healthAreaId = healthAreaId == 0 ? -1 : healthAreaId;
    }

    public Label getHealthAreaName() {
        return healthAreaName;
    }

    public void setHealthAreaName(Label healthAreaName) {
        this.healthAreaName = healthAreaName;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId == 0 ? -1 : organisationId;
    }

    public Label getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(Label organisationName) {
        this.organisationName = organisationName;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId == 0 ? -1 : programId;
    }

    public Label getProgramName() {
        return programName;
    }

    public void setProgramName(Label programName) {
        this.programName = programName;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId == 0 ? -1 : fundingSourceId;
    }

    public Label getFundingSourceName() {
        return fundingSourceName;
    }

    public void setFundingSourceName(Label fundingSourceName) {
        this.fundingSourceName = fundingSourceName;
    }

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId == 0 ? -1 : procurementAgentId;
    }

    public Label getProcurementAgentName() {
        return procurementAgentName;
    }

    public void setProcurementAgentName(Label procurementAgentName) {
        this.procurementAgentName = procurementAgentName;
    }

    public int getProgramTypeId() {
        return programTypeId;
    }

    public void setProgramTypeId(int programTypeId) {
        this.programTypeId = programTypeId;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.userId;
        hash = 31 * hash + Objects.hashCode(this.roleId);
        hash = 79 * hash + this.realmCountryId;
        hash = 79 * hash + this.healthAreaId;
        hash = 79 * hash + this.organisationId;
        hash = 79 * hash + this.programId;
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
        final UserAcl other = (UserAcl) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.realmCountryId != other.realmCountryId) {
            return false;
        }
        if (this.healthAreaId != other.healthAreaId) {
            return false;
        }
        if (this.organisationId != other.organisationId) {
            return false;
        }
        if (this.programId != other.programId) {
            return false;
        }
        if (this.fundingSourceId != other.fundingSourceId) {
            return false;
        }
        if (this.procurementAgentId != other.procurementAgentId) {
            return false;
        }
        return Objects.equals(this.roleId, other.roleId);
    }

    @Override
    public String toString() {
        return "UserAcl{" + "userAclId=" + userAclId + ", userId=" + userId + ", username=" + username + ", orgAndCountry=" + orgAndCountry + ", active=" + active + ", roleId=" + roleId + ", roleDesc=" + roleDesc + ", realmCountryId=" + realmCountryId + ", countryName=" + countryName + ", healthAreaId=" + healthAreaId + ", healthAreaName=" + healthAreaName + ", organisationId=" + organisationId + ", organisationName=" + organisationName + ", fundingSourceId=" + fundingSourceId + ", fundingSourceName=" + fundingSourceName + ", procurementAgentId=" + procurementAgentId + ", procurementAgentName=" + procurementAgentName + ", programId=" + programId + ", programName=" + programName + ", programCode=" + programCode + ", programTypeId=" + programTypeId + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
