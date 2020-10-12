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
public class StockStatusOverTimeOutput implements Serializable {

    private String dt;
    private SimpleCodeObject program;
    private SimpleObject planningUnit;
    private Integer stock;
    private Integer consumptionQty;
    private Boolean actualConsumption;
    private Double amc;
    private int amcMonthCount;
    private Double mos;

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Integer consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
        this.amc = amc;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }


    public int getAmcMonthCount() {
        return amcMonthCount;
    }

    public void setAmcMonthCount(int amcMonthCount) {
        this.amcMonthCount = amcMonthCount;
    }

    // 0 - Forecasted
    // 1 - Actual
    // null - no consumption data
    public Boolean getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }
    
}
