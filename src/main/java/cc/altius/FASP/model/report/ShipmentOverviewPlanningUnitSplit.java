/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentOverviewPlanningUnitSplit implements Serializable {

    private SimpleObject planningUnit;
    private int multiplier;
    private int plannedShipmentQty;
    private int orderedShipmentQty;

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getPlannedShipmentQty() {
        return plannedShipmentQty;
    }

    public void setPlannedShipmentQty(int plannedShipmentQty) {
        this.plannedShipmentQty = plannedShipmentQty;
    }

    public int getOrderedShipmentQty() {
        return orderedShipmentQty;
    }

    public void setOrderedShipmentQty(int orderedShipmentQty) {
        this.orderedShipmentQty = orderedShipmentQty;
    }

}
