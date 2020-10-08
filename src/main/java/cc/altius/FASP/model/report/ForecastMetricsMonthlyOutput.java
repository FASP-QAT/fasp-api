/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ForecastMetricsMonthlyOutput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date month;
    private Integer diffConsumptionHistory;
    private Integer actualConsumptionHistory;
    private Double forecastError;
    private Integer actualConsumption;
    private Integer forecastedConsumption;
    private Boolean actual;
    private int monthCount;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getDiffConsumptionHistory() {
        return diffConsumptionHistory;
    }

    public void setDiffConsumptionHistory(Integer diffConsumptionHistory) {
        this.diffConsumptionHistory = diffConsumptionHistory;
    }

    public Integer getActualConsumptionHistory() {
        return actualConsumptionHistory;
    }

    public void setActualConsumptionHistory(Integer actualConsumptionHistory) {
        this.actualConsumptionHistory = actualConsumptionHistory;
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

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    public String getMessage() {
        if (this.monthCount == 0) {
            return "static.reports.forecastMetrics.noConsumptionAcrossPeriod";
        } else if (this.actualConsumption == null || this.forecastedConsumption == null || this.actual == null || this.actual == false) {
            return "static.reports.forecastMetrics.noConsumption";
        } else if (this.actualConsumptionHistory == null || this.actualConsumptionHistory == 0) {
            return "static.reports.forecastMetrics.totalConsumptionIs0";
        } else if (this.getForecastError() == null) {
            return "";
        } else {
            return null;
        }
    }
}
