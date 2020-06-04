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
public class RealmCountryPlanningUnit extends BaseModel implements Serializable {

    private int realmCountryPlanningUnitId;
    private SimpleObject realmCountry;
    private SimpleObject planningUnit;
    private String skuCode;
    private Label label;
    private double multiplier;
    private Unit unit;
    private String gtin;

    public RealmCountryPlanningUnit() {
    }

    public RealmCountryPlanningUnit(int realmCountryPlanningUnitId, SimpleObject realmCountry, SimpleObject planningUnit) {
        this.realmCountryPlanningUnitId = realmCountryPlanningUnitId;
        this.realmCountry = realmCountry;
        this.planningUnit = planningUnit;
    }

    public int getRealmCountryPlanningUnitId() {
        return realmCountryPlanningUnitId;
    }

    public void setRealmCountryPlanningUnitId(int realmCountryPlanningUnitId) {
        this.realmCountryPlanningUnitId = realmCountryPlanningUnitId;
    }

    public SimpleObject getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(SimpleObject realmCountry) {
        this.realmCountry = realmCountry;
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

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
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
        int hash = 7;
        hash = 83 * hash + this.realmCountryPlanningUnitId;
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
        final RealmCountryPlanningUnit other = (RealmCountryPlanningUnit) obj;
        if (this.realmCountryPlanningUnitId != other.realmCountryPlanningUnitId) {
            return false;
        }
        return true;
    }

}
