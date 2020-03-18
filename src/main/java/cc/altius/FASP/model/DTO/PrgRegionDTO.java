/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgRegionDTO {

    private int regionId;
    private PrgLabelDTO label;
    private double capacityCbm;
    private boolean active;
    private int realmId;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }
    
    

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public double getCapacityCbm() {
        return capacityCbm;
    }

    public void setCapacityCbm(double capacityCbm) {
        this.capacityCbm = capacityCbm;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.regionId;
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
        final PrgRegionDTO other = (PrgRegionDTO) obj;
        if (this.regionId != other.regionId) {
            return false;
        }
        return true;
    }

}
