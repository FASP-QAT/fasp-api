/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ForecastMetricsComparisionOutput implements Serializable {

    private SimpleObject program;
    private SimpleObject planningUnit;
    private Integer actualConsumption;
    private Integer forecastedConsumption;
    private Integer diffConsumptionTotal;
    private Integer actualConsumptionTotal;
    private int monthCount;
    private Double forecastError;
    private Boolean actual;

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Integer getDiffConsumptionTotal() {
        return diffConsumptionTotal;
    }

    public void setDiffConsumptionTotal(Integer diffConsumptionTotal) {
        this.diffConsumptionTotal = diffConsumptionTotal;
    }

    public Integer getActualConsumptionTotal() {
        return actualConsumptionTotal;
    }

    public void setActualConsumptionTotal(Integer actualConsumptionTotal) {
        this.actualConsumptionTotal = actualConsumptionTotal;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public Double getForecastError() {
        return forecastError;
    }

    public void setForecastError(Double forecastError) {
        this.forecastError = forecastError;
    }

    public Integer getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Integer actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Integer getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Integer forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public String getMessage() {
        if (this.actualConsumption == null || this.forecastedConsumption == null || this.actual == null || this.actual == false) {
            return "Current month does not contain actual consumption and/or* forecasted consumption";
        } else if (this.actualConsumptionTotal == null || this.actualConsumptionTotal == 0) {
            return "Total actual consumption for last 6 months = 0";
        } else if (this.getForecastError() == null) {
            return "";
        } else {
            return this.getForecastError().toString();
        }
    }
}
