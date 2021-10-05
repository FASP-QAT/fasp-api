/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.util.List;

/**
 *
 * @author akil
 */
public class DatasetData {

    private int programId;
    private String programCode;
    private RealmCountry realmCountry;
    private SimpleCodeObject organisation;
    private List<SimpleCodeObject> healthAreaList;
    private Label label;
    private BasicUser programManager;
    private String programNotes;
    private List<Region> regionList;
    private Version currentVersion;
    private List<DatasetTree> treeList;

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

}
