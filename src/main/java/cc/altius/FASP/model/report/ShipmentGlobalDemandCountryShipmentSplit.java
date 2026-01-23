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
    private double plannedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double submittedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double approvedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double shippedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double arrivedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double receivedShipmentQty;
    @JsonView(Views.ReportView.class)
    private double onholdShipmentQty;

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

    public double getPlannedShipmentQty() {
        return plannedShipmentQty;
    }

    public void setPlannedShipmentQty(double plannedShipmentQty) {
        this.plannedShipmentQty = plannedShipmentQty;
    }

    public double getSubmittedShipmentQty() {
        return submittedShipmentQty;
    }

    public void setSubmittedShipmentQty(double submittedShipmentQty) {
        this.submittedShipmentQty = submittedShipmentQty;
    }

    public double getApprovedShipmentQty() {
        return approvedShipmentQty;
    }

    public void setApprovedShipmentQty(double approvedShipmentQty) {
        this.approvedShipmentQty = approvedShipmentQty;
    }

    public double getShippedShipmentQty() {
        return shippedShipmentQty;
    }

    public void setShippedShipmentQty(double shippedShipmentQty) {
        this.shippedShipmentQty = shippedShipmentQty;
    }

    public double getArrivedShipmentQty() {
        return arrivedShipmentQty;
    }

    public void setArrivedShipmentQty(double arrivedShipmentQty) {
        this.arrivedShipmentQty = arrivedShipmentQty;
    }

    public double getReceivedShipmentQty() {
        return receivedShipmentQty;
    }

    public void setReceivedShipmentQty(double receivedShipmentQty) {
        this.receivedShipmentQty = receivedShipmentQty;
    }

    public double getOnholdShipmentQty() {
        return onholdShipmentQty;
    }

    public void setOnholdShipmentQty(double onholdShipmentQty) {
        this.onholdShipmentQty = onholdShipmentQty;
    }

}
