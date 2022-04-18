/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitObject extends SimpleObject {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private double multiplier;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleForecastingUnitObject forecastingUnit;

    public SimplePlanningUnitObject() {
    }

    public SimplePlanningUnitObject(Integer id, Label label, double multiplier, SimpleForecastingUnitObject forecastingUnit) {
        super(id, label);
        this.multiplier = multiplier;
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleForecastingUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.forecastingUnit);
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
        final SimplePlanningUnitObject other = (SimplePlanningUnitObject) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }

}
