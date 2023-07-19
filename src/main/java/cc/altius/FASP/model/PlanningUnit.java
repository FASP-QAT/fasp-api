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
public class PlanningUnit extends BaseModel implements Serializable {

    @JsonView(Views.ReportView.class)
    private int planningUnitId;
    @JsonView(Views.ReportView.class)
    private ForecastingUnit forecastingUnit;
    @JsonView(Views.ReportView.class)
    private Label label;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject unit;
    @JsonView(Views.ReportView.class)
    private double multiplier;
    @JsonView(Views.ReportView.class)
    private boolean active;

    public PlanningUnit() {
    }

    public PlanningUnit(int planningUnitId, Label label) {
        this.planningUnitId = planningUnitId;
        this.label = label;
    }

    public PlanningUnit(int planningUnitId, ForecastingUnit forecastingUnit, Label label, SimpleCodeObject unit, double multiplier, boolean active) {
        this.planningUnitId = planningUnitId;
        this.forecastingUnit = forecastingUnit;
        this.label = label;
        this.unit = unit;
        this.multiplier = multiplier;
        this.active = active;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public ForecastingUnit getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(ForecastingUnit forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.planningUnitId;
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
        final PlanningUnit other = (PlanningUnit) obj;
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlanningUnit{" + "planningUnitId=" + planningUnitId + ", forecastingUnit=" + forecastingUnit + ", label=" + label + ", unit=" + unit + ", multiplier=" + multiplier + ", active=" + active + '}';
    }

}
