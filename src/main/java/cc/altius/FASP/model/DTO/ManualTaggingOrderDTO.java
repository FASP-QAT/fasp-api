/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author altius
 */
public class ManualTaggingOrderDTO {

    private int erpOrderId;
    private int shipmentId;
    private int quantity;
    private String orderNo;
    private int primeLineNo;
    private String roNo;
    private String roPrimeLineNo;
    private String orderType;
    private String planningUnitId;
    private String planningUnitSkuCode;
    private String procurementUnitSkuCode;
    private String currentEstimatedDeliveryDate;
    private String supplierName;
    private double price;
    private double shippingCost;
    private String status;
    private String reason;
    private String recipentCountry;
    private Label planningUnitLabel;
    private String notes;
    private double conversionFactor;
    private int programId;
    private boolean active;
    private int parentShipmentId;
    private int fundingSourceId;
    private int budgetId;

    public int getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(int erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(String roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(String planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String getPlanningUnitSkuCode() {
        return planningUnitSkuCode;
    }

    public void setPlanningUnitSkuCode(String planningUnitSkuCode) {
        this.planningUnitSkuCode = planningUnitSkuCode;
    }

    public String getProcurementUnitSkuCode() {
        return procurementUnitSkuCode;
    }

    public void setProcurementUnitSkuCode(String procurementUnitSkuCode) {
        this.procurementUnitSkuCode = procurementUnitSkuCode;
    }

    public String getCurrentEstimatedDeliveryDate() {
        return currentEstimatedDeliveryDate;
    }

    public void setCurrentEstimatedDeliveryDate(String currentEstimatedDeliveryDate) {
        this.currentEstimatedDeliveryDate = currentEstimatedDeliveryDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRecipentCountry() {
        return recipentCountry;
    }

    public void setRecipentCountry(String recipentCountry) {
        this.recipentCountry = recipentCountry;
    }

    public Label getPlanningUnitLabel() {
        return planningUnitLabel;
    }

    public void setPlanningUnitLabel(Label planningUnitLabel) {
        this.planningUnitLabel = planningUnitLabel;
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

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(int parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public int getFundingSourceId() {
        return fundingSourceId;
    }

    public void setFundingSourceId(int fundingSourceId) {
        this.fundingSourceId = fundingSourceId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    @Override
    public String toString() {
        return "ManualTaggingOrderDTO{" + "erpOrderId=" + erpOrderId + ", shipmentId=" + shipmentId + ", quantity=" + quantity + ", orderNo=" + orderNo + ", primeLineNo=" + primeLineNo + ", roNo=" + roNo + ", roPrimeLineNo=" + roPrimeLineNo + ", orderType=" + orderType + ", planningUnitSkuCode=" + planningUnitSkuCode + ", procurementUnitSkuCode=" + procurementUnitSkuCode + ", currentEstimatedDeliveryDate=" + currentEstimatedDeliveryDate + ", supplierName=" + supplierName + ", price=" + price + ", shippingCost=" + shippingCost + ", status=" + status + ", reason=" + reason + ", recipentCountry=" + recipentCountry + ", planningUnitLabel=" + planningUnitLabel + ", notes=" + notes + ", conversionFactor=" + conversionFactor + ", programId=" + programId + ", active=" + active + ", parentShipmentId=" + parentShipmentId + '}';
    }

}
