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
    private int versionId;
    private String[] planningUnitIdList;

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

    public String[] getPlanningUnitIdList() {
        return planningUnitIdList;
    }

    public void setPlanningUnitIdList(String[] planningUnitIdList) {
        this.planningUnitIdList = planningUnitIdList;
    }

    public String getPlanningUnitIdsString() {
        if (this.planningUnitIdList == null || this.planningUnitIdList.length == 0) {
            return "";
        } else {
            return String.join(",", this.planningUnitIdList);
        }
    }

}
