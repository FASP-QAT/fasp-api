/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.util.Date;

/**
 *
 * @author akil
 */
public class ProgramVersion extends Version {

    private SimpleObject program;
    private SimpleCodeObject realmCountry;
    private SimpleObject healthArea;
    private SimpleCodeObject organisation;

    public ProgramVersion() {
    }

    public ProgramVersion(SimpleObject program, SimpleCodeObject realmCountry, SimpleObject healthArea, SimpleCodeObject organisation, int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, BasicUser createdBy, Date createdDate, BasicUser lastModifiedBy, Date lastModifiedDate) {
        super(versionId, versionType, versionStatus, notes, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.program = program;
        this.realmCountry = realmCountry;
        this.healthArea = healthArea;
        this.organisation = organisation;
    }

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleObject getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(SimpleObject healthArea) {
        this.healthArea = healthArea;
    }

    public SimpleCodeObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleCodeObject organisation) {
        this.organisation = organisation;
    }

    
}
