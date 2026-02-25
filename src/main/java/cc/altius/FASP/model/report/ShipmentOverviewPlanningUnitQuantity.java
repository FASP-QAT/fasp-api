/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ShipmentOverviewPlanningUnitQuantity implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private Map<SimpleObject, Double> fspaQuantity = new HashMap<>();

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Map<SimpleObject, Double> getFspaQuantity() {
        return fspaQuantity;
    }

    public void setFspaQuantity(Map<SimpleObject, Double> fspaQuantity) {
        this.fspaQuantity = fspaQuantity;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.planningUnit);
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
        final ShipmentOverviewPlanningUnitQuantity other = (ShipmentOverviewPlanningUnitQuantity) obj;
        return Objects.equals(this.planningUnit, other.planningUnit);
    }

}
