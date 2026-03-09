/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentOverviewFspaSplit implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fspa;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject programCountry;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private double quantity;
    @JsonView(Views.ReportView.class)
    private double cost;
    @JsonView(Views.ReportView.class)
    private double freightCost;
    @JsonView(Views.ReportView.class)
    private double perc;

    public SimpleCodeObject getFspa() {
        return fspa;
    }

    public void setFspa(SimpleCodeObject fspa) {
        this.fspa = fspa;
    }

    public SimpleCodeObject getProgramCountry() {
        return programCountry;
    }

    public void setProgramCountry(SimpleCodeObject programCountry) {
        this.programCountry = programCountry;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }

    @JsonView(Views.ReportView.class)
    public double getTotalCost() {
        return this.freightCost + this.cost;
    }

}
