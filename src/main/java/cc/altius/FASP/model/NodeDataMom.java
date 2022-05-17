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
public class NodeDataMom implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataMomId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private String month;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double startValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double endValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double calculatedValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double calculatedMmdValue;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double difference;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double seasonalityPerc;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double manualChange;

    public NodeDataMom() {
    }

    public NodeDataMom(int nodeDataMomId) {
        this.nodeDataMomId = nodeDataMomId;
    }

    public int getNodeDataMomId() {
        return nodeDataMomId;
    }

    public void setNodeDataMomId(int nodeDataMomId) {
        this.nodeDataMomId = nodeDataMomId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public Double getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Double calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public Double getCalculatedMmdValue() {
        return calculatedMmdValue;
    }

    public void setCalculatedMmdValue(Double calculatedMmdValue) {
        this.calculatedMmdValue = calculatedMmdValue;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }

    public Double getSeasonalityPerc() {
        return seasonalityPerc;
    }

    public void setSeasonalityPerc(Double seasonalityPerc) {
        this.seasonalityPerc = seasonalityPerc;
    }

    public Double getManualChange() {
        return manualChange;
    }

    public void setManualChange(Double manualChange) {
        this.manualChange = manualChange;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.nodeDataMomId;
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
        final NodeDataMom other = (NodeDataMom) obj;
        if (this.nodeDataMomId != other.nodeDataMomId) {
            return false;
        }
        return true;
    }

}
