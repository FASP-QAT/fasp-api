/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramData extends BaseModel implements Serializable {

    private int programId;
    private RealmCountry realmCountry;
    private SimpleObject organisation;
    private SimpleObject healthArea;
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
    private List<Region> regionList;
    private Version currentVersion;
    private List<Version> versionList;
    private List<Consumption> consumptionList;
    private List<Inventory> inventoryList;

    public ProgramData() {
    }

    public ProgramData(Program p) {
        this.programId = p.getProgramId();
        this.realmCountry = p.getRealmCountry();
        this.organisation = p.getOrganisation();
        this.healthArea = p.getHealthArea();
        this.label = p.getLabel();
        this.programManager = p.getProgramManager();
        this.programNotes = p.getProgramNotes();
        this.airFreightPerc = p.getAirFreightPerc();
        this.seaFreightPerc = p.getSeaFreightPerc();
        this.plannedToDraftLeadTime = p.getPlannedToDraftLeadTime();
        this.submittedToApprovedLeadTime = p.getSubmittedToApprovedLeadTime();
        this.approvedToShippedLeadTime = p.getApprovedToShippedLeadTime();
        this.deliveredToReceivedLeadTime = p.getDeliveredToReceivedLeadTime();
        this.monthsInFutureForAmc = p.getMonthsInFutureForAmc();
        this.monthsInPastForAmc = p.getMonthsInPastForAmc();
        this.regionList = p.getRegionList();
        this.currentVersion = p.getCurrentVersion();
        this.versionList = p.getVersionList();
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

    public Version getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Version currentVersion) {
        this.currentVersion = currentVersion;
    }

    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    public List<Consumption> getConsumptionList() {
        return consumptionList;
    }

    public void setConsumptionList(List<Consumption> consumptionList) {
        this.consumptionList = consumptionList;
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<Inventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

}
