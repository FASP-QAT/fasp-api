/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author altius
 */
public class Region extends BaseModel {

    private int regionId;
    private Label label;
    private RealmCountry realmCountry;

    public Region(int regionId, Label label) {
        this.regionId = regionId;
        this.label = label;
    }

    public Region() {
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public RealmCountry getRealmCountry() {
        return realmCountry;
    }

    public void setRealmCountry(RealmCountry realmCountry) {
        this.realmCountry = realmCountry;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.regionId;
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
        final Region other = (Region) obj;
        if (this.regionId != other.regionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Region{" + "regionId=" + regionId + ", label=" + label + '}';
    }

}
