/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class Inventory extends BaseModel implements Serializable {

    private int inventoryId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date inventoryDate;
    private SimpleObject region;
    private SimpleObject realmCountryPlanningUnit;
    private SimpleObject planningUnit;
    private double multiplier;
    private Double actualQty;
    private double adjustmentQty;
    private double expectedBal;
    private SimpleCodeObject unit;
    private SimpleObject dataSource;
    private String batchNo;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expiryDate;
    private String notes;
    private int versionId;

    public Inventory() {
    }

    public Inventory(int inventoryId, Date inventoryDate, SimpleObject region, SimpleObject realmCountryPlanningUnit, SimpleObject planningUnit, double multiplier, double adjustmentQty, double expectedBal, SimpleCodeObject unit, SimpleObject dataSource, String notes, int versionId) {
        this.inventoryId = inventoryId;
        this.inventoryDate = inventoryDate;
        this.region = region;
        this.realmCountryPlanningUnit = realmCountryPlanningUnit;
        this.planningUnit = planningUnit;
        this.multiplier = multiplier;
        this.adjustmentQty = adjustmentQty;
        this.expectedBal = expectedBal;
        this.unit = unit;
        this.dataSource = dataSource;
        this.notes = notes;
        this.versionId = versionId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
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

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
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

    public double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(double adjustmentQty) {
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
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

    @Override
    public String toString() {
        return "Inventory{" + "inventoryId=" + inventoryId + ", inventoryDate=" + inventoryDate + ", region=" + region.getId() + ", realmCountryPlanningUnit=" + realmCountryPlanningUnit.getId() + ", actualQty=" + actualQty + ", adjustmentQty=" + adjustmentQty + ", dataSource=" + dataSource.getId() + ", batchNo=" + batchNo + ", expiryDate=" + expiryDate + ", versionId=" + versionId + '}';
    }

}
