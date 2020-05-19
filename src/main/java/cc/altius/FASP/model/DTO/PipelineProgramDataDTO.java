/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.Date;

/**
 *
 * @author altius
 */
public class PipelineProgramDataDTO {

    private String programName;
    private String dataDirectory;
    private String language;
    private double defaultLeadTimePlan;
    private double defaultLeadTimeOrder;
    private double defaultLeadTimeShip;
    private double defaultShipCost;
    private String programContact;
    private String telephone;
    private String fax;
    private String email;
    private String countryCode;
    private String countryName;
    private boolean isCurrent;
    private String note;
    private String programCode;
    private boolean isActive;
    private String startSize;
    private boolean isDefault;
    private Date archiveDate;
    private int archiveYear;
    private boolean archiveInclude;
    private int guestUserId;
    private int countryId;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    
    
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getDefaultLeadTimePlan() {
        return defaultLeadTimePlan;
    }

    public void setDefaultLeadTimePlan(double defaultLeadTimePlan) {
        this.defaultLeadTimePlan = defaultLeadTimePlan;
    }

    public double getDefaultLeadTimeOrder() {
        return defaultLeadTimeOrder;
    }

    public void setDefaultLeadTimeOrder(double defaultLeadTimeOrder) {
        this.defaultLeadTimeOrder = defaultLeadTimeOrder;
    }

    public double getDefaultLeadTimeShip() {
        return defaultLeadTimeShip;
    }

    public void setDefaultLeadTimeShip(double defaultLeadTimeShip) {
        this.defaultLeadTimeShip = defaultLeadTimeShip;
    }

    public double getDefaultShipCost() {
        return defaultShipCost;
    }

    public void setDefaultShipCost(double defaultShipCost) {
        this.defaultShipCost = defaultShipCost;
    }

    public String getProgramContact() {
        return programContact;
    }

    public void setProgramContact(String programContact) {
        this.programContact = programContact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStartSize() {
        return startSize;
    }

    public void setStartSize(String startSize) {
        this.startSize = startSize;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    public int getArchiveYear() {
        return archiveYear;
    }

    public void setArchiveYear(int archiveYear) {
        this.archiveYear = archiveYear;
    }

    public boolean isArchiveInclude() {
        return archiveInclude;
    }

    public void setArchiveInclude(boolean archiveInclude) {
        this.archiveInclude = archiveInclude;
    }

    public int getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(int guestUserId) {
        this.guestUserId = guestUserId;
    }

}
