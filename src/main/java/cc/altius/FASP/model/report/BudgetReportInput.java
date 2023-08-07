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
public class BudgetReportInput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] fundingSourceIds;
    private String[] programIds;

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

    public String[] getFundingSourceIds() {
        return fundingSourceIds;
    }

    public void setFundingSourceIds(String[] fundingSourceIds) {
        this.fundingSourceIds = fundingSourceIds;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramId(String[] programId) {
        this.programIds = programIds;
    }

    public String getFundingSourceIdString() {
        if (this.fundingSourceIds == null) {
            return "";
        } else {
            return String.join(",", this.fundingSourceIds);
        }
    }

    public String getProgramIdString() {
        if (this.programIds == null) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }

}
