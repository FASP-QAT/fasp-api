/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.util.Date;
import java.util.List;

/**
 *
 * @author akil
 */
public class StockStatusOutput {

    private Date transDate;
    private int consumptionQty;
    private boolean actual;
    private int shipmentQty;
    private List<ShipmentInfo> shipmentList;
    private int adjustmentQty;
    private int closingBalance;
    private double mos;
    private int minMonths;
    private double maxMonths;

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public int getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(int consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public boolean isActual() {
        return actual;
    }

    public void setActual(boolean actual) {
        this.actual = actual;
    }

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public List<ShipmentInfo> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<ShipmentInfo> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public int getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(int adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getMos() {
        return mos;
    }

    public void setMos(double mos) {
        this.mos = mos;
    }

    public int getMinMonths() {
        return minMonths;
    }

    public void setMinMonths(int minMonths) {
        this.minMonths = minMonths;
    }

    public double getMaxMonths() {
        return maxMonths;
    }

    public void setMaxMonths(double maxMonths) {
        this.maxMonths = maxMonths;
    }

}
