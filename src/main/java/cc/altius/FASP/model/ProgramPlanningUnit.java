/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ProgramPlanningUnit extends BaseModel implements Serializable {

    private int programPlanningUnitId;
    private SimpleObject program;
    private SimpleObject planningUnit;
    private SimpleObject productCategory;
    private SimpleObject forecastingUnit;
    private double multiplier;
    private int reorderFrequencyInMonths; 
    private Integer minMonthsOfStock; 
    private double localProcurementLeadTime;
    private int shelfLife; 
    private double catalogPrice; 
    private int monthsInFutureForAmc; 
    private int monthsInPastForAmc; 
    private int planBasedOn; //1- MoS , 2- Qty 
    private Integer minQty; 
    private Double distributionLeadTime; 
    private List<ProgramPlanningUnitProcurementAgentPrice> programPlanningUnitProcurementAgentPrices;

    public ProgramPlanningUnit() {
        programPlanningUnitProcurementAgentPrices = new LinkedList<>();
    }

    public ProgramPlanningUnit(int programPlanningUnitId, SimpleObject program, SimpleObject planningUnit, double multiplier, SimpleObject forecastingUnit, SimpleObject productCategory, int reorderFrequencyInMonths, double localProcurementLeadTime, int shelfLife, double catalogPrice, int monthsInPastForAmc, int monthsInFutureForAmc) {
        this.programPlanningUnitId = programPlanningUnitId;
        this.program = program;
        this.planningUnit = planningUnit;
        this.multiplier = multiplier;
        this.forecastingUnit = forecastingUnit;
        this.productCategory = productCategory;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.localProcurementLeadTime = localProcurementLeadTime;
        this.shelfLife = shelfLife;
        this.catalogPrice = catalogPrice;
        this.monthsInPastForAmc = monthsInPastForAmc;
        this.monthsInFutureForAmc = monthsInFutureForAmc;
        programPlanningUnitProcurementAgentPrices = new LinkedList<>();
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

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public Integer getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(Integer minMonthsOfStock) {
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

    public int getMonthsInFutureForAmc() {
        return monthsInFutureForAmc;
    }

    public void setMonthsInFutureForAmc(int monthsInFutureForAmc) {
        this.monthsInFutureForAmc = monthsInFutureForAmc;
    }

    public int getMonthsInPastForAmc() {
        return monthsInPastForAmc;
    }

    public void setMonthsInPastForAmc(int monthsInPastForAmc) {
        this.monthsInPastForAmc = monthsInPastForAmc;
    }

    public List<ProgramPlanningUnitProcurementAgentPrice> getProgramPlanningUnitProcurementAgentPrices() {
        return programPlanningUnitProcurementAgentPrices;
    }

    public void setProgramPlanningUnitProcurementAgentPrices(List<ProgramPlanningUnitProcurementAgentPrice> programPlanningUnitProcurementAgentPrices) {
        this.programPlanningUnitProcurementAgentPrices = programPlanningUnitProcurementAgentPrices;
    }

    public int getPlanBasedOn() {
        return planBasedOn;
    }

    public void setPlanBasedOn(int planBasedOn) {
        this.planBasedOn = planBasedOn;
    }

    public Integer getMinQty() {
        return minQty;
    }

    public void setMinQty(Integer minQty) {
        this.minQty = minQty;
    }

    public Double getDistributionLeadTime() {
        return distributionLeadTime;
    }

    public void setDistributionLeadTime(Double distributionLeadTime) {
        this.distributionLeadTime = distributionLeadTime;
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

    @Override
    public String toString() {
        return "ProgramPlanningUnit{" + "programPlanningUnitId=" + programPlanningUnitId + ", program=" + program + ", planningUnit=" + planningUnit + ", reorderFrequencyInMonths=" + reorderFrequencyInMonths + ", minMonthsOfStock=" + minMonthsOfStock + ", localProcurementLeadTime=" + localProcurementLeadTime + ", shelfLife=" + shelfLife + ", catalogPrice=" + catalogPrice + ", monthsInFutureForAmc=" + monthsInFutureForAmc + ", monthsInPastForAmc=" + monthsInPastForAmc + '}';
    }

}
