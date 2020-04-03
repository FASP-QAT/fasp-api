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
public class PlanningUnitForProcurementAgentMapping extends BaseModel implements Serializable {

    private int planningUnitId;
    private Label label;
    private String skuCode;
    private Double catalogPrice;
    private Integer moq;
    private Integer unitsPerPallet;
    private Integer unitsPerContainer;
    private Double volume; // in m3
    private Double weight; // in kg

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
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
        hash = 23 * hash + this.planningUnitId;
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
        final PlanningUnitForProcurementAgentMapping other = (PlanningUnitForProcurementAgentMapping) obj;
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        return true;
    }
    
    

}
