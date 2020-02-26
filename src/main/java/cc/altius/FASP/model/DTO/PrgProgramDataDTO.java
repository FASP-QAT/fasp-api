/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author altius
 */
public class PrgProgramDataDTO implements Serializable {

    private int programId;
    private PrgRealmCountryDTO realmCountry;
    private PrgOrganisationDTO organisation;
    private PrgHealthAreaDTO healthArea;
    private PrgLabelDTO label;
    private User programManagerUser;
    private String programNotes;
    private double airFreightPerc;
    private double seaFreightPerc;
    private int planToDraftLeadTime;
    private int draftToSubmittedLeadTime;
    private int submittedToApprovedLeadTime;
    private int approvedToShippedLeadTime;
    private int deliveredToReceivedLeadTime;
    private int monthsInPastForAMC;
    private int monthsInFutureForAMC;
    private User lastModifiedBy;
    private Date lastModifiedDate;
    private List<PrgProgramProductDTO> programProductList;
    private List<PrgRegionDTO> regionList;
    private List<PrgBudgetDTO> budgetData;
    private int programVersion;

    public int getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(int programVersion) {
        this.programVersion = programVersion;
    }

    public List<PrgBudgetDTO> getBudgetData() {
        return budgetData;
    }

    public void setBudgetData(List<PrgBudgetDTO> budgetData) {
        this.budgetData = budgetData;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public PrgRealmCountryDTO getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(PrgRealmCountryDTO realmCountry) {
        this.realmCountry = realmCountry;
    }

    public PrgOrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(PrgOrganisationDTO organisation) {
        this.organisation = organisation;
    }

    public PrgHealthAreaDTO getHealthArea() {
        return healthArea;
    }

    public void setHealthArea(PrgHealthAreaDTO healthArea) {
        this.healthArea = healthArea;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public User getProgramManagerUser() {
        return programManagerUser;
    }

    public void setProgramManagerUser(User programManagerUser) {
        this.programManagerUser = programManagerUser;
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

    public int getPlanToDraftLeadTime() {
        return planToDraftLeadTime;
    }

    public void setPlanToDraftLeadTime(int planToDraftLeadTime) {
        this.planToDraftLeadTime = planToDraftLeadTime;
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

    public int getMonthsInPastForAMC() {
        return monthsInPastForAMC;
    }

    public void setMonthsInPastForAMC(int monthsInPastForAMC) {
        this.monthsInPastForAMC = monthsInPastForAMC;
    }

    public int getMonthsInFutureForAMC() {
        return monthsInFutureForAMC;
    }

    public void setMonthsInFutureForAMC(int monthsInFutureForAMC) {
        this.monthsInFutureForAMC = monthsInFutureForAMC;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<PrgProgramProductDTO> getProgramProductList() {
        return programProductList;
    }

    public void setProgramProductList(List<PrgProgramProductDTO> programProductList) {
        this.programProductList = programProductList;
    }

    public List<PrgRegionDTO> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<PrgRegionDTO> regionList) {
        this.regionList = regionList;
    }

}
