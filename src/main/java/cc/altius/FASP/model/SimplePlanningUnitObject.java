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
public class SimplePlanningUnitObject extends SimpleUnitObjectWithMultiplier {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleForecastingUnitObject forecastingUnit;

    public SimplePlanningUnitObject() {
    }

    public SimplePlanningUnitObject(SimpleCodeObject unit, Integer id, Label label, double multiplier, SimpleForecastingUnitObject forecastingUnit) {
        super(unit, id, label, multiplier);
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleForecastingUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
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
