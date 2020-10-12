/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitObject extends SimpleObject {

    @JsonView(Views.InternalView.class)
    private SimpleForecastingUnitObject forecastingUnit;

    public SimplePlanningUnitObject() {
        super();
    }

    public SimplePlanningUnitObject(Integer id, Label label, SimpleForecastingUnitObject forecastingUnit) {
        super(id, label);
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleForecastingUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }
    
}
