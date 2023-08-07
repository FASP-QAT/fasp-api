/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitWithPrices extends SimpleObject {

    private SimpleForecastingUnitObject forecastingUnit;
    private SimpleCodeObject unit;
    private Double multiplier;
    private List<SimpleObjectPrice> procurementAgentPriceList;

    public SimplePlanningUnitWithPrices() {
        this.procurementAgentPriceList = new LinkedList<>();
    }

    public SimplePlanningUnitWithPrices(Integer id, Label label) {
        super(id, label);
        this.procurementAgentPriceList = new LinkedList<>();
    }

    public List<SimpleObjectPrice> getProcurementAgentPriceList() {
        return procurementAgentPriceList;
    }

    public void setProcurementAgentPriceList(List<SimpleObjectPrice> procurementAgentPriceList) {
        this.procurementAgentPriceList = procurementAgentPriceList;
    }

    public SimpleForecastingUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.getId());
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
        final SimplePlanningUnitWithPrices other = (SimplePlanningUnitWithPrices) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }

}
