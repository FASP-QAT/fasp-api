/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class UpdateProgramInfoOutput implements Serializable {

    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleCodeObject program;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject realm;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject realmCountry;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject organisation;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject healthAreas;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private SimpleObject regions;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private String programManager;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private String programNotes;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private BasicUser lastUpdatedBy;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date lastUpdatedDate;
    @JsonView({Views.ReportView.class, Views.DropDownView.class})
    private boolean active;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleObject realm) {
        this.realm = realm;
    }

    public SimpleObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleObject organisation) {
        this.organisation = organisation;
    }

    public SimpleObject getHealthAreas() {
        return healthAreas;
    }

    public void setHealthAreas(SimpleObject healthAreas) {
        this.healthAreas = healthAreas;
    }

    public SimpleObject getRegions() {
        return regions;
    }

    public void setRegions(SimpleObject regions) {
        this.regions = regions;
    }

    public String getProgramManager() {
        return programManager;
    }

    public void setProgramManager(String programManager) {
        this.programManager = programManager;
    }

    public String getProgramNotes() {
        return programNotes;
    }

    public void setProgramNotes(String programNotes) {
        this.programNotes = programNotes;
    }

    public BasicUser getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(BasicUser lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
