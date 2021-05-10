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
public class ExportShipmentLinkingDTO {

    private int programId;
    private int manualTaggingId;
    private String programName;
    private int parentShipmentId;
    private String roNo;
    private String orderNo;
    private int primeLineNo;
    private boolean active;
    private Date lastModifiedDate;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getManualTaggingId() {
        return manualTaggingId;
    }

    public void setManualTaggingId(int manualTaggingId) {
        this.manualTaggingId = manualTaggingId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(int parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getPrimeLineNo() {
        return primeLineNo;
    }

    public void setPrimeLineNo(int primeLineNo) {
        this.primeLineNo = primeLineNo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return "ExportShipmentLinkingDTO{" + "programId=" + programId + ", manualTaggingId=" + manualTaggingId + ", programName=" + programName + ", parentShipmentId=" + parentShipmentId + ", roNo=" + roNo + ", orderNo=" + orderNo + ", primeLineNo=" + primeLineNo + ", active=" + active + ", lastModifiedDate=" + lastModifiedDate + '}';
    }

}
