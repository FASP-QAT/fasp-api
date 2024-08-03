/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.utils.ArrayUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class StockStatusVerticalInput implements Serializable {

    private boolean aggregate; // True if you want the results to be aggregated and False if you want Individual Supply Plans for the Multi-Select information
    private int[] programIds; // Will be used when singleProgram is false
    @JsonIgnore
    private int programId; // Will be used only if aggregate is false
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int viewBy; // 1 for PU, 2 for ARU
    private int[] reportingUnitIds;
    @JsonIgnore
    private int reportingUnitId; // Will be used only if aggregate is false
    private int equivalencyUnitId; // actual equivalencyUnitId if the output should be in equivalencyUnit or 0 if it is PU or ARU

    public boolean isAggregate() {
        return aggregate;
    }

    public void setAggregate(boolean aggregate) {
        this.aggregate = aggregate;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(int[] programIds) {
        this.programIds = programIds;
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

    public int getReportingUnitId() {
        return reportingUnitId;
    }

    public void setReportingUnitId(int reportingUnitId) {
        this.reportingUnitId = reportingUnitId;
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
