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
public class ProcurementAgentPlanningUnit extends BaseModel implements Serializable {

    private int procurementAgentPlanningUnitId;
    private SimpleObject procurementAgent;
    private SimpleObject planningUnit;
    private String skuCode;
    private Double catalogPrice;
    private Integer moq;
    private Integer unitsPerPallet;
    private Integer unitsPerContainer;
    private Double volume; // in m3
    private Double weight; // in kg

    public ProcurementAgentPlanningUnit() {
    }

    public ProcurementAgentPlanningUnit(int procurementAgentPlanningUnitId, SimpleObject procurementAgent, SimpleObject planningUnit) {
        this.procurementAgentPlanningUnitId = procurementAgentPlanningUnitId;
        this.procurementAgent = procurementAgent;
        this.planningUnit = planningUnit;
    }

    public int getProcurementAgentPlanningUnitId() {
        return procurementAgentPlanningUnitId;
    }

    public void setProcurementAgentPlanningUnitId(int procurementAgentPlanningUnitId) {
        this.procurementAgentPlanningUnitId = procurementAgentPlanningUnitId;
    }

    public SimpleObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(Double catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public Integer getMoq() {
        return moq;
    }

    public void setMoq(Integer moq) {
        this.moq = moq;
    }

    public Integer getUnitsPerPallet() {
        return unitsPerPallet;
    }

    public void setUnitsPerPallet(Integer unitsPerPallet) {
        this.unitsPerPallet = unitsPerPallet;
    }

    public Integer getUnitsPerContainer() {
        return unitsPerContainer;
    }

    public void setUnitsPerContainer(Integer unitsPerContainer) {
        this.unitsPerContainer = unitsPerContainer;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.procurementAgentPlanningUnitId;
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
        final ProcurementAgentPlanningUnit other = (ProcurementAgentPlanningUnit) obj;
        if (this.procurementAgentPlanningUnitId != other.procurementAgentPlanningUnitId) {
            return false;
        }
        return true;
    }

}
