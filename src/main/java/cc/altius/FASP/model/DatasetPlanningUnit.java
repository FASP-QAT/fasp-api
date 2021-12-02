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
public class DatasetPlanningUnit implements Serializable {

    private int programPlanningUnitId;
    private SimplePlanningUnitTracerCategoryObject planningUnit;
    private boolean consuptionForecast;
    private boolean treeForecast;
    private Integer stock;
    private Integer existingShipments;
    private Integer monthsOfStock;
    private SimpleCodeObject procurementAgent;
    private Double price;

    public int getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(int programPlanningUnitId) {
        this.programPlanningUnitId = programPlanningUnitId;
    }

    public SimplePlanningUnitTracerCategoryObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitTracerCategoryObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public boolean isConsuptionForecast() {
        return consuptionForecast;
    }

    public void setConsuptionForecast(boolean consuptionForecast) {
        this.consuptionForecast = consuptionForecast;
    }

    public boolean isTreeForecast() {
        return treeForecast;
    }

    public void setTreeForecast(boolean treeForecast) {
        this.treeForecast = treeForecast;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getExistingShipments() {
        return existingShipments;
    }

    public void setExistingShipments(Integer existingShipments) {
        this.existingShipments = existingShipments;
    }

    public Integer getMonthsOfStock() {
        return monthsOfStock;
    }

    public void setMonthsOfStock(Integer monthsOfStock) {
        this.monthsOfStock = monthsOfStock;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
