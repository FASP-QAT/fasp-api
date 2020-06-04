/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ForecastMetricsOutput implements Serializable {

    private SimpleCodeObject realmCountry;
    private SimpleObject program;
    private SimpleCodeObject planningUnit;
    private Double historicalConsumptionDiff;
    private Double historicalConsumptionActual;
    private Double forecastError;
    private int months;

    public SimpleCodeObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleCodeObject realmCountry) {
        this.realmCountry = realmCountry;
    }

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleCodeObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleCodeObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Double getHistoricalConsumptionDiff() {
        return historicalConsumptionDiff;
    }

    public void setHistoricalConsumptionDiff(Double historicalConsumptionDiff) {
        this.historicalConsumptionDiff = historicalConsumptionDiff;
    }

    public Double getHistoricalConsumptionActual() {
        return historicalConsumptionActual;
    }

    public void setHistoricalConsumptionActual(Double historicalConsumptionActual) {
        this.historicalConsumptionActual = historicalConsumptionActual;
    }

    public Double getForecastError() {
        return forecastError;
    }

    public void setForecastError(Double forecastError) {
        this.forecastError = forecastError;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

}
