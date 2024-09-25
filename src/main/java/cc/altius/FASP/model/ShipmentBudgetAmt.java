/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentBudgetAmt implements Serializable {

    @JsonView(Views.InternalView.class)
    private int shipmentId;
    @JsonView(Views.InternalView.class)
    private int budgetId;
    @JsonView(Views.InternalView.class)
    private int currencyId;
    @JsonView(Views.InternalView.class)
    private double conversionRateToUsd;
    @JsonView(Views.InternalView.class)
    private double shipmentAmt;

    public ShipmentBudgetAmt() {
    }

    public ShipmentBudgetAmt(int shipmentId, int budgetId, int currencyId, double conversionRateToUsd, double shipmentAmt) {
        this.shipmentId = shipmentId;
        this.budgetId = budgetId;
        this.currencyId = currencyId;
        this.conversionRateToUsd = conversionRateToUsd;
        this.shipmentAmt = shipmentAmt;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(int currencyId) {
        this.currencyId = currencyId;
    }

    public double getConversionRateToUsd() {
        return conversionRateToUsd;
    }

    public void setConversionRateToUsd(double conversionRateToUsd) {
        this.conversionRateToUsd = conversionRateToUsd;
    }

    public double getShipmentAmt() {
        return shipmentAmt;
    }

    public void setShipmentAmt(double shipmentAmt) {
        this.shipmentAmt = shipmentAmt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.shipmentId;
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
        final ShipmentBudgetAmt other = (ShipmentBudgetAmt) obj;
        return this.shipmentId == other.shipmentId;
    }

}
