/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentLinking extends BaseModel implements Serializable {

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
    // Only for reporting purposes, not to be stored
    @JsonView({Views.InternalView.class})
    private Integer qatPlanningUnitId;
    @JsonView({Views.InternalView.class})
    private int versionId;

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

}
