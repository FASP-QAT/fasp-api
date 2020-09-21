/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ExpiredStockOutput implements Serializable {

    private SimpleCodeObject program;
    private SimpleObject planningUnit;
    private Batch batchInfo;
    private int expiredQty;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Batch getBatchInfo() {
        return batchInfo;
    }

    public void setBatchInfo(Batch batchInfo) {
        this.batchInfo = batchInfo;
    }

    public int getExpiredQty() {
        return expiredQty;
    }

    public void setExpiredQty(int expiredQty) {
        this.expiredQty = expiredQty;
    }

}
