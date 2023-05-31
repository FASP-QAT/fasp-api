/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SelectedForecast implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer treeId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer scenarioId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Integer consumptionExtrapolationId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String notes;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double totalForecast;

    public Integer getTreeId() {
        return treeId;
    }

    public void setTreeId(Integer treeId) {
        this.treeId = treeId;
    }

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
