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
    private int planningUnitId;
    private Label label;
    private Supplier supplier;
    private String startDate;
    private String stopDate;
    private double capacity;

    public PlanningUnitCapacity() {
    }

    public PlanningUnitCapacity(int planningUnitCapacityId, int planningUnitId, Label label, Supplier supplier, String startDate, String stopDate, double capacity) {
        this.planningUnitCapacityId = planningUnitCapacityId;
        this.planningUnitId = planningUnitId;
        this.label = label;
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

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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
