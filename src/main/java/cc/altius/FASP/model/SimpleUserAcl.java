/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SimpleUserAcl implements Serializable {

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
        this.realmCountryId = realmCountryId;
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
        this.healthAreaId = healthAreaId;
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
        this.organisationId = organisationId;
    }

    public Label getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(Label organisationName) {
        this.organisationName = organisationName;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
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
        this.procurementAgentId = procurementAgentId;
    }

    public Label getProcurementAgentName() {
        return procurementAgentName;
    }

    public void setProcurementAgentName(Label procurementAgentName) {
        this.procurementAgentName = procurementAgentName;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
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

}
