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
    private String planningUnitId;
    private int reorderFrequencyInMonths;
    private int minMonthsOfStock;
    private int productCategoryId;
    private String pipelineProductName;
    private String pipelineProductCategoryName;
    private double localProcurmentLeadTime;
    private int shelfLife;
    private double catalogPrice;
    private int monthsInPastForAmc;
    private int monthsInFutureForAmc;
    
    public QatTempProgramPlanningUnit() {
    }

    public QatTempProgramPlanningUnit(String programPlanningUnitId, SimpleObject program, String planningUnitId, int reorderFrequencyInMonths, int minMonthsOfStock, int productCategoryId, String pipelineProductName, String pipelineProductCategoryName, double localProcurmentLeadTime, int shelfLife, double catalogPrice, int monthsInPastForAmc, int monthsInFutureForAmc) {
        this.programPlanningUnitId = programPlanningUnitId;
        this.program = program;
        this.planningUnitId = planningUnitId;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.minMonthsOfStock = minMonthsOfStock;
        this.productCategoryId = productCategoryId;
        this.pipelineProductName = pipelineProductName;
        this.pipelineProductCategoryName = pipelineProductCategoryName;
        this.localProcurmentLeadTime = localProcurmentLeadTime;
        this.shelfLife = shelfLife;
        this.catalogPrice = catalogPrice;
        this.monthsInFutureForAmc=monthsInFutureForAmc;
        this.monthsInPastForAmc=monthsInPastForAmc;
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

    public String getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(String planningUnitId) {
        this.planningUnitId = planningUnitId;
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

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getPipelineProductName() {
        return pipelineProductName;
    }

    public void setPipelineProductName(String pipelineProductName) {
        this.pipelineProductName = pipelineProductName;
    }

    public String getPipelineProductCategoryName() {
        return pipelineProductCategoryName;
    }

    public void setPipelineProductCategoryName(String pipelineProductCategoryName) {
        this.pipelineProductCategoryName = pipelineProductCategoryName;
    }

    public double getLocalProcurmentLeadTime() {
        return localProcurmentLeadTime;
    }

    public void setLocalProcurmentLeadTime(double localProcurmentLeadTime) {
        this.localProcurmentLeadTime = localProcurmentLeadTime;
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

    public int getMonthsInPastForAmc() {
        return monthsInPastForAmc;
    }

    public void setMonthsInPastForAmc(int monthsInPastForAmc) {
        this.monthsInPastForAmc = monthsInPastForAmc;
    }

    public int getMonthsInFutureForAmc() {
        return monthsInFutureForAmc;
    }

    public void setMonthsInFutureForAmc(int monthsInFutureForAmc) {
        this.monthsInFutureForAmc = monthsInFutureForAmc;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.programPlanningUnitId);
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
