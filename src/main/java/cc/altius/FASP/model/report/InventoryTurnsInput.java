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
public class InventoryTurnsInput implements Serializable {

    /**
     * dt is the date that you want to run the report for
     *
     * viewBy = 1 View by RealmCountry, ViewBy = 2 View by ProductCategory
     *
     * programIds is the list of Programs that should be included in the final
     * output, cannot be empty you must pass the ProgramsIds that you want to
     * view it by
     *
     * productCategoryIds is the list of ProductCategoryIds that should be
     * included in the final output, cannot be empty if you want to select all
     * pass '0'
     *
     * includePlannedShipments = 1 means that Shipments that are in the Planned,
     * Draft, Submitted stages will also be considered in the report
     *
     * includePlannedShipments = 0 means that Shipments that are in the Planned,
     * Draft, Submitted stages will not be considered in the report
     *
     * Inventory Turns = Total Consumption for the last 12 months (including
     * current month) / Avg Stock during that period
     * 
     * * useApprovedSupplyPlanOnly = 1 means that only final approved versions will be considered
     *
     * useApprovedSupplyPlanOnly = 0 means that all the versions will be considered
     * Draft, Submitted stages will not be considered in the report
     *
     */
    private String[] programIds;
    private String[] productCategoryIds;
    private int viewBy;

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private boolean includePlannedShipments;
    private boolean useApprovedSupplyPlanOnly;

    public String[] getProgramIds() {
        return programIds;
    }

    public void setProgramIds(String[] programIds) {
        this.programIds = programIds;
    }

    public String[] getProductCategoryIds() {
        return productCategoryIds;
    }

    public void setProductCategoryIds(String[] productCategoryIds) {
        this.productCategoryIds = productCategoryIds;
    }

    public int getViewBy() {
        return viewBy;
    }

    public void setViewBy(int viewBy) {
        this.viewBy = viewBy;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public boolean isIncludePlannedShipments() {
        return includePlannedShipments;
    }

    public void setIncludePlannedShipments(boolean includePlannedShipments) {
        this.includePlannedShipments = includePlannedShipments;
    }

    public boolean isUseApprovedSupplyPlanOnly() {
        return useApprovedSupplyPlanOnly;
    }

    public void setUseApprovedSupplyPlanOnly(boolean useApprovedSupplyPlanOnly) {
        this.useApprovedSupplyPlanOnly = useApprovedSupplyPlanOnly;
    }

}
