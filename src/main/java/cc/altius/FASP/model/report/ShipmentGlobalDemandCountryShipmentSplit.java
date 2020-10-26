/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentGlobalDemandCountryShipmentSplit implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject country;
    @JsonView(Views.ReportView.class)
    private int plannedShipmentAmt;
    @JsonView(Views.ReportView.class)
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
