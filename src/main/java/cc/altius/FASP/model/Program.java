/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class Program extends BaseModel implements Serializable {

    private int programId;
    private String programCode;
    private RealmCountry realmCountry;
    private SimpleCodeObject organisation;
    private List<SimpleCodeObject> healthAreaList;
    private Label label;
    private BasicUser programManager;
    private String programNotes;
    private double airFreightPerc;
    private double seaFreightPerc;
    private double roadFreightPerc;
    private double plannedToSubmittedLeadTime;
    private double submittedToApprovedLeadTime;
    private double approvedToShippedLeadTime;
    private double shippedToArrivedByAirLeadTime;
    private double shippedToArrivedBySeaLeadTime;
    private double shippedToArrivedByRoadLeadTime;
    private double arrivedToDeliveredLeadTime;
    private Integer noOfMonthsInPastForBottomDashboard;
    private Integer noOfMonthsInFutureForBottomDashboard;

    private int programTypeId;

    private List<Region> regionList;
    private Version currentVersion;
    private List<Version> versionList;

    public Program() {
        this.regionList = new LinkedList<>();
        this.healthAreaList = new LinkedList<>();
    }

    public Program(int programId, String programCode, Label label) {
        this.programId = programId;
        this.programCode = programCode;
        this.label = label;
        this.regionList = new LinkedList<>();
        this.healthAreaList = new LinkedList<>();
    }

    public double getArrivedToDeliveredLeadTime() {
        return arrivedToDeliveredLeadTime;
    }

    public void setArrivedToDeliveredLeadTime(double arrivedToDeliveredLeadTime) {
        this.arrivedToDeliveredLeadTime = arrivedToDeliveredLeadTime;
    }

    public double getShippedToArrivedBySeaLeadTime() {
        return shippedToArrivedBySeaLeadTime;
    }

    public void setShippedToArrivedBySeaLeadTime(double shippedToArrivedBySeaLeadTime) {
        this.shippedToArrivedBySeaLeadTime = shippedToArrivedBySeaLeadTime;
    }

    public double getShippedToArrivedByAirLeadTime() {
        return shippedToArrivedByAirLeadTime;
    }

    public void setShippedToArrivedByAirLeadTime(double shippedToArrivedByAirLeadTime) {
        this.shippedToArrivedByAirLeadTime = shippedToArrivedByAirLeadTime;
    }

    public double getShippedToArrivedByRoadLeadTime() {
        return shippedToArrivedByRoadLeadTime;
    }

    public void setShippedToArrivedByRoadLeadTime(double shippedToArrivedByRoadLeadTime) {
        this.shippedToArrivedByRoadLeadTime = shippedToArrivedByRoadLeadTime;
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

    public List<Integer> getHealthAreaIdList() {
        return healthAreaList.stream().map(SimpleCodeObject::getId).collect(Collectors.toList());
    }

    public String[] getHealthAreaArray() {
        if (this.healthAreaList.isEmpty()) {
            return new String[0];
        } else {
            return healthAreaList.stream().map(SimpleCodeObject::getIdString).toArray(String[]::new);
        }
    }

    public void setHealthAreaArray(String[] healthAreaArray) {
        this.healthAreaList.clear();
        for (String ha : healthAreaArray) {
            this.healthAreaList.add(new SimpleCodeObject(Integer.parseInt(ha), null, null));
        }
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

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public String[] getRegionArray() {
        if (this.regionList.isEmpty()) {
            return new String[0];
        } else {
            return regionList.stream().map(Region::getRegionIdString).toArray(String[]::new);
        }
    }

    public void setRegionArray(String[] regionArray) {
        this.regionList.clear();
        for (String r : regionArray) {
            this.regionList.add(new Region(Integer.parseInt(r), null));
        }
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

    public int getProgramTypeId() {
        return programTypeId;
    }

    public void setProgramTypeId(int programTypeId) {
        this.programTypeId = programTypeId;
    }

    public Integer getNoOfMonthsInPastForBottomDashboard() {
        return noOfMonthsInPastForBottomDashboard;
    }

    public void setNoOfMonthsInPastForBottomDashboard(Integer noOfMonthsInPastForBottomDashboard) {
        this.noOfMonthsInPastForBottomDashboard = noOfMonthsInPastForBottomDashboard;
    }

    public Integer getNoOfMonthsInFutureForBottomDashboard() {
        return noOfMonthsInFutureForBottomDashboard;
    }

    public void setNoOfMonthsInFutureForBottomDashboard(Integer noOfMonthsInFutureForBottomDashboard) {
        this.noOfMonthsInFutureForBottomDashboard = noOfMonthsInFutureForBottomDashboard;
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
