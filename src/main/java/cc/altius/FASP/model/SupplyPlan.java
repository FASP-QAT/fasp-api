/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class SupplyPlan implements Serializable {

    private int programId;
    private int versionId;
    private List<SupplyPlanDate> supplyPlanDateList;

    public int getPrevClosingBalance(int planningUnitId, int batchId, String transDate) {
        SupplyPlanDate s1 = new SupplyPlanDate(planningUnitId, transDate);
        int idx1 = this.supplyPlanDateList.indexOf(s1);
        if (idx1 == -1) {
            return 0;
        } else {
            SupplyPlanBatchInfo s2 = new SupplyPlanBatchInfo(batchId);
            int idx2 = this.supplyPlanDateList.get(idx1).getBatchList().indexOf(s2);
            if (idx2 == -1) {
                return 0;
            } else {
                return this.supplyPlanDateList.get(idx1).getBatchList().get(idx2).getClosingBalance();
            }
        }
    }
    
    public int getPrevClosingBalanceWps(int planningUnitId, int batchId, String transDate) {
        SupplyPlanDate s1 = new SupplyPlanDate(planningUnitId, transDate);
        int idx1 = this.supplyPlanDateList.indexOf(s1);
        if (idx1 == -1) {
            return 0;
        } else {
            SupplyPlanBatchInfo s2 = new SupplyPlanBatchInfo(batchId);
            int idx2 = this.supplyPlanDateList.get(idx1).getBatchList().indexOf(s2);
            if (idx2 == -1) {
                return 0;
            } else {
                return this.supplyPlanDateList.get(idx1).getBatchList().get(idx2).getClosingBalanceWps();
            }
        }
    }

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

    public List<SupplyPlanDate> getSupplyPlanDateList() {
        return supplyPlanDateList;
    }

    public void setSupplyPlanDateList(List<SupplyPlanDate> supplyPlanDateList) {
        this.supplyPlanDateList = supplyPlanDateList;
    }

}
