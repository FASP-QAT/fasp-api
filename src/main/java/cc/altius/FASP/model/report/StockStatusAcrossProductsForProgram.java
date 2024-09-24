/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsForProgram implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private Double amc;
    @JsonView(Views.ReportView.class)
    private long amcCount;
    @JsonView(Views.ReportView.class)
    private Double finalClosingBalance;
    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private int minMos;
    @JsonView(Views.ReportView.class)
    private int maxMos;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
        this.amc = amc;
    }

    public long getAmcCount() {
        return amcCount;
    }

    public void setAmcCount(long amcCount) {
        this.amcCount = amcCount;
    }

    public Double getFinalClosingBalance() {
        return finalClosingBalance;
    }

    public void setFinalClosingBalance(Double finalClosingBalance) {
        this.finalClosingBalance = finalClosingBalance;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public int getMinMos() {
        return minMos;
    }

    public void setMinMos(int minMos) {
        this.minMos = minMos;
    }

    public int getMaxMos() {
        return maxMos;
    }

    public void setMaxMos(int maxMos) {
        this.maxMos = maxMos;
    }

    @JsonView(Views.ReportView.class)
    public String getOutputString() {
        if (mos == null || mos == 0) {
            return "OUT";
        } else if (mos != null && mos < minMos) {
            return "low";
        } else if (mos != null && mos >= minMos && mos <= maxMos) {
            return "";
        } else if (mos != null && mos > maxMos) {
            return "excess";
        } else {
            return "unkown";
        }
    }

}
