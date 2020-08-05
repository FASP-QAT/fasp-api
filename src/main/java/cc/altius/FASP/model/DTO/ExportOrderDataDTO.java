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
    private int shipmentQty;
    private Date expectedDeliveryDate;

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

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    @Override
    public String toString() {
        return "ExportOrderDataDTO{" + "shipmentId=" + shipmentId + ", skuCode=" + skuCode + ", programId=" + programId + ", procurementAgentCode=" + procurementAgentCode + ", shipmentQty=" + shipmentQty + ", expectedDeliveryDate=" + expectedDeliveryDate + '}';
    }

}
