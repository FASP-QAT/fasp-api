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
public class StockStatusMatrixInput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date stopDate;
    private int programId;
    private int versionId;
    private String[] planningUnitIds; // Empty means all selected
    private String[] stockStatusConditions; // Empty means all conditions selected
    private int removePlannedShipments; // 0 - Retain all Planned Shipments, 1 - Remove all Planned Shipments, 2 - Remove all Planned Shipments that have Funding Source TBD
    private String[] fundingSourceIds; // Only applies to the removePlannedShipmentsThatFailLeadTime flag; Empty means all selected
    private String[] procurementAgentIds; // Only applies to the removePlannedShipmentsThatFailLeadTime flag; Empty means all selected
    private boolean showByQty;

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

    public int getRemovePlannedShipments() {
        return removePlannedShipments;
    }

    public void setRemovePlannedShipments(int removePlannedShipments) {
        this.removePlannedShipments = removePlannedShipments;
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

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            return String.join(",", this.planningUnitIds);
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
