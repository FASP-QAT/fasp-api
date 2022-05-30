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
public class LinkedShipments extends BaseModel implements Serializable {

    @JsonView({Views.InternalView.class})
    private int erpShipmentLinkingId;
    @JsonView({Views.InternalView.class})
    private int versionId;
    @JsonView({Views.InternalView.class})
    private SimpleCodeObject procurementAgent;
    @JsonView({Views.InternalView.class})
    private int parentShipmentId;
    @JsonView({Views.InternalView.class})
    private int tempParentShipmentId;
    @JsonView({Views.InternalView.class})
    private int childShipmentId;
    @JsonView({Views.InternalView.class})
    private int tempChildShipmentId;
    @JsonView({Views.InternalView.class})
    private SimpleObject erpPlanningUnit;
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
    private String erpShipmentStatus;
    @JsonView({Views.InternalView.class})
    private double conversionFactor;

    public int getErpShipmentLinkingId() {
        return erpShipmentLinkingId;
    }

    public void setErpShipmentLinkingId(int erpShipmentLinkingId) {
        this.erpShipmentLinkingId = erpShipmentLinkingId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public int getTempParentShipmentId() {
        return tempParentShipmentId;
    }

    public void setTempParentShipmentId(int tempParentShipmentId) {
        this.tempParentShipmentId = tempParentShipmentId;
    }

    public int getChildShipmentId() {
        return childShipmentId;
    }

    public void setChildShipmentId(int childShipmentId) {
        this.childShipmentId = childShipmentId;
    }

    public int getTempChildShipmentId() {
        return tempChildShipmentId;
    }

    public void setTempChildShipmentId(int tempChildShipmentId) {
        this.tempChildShipmentId = tempChildShipmentId;
    }

    public SimpleObject getErpPlanningUnit() {
        return erpPlanningUnit;
    }

    public void setErpPlanningUnit(SimpleObject erpPlanningUnit) {
        this.erpPlanningUnit = erpPlanningUnit;
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

    public String getErpShipmentStatus() {
        return erpShipmentStatus;
    }

    public void setErpShipmentStatus(String erpShipmentStatus) {
        this.erpShipmentStatus = erpShipmentStatus;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

}
