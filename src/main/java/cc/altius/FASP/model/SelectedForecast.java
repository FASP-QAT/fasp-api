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
public class SelectedForecast implements Serializable {

    private Integer scenarioId;
    private Integer consumptionExtrapolationId;
    private String notes;
    private Double totalForecast;

    public Integer getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(Integer scenarioId) {
        this.scenarioId = scenarioId;
    }

    public Integer getConsumptionExtrapolationId() {
        return consumptionExtrapolationId;
    }

    public void setConsumptionExtrapolationId(Integer consumptionExtrapolationId) {
        this.consumptionExtrapolationId = consumptionExtrapolationId;
    }

    public Double getTotalForecast() {
        return totalForecast;
    }

    public void setTotalForecast(Double totalForecast) {
        this.totalForecast = totalForecast;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
