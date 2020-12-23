/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.ReportView.class)
    private Date month;
    @JsonView(Views.ReportView.class)
    private Long diffConsumptionHistory;
    @JsonView(Views.ReportView.class)
    private Long actualConsumptionHistory;
    @JsonView(Views.ReportView.class)
    private Double forecastError;
    @JsonView(Views.ReportView.class)
    private Long actualConsumption;
    @JsonView(Views.ReportView.class)
    private Long forecastedConsumption;
    @JsonView(Views.ReportView.class)
    private Boolean actual;
    @JsonView(Views.ReportView.class)
    private int monthCount;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Long getDiffConsumptionHistory() {
        return diffConsumptionHistory;
    }

    public void setDiffConsumptionHistory(Long diffConsumptionHistory) {
        this.diffConsumptionHistory = diffConsumptionHistory;
    }

    public Long getActualConsumptionHistory() {
        return actualConsumptionHistory;
    }

    public void setActualConsumptionHistory(Long actualConsumptionHistory) {
        this.actualConsumptionHistory = actualConsumptionHistory;
    }

    public Double getForecastError() {
        return forecastError;
    }

    public void setForecastError(Double forecastError) {
        this.forecastError = forecastError;
    }

    public Long getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Long actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Long getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Long forecastedConsumption) {
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

    @JsonView(Views.ReportView.class)
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
