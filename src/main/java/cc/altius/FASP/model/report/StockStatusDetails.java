/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusDetails implements Serializable {

    @JsonView(Views.ReportView.class)
    private String month;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private int consumptionQty;
    @JsonView(Views.ReportView.class)
    private boolean actualConsumption;
    @JsonView(Views.ReportView.class)
    private Double amc;
    @JsonView(Views.ReportView.class)
    private int closingBalance;
    @JsonView(Views.ReportView.class)
    private boolean actualStock;
    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private int stockStatusId;  // 0 - Stock out, 1 - Below Min, 2 - Stocked to plan, 3 - Above Max

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(int consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public boolean isActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
        this.amc = amc;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public boolean isActualStock() {
        return actualStock;
    }

    public void setActualStock(boolean actualStock) {
        this.actualStock = actualStock;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public int getStockStatusId() {
        return stockStatusId;
    }

    public void setStockStatusId(int stockStatusId) {
        this.stockStatusId = stockStatusId;
    }

}
