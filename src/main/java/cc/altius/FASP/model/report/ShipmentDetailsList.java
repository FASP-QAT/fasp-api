/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentDetailsList implements Serializable {

    private int shipmentId;
    private SimpleObject planningUnit;
    private SimpleObject forecastingUnit;
    private int multiplier;
    private SimpleCodeObject procurementAgent;
    private SimpleCodeObject fundingSource;
    private SimpleObject shipmentStatus;
    private long shipmentQty;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expectedDeliveryDate;
    private double productCost;
    private double freightCost;
    private double totalCost;
    private String notes;

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
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

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
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

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
