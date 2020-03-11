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
    private Organisation organisation;
    private HealthArea healthArea;
    private Label label;
    private BasicUser programManager;
    private String programNotes;
    private double airFreightPerc;
    private double seaFreightPerc;
    private int plannedToDraftLeadTime;
    private int draftToSubmittedLeadTime;
    private int submittedToApprovedLeadTime;
    private int approvedToShippedLeadTime;
    private int deliveredToReceivedLeadTime;
    private int monthsInPastForAmc;
    private int monthsInFutureForAmc;
    @JsonIgnore
    private List<Region> regionList;
    String[] regionArray;

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

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public HealthArea getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(HealthArea healthArea) {
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

    public int getPlannedToDraftLeadTime() {
        return plannedToDraftLeadTime;
    }

    public void setPlannedToDraftLeadTime(int plannedToDraftLeadTime) {
        this.plannedToDraftLeadTime = plannedToDraftLeadTime;
    }

    public int getDraftToSubmittedLeadTime() {
        return draftToSubmittedLeadTime;
    }

    public void setDraftToSubmittedLeadTime(int draftToSubmittedLeadTime) {
        this.draftToSubmittedLeadTime = draftToSubmittedLeadTime;
    }

    public int getSubmittedToApprovedLeadTime() {
        return submittedToApprovedLeadTime;
    }

    public void setSubmittedToApprovedLeadTime(int submittedToApprovedLeadTime) {
        this.submittedToApprovedLeadTime = submittedToApprovedLeadTime;
    }

    public int getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(int approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public int getDeliveredToReceivedLeadTime() {
        return deliveredToReceivedLeadTime;
    }

    public void setDeliveredToReceivedLeadTime(int deliveredToReceivedLeadTime) {
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
