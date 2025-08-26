/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Inventory implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int inventoryId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String inventoryDate;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject region;
    @JsonView({Views.InternalView.class})
    private SimpleObject realmCountryPlanningUnit;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private SimpleObject alternateReportingUnit;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimplePlanningUnitProductCategoryObject planningUnit;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double multiplier;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double actualQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double adjustmentQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double expectedBal;
    @JsonIgnore
    private SimpleCodeObject unit;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject dataSource;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<InventoryBatchInfo> batchInfoList;
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
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private boolean active;
    @JsonView(Views.InternalView.class)
    private boolean addNewBatch;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Inventory() {
        this.batchInfoList = new LinkedList<>();
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public SimpleObject getRealmCountryPlanningUnit() {
        return realmCountryPlanningUnit;
    }

    public SimpleObject getAlternateReportingUnit() {
        return alternateReportingUnit;
    }

    public void setRealmCountryPlanningUnit(SimpleObject realmCountryPlanningUnit) {
        this.realmCountryPlanningUnit = realmCountryPlanningUnit;
        this.alternateReportingUnit = realmCountryPlanningUnit;
    }

    public SimplePlanningUnitProductCategoryObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitProductCategoryObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Double adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public double getExpectedBal() {
        return expectedBal;
    }

    public void setExpectedBal(double expectedBal) {
        this.expectedBal = expectedBal;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public SimpleObject getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleObject dataSource) {
        this.dataSource = dataSource;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public List<InventoryBatchInfo> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<InventoryBatchInfo> batchInfoList) {
        this.batchInfoList = batchInfoList;
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

    public boolean isAddNewBatch() {
        return addNewBatch;
    }

    public void setAddNewBatch(boolean addNewBatch) {
        this.addNewBatch = addNewBatch;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.inventoryId;
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
        final Inventory other = (Inventory) obj;
        if (this.inventoryId != other.inventoryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inventory{" + "inventoryId=" + inventoryId + ", inventoryDate=" + inventoryDate + ", region=" + region.getId() + ", realmCountryPlanningUnit=" + realmCountryPlanningUnit.getId() + ", actualQty=" + actualQty + ", adjustmentQty=" + adjustmentQty + ", dataSource=" + dataSource.getId() + ", versionId=" + versionId + '}';
    }

}
