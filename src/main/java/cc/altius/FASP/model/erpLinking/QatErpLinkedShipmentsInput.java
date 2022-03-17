/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.erpLinking;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class QatErpLinkedShipmentsInput implements Serializable {

    private int programId;
    private String[] planningUnitIdList;
    private int procurementAgentId;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String[] getPlanningUnitIdList() {
        return planningUnitIdList;
    }

    public void setPlanningUnitIdList(String[] planningUnitIdList) {
        this.planningUnitIdList = planningUnitIdList;
    }

    public int getProcurementAgentId() {
        return procurementAgentId;
    }

    public void setProcurementAgentId(int procurementAgentId) {
        this.procurementAgentId = procurementAgentId;
    }

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIdList == null || this.planningUnitIdList.length == 0) {
            return "";
        } else {
            return String.join(",", this.planningUnitIdList);
        }
    }

}
