/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramVersion extends Version {

    @JsonView({Views.ReportView.class})
    private int programVersionId;
    @JsonView({Views.ReportView.class})
    private SimpleCodeObject program;
    @JsonView({Views.ReportView.class})
    private SimpleCodeObject realmCountry;
    @JsonView({Views.ReportView.class})
    private List<SimpleCodeObject> healthAreaList;
    @JsonView({Views.ReportView.class})
    private SimpleCodeObject organisation;

    public ProgramVersion() {
        healthAreaList = new LinkedList<>();
    }

    public ProgramVersion(int programVersionId, SimpleCodeObject program, SimpleCodeObject realmCountry, SimpleCodeObject healthArea, SimpleCodeObject organisation, int versionId, SimpleObject versionType, SimpleObject versionStatus, String notes, BasicUser createdBy, Date createdDate, BasicUser lastModifiedBy, Date lastModifiedDate) {
        super(versionId, versionType, versionStatus, notes, createdBy, createdDate, lastModifiedBy, lastModifiedDate);
        this.programVersionId = programVersionId;
        this.program = program;
        this.realmCountry = realmCountry;
        this.healthAreaList = new LinkedList<>();
        this.healthAreaList.add(healthArea);
        this.organisation = organisation;
    }

    public void addHeathArea(SimpleCodeObject healthArea) {
        int idx = this.healthAreaList.indexOf(healthArea);
        if (idx == -1) {
            this.healthAreaList.add(healthArea);
        }
    }

    public int getProgramVersionId() {
        return programVersionId;
    }

    public void setProgramVersionId(int programVersionId) {
        this.programVersionId = programVersionId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public List<SimpleCodeObject> getHealthAreaList() {
        return healthAreaList;
    }

    public void setHealthAreaList(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
    }

    public SimpleCodeObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleCodeObject organisation) {
        this.organisation = organisation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.programVersionId;
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
        final ProgramVersion other = (ProgramVersion) obj;
        if (this.programVersionId != other.programVersionId) {
            return false;
        }
        return true;
    }

}
