/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

/**
 *
 * @author altius
 */
public class QatTempConsumption {

    private int consumptionId;
    private SimpleObject region;
    private SimpleObject planningUnit;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date consumptionDate;
    private boolean actualFlag;
    private double consumptionQty;
    private int dayOfStockOut;
    private SimpleObject dataSource;
    private String notes;
    
    
    public QatTempConsumption() {
    }

    public QatTempConsumption(int consumptionId, SimpleObject region, SimpleObject planningUnit, Date consumptionDate, boolean actualFlag, double consumptionQty, int dayOfStockOut, SimpleObject dataSource, String notes) {
        this.consumptionId = consumptionId;
        this.region = region;
        this.planningUnit = planningUnit;
        this.consumptionDate = consumptionDate;
        this.actualFlag = actualFlag;
        this.consumptionQty = consumptionQty;
        this.dayOfStockOut = dayOfStockOut;
        this.dataSource = dataSource;
        this.notes = notes;
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

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
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

    @Override
    public String toString() {
        return "QatTempConsumption{" + "consumptionId=" + consumptionId + ", region=" + region + ", planningUnit=" + planningUnit + ", consumptionDate=" + consumptionDate + ", actualFlag=" + actualFlag + ", consumptionQty=" + consumptionQty + ", dayOfStockOut=" + dayOfStockOut + ", dataSource=" + dataSource + ", notes=" + notes + '}';
    }

}
