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
public class ShipmentOverviewInput implements Serializable {

    private int realmId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] realmCountryIds;
    private String[] programIds;
    private String[] planningUnitIds;
    private String[] fundingSourceIds;
    private String[] shipmentStatusIds;
    private boolean useApprovedSupplyPlanOnly;
    private boolean groupByProcurementAgentType;
    private boolean groupByFundingSourceType;

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

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public String[] getFundingSourceIds() {
        return fundingSourceIds;
    }

    public void setFundingSourceIds(String[] fundingSourceIds) {
        this.fundingSourceIds = fundingSourceIds;
    }

    public String[] getShipmentStatusIds() {
        return shipmentStatusIds;
    }

    public void setShipmentStatusIds(String[] shipmentStatusIds) {
        this.shipmentStatusIds = shipmentStatusIds;
    }

    public String getRealmCountryIdsString() {
        if (this.realmCountryIds == null || this.realmCountryIds.length == 0) {
            return "";
        } else {
            return String.join(",", this.realmCountryIds);
        }
    }

    public String getProgramIdsString() {
        if (this.programIds == null || this.programIds.length == 0) {
            return "";
        } else {
            return String.join(",", this.programIds);
        }
    }

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIds == null || this.planningUnitIds.length == 0) {
            return "";
        } else {
            return String.join(",", this.planningUnitIds);
        }
    }

    public String getFundingSourceIdsString() {
        if (this.fundingSourceIds == null || this.fundingSourceIds.length == 0) {
            return "";
        } else {
            return String.join(",", this.fundingSourceIds);
        }
    }

    public String getShipmentStatusIdsString() {
        if (this.shipmentStatusIds == null || this.shipmentStatusIds.length == 0) {
            return "";
        } else {
            return String.join(",", this.shipmentStatusIds);
        }
    }

    public boolean isUseApprovedSupplyPlanOnly() {
        return useApprovedSupplyPlanOnly;
    }

    public void setUseApprovedSupplyPlanOnly(boolean useApprovedSupplyPlanOnly) {
        this.useApprovedSupplyPlanOnly = useApprovedSupplyPlanOnly;
    }

    public boolean isGroupByProcurementAgentType() {
        return groupByProcurementAgentType;
    }

    public void setGroupByProcurementAgentType(boolean groupByProcurementAgentType) {
        this.groupByProcurementAgentType = groupByProcurementAgentType;
    }

    public boolean isGroupByFundingSourceType() {
        return groupByFundingSourceType;
    }

    public void setGroupByFundingSourceType(boolean groupByFundingSourceType) {
        this.groupByFundingSourceType = groupByFundingSourceType;
    }

}
