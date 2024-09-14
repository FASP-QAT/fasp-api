/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author akil
 */
public class ActualConsumptionData implements Serializable {

    private int programId;
    private int versionId;
    private String[] planningUnitIds;
    private String[] regionIds;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public String[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String[] regionIds) {
        this.regionIds = regionIds;
    }

    public String getPlanningUnitIdString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            return String.join(",", this.planningUnitIds);
        }
    }

    public String getRegionIdString() {
        if (this.regionIds == null) {
            return "";
        } else {
            return String.join(",", this.regionIds);
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.programId;
        hash = 67 * hash + this.versionId;
        hash = 67 * hash + Arrays.deepHashCode(this.planningUnitIds);
        hash = 67 * hash + Arrays.deepHashCode(this.regionIds);
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
        final ActualConsumptionData other = (ActualConsumptionData) obj;
        if (this.programId != other.programId) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if (!Arrays.deepEquals(this.planningUnitIds, other.planningUnitIds)) {
            return false;
        }
        return Arrays.deepEquals(this.regionIds, other.regionIds);
    }
    
    
}
