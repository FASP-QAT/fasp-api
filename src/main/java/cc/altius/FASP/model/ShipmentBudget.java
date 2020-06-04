/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentBudget implements Serializable {

    private int shipmentBudgetId;
    private SimpleBudgetObject budget;
    private transient boolean active;
    private double budgetAmt;
    private Currency currency;
    private double conversionRateToUsd;

    public ShipmentBudget() {
    }

    public ShipmentBudget(int shipmentBudgetId, SimpleBudgetObject budget, boolean active, double budgetAmt, double conversionRateToUsd, Currency currency) {
        this.shipmentBudgetId = shipmentBudgetId;
        this.budget = budget;
        this.active = active;
        this.budgetAmt = budgetAmt;
        this.conversionRateToUsd = conversionRateToUsd;
        this.currency = currency;
    }

    public int getShipmentBudgetId() {
        return shipmentBudgetId;
    }

    public void setShipmentBudgetId(int shipmentBudgetId) {
        this.shipmentBudgetId = shipmentBudgetId;
    }

    public SimpleBudgetObject getBudget() {
        return budget;
    }

    public void setBudget(SimpleBudgetObject budget) {
        this.budget = budget;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public double getConversionRateToUsd() {
        return conversionRateToUsd;
    }

    public void setConversionRateToUsd(double conversionRateToUsd) {
        this.conversionRateToUsd = conversionRateToUsd;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.shipmentBudgetId;
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
        final ShipmentBudget other = (ShipmentBudget) obj;
        if (this.shipmentBudgetId != other.shipmentBudgetId) {
            return false;
        }
        return true;
    }
    
}
