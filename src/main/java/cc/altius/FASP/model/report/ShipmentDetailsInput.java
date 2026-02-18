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
public class ShipmentDetailsInput {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] realmCountryIds;
    private String[] programIds;
    private Integer versionId; // Only to be used if Single Program is selected, if more than 1 Program is selected it should be null
    private String[] planningUnitIds;
    private int reportView; // 1 - Funding Source, 2 - Procurement Agent
    private String[] fundingSourceProcurementAgentIds;
    private String[] budgetIds; // Only to be considered if reportView=1

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

    public String[] getRealmCountryIds() {
        return realmCountryIds;
    }

    public void setRealmCountryIds(String[] realmCountryIds) {
        this.realmCountryIds = realmCountryIds;
    }

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public Integer getVersionId() {
        return versionId;
    }

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public int getReportView() {
        return reportView;
    }

    public void setReportView(int reportView) {
        this.reportView = reportView;
    }

    public String[] getFundingSourceProcurementAgentIds() {
        return fundingSourceProcurementAgentIds;
    }

    public void setFundingSourceProcurementAgentIds(String[] fundingSourceProcurementAgentIds) {
        this.fundingSourceProcurementAgentIds = fundingSourceProcurementAgentIds;
    }

    public String[] getBudgetIds() {
        return budgetIds;
    }

    public void setBudgetIds(String[] budgetIds) {
        this.budgetIds = budgetIds;
    }

    public String getRealmCountryIdsString() {
        if (this.realmCountryIds == null) {
            return "";
        } else {
            return String.join(",", this.realmCountryIds);
        }
    }
    
    public String getProgramIdsString() {
        if (this.programIds == null) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }
    
    public String getPlanningUnitIdsString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            return String.join(",", this.planningUnitIds);
        }
    }
    
    public String getFundingSourceProcurementAgentIdsString() {
        if (this.fundingSourceProcurementAgentIds == null) {
            return "";
        } else {
            return String.join(",", this.fundingSourceProcurementAgentIds);
        }
    }
    
    public String getBudgetIdsString() {
        if (this.budgetIds == null) {
            return "";
        } else {
            return String.join(",", this.budgetIds);
        }
    }

}
