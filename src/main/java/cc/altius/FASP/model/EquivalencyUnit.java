/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class EquivalencyUnit extends BaseModel implements Serializable {

    private int equivalencyUnitId;
    private SimpleCodeObject realm;
    private Label label;

    public EquivalencyUnit() {
    }

    public EquivalencyUnit(int equivalencyUnitId, SimpleCodeObject realm, Label label) {
        this.equivalencyUnitId = equivalencyUnitId;
        this.realm = realm;
        this.label = label;
    }

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
    }

    public SimpleCodeObject getRealm() {
        return realm;
    }

    public void setRealm(SimpleCodeObject realm) {
        this.realm = realm;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.equivalencyUnitId;
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
        final EquivalencyUnit other = (EquivalencyUnit) obj;
        if (this.equivalencyUnitId != other.equivalencyUnitId) {
            return false;
        }
        return true;
    }

}
