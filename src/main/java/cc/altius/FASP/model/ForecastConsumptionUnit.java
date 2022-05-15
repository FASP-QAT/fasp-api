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
public class ForecastConsumptionUnit implements Serializable {

    private int forecastConsumptionUnitId;
    private int dataType; // 1=Forecast, 2=PlanningUnit, 3=Other Unit
    private SimpleObjectWithMultiplier forecastingUnit;
    private SimpleObjectWithMultiplier planningUnit;
    private SimpleObjectWithMultiplier otherUnit;
    

    public int getForecastConsumptionUnitId() {
        return forecastConsumptionUnitId;
    }

    public void setForecastConsumptionUnitId(int forecastConsumptionUnitId) {
        this.forecastConsumptionUnitId = forecastConsumptionUnitId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public SimpleObjectWithMultiplier getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObjectWithMultiplier forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleObjectWithMultiplier getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObjectWithMultiplier planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObjectWithMultiplier getOtherUnit() {
        return otherUnit;
    }

    public void setOtherUnit(SimpleObjectWithMultiplier otherUnit) {
        this.otherUnit = otherUnit;
    }

}
