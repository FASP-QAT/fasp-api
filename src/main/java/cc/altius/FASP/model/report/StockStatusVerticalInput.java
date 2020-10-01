/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class StockStatusVerticalInput implements Serializable {
    
    private int programId;
    private int versionId;
    private String startDate;
    private String stopDate;
    private int planningUnitId;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    @Override
    public String toString() {
        return "StockStatusVerticalInput{" + "programId=" + programId + ", versionId=" + versionId + ", startDate=" + startDate + ", stopDate=" + stopDate + ", planningUnitId=" + planningUnitId + "}\n";
    }
    
    
}
