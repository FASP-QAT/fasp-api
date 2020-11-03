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
public class ExportProgramDataDTO {

    private int programId;
    private String programCode;
    private String programName;
    private String countryCode2;
    private String technicalArea;
    private boolean programActive;
    private Date lastModifiedDate;

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

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCountryCode2() {
        return countryCode2;
    }

    public void setCountryCode2(String countryCode2) {
        this.countryCode2 = countryCode2;
    }

    public String getTechnicalArea() {
        return technicalArea;
    }

    public void setTechnicalArea(String technicalArea) {
        this.technicalArea = technicalArea;
    }

    public boolean isProgramActive() {
        return programActive;
    }

    public void setProgramActive(boolean programActive) {
        this.programActive = programActive;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "ExportProgramDataDTO{" + "programId=" + programId + ", programCode=" + programCode + ", programName=" + programName + ", countryCode2=" + countryCode2 + ", technicalArea=" + technicalArea + ", programActive=" + programActive + '}';
    }

}
