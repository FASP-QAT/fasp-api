/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ShipmentOverviewOutput implements Serializable {

    List<ShipmentOverviewFundingSourceSplit> fundingSourceSplit;
    List<ShipmentOverviewPlanningUnitSplit> planningUnitSplit;
    List<ShipmentOverviewProcurementAgentSplit> procurementAgentSplit;

    public List<ShipmentOverviewFundingSourceSplit> getFundingSourceSplit() {
        return fundingSourceSplit;
    }

    public void setFundingSourceSplit(List<ShipmentOverviewFundingSourceSplit> fundingSourceSplit) {
        this.fundingSourceSplit = fundingSourceSplit;
    }

    public List<ShipmentOverviewPlanningUnitSplit> getPlanningUnitSplit() {
        return planningUnitSplit;
    }

    public void setPlanningUnitSplit(List<ShipmentOverviewPlanningUnitSplit> planningUnitSplit) {
        this.planningUnitSplit = planningUnitSplit;
    }

    public List<ShipmentOverviewProcurementAgentSplit> getProcurementAgentSplit() {
        return procurementAgentSplit;
    }

    public void setProcurementAgentSplit(List<ShipmentOverviewProcurementAgentSplit> procurementAgentSplit) {
        this.procurementAgentSplit = procurementAgentSplit;
    }

}
