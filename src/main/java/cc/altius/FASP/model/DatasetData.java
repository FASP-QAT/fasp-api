/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;

/**
 *
 * @author akil
 */
public class DatasetData {

    @JsonView(Views.InternalView.class)
    private int programId;
    @JsonView(Views.InternalView.class)
    private String programCode;
    @JsonView(Views.InternalView.class)
    private RealmCountry realmCountry;
    @JsonView(Views.InternalView.class)
    private SimpleCodeObject organisation;
    @JsonView(Views.InternalView.class)
    private List<SimpleCodeObject> healthAreaList;
    @JsonView(Views.InternalView.class)
    private Label label;
    @JsonView(Views.InternalView.class)
    private BasicUser programManager;
    @JsonView(Views.InternalView.class)
    private String programNotes;
    @JsonView(Views.InternalView.class)
    private List<Region> regionList;
    @JsonView(Views.InternalView.class)
    private Version currentVersion;
    @JsonView(Views.InternalView.class)
    private List<Version> versionList;
    @JsonView(Views.InternalView.class)
    private List<DatasetTree> treeList;
    @JsonView(Views.InternalView.class)
    private List<ForecastActualConsumption> actualConsumptionList;
    @JsonView(Views.InternalView.class)
    private List<ForecastConsumptionExtrapolation> consumptionExtrapolation;
    @JsonView(Views.InternalView.class)
    private List<DatasetPlanningUnit> planningUnitList;
    

    public DatasetData() {
    }

    public DatasetData(Program p) {
        this.programId = p.getProgramId();
        this.programCode = p.getProgramCode();
        this.realmCountry = p.getRealmCountry();
        this.organisation = p.getOrganisation();
        this.healthAreaList = p.getHealthAreaList();
        this.label = p.getLabel();
        this.programManager = p.getProgramManager();
        this.programNotes = p.getProgramNotes();
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

    public List<DatasetTree> getTreeList() {
        return treeList;
    }

    public void setTreeList(List<DatasetTree> treeList) {
        this.treeList = treeList;
    }

    public List<Version> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<Version> versionList) {
        this.versionList = versionList;
    }

    public List<ForecastActualConsumption> getActualConsumptionList() {
        return actualConsumptionList;
    }

    public void setActualConsumptionList(List<ForecastActualConsumption> actualConsumptionList) {
        this.actualConsumptionList = actualConsumptionList;
    }

    public List<DatasetPlanningUnit> getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(List<DatasetPlanningUnit> planningUnitList) {
        this.planningUnitList = planningUnitList;
    }

    public List<ForecastConsumptionExtrapolation> getConsumptionExtrapolation() {
        return consumptionExtrapolation;
    }

    public void setConsumptionExtrapolation(List<ForecastConsumptionExtrapolation> consumptionExtrapolation) {
        this.consumptionExtrapolation = consumptionExtrapolation;
    }

}
