/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentLinkingOutput implements Serializable {

    private String roNo;
    private String roPrimeLineNo;
    private String orderNo;
    private String primeLineNo;
    private String erpShipmentStatus;
    private int erpQty;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expectedDeliveryDate;
    private String knShipmentNo;
    private String batchNo;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expiryDate;
    private SimpleObject erpPlanningUnit;
    private Double price;
    private Double shippingCost;
    private SimpleObject qatEquivalentShipmentStatus;

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

    public String getErpShipmentStatus() {
        return erpShipmentStatus;
    }

    public void setErpShipmentStatus(String erpShipmentStatus) {
        this.erpShipmentStatus = erpShipmentStatus;
    }

    public int getErpQty() {
        return erpQty;
    }

    public void setErpQty(int erpQty) {
        this.erpQty = erpQty;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getKnShipmentNo() {
        return knShipmentNo;
    }

    public void setKnShipmentNo(String knShipmentNo) {
        this.knShipmentNo = knShipmentNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public SimpleObject getErpPlanningUnit() {
        return erpPlanningUnit;
    }

    public void setErpPlanningUnit(SimpleObject erpPlanningUnit) {
        this.erpPlanningUnit = erpPlanningUnit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public SimpleObject getQatEquivalentShipmentStatus() {
        return qatEquivalentShipmentStatus;
    }

    public void setQatEquivalentShipmentStatus(SimpleObject qatEquivalentShipmentStatus) {
        this.qatEquivalentShipmentStatus = qatEquivalentShipmentStatus;
    }
}
