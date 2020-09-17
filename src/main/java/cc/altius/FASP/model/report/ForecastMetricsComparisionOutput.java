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
    private Integer diffConsumptionTotal;
    private Integer actualConsumptionTotal;
    private int monthCount;
    private Double forecastError;

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

}
