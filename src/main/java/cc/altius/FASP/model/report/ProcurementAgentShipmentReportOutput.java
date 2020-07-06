/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProcurementAgentShipmentReportOutput extends ShipmentReportOutput implements Serializable {

    private SimpleCodeObject procurementAgent;

    public ProcurementAgentShipmentReportOutput(SimpleObject planningUnit, int qty, double productCost, double freightPerc, double freightCost) {
        super(planningUnit, qty, productCost, freightPerc, freightCost);
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

}
