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
public class Unit extends BaseModel implements Serializable {

    private int unitId;
    private Label label;
    private String unitCode;
    private SimpleObject dimension;

    public Unit() {
    }

    public Unit(int unitId, Label label, String unitCode) {
        this.unitId = unitId;
        this.label = label;
        this.unitCode = unitCode;
    }

    public Unit(int unitId, Label label, String unitCode, SimpleObject dimension) {
        this.unitId = unitId;
        this.label = label;
        this.unitCode = unitCode;
        this.dimension = dimension;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public SimpleObject getDimension() {
        return dimension;
    }

    public void setDimension(SimpleObject dimension) {
        this.dimension = dimension;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.unitId;
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
        final Unit other = (Unit) obj;
        if (this.unitId != other.unitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Unit{" + "unitId=" + unitId + ", label=" + label + ", unitCode=" + unitCode + ", dimension=" + dimension + '}';
    }

}
