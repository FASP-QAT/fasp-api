/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentLinking implements Serializable {

    @JsonView({Views.InternalView.class})
    private int shipmentLinkingId;
    @JsonView({Views.InternalView.class})
    private int programId;
    @JsonView({Views.InternalView.class})
    private SimpleCodeObject procurementAgent;
    @JsonView({Views.InternalView.class})
    private int parentShipmentId;
    // Only used in Input not in Output
    @JsonView({Views.InternalView.class})
    private Integer tempParentShipmentId;
    @JsonView({Views.InternalView.class})
    private int childShipmentId;
    // Only used in Input not in Output
    @JsonView({Views.InternalView.class})
    private Integer tempChildShipmentId;
    // Only for reporting purposes, not to be stored 
    @JsonView({Views.InternalView.class})
    private SimpleObject erpPlanningUnit;
    // Only for reporting purposes, not to be stored 
    @JsonView({Views.InternalView.class})
    private String erpShipmentStatus;
    @JsonView({Views.InternalView.class})
    private String roNo;
    @JsonView({Views.InternalView.class})
    private String roPrimeLineNo;
    @JsonView({Views.InternalView.class})
    private String orderNo;
    @JsonView({Views.InternalView.class})
    private String primeLineNo;
    @JsonView({Views.InternalView.class})
    private String knShipmentNo;
    @JsonView({Views.InternalView.class})
    private double conversionFactor;
    @JsonView({Views.InternalView.class})
    private String notes;
    // Only for reporting purposes, not to be stored
    @JsonView({Views.InternalView.class})
    private Integer qatPlanningUnitId;
    @JsonView({Views.InternalView.class})
    private int versionId;
    @JsonView({Views.InternalView.class})
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView({Views.InternalView.class})
    private Date createdDate;
    @JsonView({Views.InternalView.class})
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView({Views.InternalView.class})
    private Date lastModifiedDate;
    @JsonView({Views.InternalView.class})
    private boolean active;

    public int getShipmentLinkingId() {
        return shipmentLinkingId;
    }

    public void setShipmentLinkingId(int shipmentLinkingId) {
        this.shipmentLinkingId = shipmentLinkingId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public int getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(int parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public Integer getTempParentShipmentId() {
        return tempParentShipmentId;
    }

    public void setTempParentShipmentId(Integer tempParentShipmentId) {
        this.tempParentShipmentId = tempParentShipmentId;
    }

    public int getChildShipmentId() {
        return childShipmentId;
    }

    public void setChildShipmentId(int childShipmentId) {
        this.childShipmentId = childShipmentId;
    }

    public Integer getTempChildShipmentId() {
        return tempChildShipmentId;
    }

    public void setTempChildShipmentId(Integer tempChildShipmentId) {
        this.tempChildShipmentId = tempChildShipmentId;
    }

    public SimpleObject getErpPlanningUnit() {
        return erpPlanningUnit;
    }

    public void setErpPlanningUnit(SimpleObject erpPlanningUnit) {
        this.erpPlanningUnit = erpPlanningUnit;
    }

    public String getErpShipmentStatus() {
        return erpShipmentStatus;
    }

    public void setErpShipmentStatus(String erpShipmentStatus) {
        this.erpShipmentStatus = erpShipmentStatus;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(String roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPrimeLineNo() {
        return primeLineNo;
    }

    public void setPrimeLineNo(String primeLineNo) {
        this.primeLineNo = primeLineNo;
    }

    public String getKnShipmentNo() {
        return knShipmentNo;
    }

    public void setKnShipmentNo(String knShipmentNo) {
        this.knShipmentNo = knShipmentNo;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getQatPlanningUnitId() {
        return qatPlanningUnitId;
    }

    public void setQatPlanningUnitId(Integer qatPlanningUnitId) {
        this.qatPlanningUnitId = qatPlanningUnitId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
