/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.rowMapper;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author akil
 */
public class ForecastConsumptionExtrapolationSettings implements Serializable {

    private SimpleObject planningUnit;
    private SimpleObject region;
    private Map<SimpleObject, String> extrapolationProperties;

    public ForecastConsumptionExtrapolationSettings() {
        this.extrapolationProperties = new HashMap<>();
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public Map<SimpleObject, String> getExtrapolationProperties() {
        return extrapolationProperties;
    }

    public void setExtrapolationProperties(Map<SimpleObject, String> extrapolationProperties) {
        this.extrapolationProperties = extrapolationProperties;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.planningUnit.getId();
        hash = 79 * hash + this.region.getId();
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
        final ForecastConsumptionExtrapolationSettings other = (ForecastConsumptionExtrapolationSettings) obj;
        if (this.planningUnit == null || other.planningUnit == null || !this.planningUnit.equals(other.planningUnit)) {
            return false;
        }
        if (this.region == null || other.region == null || !this.region.equals(other.region)) {
            return false;
        }
        return true;
    }

}
