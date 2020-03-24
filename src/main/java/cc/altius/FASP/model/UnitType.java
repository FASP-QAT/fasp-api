/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author palash
 */
public class UnitType extends BaseModel implements Serializable {

    private int unitTypeId;
    private Label label;

    public UnitType() {
    }

    public UnitType(int unitTypeId, Label label) {
        this.unitTypeId = unitTypeId;
        this.label = label;
    }

    public int getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(int unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.unitTypeId;
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
        final UnitType other = (UnitType) obj;
        if (this.unitTypeId != other.unitTypeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnitType{" + "unitTypeId=" + unitTypeId + ", label=" + label + '}';
    }
   
   
}
