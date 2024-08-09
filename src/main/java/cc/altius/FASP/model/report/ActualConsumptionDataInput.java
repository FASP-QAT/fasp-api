/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.DTO.ProgramAndVersionDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ActualConsumptionDataInput implements Serializable {

    private ProgramAndVersionDTO[] programVersionList;
    private String[] planningUnitIds;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] regionIds;

    public ProgramAndVersionDTO[] getProgramVersionList() {
        return programVersionList;
    }

    public void setProgramVersionList(ProgramAndVersionDTO[] programVersionList) {
        this.programVersionList = programVersionList;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
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
}
