/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class CostOfInventoryInput implements Serializable {

    private int programId;
    private String[] regionIds;
    private String[] planningUnitIds;
    private int versionId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private boolean includePlannedShipments;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String[] getRegions() {
        return regionIds;
    }

    public void setRegions(String[] regions) {
        this.regionIds = regions;
    }

    public String[] getPlanningUnits() {
        return planningUnitIds;
    }

    public void setPlanningUnits(String[] planningUnits) {
        this.planningUnitIds = planningUnits;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public boolean isIncludePlannedShipments() {
        return includePlannedShipments;
    }

    public void setIncludePlannedShipments(boolean includePlannedShipments) {
        this.includePlannedShipments = includePlannedShipments;
    }

    public String getPlanningUnitIdString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.planningUnitIds);
            if (this.planningUnitIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }

    public String getRegionIdString() {
        if (this.regionIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.regionIds);
            if (this.regionIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
}
