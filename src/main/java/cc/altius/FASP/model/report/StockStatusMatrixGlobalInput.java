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
    private int removePlannedShipments; // 0 - Retain all Planned Shipments, 1 - Remove all Planned Shipments, 2 - Remove all Planned Shipments that have Funding Source TBD
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

    public int getRemovePlannedShipments() {
        return removePlannedShipments;
    }

    public void setRemovePlannedShipments(int removePlannedShipments) {
        this.removePlannedShipments = removePlannedShipments;
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

}
