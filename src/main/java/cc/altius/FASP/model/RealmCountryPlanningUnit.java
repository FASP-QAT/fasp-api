/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class RealmCountryPlanningUnit extends BaseModel implements Serializable {

    @JsonView(Views.ReportView.class)
    private int realmCountryPlanningUnitId;
    @JsonView(Views.ReportView.class)
    private SimpleObject realmCountry;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private String skuCode;
    @JsonView(Views.ReportView.class)
    private Label label;
    @JsonView(Views.ReportView.class)
    private int conversionMethod; // 1 for Multiply and 2 for Divide
    @JsonView(Views.ReportView.class)
    private double conversionNumber;
    private Unit unit;

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

    public int getConversionMethod() {
        return conversionMethod;
    }

    public void setConversionMethod(int conversionMethod) {
        this.conversionMethod = conversionMethod;
    }

    public double getConversionNumber() {
        return conversionNumber;
    }

    public void setConversionNumber(double conversionNumber) {
        this.conversionNumber = conversionNumber;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public double getMultiplier() {
        switch (this.conversionMethod) {
            case 1:
            case 0:
                return this.conversionNumber;
            case 2:
                return 1.0 / this.conversionNumber;
            default:
                return 0;
        }
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

    @Override
    public String toString() {
        return "RealmCountryPlanningUnit{" + "realmCountryPlanningUnitId=" + realmCountryPlanningUnitId + ", realmCountry=" + realmCountry + ", planningUnit=" + planningUnit + ", skuCode=" + skuCode + ", label=" + label + ", conversionMethod=" + conversionMethod + ", conversionNumber=" + conversionNumber + ", unit=" + unit + '}';
    }

}
