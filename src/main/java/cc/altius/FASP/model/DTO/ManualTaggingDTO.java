/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class ManualTaggingDTO implements Serializable {

    private int shipmentId;
    private int shipmentTransId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expectedDeliveryDate;
    private SimpleObject shipmentStatus;
    private SimpleCodeObject procurementAgent;
    private SimpleCodeObject fundingSource;
    private SimpleCodeObject budget;
    private int shipmentQty;
    private double productCost;
    private String orderNo;
    private SimpleObject planningUnit;

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

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleCodeObject getBudget() {
        return budget;
    }

    public void setBudget(SimpleCodeObject budget) {
        this.budget = budget;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    @Override
    public String toString() {
        return "ManualTaggingDTO{" + "shipmentId=" + shipmentId + ", shipmentTransId=" + shipmentTransId + ", expectedDeliveryDate=" + expectedDeliveryDate + ", shipmentStatus=" + shipmentStatus + ", procurementAgent=" + procurementAgent + ", fundingSource=" + fundingSource + ", budget=" + budget + ", shipmentQty=" + shipmentQty + ", productCost=" + productCost + ", orderNo=" + orderNo + ", planningUnit=" + planningUnit + '}';
    }

}
