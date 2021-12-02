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
public class Consumption implements Serializable {

    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private int consumptionId;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject region;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private SimplePlanningUnitProductCategoryObject planningUnit;
    @JsonView({Views.InternalView.class})
    private SimpleObject realmCountryPlanningUnit;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class})
    private SimpleObject alternateReportingUnit;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private double multiplier;
    @JsonView({Views.GfpVanView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private String consumptionDate;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private boolean actualFlag;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private double consumptionRcpuQty;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private double consumptionQty;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private int dayOfStockOut;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private SimpleObject dataSource;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private List<ConsumptionBatchInfo> batchInfoList;
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
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Consumption() {
        batchInfoList = new LinkedList<>();
    }

    public int getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(int consumptionId) {
        this.consumptionId = consumptionId;
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

    public String getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(String consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public boolean isActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(boolean actualFlag) {
        this.actualFlag = actualFlag;
    }

    public double getConsumptionRcpuQty() {
        return consumptionRcpuQty;
    }

    public void setConsumptionRcpuQty(double consumptionRcpuQty) {
        this.consumptionRcpuQty = consumptionRcpuQty;
    }

    public double getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(double consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public int getDayOfStockOut() {
        return dayOfStockOut;
    }

    public void setDayOfStockOut(int dayOfStockOut) {
        this.dayOfStockOut = dayOfStockOut;
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

    public List<ConsumptionBatchInfo> getBatchInfoList() {
        return batchInfoList;
    }

    public void setBatchInfoList(List<ConsumptionBatchInfo> batchInfoList) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.consumptionId;
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
        final Consumption other = (Consumption) obj;
        if (this.consumptionId != other.consumptionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Consumption{" + "consumptionId=" + consumptionId + ", region=" + region.getId() + ", planningUnit=" + planningUnit.getId() + ", dataSource=" + dataSource.getId() + ", consumptionDate=" + consumptionDate + ", actualFlag=" + actualFlag + ", consumptionQty=" + consumptionQty + ", dayOfStockOut=" + dayOfStockOut + ", versionId=" + versionId + '}';
    }

}
