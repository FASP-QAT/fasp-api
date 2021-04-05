/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author altius
 */
public class ConsumptionInfo {

    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private String consumptionDate;
    @JsonView(Views.ReportView.class)
    private int consumptionId;

    public ConsumptionInfo() {
    }

    public ConsumptionInfo(String notes, String consumptionDate, int consumptionId) {
        this.notes = notes;
        this.consumptionDate = consumptionDate;
        this.consumptionId = consumptionId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(String consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public int getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(int consumptionId) {
        this.consumptionId = consumptionId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.consumptionId;
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
        final ConsumptionInfo other = (ConsumptionInfo) obj;
        if (this.consumptionId != other.consumptionId) {
            return false;
        }
        return true;
    }

}
