/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

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
    private int primeLineNo;
    private String roNo;
    private int roPrimeLineNo;
    private SimpleObject planningUnit;
    private SimpleObject erpPlanningUnit;
    private String skuCode;
    private int programId;
    private String[] planningUnitIdList;
    private String[] productCategoryIdList;
    private int countryId;
    private String notes;
    private int linkingType;
    private double conversionFactor;
    private int parentShipmentId;
    private int erpOrderId;
    private String erpStatus;
    private Date receivedOn;

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

    public String[] getPlanningUnitIdList() {
        return planningUnitIdList;
    }

    public void setPlanningUnitIdList(String[] planningUnitIdList) {
        this.planningUnitIdList = planningUnitIdList;
    }

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIdList == null || this.planningUnitIdList.length == 0) {
            return "";
        } else {
            return String.join(",", this.planningUnitIdList);
        }
    }

    public String[] getProductCategoryIdList() {
        return productCategoryIdList;
    }

    public void setProductCategoryIdList(String[] productCategoryIdList) {
        this.productCategoryIdList = productCategoryIdList;
    }

    public String getProductCategoryIdsString() {
        if (this.productCategoryIdList == null || this.productCategoryIdList.length == 0) {
            return "";
        } else {
            return String.join(",", this.productCategoryIdList);
        }
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

    public int getLinkingType() {
        return linkingType;
    }

    public void setLinkingType(int linkingType) {
        this.linkingType = linkingType;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public int getPrimeLineNo() {
        return primeLineNo;
    }

    public void setPrimeLineNo(int primeLineNo) {
        this.primeLineNo = primeLineNo;
    }

    public int getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(int roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    public int getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(int parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public int getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(int erpOrderId) {
        this.erpOrderId = erpOrderId;
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

    @Override
    public String toString() {
        return "ManualTaggingDTO{" + "shipmentId=" + shipmentId + ", shipmentTransId=" + shipmentTransId + ", expectedDeliveryDate=" + expectedDeliveryDate + ", shipmentStatus=" + shipmentStatus + ", procurementAgent=" + procurementAgent + ", fundingSource=" + fundingSource + ", budget=" + budget + ", shipmentQty=" + shipmentQty + ", productCost=" + productCost + ", orderNo=" + orderNo + ", primeLineNo=" + primeLineNo + ", roNo=" + roNo + ", roPrimeLineNo=" + roPrimeLineNo + ", planningUnit=" + planningUnit + ", erpPlanningUnit=" + erpPlanningUnit + ", skuCode=" + skuCode + ", programId=" + programId + ", planningUnitIdList=" + planningUnitIdList + ", productCategoryIdList=" + productCategoryIdList + ", countryId=" + countryId + ", notes=" + notes + ", linkingType=" + linkingType + ", conversionFactor=" + conversionFactor + ", parentShipmentId=" + parentShipmentId + ", erpOrderId=" + erpOrderId + ", erpStatus=" + erpStatus + '}';
    }

}
