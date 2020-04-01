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
public class Dimension extends BaseModel implements Serializable {

    private int dimensionId;
    private Label label;

    public Dimension() {
    }

    public Dimension(int dimensionId, Label label) {
        this.dimensionId = dimensionId;
        this.label = label;
    }

    public int getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(int dimensionId) {
        this.dimensionId = dimensionId;
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
        hash = 97 * hash + this.dimensionId;
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
        final Dimension other = (Dimension) obj;
        if (this.dimensionId != other.dimensionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dimension{" + "dimensionId=" + dimensionId + ", label=" + label + '}';
    }
   
   
}
