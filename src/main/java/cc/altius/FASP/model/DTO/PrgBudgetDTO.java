/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.Date;

/**
 *
 * @author altius
 */
public class PrgBudgetDTO {

    private int budgetId;
    private PrgSubFundingSourceDTO subFundingSource;
    private PrgLabelDTO label;
    private double budgetAmount;
    private Date startDate;
    private Date stopDate;

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public PrgSubFundingSourceDTO getSubFundingSource() {
        return subFundingSource;
    }

    public void setSubFundingSource(PrgSubFundingSourceDTO subFundingSource) {
        this.subFundingSource = subFundingSource;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + this.budgetId;
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
        final PrgBudgetDTO other = (PrgBudgetDTO) obj;
        if (this.budgetId != other.budgetId) {
            return false;
        }
        return true;
    }
    
    

}
