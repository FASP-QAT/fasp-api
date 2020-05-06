/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class Consumption extends BaseModel implements Serializable {

    private int consumptionId;
    private SimpleObject region;
    private SimpleObject planningUnit;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private boolean actualFlag;
    private double consumptionQty;
    private int dayOfStockOut;
    private SimpleObject dataSource;
    private String notes;
    private int versionId;
    @JsonIgnore
    private boolean changed;

    public Consumption() {
    }

    public Consumption(int consumptionId, SimpleObject region, SimpleObject planningUnit, Date startDate, Date stopDate, boolean actualFlag, double consumptionQty, int dayOfStockOut, SimpleObject dataSource, String notes, int versionId) {
        this.consumptionId = consumptionId;
        this.region = region;
        this.planningUnit = planningUnit;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.actualFlag = actualFlag;
        this.consumptionQty = consumptionQty;
        this.dayOfStockOut = dayOfStockOut;
        this.dataSource = dataSource;
        this.notes = notes;
        this.versionId = versionId;
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

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public boolean isActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(boolean actualFlag) {
        this.actualFlag = actualFlag;
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

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "Consumption{" + "consumptionId=" + consumptionId + ", region=" + region.getId() + ", planningUnit=" + planningUnit.getId() + ", dataSource=" + dataSource.getId() + ", startDate=" + startDate + ", stopDate=" + stopDate + ", actualFlag=" + actualFlag + ", consumptionQty=" + consumptionQty + ", dayOfStockOut=" + dayOfStockOut + ", versionId=" + versionId + '}';
    }

}
