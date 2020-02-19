/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.sun.javadoc.SerialFieldTag;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class UserAcl implements Serializable {

    private int userId;
    private int realmCountryId;
    private Label countryName;
    private int healthAreaId;
    private Label healthAreaName;
    private int organisationId;
    private Label organisationName;
    private int programId;
    private Label programName;

    public UserAcl() {
    }

    public UserAcl(int userId, int realmCountryId, int healthAreaId, int organisationId, int programId) {
        this.userId = userId;
        this.realmCountryId = realmCountryId;
        this.healthAreaId = healthAreaId;
        this.organisationId = organisationId;
        this.programId = programId;
    }

    public UserAcl(int userId, int realmCountryId, Label countryName, int healthAreaId, Label healthAreaName, int organisationId, Label organisationName, int programId, Label programName) {
        this.userId = userId;
        this.realmCountryId = realmCountryId;
        this.countryName = countryName;
        this.healthAreaId = healthAreaId;
        this.healthAreaName = healthAreaName;
        this.organisationId = organisationId;
        this.organisationName = organisationName;
        this.programId = programId;
        this.programName = programName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
    
}
