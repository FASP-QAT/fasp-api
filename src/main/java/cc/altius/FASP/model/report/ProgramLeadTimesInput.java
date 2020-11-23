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
public class ProgramLeadTimesInput implements Serializable {

    private int programId;
    private String[] planningUnitIds;
    private String[] procurementAgentIds;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String[] getPlanningUnitIds() {
        return planningUnitIds;
    }

    public void setPlanningUnitIds(String[] planningUnitIds) {
        this.planningUnitIds = planningUnitIds;
    }

    public String[] getProcurementAgentIds() {
        return procurementAgentIds;
    }

    public void setProcurementAgentIds(String[] procurementAgentIds) {
        this.procurementAgentIds = procurementAgentIds;
    }

    public String getPlanningUnitIdString() {
        if (this.planningUnitIds == null) {
            return "";
        } else {
            String opt = String.join("','", this.planningUnitIds);
            if (this.planningUnitIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
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

    @Override
    public String toString() {
        return "ProgramLeadTimesInput{" + "programId=" + programId + ", planningUnitIds=" + planningUnitIds + ", procurementAgentIds=" + procurementAgentIds + '}';
    }

}
