/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusOverTimeOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private String dt;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private Long stock;
    @JsonView(Views.ReportView.class)
    private Long consumptionQty;
    @JsonView(Views.ReportView.class)
    private Boolean actualConsumption;
    @JsonView(Views.ReportView.class)
    private Double amc;
    @JsonView(Views.ReportView.class)
    private int amcMonthCount;
    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private int mosPast;
    @JsonView(Views.ReportView.class)
    private int mosFuture;

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

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Long consumptionQty) {
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

    public int getMosPast() {
        return mosPast;
    }

    public void setMosPast(int mosPast) {
        this.mosPast = mosPast;
    }

    public int getMosFuture() {
        return mosFuture;
    }

    public void setMosFuture(int mosFuture) {
        this.mosFuture = mosFuture;
    }

}
