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
    private String regionList;
    private String planningUnitList;
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

    public String getRegionList() {
        return regionList;
    }

    public void setRegionList(String regionList) {
        this.regionList = regionList;
    }

    public String getPlanningUnitList() {
        return planningUnitList;
    }

    public void setPlanningUnitList(String planningUnitList) {
        this.planningUnitList = planningUnitList;
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

}
