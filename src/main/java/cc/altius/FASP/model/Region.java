/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author altius
 */
public class Region extends BaseModel {

    private int regionId;
    private Label label;
    private RealmCountry realmCountry;
    private String gln;
    private Double capacityCbm;

    public Region() {
    }

    public Region(int regionId, Label label) {
        this.regionId = regionId;
        this.label = label;
    }

    public Region(int regionId, Label label, RealmCountry realmCountry) {
        this.regionId = regionId;
        this.label = label;
        this.realmCountry = realmCountry;
    }

    public int getRegionId() {
        return regionId;
    }

    @JsonIgnore
    public String getRegionIdString() {
        return Integer.toString(regionId);
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

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }

    public Double getCapacityCbm() {
        return capacityCbm;
    }

    public void setCapacityCbm(Double capacityCbm) {
        this.capacityCbm = capacityCbm;
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
