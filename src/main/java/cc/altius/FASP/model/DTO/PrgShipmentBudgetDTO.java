/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgShipmentBudgetDTO {

    private int shipmentBudgetId;
    private PrgSubFundingSourceDTO subFundingSource;
    private PrgBudgetDTO budget;
    private double budgetAmount;

    public int getShipmentBudgetId() {
        return shipmentBudgetId;
    }

    public void setShipmentBudgetId(int shipmentBudgetId) {
        this.shipmentBudgetId = shipmentBudgetId;
    }

    public PrgSubFundingSourceDTO getSubFundingSource() {
        return subFundingSource;
    }

    public void setSubFundingSource(PrgSubFundingSourceDTO subFundingSource) {
        this.subFundingSource = subFundingSource;
    }

    public PrgBudgetDTO getBudget() {
        return budget;
    }

    public void setBudget(PrgBudgetDTO budget) {
        this.budget = budget;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

}
