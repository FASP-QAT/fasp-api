/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author ekta
 */
public class AnnualShipmentCostOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    SimpleCodeObject procurementAgent;
    @JsonView(Views.ReportView.class)
    SimpleCodeObject fundingSource;
    @JsonView(Views.ReportView.class)
    SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    Map<String, Double> shipmentAmt;

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Map<String, Double> getShipmentAmt() {
        return shipmentAmt;
    }

    public void setShipmentAmt(Map<String, Double> shipmentAmt) {
        this.shipmentAmt = shipmentAmt;
    }

}
