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
public class PlanningUnit extends BaseModel implements Serializable {

    private int planningUnitId;
    private ForecastingUnit forecastingUnit;
    private String skuCode;
    private Label label;
    private SimpleObject unit;
    private double multiplier;

    public PlanningUnit() {
    }

    public PlanningUnit(int planningUnitId, Label label) {
        this.planningUnitId = planningUnitId;
        this.label = label;
    }

    public PlanningUnit(int planningUnitId, ForecastingUnit forecastingUnit, String skuCode, Label label, SimpleObject unit, double multiplier) {
        this.planningUnitId = planningUnitId;
        this.forecastingUnit = forecastingUnit;
        this.skuCode = skuCode;
        this.label = label;
        this.unit = unit;
        this.multiplier = multiplier;
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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public SimpleObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleObject unit) {
        this.unit = unit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
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
        return "PlanningUnit{" + "planningUnitId=" + planningUnitId + ", label=" + label + ", multiplier=" + multiplier + '}';
    }

}
