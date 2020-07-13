/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandCountryShipmentSplit implements Serializable {

    private SimpleCodeObject country;
    private int plannedShipmentAmt;
    private int orderedShipmentAmt;

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

    public int getPlannedShipmentAmt() {
        return plannedShipmentAmt;
    }

    public void setPlannedShipmentAmt(int plannedShipmentAmt) {
        this.plannedShipmentAmt = plannedShipmentAmt;
    }

    public int getOrderedShipmentAmt() {
        return orderedShipmentAmt;
    }

    public void setOrderedShipmentAmt(int orderedShipmentAmt) {
        this.orderedShipmentAmt = orderedShipmentAmt;
    }

}
