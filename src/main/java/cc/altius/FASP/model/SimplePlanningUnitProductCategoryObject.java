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
public class SimplePlanningUnitProductCategoryObject extends SimpleObject {

    @JsonView(Views.InternalView.class)
    private SimpleForecastingUnitProductCategoryObject forecastingUnit;

    public SimplePlanningUnitProductCategoryObject() {
        super();
    }

    public SimplePlanningUnitProductCategoryObject(Integer id, Label label, SimpleForecastingUnitProductCategoryObject forecastingUnit) {
        super(id, label);
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleForecastingUnitProductCategoryObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitProductCategoryObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }
    
}
