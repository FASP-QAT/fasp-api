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
public class ShipmentGlobalDemandInput implements Serializable {

    private int realmId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] realmCountryIds;
    private String[] programIds;
    private int equivalencyUnitId; // 0 - Means no Equivalency Unit; Valid number means there is an Equivalency Unit and therefore PU becomes Multi-select
    private String[] planningUnitIds;
    private int reportView; // 1 = Funding Source, 2 = Procurement Agent, 3 = Procurement Agent Type, 4 = Funding Source Type
    private String[] fundingSourceProcurementAgentIds;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
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

    public int getEquivalencyUnitId() {
        return equivalencyUnitId;
    }

    public void setEquivalencyUnitId(int equivalencyUnitId) {
        this.equivalencyUnitId = equivalencyUnitId;
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

    public String getFundingSourceProcurementAgentIdsString() {
        if (this.fundingSourceProcurementAgentIds == null) {
            return "";
        } else {
            return String.join(",", this.fundingSourceProcurementAgentIds);
        }
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
        if (this.programIds == null) {
            return "";
        } else {
            if (isEquivalencyUnitSelected()) {
                return String.join(",", this.planningUnitIds);
            } else {
                return this.planningUnitIds[0];
            }
        }
    }
    
    public boolean isEquivalencyUnitSelected() {
        return this.equivalencyUnitId != 0;
    }

}
