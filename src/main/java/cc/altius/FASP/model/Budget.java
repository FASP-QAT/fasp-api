/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class Budget extends BaseModel implements Serializable {

    private int budgetId;
    private Program program;
    private SubFundingSource subFundingSource;
    private Label label;
    private int budgetAmt;
    private Date startDate;
    private Date stopDate;

    public Budget() {
    }

    public Budget(int budgetId, Label label) {
        this.budgetId = budgetId;
        this.label = label;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public SubFundingSource getSubFundingSource() {
        return subFundingSource;
    }

    public void setSubFundingSource(SubFundingSource subFundingSource) {
        this.subFundingSource = subFundingSource;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(int budgetAmt) {
        this.budgetAmt = budgetAmt;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.budgetId;
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
        final Budget other = (Budget) obj;
        if (this.budgetId != other.budgetId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Budget{" + "budgetId=" + budgetId + ", label=" + label + ", budgetAmt=" + budgetAmt + ", startDate=" + startDate + ", stopDate=" + stopDate + '}';
    }

    
}
