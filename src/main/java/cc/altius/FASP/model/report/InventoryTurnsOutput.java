/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

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
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private int totalConsumption;
    @JsonView(Views.ReportView.class)
    private double avergeStock;
    @JsonView(Views.ReportView.class)
    private int noOfMonths;
    @JsonView(Views.ReportView.class)
    private double inventoryTurns;

    public InventoryTurnsOutput() {
    }

    public InventoryTurnsOutput(SimpleObject planningUnit, int totalConsumption, double avergeStock, int noOfMonths, double inventoryTurns) {
        this.planningUnit = planningUnit;
        this.totalConsumption = totalConsumption;
        this.avergeStock = avergeStock;
        this.noOfMonths = noOfMonths;
        this.inventoryTurns = inventoryTurns;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(int totalConsumption) {
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

    public double getInventoryTurns() {
        return inventoryTurns;
    }

    public void setInventoryTurns(double inventoryTurns) {
        this.inventoryTurns = inventoryTurns;
    }

}
