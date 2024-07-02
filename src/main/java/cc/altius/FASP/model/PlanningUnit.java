/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class PlanningUnit extends BaseModel implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int planningUnitId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private ForecastingUnit forecastingUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Label label;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleCodeObject unit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private double multiplier;

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
        super.setActive(active);
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

    @JsonView(Views.ReportView.class)
    public boolean isActive() {
        return super.isActive();
    }

    public void setActive(boolean active) {
        super.setActive(active);
    }

    @JsonView(Views.ReportView.class)
    public Date getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @JsonView(Views.ReportView.class)
    public BasicUser getLastModifiedBy() {
        return super.getLastModifiedBy();
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
        return "PlanningUnit{" + "planningUnitId=" + planningUnitId + ", forecastingUnit=" + forecastingUnit + ", label=" + label + ", unit=" + unit + ", multiplier=" + multiplier + ", active=" + isActive() + '}';
    }

}
