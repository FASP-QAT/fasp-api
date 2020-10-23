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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private List<StockStatusAcrossProductsForProgram> programData;

    public StockStatusAcrossProductsOutput() {
        this.programData = new LinkedList<>();
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public List<StockStatusAcrossProductsForProgram> getProgramData() {
        return programData;
    }

    public void setProgramData(List<StockStatusAcrossProductsForProgram> programData) {
        this.programData = programData;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.planningUnit);
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
        final StockStatusAcrossProductsOutput other = (StockStatusAcrossProductsOutput) obj;
        if (!Objects.equals(this.planningUnit.getId(), other.planningUnit.getId())) {
            return false;
        }
        return true;
    }
    
}
