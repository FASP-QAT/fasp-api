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
public class Consumption extends BaseModel implements Serializable {

    private int consumptionId;
    private SimpleObject region;
    private SimplePlanningUnitObject planningUnit;
    private SimpleObject realmCountryPlanningUnit;
    private double multiplier;
    private String consumptionDate;
    private boolean actualFlag;
    private double consumptionRcpuQty;
    private double consumptionQty;
    private int dayOfStockOut;
    private SimpleObject dataSource;
    private String notes;
    private int versionId;
    private List<ConsumptionBatchInfo> batchInfoList;
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
