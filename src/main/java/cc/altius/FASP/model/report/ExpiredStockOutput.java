/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Batch;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ExpiredStockOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private Batch batchInfo;
    @JsonView(Views.ReportView.class)
    private Double cost;
    @JsonView(Views.ReportView.class)
    private long expiredQty;
    @JsonView(Views.ReportView.class)
    private List<SimpleBatchQuantityWithTransHistory> batchHistory;

    public ExpiredStockOutput() {
        this.batchHistory = new LinkedList<>();
    }

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

    public long getExpiredQty() {
        return expiredQty;
    }

    public void setExpiredQty(long expiredQty) {
        this.expiredQty = expiredQty;
    }

    public List<SimpleBatchQuantityWithTransHistory> getBatchHistory() {
        return batchHistory;
    }

    public void setBatchHistory(List<SimpleBatchQuantityWithTransHistory> batchHistory) {
        this.batchHistory = batchHistory;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.program);
        hash = 97 * hash + Objects.hashCode(this.planningUnit);
        hash = 97 * hash + Objects.hashCode(this.batchInfo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExpiredStockOutput other = (ExpiredStockOutput) obj;
        if (!Objects.equals(this.program.getId(), other.program.getId())) {
            return false;
        }
        if (!Objects.equals(this.planningUnit.getId(), other.planningUnit.getId())) {
            return false;
        }
        if (!Objects.equals(this.batchInfo.getBatchId(), other.batchInfo.getBatchId())) {
            return false;
        }
        return true;
    }

}
