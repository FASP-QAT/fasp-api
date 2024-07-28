/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.utils.ArrayUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class StockStatusVerticalInput implements Serializable {

    private boolean singleProgram;
    private String[] programIds; // Will be used when singleProgram is false
    private int programId; // Will be used only if singleProgram is true
    private int versionId; // Will be used only if singleProgram is true
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int viewBy; // 1 for PU, 2 for ARU
    private int[] reportingUnitIds;
    private int equivalencyUnitId; // actual equivalencyUnitId if the output should be in equivalencyUnit or 0 if it is PU or ARU

    public boolean isSingleProgram() {
        return singleProgram;
    }

    public void setSingleProgram(boolean singleProgram) {
        this.singleProgram = singleProgram;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public int getViewBy() {
        return viewBy;
    }

    public void setViewBy(int viewBy) {
        this.viewBy = viewBy;
    }

    public int[] getReportingUnitIds() {
        return reportingUnitIds;
    }

    public void setReportingUnitIds(int[] reportingUnitIds) {
        this.reportingUnitIds = reportingUnitIds;
    }

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
    }

    public String getReportingUnitIdsString() {
        return ArrayUtils.convertArrayToString(reportingUnitIds);
    }
    
    public String getProgramIdsString() {
        return ArrayUtils.convertArrayToString(programIds);
    }
}
