/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class PplPrograminfo implements Serializable {

    private String programname;
    private String datadirectory;
    private String language;
    private Double defaultleadtimeplan;
    private Double defaultleadtimeorder;
    private Double defaultleadtimeship;
    private Double defaultshipcost;
    private String programcontact;
    private String telephone;
    private String fax;
    private String email;
    private String countrycode;
    private String countryname;
    private Boolean iscurrent;
    private String note;
    private String programcode;
    private Boolean isactive;
    private String startsize;
    private Boolean isdefault;
    private Date archivedate;
    private Integer archiveyear;
    private Boolean archiveinclude;
    private int pipelineId;

    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname;
    }

    public String getDatadirectory() {
        return datadirectory;
    }

    public void setDatadirectory(String datadirectory) {
        this.datadirectory = datadirectory;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getDefaultleadtimeplan() {
        return defaultleadtimeplan;
    }

    public void setDefaultleadtimeplan(Double defaultleadtimeplan) {
        this.defaultleadtimeplan = defaultleadtimeplan;
    }

    public Double getDefaultleadtimeorder() {
        return defaultleadtimeorder;
    }

    public void setDefaultleadtimeorder(Double defaultleadtimeorder) {
        this.defaultleadtimeorder = defaultleadtimeorder;
    }

    public Double getDefaultleadtimeship() {
        return defaultleadtimeship;
    }

    public void setDefaultleadtimeship(Double defaultleadtimeship) {
        this.defaultleadtimeship = defaultleadtimeship;
    }

    public Double getDefaultshipcost() {
        return defaultshipcost;
    }

    public void setDefaultshipcost(Double defaultshipcost) {
        this.defaultshipcost = defaultshipcost;
    }

    public String getProgramcontact() {
        return programcontact;
    }

    public void setProgramcontact(String programcontact) {
        this.programcontact = programcontact;
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

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public Boolean getIscurrent() {
        return iscurrent;
    }

    public void setIscurrent(Boolean iscurrent) {
        this.iscurrent = iscurrent;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProgramcode() {
        return programcode;
    }

    public void setProgramcode(String programcode) {
        this.programcode = programcode;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public String getStartsize() {
        return startsize;
    }

    public void setStartsize(String startsize) {
        this.startsize = startsize;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Date getArchivedate() {
        return archivedate;
    }

    public void setArchivedate(Date archivedate) {
        this.archivedate = archivedate;
    }

    public Integer getArchiveyear() {
        return archiveyear;
    }

    public void setArchiveyear(Integer archiveyear) {
        this.archiveyear = archiveyear;
    }

    public Boolean getArchiveinclude() {
        return archiveinclude;
    }

    public void setArchiveinclude(Boolean archiveinclude) {
        this.archiveinclude = archiveinclude;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

}
