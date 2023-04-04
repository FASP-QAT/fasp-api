/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class Shipment implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int shipmentId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Integer parentShipmentId;
    @JsonView({Views.InternalView.class})
    private Integer parentLinkedShipmentId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimplePlanningUnitProductCategoryObject planningUnit;
    @JsonView({Views.InternalView.class})
    private SimpleObjectWithMultiplier realmCountryPlanningUnit;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String expectedDeliveryDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long suggestedQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleProcurementAgentObject procurementAgent;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleBudgetObject budget;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleCodeObject fundingSource;
    @JsonView(Views.InternalView.class)
    private SimpleObject procurementUnit;
    @JsonView(Views.InternalView.class)
    private SimpleObject supplier;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long shipmentQty;
    @JsonView({Views.InternalView.class})
    private long shipmentRcpuQty;
    @JsonView({Views.GfpVanView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double rate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Currency currency;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double productCost;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String shipmentMode;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double freightCost;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String plannedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String submittedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String approvedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String shippedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String arrivedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String receivedDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject shipmentStatus;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject dataSource;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean accountFlag;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean erpFlag;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String orderNo;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String primeLineNo;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean emergencyOrder;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean localProcurement;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<ShipmentBatchInfo> batchInfoList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean active;
    @JsonView(Views.InternalView.class)
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date createdDate;
    @JsonView(Views.InternalView.class)
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @JsonView(Views.InternalView.class)
    private Date lastModifiedDate;
    @JsonView({Views.InternalView.class})
    private Integer tempParentShipmentId;
    @JsonView({Views.InternalView.class})
    private Integer tempParentLinkedShipmentId;
    @JsonView({Views.InternalView.class})
    private Integer tempShipmentId;

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

    public Integer getParentLinkedShipmentId() {
        return parentLinkedShipmentId;
    }

    public void setParentLinkedShipmentId(Integer parentLinkedShipmentId) {
        this.parentLinkedShipmentId = parentLinkedShipmentId;
    }

    public SimplePlanningUnitProductCategoryObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitProductCategoryObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public long getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(long suggestedQty) {
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

    public long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(long shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
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

    public boolean isLocalProcurement() {
        return localProcurement;
    }

    public void setLocalProcurement(boolean localProcurement) {
        this.localProcurement = localProcurement;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getTempParentShipmentId() {
        return tempParentShipmentId;
    }

    public void setTempParentShipmentId(Integer tempParentShipmentId) {
        this.tempParentShipmentId = tempParentShipmentId;
    }

    public Integer getTempParentLinkedShipmentId() {
        return tempParentLinkedShipmentId;
    }

    public void setTempParentLinkedShipmentId(Integer tempParentLinkedShipmentId) {
        this.tempParentLinkedShipmentId = tempParentLinkedShipmentId;
    }

    public Integer getTempShipmentId() {
        return tempShipmentId;
    }

    public void setTempShipmentId(Integer tempShipmentId) {
        this.tempShipmentId = tempShipmentId;
    }

    public SimpleObjectWithMultiplier getRealmCountryPlanningUnit() {
        return realmCountryPlanningUnit;
    }

    public void setRealmCountryPlanningUnit(SimpleObjectWithMultiplier realmCountryPlanningUnit) {
        this.realmCountryPlanningUnit = realmCountryPlanningUnit;
    }

    public long getShipmentRcpuQty() {
        return shipmentRcpuQty;
    }

    public void setShipmentRcpuQty(long shipmentRcpuQty) {
        this.shipmentRcpuQty = shipmentRcpuQty;
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