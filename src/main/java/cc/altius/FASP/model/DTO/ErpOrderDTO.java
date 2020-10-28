/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Label;
import java.util.Date;

/**
 *
 * @author altius
 */
public class ErpOrderDTO {

    private int erpOrderId;
    private int shipmentId;
    private int programId;
    private int versionId;
    private int planningUnitId;
    private Integer procurementUnitId;
    private Integer supplierId;
    private Integer manualTaggingId;
    private Boolean shipmentActive;
    private Boolean shipmentErpFlag;
    private Integer shipmentParentShipmentId;
    private Integer shipmentShipmentId;
    private int quantity;
    private String orderNo;
    private int primeLineNo;
    private String roNo;
    private String roPrimeLineNo;
    private String orderType;
    private String planningUnitSkuCode;
    private String procurementUnitSkuCode;
    private Date currentEstimatedDeliveryDate;
    private String supplierName;
    private double price;
    private double shippingCost;
    private String status;
    private String reason;
    private String recipentCountry;
    private Label planningUnitLabel;
    private String notes;

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

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Integer getProcurementUnitId() {
        return procurementUnitId;
    }

    public void setProcurementUnitId(Integer procurementUnitId) {
        this.procurementUnitId = procurementUnitId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getManualTaggingId() {
        return manualTaggingId;
    }

    public void setManualTaggingId(Integer manualTaggingId) {
        this.manualTaggingId = manualTaggingId;
    }

    public Boolean getShipmentActive() {
        return shipmentActive;
    }

    public void setShipmentActive(Boolean shipmentActive) {
        this.shipmentActive = shipmentActive;
    }

    public Boolean getShipmentErpFlag() {
        return shipmentErpFlag;
    }

    public void setShipmentErpFlag(Boolean shipmentErpFlag) {
        this.shipmentErpFlag = shipmentErpFlag;
    }

    public boolean isShipmentErpFlag() {
        if (this.shipmentErpFlag == null) {
            return false;
        } else {
            return this.shipmentErpFlag;
        }
    }

    public Integer getShipmentParentShipmentId() {
        return shipmentParentShipmentId;
    }

    public void setShipmentParentShipmentId(Integer shipmentParentShipmentId) {
        this.shipmentParentShipmentId = shipmentParentShipmentId;
    }

    public Integer getShipmentShipmentId() {
        return shipmentShipmentId;
    }

    public void setShipmentShipmentId(Integer shipmentShipmentId) {
        this.shipmentShipmentId = shipmentShipmentId;
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

    public Date getCurrentEstimatedDeliveryDate() {
        return currentEstimatedDeliveryDate;
    }

    public void setCurrentEstimatedDeliveryDate(Date currentEstimatedDeliveryDate) {
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

    public boolean isManual() {
        return this.manualTaggingId != null;
    }

    public String toString() {
        return "ErpOrderDTO{" + "erpOrderId=" + erpOrderId + ", shipmentId=" + shipmentId + ", quantity=" + quantity + ", orderNo=" + orderNo + ", primeLineNo=" + primeLineNo + ", roNo=" + roNo + ", roPrimeLineNo=" + roPrimeLineNo + ", orderType=" + orderType + ", planningUnitSkuCode=" + planningUnitSkuCode + ", procurementUnitSkuCode=" + procurementUnitSkuCode + ", currentEstimatedDeliveryDate=" + currentEstimatedDeliveryDate + ", supplierName=" + supplierName + ", price=" + price + ", shippingCost=" + shippingCost + ", status=" + status + ", reason=" + reason + ", recipentCountry=" + recipentCountry + ", planningUnitLabel=" + planningUnitLabel + ", notes=" + notes + '}';
    }

}
