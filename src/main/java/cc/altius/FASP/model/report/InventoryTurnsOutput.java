/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class InventoryTurnsOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject realmCountry;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject productCategory;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private long totalConsumption;
    @JsonView(Views.ReportView.class)
    private double avergeStock;
    @JsonView(Views.ReportView.class)
    private int noOfMonths;
    @JsonView(Views.ReportView.class)
    private Double inventoryTurns;
    @JsonView(Views.ReportView.class)
    private int reorderFrequencyInMonths;
    @JsonView(Views.ReportView.class)
    private int minMonthsOfStock;
    @JsonView(Views.ReportView.class)
    private int totalMonthsOfPlannedConsumption;
    @JsonView(Views.ReportView.class)
    private double plannedInventoryTurns;

    public InventoryTurnsOutput() {
    }

    public InventoryTurnsOutput(SimpleObject realmCountry, SimpleCodeObject program, SimpleObject productCategory, SimpleObject planningUnit, long totalConsumption, double avergeStock, int noOfMonths, int reorderFrequencyInMonths, int minMonthsOfStock, int totalMonthsOfPlannedConsumption, double plannedInventoryTurns) {
        this.realmCountry = realmCountry;
        this.program = program;
        this.productCategory = productCategory;
        this.planningUnit = planningUnit;
        this.totalConsumption = totalConsumption;
        this.avergeStock = avergeStock;
        this.noOfMonths = noOfMonths;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.minMonthsOfStock = minMonthsOfStock;
        this.totalMonthsOfPlannedConsumption = totalMonthsOfPlannedConsumption;
        this.plannedInventoryTurns = plannedInventoryTurns;
    }

    public SimpleObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public long getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(long totalConsumption) {
        this.totalConsumption = totalConsumption;
    }

    public double getAvergeStock() {
        return avergeStock;
    }

    public void setAvergeStock(double avergeStock) {
        this.avergeStock = avergeStock;
    }

    public int getNoOfMonths() {
        return noOfMonths;
    }

    public void setNoOfMonths(int noOfMonths) {
        this.noOfMonths = noOfMonths;
    }

    public Double getInventoryTurns() {
        return inventoryTurns;
    }

    public void setInventoryTurns(Double inventoryTurns) {
        this.inventoryTurns = inventoryTurns;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public int getTotalMonthsOfPlannedConsumption() {
        return totalMonthsOfPlannedConsumption;
    }

    public void setTotalMonthsOfPlannedConsumption(int totalMonthsOfPlannedConsumption) {
        this.totalMonthsOfPlannedConsumption = totalMonthsOfPlannedConsumption;
    }

    public double getPlannedInventoryTurns() {
        return plannedInventoryTurns;
    }

    public void setPlannedInventoryTurns(double plannedInventoryTurns) {
        this.plannedInventoryTurns = plannedInventoryTurns;
    }

    @JsonView(Views.ReportView.class)
    public Double getDelta() {
        if (this.inventoryTurns == null) {
            return null;
        } else {
            return this.inventoryTurns - this.plannedInventoryTurns;
        }
    }

    @JsonView(Views.ReportView.class)
    public Double getAbsDelta() {
        if (getDelta() == null) {
            return null;
        } else {
            return Math.abs(getDelta());
        }
    }

    @JsonView(Views.ReportView.class)
    public Double getMape() {
        if (getInventoryTurns() == null) {
            return null;
        } else if (getInventoryTurns() == 0.00) {
            return null;
        } else {
            return Math.abs(getDelta() / getInventoryTurns());
        }
    }

    @JsonView(Views.ReportView.class)
    public Double getMse() {
        if (getDelta() == null) {
            return null;
        } else {
            return getDelta() * getDelta();
        }
    }
}
