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
public class PlanningUnitForRealmCountryMapping extends BaseModel implements Serializable {

    private int planningUnitId;
    private Label label; // name of PlanningUnit
    private String skuCode;
    private Label countrySku;
    private double multiplier;
    private Unit unit;
    private String gtin;

    public PlanningUnitForRealmCountryMapping() {
    }

    public PlanningUnitForRealmCountryMapping(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

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

    public Label getCountrySku() {
        return countrySku;
    }

    public void setCountrySku(Label countrySku) {
        this.countrySku = countrySku;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.planningUnitId;
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
        final PlanningUnitForRealmCountryMapping other = (PlanningUnitForRealmCountryMapping) obj;
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        return true;
    }

}
