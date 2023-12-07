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
import java.util.Date;

/**
 *
 * @author akil
 */
public class ForecastErrorInput {

    private int programId;
    private int versionId;
    private int viewBy; // 1 for Planning Unit; 2 for Forecasting Unit and 3 for Equivalency Unit 
    private int unitId;
    private String[] regionIds;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int equivalencyUnitId; // 0 if no Equivalency unit
    private int previousMonths;  // the number of months that we need to include to calculate Average for WAPE
    private boolean daysOfStockOut;

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

    public int getViewBy() {
        return viewBy;
    }

    public void setViewBy(int viewBy) {
        this.viewBy = viewBy;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(String[] regionIds) {
        this.regionIds = regionIds;
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

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
    }
    
    public String getRegionIdString() {
        if (this.regionIds == null) {
            return "";
        } else {
            String opt = String.join(",", this.regionIds);
            if (this.regionIds.length > 0) {
                return opt;
            } else {
                return "";
            }
        }
    }

    public int getPreviousMonths() {
        return previousMonths;
    }

    public void setPreviousMonths(int previousMonths) {
        this.previousMonths = previousMonths;
    }

    public boolean isDaysOfStockOut() {
        return daysOfStockOut;
    }

    public void setDaysOfStockOut(boolean daysOfStockOut) {
        this.daysOfStockOut = daysOfStockOut;
    }
    
}
