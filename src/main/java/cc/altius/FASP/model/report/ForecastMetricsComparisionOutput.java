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
    private Integer actualConsumption;
    @JsonView(Views.ReportView.class)
    private Integer forecastedConsumption;
    @JsonView(Views.ReportView.class)
    private Integer diffConsumptionTotal;
    @JsonView(Views.ReportView.class)
    private Integer actualConsumptionTotal;
    @JsonView(Views.ReportView.class)
    private int monthCount;
    @JsonView(Views.ReportView.class)
    private Double forecastError;
    @JsonView(Views.ReportView.class)
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

    @JsonView(Views.ReportView.class)
    public String getMessage() {
        if (this.monthCount == 0) {
            return "static.reports.forecastMetrics.noConsumptionAcrossPeriod";
        } else if (this.actualConsumption == null || this.forecastedConsumption == null || this.actual == null || this.actual == false) {
            return "static.reports.forecastMetrics.noConsumption";
        } else if (this.actualConsumptionTotal == null || this.actualConsumptionTotal == 0) {
            return "static.reports.forecastMetrics.totalConsumptionIs0";
        } else if (this.getForecastError() == null) {
            return "";
        } else {
            return null;
        }
    }
}
