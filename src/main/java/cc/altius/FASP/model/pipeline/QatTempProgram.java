/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cc.altius.FASP.model.pipeline;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import cc.altius.FASP.model.Version;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.BasicUser;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.RealmCountry;
/**
 *
 * @author ekta
 */
public class QatTempProgram extends BaseModel implements Serializable {
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
    private int monthsInPastForAmc;
    private int monthsInFutureForAmc;
    private double shelfLife;

    private List<Region> regionList;
    String[] regionArray;
    private Version currentVersion;
    private List<Version> versionList;

    public QatTempProgram() {
        this.regionList = new LinkedList<>();
    }

    public QatTempProgram(int programId, String programCode, Label label) {
        this.programId = programId;
        this.programCode = programCode;
        this.label = label;
        this.regionList = new LinkedList<>();
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
        this.regionArray = regionArray;
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

    public double getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(double shelfLife) {
        this.shelfLife = shelfLife;
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
        final QatTempProgram other = (QatTempProgram) obj;
        if (this.programId != other.programId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "QatTempProgram{" + "programId=" + programId + ", label=" + label + '}';
    }


}
