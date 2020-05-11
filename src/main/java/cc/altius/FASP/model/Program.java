/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class Program extends BaseModel implements Serializable {

    private int programId;
    private RealmCountry realmCountry;
    private SimpleObject organisation;
    private SimpleObject healthArea;
    private Label label;
    private BasicUser programManager;
    private String programNotes;
    private double airFreightPerc;
    private double seaFreightPerc;
    private double plannedToDraftLeadTime;
    private double draftToSubmittedLeadTime;
    private double submittedToApprovedLeadTime;
    private double approvedToShippedLeadTime;
    private double deliveredToReceivedLeadTime;
    private int monthsInPastForAmc;
    private int monthsInFutureForAmc;
    @JsonIgnore
    private List<Region> regionList;
    String[] regionArray;
    private Version currentVersion;
    private List<Version> versionList;

    public Program() {
    }

    public Program(int programId, Label label) {
        this.programId = programId;
        this.label = label;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public RealmCountry getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(RealmCountry realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleObject organisation) {
        this.organisation = organisation;
    }

    public SimpleObject getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(SimpleObject healthArea) {
        this.healthArea = healthArea;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public BasicUser getProgramManager() {
        return programManager;
    }

    public void setProgramManager(BasicUser programManager) {
        this.programManager = programManager;
    }

    public String getProgramNotes() {
        return programNotes;
    }

    public void setProgramNotes(String programNotes) {
        this.programNotes = programNotes;
    }

    public double getAirFreightPerc() {
        return airFreightPerc;
    }

    public void setAirFreightPerc(double airFreightPerc) {
        this.airFreightPerc = airFreightPerc;
    }

    public double getSeaFreightPerc() {
        return seaFreightPerc;
    }

    public void setSeaFreightPerc(double seaFreightPerc) {
        this.seaFreightPerc = seaFreightPerc;
    }

    public double getPlannedToDraftLeadTime() {
        return plannedToDraftLeadTime;
    }

    public void setPlannedToDraftLeadTime(double plannedToDraftLeadTime) {
        this.plannedToDraftLeadTime = plannedToDraftLeadTime;
    }

    public double getDraftToSubmittedLeadTime() {
        return draftToSubmittedLeadTime;
    }

    public void setDraftToSubmittedLeadTime(double draftToSubmittedLeadTime) {
        this.draftToSubmittedLeadTime = draftToSubmittedLeadTime;
    }

    public double getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(double submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

    public double getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(double approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public double getDeliveredToReceivedLeadTime() {
        return deliveredToReceivedLeadTime;
    }

    public void setDeliveredToReceivedLeadTime(double deliveredToReceivedLeadTime) {
        this.deliveredToReceivedLeadTime = deliveredToReceivedLeadTime;
    }

    public int getMonthsInPastForAmc() {
        return monthsInPastForAmc;
    }

    public void setMonthsInPastForAmc(int monthsInPastForAmc) {
        this.monthsInPastForAmc = monthsInPastForAmc;
    }

    public int getMonthsInFutureForAmc() {
        return monthsInFutureForAmc;
    }

    public void setMonthsInFutureForAmc(int monthsInFutureForAmc) {
        this.monthsInFutureForAmc = monthsInFutureForAmc;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public String[] getRegionArray() {
        return regionArray;
    }

    public void setRegionArray(String[] regionArray) {
        this.regionArray = regionArray;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Version currentVersion) {
        this.currentVersion                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     = currentVersion;
    }

    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.programId;
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
        final Program other = (Program) obj;
        if (this.programId != other.programId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Program{" + "programId=" + programId + ", label=" + label + '}';
    }

}
