/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class BudgetReportOutput implements Serializable {

    private SimpleCodeObject budget;
    private SimpleCodeObject currency;
    private SimpleCodeObject fundingSource;
    private SimpleCodeObject program;
    private double budgetAmt;
    private double plannedBudgetAmt;
    private double orderedBudgetAmt;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;

    public SimpleCodeObject getBudget() {
        return budget;
    }

    public void setBudget(SimpleCodeObject budget) {
        this.budget = budget;
    }

    public SimpleCodeObject getCurrency() {
        return currency;
    }

    public void setCurrency(SimpleCodeObject currency) {
        this.currency = currency;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public double getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(double budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public double getPlannedBudgetAmt() {
        return plannedBudgetAmt;
    }

    public void setPlannedBudgetAmt(double plannedBudgetAmt) {
        this.plannedBudgetAmt = plannedBudgetAmt;
    }

    public double getOrderedBudgetAmt() {
        return orderedBudgetAmt;
    }

    public void setOrderedBudgetAmt(double orderedBudgetAmt) {
        this.orderedBudgetAmt = orderedBudgetAmt;
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

    public double getRemainingBudgetAmtUsd() {
        return (this.budgetAmt - this.orderedBudgetAmt - this.plannedBudgetAmt);
    }
}
