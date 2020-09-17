/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class Shipment extends BaseModel implements Serializable {

    private int shipmentId;
    private Integer parentShipmentId;
    private SimplePlanningUnitObject planningUnit;
    private String expectedDeliveryDate;
    private int suggestedQty;
    private SimpleProcurementAgentObject procurementAgent;
    private SimpleBudgetObject budget;
    private SimpleCodeObject fundingSource;
    private SimpleObject procurementUnit;
    private SimpleObject supplier;
    private int shipmentQty;
    private double rate;
    private Currency currency;
    private double productCost;
    private String shipmentMode;
    private double freightCost;
    private String plannedDate;
    private String submittedDate;
    private String approvedDate;
    private String shippedDate;
    private String arrivedDate;
    private String receivedDate;
    private SimpleObject shipmentStatus;
    private String notes;
    private SimpleObject dataSource;
    private boolean accountFlag;
    private boolean erpFlag;
    private String orderNo;
    private String primeLineNo;
    private boolean emergencyOrder;
    private int versionId;
    private Date lastModifiedDate;
    private List<ShipmentBatchInfo> batchInfoList;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Shipment() {
        this.batchInfoList = new LinkedList<>();
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Integer getParentShipmentId() {
        return parentShipmentId;
    }

    public void setParentShipmentId(Integer parentShipmentId) {
        this.parentShipmentId = parentShipmentId;
    }

    public SimplePlanningUnitObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public double getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(int suggestedQty) {
        this.suggestedQty = suggestedQty;
    }

    public SimpleProcurementAgentObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleProcurementAgentObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleObject getProcurementUnit() {
        return procurementUnit;
    }

    public void setProcurementUnit(SimpleObject procurementUnit) {
        this.procurementUnit = procurementUnit;
    }

    public SimpleBudgetObject getBudget() {
        return budget;
    }

    public void setBudget(SimpleBudgetObject budget) {
        this.budget = budget;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleObject getSupplier() {
        return supplier;
    }

    public void setSupplier(SimpleObject supplier) {
        this.supplier = supplier;
    }

    public double getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public String getPlannedDate() {
        return plannedDate;
    }

    public void setPlannedDate(String plannedDate) {
        this.plannedDate = plannedDate;
    }

    public String getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(String shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(String arrivedDate) {
        this.arrivedDate = arrivedDate;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleObject getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleObject dataSource) {
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

    public boolean isEmergencyOrder() {
        return emergencyOrder;
    }

    public void setEmergencyOrder(boolean emergencyOrder) {
        this.emergencyOrder = emergencyOrder;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public List<ShipmentBatchInfo> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<ShipmentBatchInfo> batchInfoList) {
        this.batchInfoList = batchInfoList;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.shipmentId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Shipment other = (Shipment) obj;
        if (this.shipmentId != other.shipmentId) {
            return false;
        }
        return true;

    }

}
