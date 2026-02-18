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
public class ShipmentDetailsFundingSourceProcurementAgent implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fspa;
    @JsonView(Views.ReportView.class)
    private int countries;
    @JsonView(Views.ReportView.class)
    private int programs;
    @JsonView(Views.ReportView.class)
    private int shipments;
    @JsonView(Views.ReportView.class)
    private double cost;

    public SimpleCodeObject getFspa() {
        return fspa;
    }

    public void setFspa(SimpleCodeObject fspa) {
        this.fspa = fspa;
    }

    public int getCountries() {
        return countries;
    }

    public void setCountries(int countries) {
        this.countries = countries;
    }

    public int getPrograms() {
        return programs;
    }

    public void setPrograms(int programs) {
        this.programs = programs;
    }

    public int getShipments() {
        return shipments;
    }

    public void setShipments(int shipments) {
        this.shipments = shipments;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
