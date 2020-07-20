/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramData extends BaseModel implements Serializable {

    private int programId;
    private String programCode;
    private RealmCountry realmCountry;
    private SimpleCodeObject organisation;
    private SimpleCodeObject healthArea;
    private Label label;
    private BasicUser programManager;
    private String programNotes;
    private double airFreightPerc;
    private double seaFreightPerc;
    private double plannedToSubmittedLeadTime;
    private double submittedToApprovedLeadTime;
    private double approvedToShippedLeadTime;
    private double shippedToArrivedByAirLeadTime;
    private double shippedToArrivedBySeaLeadTime;
    private double arrivedToDeliveredLeadTime;
    private SimpleObject versionType;
    private SimpleObject versionStatus;
    private String notes;
    private List<Region> regionList;
    private Version currentVersion;
    @JsonIgnore
    private List<Version> versionList;
    private List<Consumption> consumptionList;
    private List<Inventory> inventoryList;
    private List<Shipment> shipmentList;
    private List<Batch> batchInfoList;
    private int requestedProgramVersion;
    @JsonIgnore
    private Date lastModifiedDate;
    @JsonIgnore
    private BasicUser lastModifiedBy;
    @JsonIgnore
    private boolean active;
    
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRequestedProgramVersion() {
        return requestedProgramVersion;
    }

    public void setRequestedProgramVersion(int requestedProgramVersion) {
        this.requestedProgramVersion = requestedProgramVersion;
    }

    public ProgramData() {
    }

    public ProgramData(Program p) {
        this.programId = p.getProgramId();
        this.programCode = p.getProgramCode();
        this.realmCountry = p.getRealmCountry();
        this.organisation = p.getOrganisation();
        this.healthArea = p.getHealthArea();
        this.label = p.getLabel();
        this.programManager = p.getProgramManager();
        this.programNotes = p.getProgramNotes();
        this.airFreightPerc = p.getAirFreightPerc();
        this.seaFreightPerc = p.getSeaFreightPerc();
        this.plannedToSubmittedLeadTime = p.getPlannedToSubmittedLeadTime();
        this.submittedToApprovedLeadTime = p.getSubmittedToApprovedLeadTime();
        this.approvedToShippedLeadTime = p.getApprovedToShippedLeadTime();
        this.shippedToArrivedBySeaLeadTime = p.getShippedToArrivedBySeaLeadTime();
        this.shippedToArrivedByAirLeadTime = p.getShippedToArrivedByAirLeadTime();
        this.arrivedToDeliveredLeadTime = p.getArrivedToDeliveredLeadTime();
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

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public RealmCountry getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(RealmCountry realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleCodeObject getOrganisation() {
        return organisation;
    }

    public void setOrganisation(SimpleCodeObject organisation) {
        this.organisation = organisation;
    }

    public SimpleCodeObject getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(SimpleCodeObject healthArea) {
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

    public double getPlannedToSubmittedLeadTime() {
        return plannedToSubmittedLeadTime;
    }

    public void setPlannedToSubmittedLeadTime(double plannedToSubmittedLeadTime) {
        this.plannedToSubmittedLeadTime = plannedToSubmittedLeadTime;
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

    public double getShippedToArrivedByAirLeadTime() {
        return shippedToArrivedByAirLeadTime;
    }

    public void setShippedToArrivedByAirLeadTime(double shippedToArrivedByAirLeadTime) {
        this.shippedToArrivedByAirLeadTime = shippedToArrivedByAirLeadTime;
    }

    public double getShippedToArrivedBySeaLeadTime() {
        return shippedToArrivedBySeaLeadTime;
    }

    public void setShippedToArrivedBySeaLeadTime(double shippedToArrivedBySeaLeadTime) {
        this.shippedToArrivedBySeaLeadTime = shippedToArrivedBySeaLeadTime;
    }

    public double getArrivedToDeliveredLeadTime() {
        return arrivedToDeliveredLeadTime;
    }

    public void setArrivedToDeliveredLeadTime(double arrivedToDeliveredLeadTime) {
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
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

    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<Shipment> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public List<Batch> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<Batch> batchInfoList) {
        this.batchInfoList = batchInfoList;
    }

    public SimpleObject getVersionType() {
        return versionType;
    }

    public void setVersionType(SimpleObject versionType) {
        this.versionType = versionType;
    }

    public SimpleObject getVersionStatus() {
        return versionStatus;
    }

    public void setVersionStatus(SimpleObject versionStatus) {
        this.versionStatus = versionStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public String toString() {
        return "ProgramData{" + "programId=" + programId + ", realmCountry=" + realmCountry + ", organisation=" + organisation + ", healthArea=" + healthArea + ", label=" + label + ", programManager=" + programManager + ", programNotes=" + programNotes + ", airFreightPerc=" + airFreightPerc + ", seaFreightPerc=" + seaFreightPerc + ", plannedToSubmittedLeadTime=" + plannedToSubmittedLeadTime + ", submittedToApprovedLeadTime=" + submittedToApprovedLeadTime + ", approvedToShippedLeadTime=" + approvedToShippedLeadTime + ", shippedToArrivedBySeaLeadTime=" + shippedToArrivedBySeaLeadTime + ", shippedToArrivedByAirLeadTime=" + shippedToArrivedByAirLeadTime + ", regionList=" + regionList + ", currentVersion=" + currentVersion + ", versionList=" + versionList + ", consumptionList=" + consumptionList + ", inventoryList=" + inventoryList + '}';
    }

}
