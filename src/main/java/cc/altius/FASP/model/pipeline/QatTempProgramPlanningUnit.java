/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.pipeline;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author altius
 */
public class QatTempProgramPlanningUnit extends BaseModel implements Serializable {
    
    private String programPlanningUnitId;
    private SimpleObject program;
    private SimpleObject planningUnit;
    private int reorderFrequencyInMonths;
    private int minMonthsOfStock;
    private int productCategoryId;
    
    public QatTempProgramPlanningUnit() {
    }

    public QatTempProgramPlanningUnit(String programPlanningUnitId, SimpleObject program, SimpleObject planningUnit, int reorderFrequencyInMonths, int minMonthsOfStock, int productCategoryId) {
        this.programPlanningUnitId = programPlanningUnitId;
        this.program = program;
        this.planningUnit = planningUnit;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.minMonthsOfStock = minMonthsOfStock;
        this.productCategoryId=productCategoryId;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    
    
    public String getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(String programPlanningUnitId) {
        this.programPlanningUnitId = programPlanningUnitId;
    }

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.programPlanningUnitId);
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
        final QatTempProgramPlanningUnit other = (QatTempProgramPlanningUnit) obj;
        if (!Objects.equals(this.programPlanningUnitId, other.programPlanningUnitId)) {
            return false;
        }
        return true;
    }

    
}
