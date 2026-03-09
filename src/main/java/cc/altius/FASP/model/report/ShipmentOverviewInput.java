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
    private int versionId; // If a Single Program is selected then versionId is relevant. When Multiple Programs are selected this should be 0. If single program is selected then cannot be 0. -1 for latest versionId
    private String[] planningUnitIds;
    private String[] fundingSourceIds;
    private String[] shipmentStatusIds;
    private int reportView; // 1 = Funding Source, 2 = Procurement Agent

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

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getReportView() {
        return reportView;
    }

    public void setReportView(int reportView) {
        this.reportView = reportView;
    }

    public String getFspa() {
        if (this.reportView == 1) {
            return "FS";
        }
        if (this.reportView == 2) {
            return "PA";
        } else {
            return "XX";
        }
    }

}
