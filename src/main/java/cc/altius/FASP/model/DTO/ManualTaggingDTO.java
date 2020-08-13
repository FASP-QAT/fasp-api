/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class ManualTaggingDTO implements Serializable {

    private Date expectedDeliveryDate;
    private String shipmentStatusDesc;
    private String procurementAgentName;
    private String budgetDesc;
    private int shipmentQty;
    private double productCost;
    private int shipmentId;
    private int shipmentTransId;

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getShipmentStatusDesc() {
        return shipmentStatusDesc;
    }

    public void setShipmentStatusDesc(String shipmentStatusDesc) {
        this.shipmentStatusDesc = shipmentStatusDesc;
    }

    public String getProcurementAgentName() {
        return procurementAgentName;
    }

    public void setProcurementAgentName(String procurementAgentName) {
        this.procurementAgentName = procurementAgentName;
    }

    public String getBudgetDesc() {
        return budgetDesc;
    }

    public void setBudgetDesc(String budgetDesc) {
        this.budgetDesc = budgetDesc;
    }

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getShipmentTransId() {
        return shipmentTransId;
    }

    public void setShipmentTransId(int shipmentTransId) {
        this.shipmentTransId = shipmentTransId;
    }

    @Override
    public String toString() {
        return "ManualTaggingDTO{" + "expectedDeliveryDate=" + expectedDeliveryDate + ", shipmentStatusDesc=" + shipmentStatusDesc + ", procurementAgentName=" + procurementAgentName + ", budgetDesc=" + budgetDesc + ", shipmentQty=" + shipmentQty + ", productCost=" + productCost + ", shipmentId=" + shipmentId + ", shipmentTransId=" + shipmentTransId + '}';
    }

}
