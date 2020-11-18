/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProcurementAgentShipmentReportInput extends ShipmentReportInput implements Serializable {

    private String[] procurementAgentIds;

    public String[] getProcurementAgentIds() {
        return procurementAgentIds;
    }

    public void setProcurementAgentIds(String[] procurementAgentIds) {
        this.procurementAgentIds = procurementAgentIds;
    }

    public String getProcurementAgentIdString() {
        if (this.procurementAgentIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.procurementAgentIds);
            if (this.procurementAgentIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
}
