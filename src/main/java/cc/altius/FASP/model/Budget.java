/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class Budget extends BaseModel implements Serializable {

    private int budgetId;
    private String budgetCode;
    private SimpleObject program;
    private FundingSource fundingSource;
    private Label label;
    private Currency currency;
    private double budgetAmt;
    private double budgetUsdAmt;
    private double usedUsdAmt; // Will always be converted into USD
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String notes;

    public Budget() {
    }

    public Budget(int budgetId, String budgetCode, Label label) {
        this.budgetId = budgetId;
        this.budgetCode = budgetCode;
        this.label = label;
    }

    public Budget(int budgetId, String budgetCode, FundingSource fundingSource, Label label) {
        this.budgetId = budgetId;
        this.budgetCode = budgetCode;
        this.fundingSource = fundingSource;
        this.label = label;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public String getBudgetCode() {
        return budgetCode;
    }

    public void setBudgetCode(String budgetCode) {
        this.budgetCode = budgetCode;
    }

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public FundingSource getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(FundingSource fundingSource) {
        this.fundingSource = fundingSource;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public double getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public double getUsedUsdAmt() {
        return usedUsdAmt;
    }

    public void setUsedUsdAmt(double usedUsdAmt) {
        this.usedUsdAmt = usedUsdAmt;
    }

    public double getBudgetUsdAmt() {
        return budgetUsdAmt;
    }

    public void setBudgetUsdAmt(double budgetUsdAmt) {
        this.budgetUsdAmt = budgetUsdAmt;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isBudgetUsable() {
        if (this.budgetUsdAmt > this.usedUsdAmt
                && DateUtils.compareDate(DateUtils.getCurrentDateObject(DateUtils.EST), this.stopDate) <= 0) {
            return this.isActive();
        } else {
            return false;
        }
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
