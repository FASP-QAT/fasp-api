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
public class StockStatusMatrixGlobalInput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private String[] realmCountryIds;
    private String[] programIds;
    private int versionId;
    private int equivalencyUnitId; // 0=No Equivalency Unit; Value means the EquivalencyUnitId is selected and therefore the report should be in terms of EU Id
    private String[] planningUnitIds; // If EU is selected then this is a multi-select. Empty means all selected. If EU=0 then this must be a single select
    private String[] stockStatusConditions; // Empty means all conditions selected
    private boolean removePlannedShipments; // remove all planned shipments in the calculation
    private boolean removePlannedShipmentsThatFailLeadTime; // remove only those planned shipments that fail the Lead Times. Only for those FS and PA that are provided below
    private String[] fundingSourceIds; // Only applies to the removePlannedShipmentsThatFailLeadTime flag; Empty means all selected
    private String[] procurementAgentIds; // Only applies to the removePlannedShipmentsThatFailLeadTime flag; Empty means all selected
    private boolean showByQty;
    private int reportView; // 1 - Group by Program; 2 - Group by Country

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

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public String[] getStockStatusConditions() {
        return stockStatusConditions;
    }

    public void setStockStatusConditions(String[] stockStatusConditions) {
        this.stockStatusConditions = stockStatusConditions;
    }

    public boolean isRemovePlannedShipments() {
        return removePlannedShipments;
    }

    public void setRemovePlannedShipments(boolean removePlannedShipments) {
        this.removePlannedShipments = removePlannedShipments;
    }

    public boolean isRemovePlannedShipmentsThatFailLeadTime() {
        return removePlannedShipmentsThatFailLeadTime;
    }

    public void setRemovePlannedShipmentsThatFailLeadTime(boolean removePlannedShipmentsThatFailLeadTime) {
        this.removePlannedShipmentsThatFailLeadTime = removePlannedShipmentsThatFailLeadTime;
    }

    public String[] getFundingSourceIds() {
        return fundingSourceIds;
    }

    public void setFundingSourceIds(String[] fundingSourceIds) {
        this.fundingSourceIds = fundingSourceIds;
    }

    public String[] getProcurementAgentIds() {
        return procurementAgentIds;
    }

    public void setProcurementAgentIds(String[] procurementAgentIds) {
        this.procurementAgentIds = procurementAgentIds;
    }

    public boolean isShowByQty() {
        return showByQty;
    }

    public void setShowByQty(boolean showByQty) {
        this.showByQty = showByQty;
    }

    public int getReportView() {
        return reportView;
    }

    public void setReportView(int reportView) {
        this.reportView = reportView;
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
            if (this.equivalencyUnitId != 0) {
                return String.join(",", this.planningUnitIds);
            } else {
                return this.planningUnitIds[0];
            }
        }
    }

    public String getStockStatusConditioinsString() {
        if (this.stockStatusConditions == null) {
            return "";
        } else {
            return String.join(",", this.stockStatusConditions);
        }
    }

    public String getFundingSourceIdsString() {
        if (this.fundingSourceIds == null) {
            return "";
        } else {
            return String.join(",", this.fundingSourceIds);
        }
    }

    public String getProcurementAgentIdsString() {
        if (this.procurementAgentIds == null) {
            return "";
        } else {
            return String.join(",", this.procurementAgentIds);
        }
    }
}
