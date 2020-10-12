/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusAcrossProductsForProgram implements Serializable {

    private SimpleCodeObject program;
    private Double amc;
    private int amcCount;
    private Integer finalClosingBalance;
    private Double mos;
    private int minMos;
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

    public int getAmcCount() {
        return amcCount;
    }

    public void setAmcCount(int amcCount) {
        this.amcCount = amcCount;
    }

    public Integer getFinalClosingBalance() {
        return finalClosingBalance;
    }

    public void setFinalClosingBalance(Integer finalClosingBalance) {
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
