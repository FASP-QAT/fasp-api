/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitForAdjustPlanningUnit extends SimpleObject {

    @JsonView({Views.InternalView.class})
    private SimpleForecastingUnitForAdjustPlanningUnit forecastingUnit;

    public SimpleForecastingUnitForAdjustPlanningUnit getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitForAdjustPlanningUnit forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

}
