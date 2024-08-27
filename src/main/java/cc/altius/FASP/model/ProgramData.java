/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramData extends BaseModel implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int programId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String programCode;
    @JsonView({Views.InternalView.class})
    private String cutOffDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private RealmCountry realmCountry;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleCodeObject organisation;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<SimpleCodeObject> healthAreaList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Label label;
    @JsonView({Views.InternalView.class})
    private BasicUser programManager;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String programNotes;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double airFreightPerc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double seaFreightPerc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double roadFreightPerc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double plannedToSubmittedLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double submittedToApprovedLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double approvedToShippedLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double shippedToArrivedByAirLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double shippedToArrivedBySeaLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double shippedToArrivedByRoadLeadTime;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double arrivedToDeliveredLeadTime;
    @JsonView(Views.InternalView.class)
    private SimpleObject versionType;
    @JsonView(Views.InternalView.class)
    private SimpleObject versionStatus;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<Region> regionList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Version currentVersion;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private List<ProgramVersionTrans> currentVersionTrans;
    private List<Version> versionList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<Consumption> consumptionList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<Inventory> inventoryList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<Shipment> shipmentList;
    @JsonView({Views.InternalView.class})
    private List<ShipmentLinking> shipmentLinkingList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<Batch> batchInfoList;
    @JsonView({Views.InternalView.class})
    private List<BatchInventory> batchInventoryList;
    @JsonView(Views.InternalView.class)
    private List<ProblemReport> problemReportList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<SimplifiedSupplyPlan> supplyPlan;
    @JsonView(Views.InternalView.class)
    private int requestedProgramVersion;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<SimplePlanningUnitForSupplyPlanObject> planningUnitList;
    @JsonView(Views.InternalView.class)
    private List<SimpleCodeObject> procurementAgentList;
    
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
        this.healthAreaList = p.getHealthAreaList();
        this.label = p.getLabel();
        this.programManager = p.getProgramManager();
        this.programNotes = p.getProgramNotes();
        this.airFreightPerc = p.getAirFreightPerc();
        this.seaFreightPerc = p.getSeaFreightPerc();
        this.roadFreightPerc = p.getRoadFreightPerc();
        this.plannedToSubmittedLeadTime = p.getPlannedToSubmittedLeadTime();
        this.submittedToApprovedLeadTime = p.getSubmittedToApprovedLeadTime();
        this.approvedToShippedLeadTime = p.getApprovedToShippedLeadTime();
        this.shippedToArrivedBySeaLeadTime = p.getShippedToArrivedBySeaLeadTime();
        this.shippedToArrivedByAirLeadTime = p.getShippedToArrivedByAirLeadTime();
        this.shippedToArrivedByRoadLeadTime = p.getShippedToArrivedByRoadLeadTime();
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

    public String getCutOffDate() {
        return cutOffDate;
    }

    public void setCutOffDate(String cutOffDate) {
        this.cutOffDate = cutOffDate;
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

    public List<SimpleCodeObject> getHealthAreaList() {
        return healthAreaList;
    }

    public void setHealthAreaList(List<SimpleCodeObject> healthAreaList) {
        this.healthAreaList = healthAreaList;
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

    public double getRoadFreightPerc() {
        return roadFreightPerc;
    }

    public void setRoadFreightPerc(double roadFreightPerc) {
        this.roadFreightPerc = roadFreightPerc;
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

    public double getShippedToArrivedByRoadLeadTime() {
        return shippedToArrivedByRoadLeadTime;
    }

    public void setShippedToArrivedByRoadLeadTime(double shippedToArrivedByRoadLeadTime) {
        this.shippedToArrivedByRoadLeadTime = shippedToArrivedByRoadLeadTime;
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

    public List<ProgramVersionTrans> getCurrentVersionTrans() {
        return currentVersionTrans;
    }

    public void setCurrentVersionTrans(List<ProgramVersionTrans> currentVersionTrans) {
        this.currentVersionTrans = currentVersionTrans;
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

    public List<ShipmentLinking> getShipmentLinkingList() {
        return shipmentLinkingList;
    }

    public void setShipmentLinkingList(List<ShipmentLinking> shipmentLinkingList) {
        this.shipmentLinkingList = shipmentLinkingList;
    }

    public List<Batch> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<Batch> batchInfoList) {
        this.batchInfoList = batchInfoList;
    }

    public List<BatchInventory> getBatchInventoryList() {
        return batchInventoryList;
    }

    public void setBatchInventoryList(List<BatchInventory> batchInventoryList) {
        this.batchInventoryList = batchInventoryList;
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

    public List<ProblemReport> getProblemReportList() {
        return problemReportList;
    }

    public void setProblemReportList(List<ProblemReport> problemReportList) {
        this.problemReportList = problemReportList;
    }

    public List<SimplifiedSupplyPlan> getSupplyPlan() {
        return supplyPlan;
    }

    public void setSupplyPlan(List<SimplifiedSupplyPlan> supplyPlan) {
        this.supplyPlan = supplyPlan;
    }

    public List<SimplePlanningUnitForSupplyPlanObject> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<SimplePlanningUnitForSupplyPlanObject> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<SimpleCodeObject> getProcurementAgentList() {
        return procurementAgentList;
    }

    public void setProcurementAgentList(List<SimpleCodeObject> procurementAgentList) {
        this.procurementAgentList = procurementAgentList;
    }

    @Override
    public String toString() {
        return "ProgramData{" + "programId=" + programId + ", realmCountry=" + realmCountry + ", organisation=" + organisation + ", healthAreaList=" + healthAreaList + ", label=" + label + ", programManager=" + programManager + ", programNotes=" + programNotes + ", airFreightPerc=" + airFreightPerc + ", seaFreightPerc=" + seaFreightPerc + ", plannedToSubmittedLeadTime=" + plannedToSubmittedLeadTime + ", submittedToApprovedLeadTime=" + submittedToApprovedLeadTime + ", approvedToShippedLeadTime=" + approvedToShippedLeadTime + ", shippedToArrivedBySeaLeadTime=" + shippedToArrivedBySeaLeadTime + ", shippedToArrivedByAirLeadTime=" + shippedToArrivedByAirLeadTime + ", regionList=" + regionList + ", currentVersion=" + currentVersion + ", versionList=" + versionList + ", consumptionList=" + consumptionList + ", inventoryList=" + inventoryList + '}';
    }

}
