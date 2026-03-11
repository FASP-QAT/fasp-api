/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class StockStatusMatrix implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private int planBasedOn;
    @JsonView(Views.ReportView.class)
    private int minMonthsOfStock;
    @JsonView(Views.ReportView.class)
    private int reorderFrequency;
    @JsonView(Views.ReportView.class)
    private int maxStock;
    @JsonView(Views.ReportView.class)
    private int minStock;
    @JsonView(Views.ReportView.class)
    private Map<String, AmcAndQty> dataMap;
    @JsonView(Views.ReportView.class)
    private String notes;

    public StockStatusMatrix(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getPlanBasedOn() {
        return planBasedOn;
    }

    public void setPlanBasedOn(int planBasedOn) {
        this.planBasedOn = planBasedOn;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public int getReorderFrequency() {
        return reorderFrequency;
    }

    public void setReorderFrequency(int reorderFrequency) {
        this.reorderFrequency = reorderFrequency;
    }

    public int getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public Map<String, AmcAndQty> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, AmcAndQty> dataMap) {
        this.dataMap = dataMap;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.planningUnit);
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
        final StockStatusMatrix other = (StockStatusMatrix) obj;
        if (this.getPlanningUnit().getId() == null || other.getPlanningUnit().getId() == null) {
            return false;
        }
        return Objects.equals(this.planningUnit.getId(), other.planningUnit.getId());
    }

}
