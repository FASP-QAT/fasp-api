/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class Inventory extends BaseModel implements Serializable {

    private int inventoryId;
    private String inventoryDate;
    private SimpleObject region;
    private SimpleObject realmCountryPlanningUnit;
    private SimplePlanningUnitObject planningUnit;
    private double multiplier;
    private Double actualQty;
    private Double adjustmentQty;
    private double expectedBal;
    @JsonIgnore
    private SimpleCodeObject unit;
    private SimpleObject dataSource;
    private String notes;
    private int versionId;
    private List<InventoryBatchInfo> batchInfoList;
    private boolean active;

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

    public void setRealmCountryPlanningUnit(SimpleObject realmCountryPlanningUnit) {
        this.realmCountryPlanningUnit = realmCountryPlanningUnit;
    }

    public SimplePlanningUnitObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
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
