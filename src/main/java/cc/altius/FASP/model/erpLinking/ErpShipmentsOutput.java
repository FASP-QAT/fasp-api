/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.erpLinking;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ErpShipmentsOutput {

    private int shipmentId;
    private int parentShipmentId;
    private int shipmentTransId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    Date expectedDeliveryDate;
    private SimpleObject shipmentStatus;
    private SimpleCodeObject procurementAgent;
    private SimpleCodeObject fundingSource;
    private SimpleCodeObject budget;
    private long shipmentQty;
    private double productCost;
    private String orderNo;
    private int primeLineNo;
    private String roNo;
    private int roPrimeLineNo;

    private SimpleObject planningUnit;
    private SimpleObject erpPlanningUnit;
    private String skuCode;
    private int programId;
    private int countryId;
    private String notes;
    private double conversionFactor;

    private String erpStatus;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date receivedOn;

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(int parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
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

    public long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(long shipmentQty) {
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

    public int getPrimeLineNo() {
        return primeLineNo;
    }

    public void setPrimeLineNo(int primeLineNo) {
        this.primeLineNo = primeLineNo;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public int getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(int roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getErpPlanningUnit() {
        return erpPlanningUnit;
    }

    public void setErpPlanningUnit(SimpleObject erpPlanningUnit) {
        this.erpPlanningUnit = erpPlanningUnit;
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

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getErpStatus() {
        return erpStatus;
    }

    public void setErpStatus(String erpStatus) {
        this.erpStatus = erpStatus;
    }

    public Date getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(Date receivedOn) {
        this.receivedOn = receivedOn;
    }

}
