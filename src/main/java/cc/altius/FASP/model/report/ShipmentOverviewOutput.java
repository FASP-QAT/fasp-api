/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ShipmentOverviewOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewFundingSourceSplit> fundingSourceSplit;
    @JsonView(Views.ReportView.class)
    List<ShipmentOverviewPlanningUnitSplit> planningUnitSplit;
    @JsonView(Views.ReportView.class)
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
