/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class MonthlyForecastOutput {

    @JsonView(Views.ReportView.class)
    private SimpleObjectWithMultiplier planningUnit;
    @JsonView(Views.ReportView.class)
    private SimpleObject forecastingUnit;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private List<MonthlyForecastData> monthlyForecastData;

    public MonthlyForecastOutput() {
        this.monthlyForecastData = new LinkedList<>();
    }

    public SimpleObjectWithMultiplier getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObjectWithMultiplier planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public List<MonthlyForecastData> getMonthlyForecastData() {
        return monthlyForecastData;
    }

    public void setMonthlyForecastData(List<MonthlyForecastData> monthlyForecastData) {
        this.monthlyForecastData = monthlyForecastData;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.planningUnit);
        hash = 47 * hash + Objects.hashCode(this.region);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MonthlyForecastOutput other = (MonthlyForecastOutput) obj;
        if (!Objects.equals(this.planningUnit, other.planningUnit)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        return true;
    }

}
