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
public class ProgramPlanningUnit extends BaseModel implements Serializable {

    private int programPlanningUnitId;
    private SimpleObject program;
    private SimpleObject planningUnit;
    private int reorderFrequencyInMonths;
    private int minMonthsOfStock;
    private double localProcurementLeadTime;
    private int shelfLife;
    private double catalogPrice;

    public ProgramPlanningUnit() {
    }

    public ProgramPlanningUnit(int programPlanningUnitId, SimpleObject program, SimpleObject planningUnit, int reorderFrequencyInMonths, int minMonthsOfStock, double localProcurementLeadTime, int shelfLife, double catalogPrice) {
        this.programPlanningUnitId = programPlanningUnitId;
        this.program = program;
        this.planningUnit = planningUnit;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.minMonthsOfStock = minMonthsOfStock;
        this.localProcurementLeadTime = localProcurementLeadTime;
        this.shelfLife = shelfLife;
        this.catalogPrice = catalogPrice;
    }

    public int getProgramPlanningUnitId() {
        return programPlanningUnitId;
    }

    public void setProgramPlanningUnitId(int programPlanningUnitId) {
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

    public double getLocalProcurementLeadTime() {
        return localProcurementLeadTime;
    }

    public void setLocalProcurementLeadTime(double localProcurementLeadTime) {
        this.localProcurementLeadTime = localProcurementLeadTime;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public double getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(double catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.programPlanningUnitId;
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
        final ProgramPlanningUnit other = (ProgramPlanningUnit) obj;
        if (this.programPlanningUnitId != other.programPlanningUnitId) {
            return false;
        }
        return true;
    }

}
