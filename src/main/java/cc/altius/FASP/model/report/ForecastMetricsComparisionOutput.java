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
public class ForecastMetricsComparisionOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private Double actualConsumption;
    @JsonView(Views.ReportView.class)
    private Double forecastedConsumption;
    @JsonView(Views.ReportView.class)
    private Double diffConsumptionTotal;
    @JsonView(Views.ReportView.class)
    private Double actualConsumptionTotal;
    @JsonView(Views.ReportView.class)
    private int monthCount;
    @JsonView(Views.ReportView.class)
    private Double forecastError;
    @JsonView(Views.ReportView.class)
    private Boolean actual;
    @JsonView(Views.ReportView.class)
    private Double forecastErrorThreshold;

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

    public Double getDiffConsumptionTotal() {
        return diffConsumptionTotal;
    }

    public void setDiffConsumptionTotal(Double diffConsumptionTotal) {
        this.diffConsumptionTotal = diffConsumptionTotal;
    }

    public Double getActualConsumptionTotal() {
        return actualConsumptionTotal;
    }

    public void setActualConsumptionTotal(Double actualConsumptionTotal) {
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

    public Double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Double getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Double forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public Boolean getActual() {
        return actual;
    }

    public void setActual(Boolean actual) {
        this.actual = actual;
    }

    public Double getForecastErrorThreshold() {
        return forecastErrorThreshold;
    }

    public void setForecastErrorThreshold(Double forecastErrorThreshold) {
        this.forecastErrorThreshold = forecastErrorThreshold;
    }

    @JsonView(Views.ReportView.class)
    public String getMessage() {
        if (this.monthCount == 0) {
            return "static.reports.forecastMetrics.noConsumptionAcrossPeriod";
//        } else if (this.actualConsumption == null || this.forecastedConsumption == null || this.actual == null || this.actual == false) {
//            return "static.reports.forecastMetrics.noConsumption";
        } else if (this.actualConsumptionTotal == null || this.actualConsumptionTotal == 0) {
            return "static.reports.forecastMetrics.totalConsumptionIs0";
        } else if (this.getForecastError() == null) {
            return "";
        } else {
            return null;
        }
    }
}
