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
public class ShipmentReportOutput implements Serializable {
    
    private SimpleObject planningUnit;
    private int qty;
    private double productCost;
    private double freightPerc;
    private double freightCost;

    public ShipmentReportOutput(SimpleObject planningUnit, int qty, double productCost, double freightPerc, double freightCost) {
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
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

    public double getTotalCost() {
        return this.freightCost + this.productCost;
    }

}
