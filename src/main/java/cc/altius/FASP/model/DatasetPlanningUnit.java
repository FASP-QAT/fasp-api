/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author akil
 */
public class DatasetPlanningUnit extends BaseModel implements Serializable {

    private int programPlanningUnitId;
    private SimplePlanningUnitTracerCategoryObject planningUnit;
    private boolean consuptionForecast;
    private boolean treeForecast;
    private Integer stock;
    private Integer existingShipments;
    private Integer monthsOfStock;
    private SimpleCodeObject procurementAgent;
    private Double price;
    private Double higherThenConsumptionThreshold;
    private Double lowerThenConsumptionThreshold;
    private String consumptionNotes;
    private Integer consumptionDataType; // null=Not a Consumption Unit, 1=Forecast, 2=PlanningUnit, 3=Other Unit
    private SimpleObjectWithMultiplier otherUnit;
    private Map<Integer, SelectedForecast> selectedForecastMap;

    public int getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(int programPlanningUnitId) {
        this.programPlanningUnitId = programPlanningUnitId;
    }

    public SimplePlanningUnitTracerCategoryObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitTracerCategoryObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public boolean isConsuptionForecast() {
        return consuptionForecast;
    }

    public void setConsuptionForecast(boolean consuptionForecast) {
        this.consuptionForecast = consuptionForecast;
    }

    public boolean isTreeForecast() {
        return treeForecast;
    }

    public void setTreeForecast(boolean treeForecast) {
        this.treeForecast = treeForecast;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getExistingShipments() {
        return existingShipments;
    }

    public void setExistingShipments(Integer existingShipments) {
        this.existingShipments = existingShipments;
    }

    public Integer getMonthsOfStock() {
        return monthsOfStock;
    }

    public void setMonthsOfStock(Integer monthsOfStock) {
        this.monthsOfStock = monthsOfStock;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getHigherThenConsumptionThreshold() {
        return higherThenConsumptionThreshold;
    }

    public void setHigherThenConsumptionThreshold(Double higherThenConsumptionThreshold) {
        this.higherThenConsumptionThreshold = higherThenConsumptionThreshold;
    }

    public Double getLowerThenConsumptionThreshold() {
        return lowerThenConsumptionThreshold;
    }

    public void setLowerThenConsumptionThreshold(Double lowerThenConsumptionThreshold) {
        this.lowerThenConsumptionThreshold = lowerThenConsumptionThreshold;
    }

    public String getConsumptionNotes() {
        return consumptionNotes;
    }

    public void setConsumptionNotes(String consumptionNotes) {
        this.consumptionNotes = consumptionNotes;
    }

    public Integer getConsumptionDataType() {
        return consumptionDataType;
    }

    public void setConsumptionDataType(Integer consumptionDataType) {
        this.consumptionDataType = consumptionDataType;
    }

    public SimpleObjectWithMultiplier getOtherUnit() {
        return otherUnit;
    }

    public void setOtherUnit(SimpleObjectWithMultiplier otherUnit) {
        this.otherUnit = otherUnit;
    }

    public Map<Integer, SelectedForecast> getSelectedForecastMap() {
        return selectedForecastMap;
    }

    public void setSelectedForecastMap(Map<Integer, SelectedForecast> selectedForecastMap) {
        this.selectedForecastMap = selectedForecastMap;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + this.programPlanningUnitId;
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
        final DatasetPlanningUnit other = (DatasetPlanningUnit) obj;
        if (this.programPlanningUnitId != other.programPlanningUnitId) {
            return false;
        }
        return true;
    }

}
