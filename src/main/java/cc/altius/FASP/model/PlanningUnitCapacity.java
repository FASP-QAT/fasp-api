/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class PlanningUnitCapacity extends BaseModel implements Serializable {

    private int planningUnitCapacityId;
    private SimpleObject planningUnit;
    private SimpleObject supplier;
    private String startDate;
    private String stopDate;
    private double capacity;

    public PlanningUnitCapacity() {
    }

    public PlanningUnitCapacity(int planningUnitCapacityId, SimpleObject planningUnit, SimpleObject supplier, String startDate, String stopDate, double capacity) {
        this.planningUnitCapacityId = planningUnitCapacityId;
        this.planningUnit = planningUnit;
        this.supplier = supplier;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.capacity = capacity;
    }

    public int getPlanningUnitCapacityId() {
        return planningUnitCapacityId;
    }

    public void setPlanningUnitCapacityId(int planningUnitCapacityId) {
        this.planningUnitCapacityId = planningUnitCapacityId;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getSupplier() {
        return supplier;
    }

    public void setSupplier(SimpleObject supplier) {
        this.supplier = supplier;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }


}
