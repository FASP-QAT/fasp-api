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
public class ExportOrderDataDTO {

    private int shipmentId;
    private String skuCode;
    private int programId;
    private String procurementAgentCode;
    private long shipmentQty;
    private Date expectedDeliveryDate;
    private int tracerCategoryId;
    private String tracerCategoryDesc;
    private boolean active;
    private Date lastModifiedDate;

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getProcurementAgentCode() {
        return procurementAgentCode;
    }

    public void setProcurementAgentCode(String procurementAgentCode) {
        this.procurementAgentCode = procurementAgentCode;
    }

    public long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(long shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public int getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(int tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

    public String getTracerCategoryDesc() {
        return tracerCategoryDesc;
    }

    public void setTracerCategoryDesc(String tracerCategoryDesc) {
        this.tracerCategoryDesc = tracerCategoryDesc;
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
        return "ExportOrderDataDTO{" + "shipmentId=" + shipmentId + ", skuCode=" + skuCode + ", programId=" + programId + ", procurementAgentCode=" + procurementAgentCode + ", shipmentQty=" + shipmentQty + ", expectedDeliveryDate=" + expectedDeliveryDate + '}';
    }

}
