/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author ekta
 */
public class QatTempShipment {
     private int shipmentId;
    private String planningUnit;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expectedDeliveryDate;
    private double suggestedQty;
    private String procurementAgent;
    private String procurementUnit;
    private String supplier;
    private double quantity;
    private double rate;
    private double productCost;
    private String shipmentMode;
    private double freightCost;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date plannedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date submittedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date approvedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date shippedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date arrivedDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date receivedDate;
    private String shipmentStatus;
    private String notes;
    private String dataSource;
    private boolean accountFlag;
    private boolean erpFlag;
    private int versionId;
    private String fundingSource;
     private boolean active;

    public QatTempShipment() {
    }

    
    public QatTempShipment(int shipmentId, String planningUnit, Date expectedDeliveryDate, double suggestedQty, String procurementAgent, String procurementUnit, String supplier, double quantity, double rate, double productCost, String shipmentMode, double freightCost, Date plannedDate, Date submittedDate, Date approvedDate, Date shippedDate, Date arrivedDate, Date receivedDate, String shipmentStatus, String notes, String dataSource, boolean accountFlag, boolean erpFlag, int versionId, String fundingSource) {
        this.shipmentId = shipmentId;
        this.planningUnit = planningUnit;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.suggestedQty = suggestedQty;
        this.procurementAgent = procurementAgent;
        this.procurementUnit = procurementUnit;
        this.supplier = supplier;
        this.quantity = quantity;
        this.rate = rate;
        this.productCost = productCost;
        this.shipmentMode = shipmentMode;
        this.freightCost = freightCost;
        this.plannedDate = plannedDate;
        this.submittedDate = submittedDate;
        this.approvedDate = approvedDate;
        this.shippedDate = shippedDate;
        this.arrivedDate = arrivedDate;
        this.receivedDate = receivedDate;
        this.shipmentStatus = shipmentStatus;
        this.notes = notes;
        this.dataSource = dataSource;
        this.accountFlag = accountFlag;
        this.erpFlag = erpFlag;
        this.versionId = versionId;
        this.fundingSource = fundingSource;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(String planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public double getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(double suggestedQty) {
        this.suggestedQty = suggestedQty;
    }

    public String getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(String procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public String getProcurementUnit() {
        return procurementUnit;
    }

    public void setProcurementUnit(String procurementUnit) {
        this.procurementUnit = procurementUnit;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public String getShipmentMode() {
        return shipmentMode;
    }

    public void setShipmentMode(String shipmentMode) {
        this.shipmentMode = shipmentMode;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }

    public Date getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(Date plannedDate) {
        this.plannedDate = plannedDate;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(Date arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(String shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(boolean accountFlag) {
        this.accountFlag = accountFlag;
    }

    public boolean isErpFlag() {
        return erpFlag;
    }

    public void setErpFlag(boolean erpFlag) {
        this.erpFlag = erpFlag;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
