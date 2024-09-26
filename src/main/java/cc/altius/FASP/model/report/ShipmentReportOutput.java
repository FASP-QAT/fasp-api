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

/**
 *
 * @author akil
 */
public class ShipmentReportOutput implements Serializable {
    
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private double qty;
    @JsonView(Views.ReportView.class)
    private double productCost;
    @JsonView(Views.ReportView.class)
    private double freightPerc;
    @JsonView(Views.ReportView.class)
    private double freightCost;

    public ShipmentReportOutput(SimpleObject planningUnit, double qty, double productCost, double freightPerc, double freightCost) {
        this.planningUnit = planningUnit;
        this.qty = qty;
        this.productCost = productCost;
        this.freightPerc = freightPerc;
        this.freightCost = freightCost;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }

    public double getFreightPerc() {
        return freightPerc;
    }

    public void setFreightPerc(double freightPerc) {
        this.freightPerc = freightPerc;
    }

    public double getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(double freightCost) {
        this.freightCost = freightCost;
    }

    @JsonView(Views.ReportView.class)
    public double getTotalCost() {
        return this.freightCost + this.productCost;
    }

}
