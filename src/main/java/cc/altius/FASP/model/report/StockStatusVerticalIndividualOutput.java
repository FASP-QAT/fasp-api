/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class StockStatusVerticalIndividualOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject reportingUnit; // PU or ARU
    @JsonView(Views.ReportView.class)
    private int reorderFrequencyInMonths;
    @JsonView(Views.ReportView.class)
    private Integer minMonthsOfStock;
    @JsonView(Views.ReportView.class)
    private double localProcurementLeadTime;
    @JsonView(Views.ReportView.class)
    private int shelfLife;
    @JsonView(Views.ReportView.class)
    private int monthsInFutureForAmc;
    @JsonView(Views.ReportView.class)
    private int monthsInPastForAmc;
    @JsonView(Views.ReportView.class)
    private int planBasedOn; //1- MoS , 2- Qty 
    @JsonView(Views.ReportView.class)
    private Integer minQty;
    @JsonView(Views.ReportView.class)
    private Double distributionLeadTime;
    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private List<ConsumptionInfo> consumptionInfo;
    @JsonView(Views.ReportView.class)
    private List<InventoryInfo> inventoryInfo;
    @JsonView(Views.ReportView.class)
    List<StockStatusVertical> stockStatusVertical;
    @JsonView(Views.ReportView.class)
    private String ppuNotes;

    public StockStatusVerticalIndividualOutput() {
        this.stockStatusVertical = new LinkedList<>();
        this.consumptionInfo = new LinkedList<>();
        this.inventoryInfo = new LinkedList<>();
    }

    public StockStatusVerticalIndividualOutput(SimpleObject reportingUnit, int reorderFrequencyInMonths, Integer minMonthsOfStock, double localProcurementLeadTime, int shelfLife, int monthsInFutureForAmc, int monthsInPastForAmc, int planBasedOn, Integer minQty, Double distributionLeadTime, String notes) {
        this.reportingUnit = reportingUnit;
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
        this.minMonthsOfStock = minMonthsOfStock;
        this.localProcurementLeadTime = localProcurementLeadTime;
        this.shelfLife = shelfLife;
        this.monthsInFutureForAmc = monthsInFutureForAmc;
        this.monthsInPastForAmc = monthsInPastForAmc;
        this.planBasedOn = planBasedOn;
        this.minQty = minQty;
        this.distributionLeadTime = distributionLeadTime;
        this.notes = notes;
        this.stockStatusVertical = new LinkedList<>();
        this.consumptionInfo = new LinkedList<>();
        this.inventoryInfo = new LinkedList<>();
    }

    public SimpleObject getReportingUnit() {
        return reportingUnit;
    }

    public void setReportingUnit(SimpleObject reportingUnit) {
        this.reportingUnit = reportingUnit;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public Integer getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(Integer minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public double getLocalProcurementLeadTime() {
        return localProcurementLeadTime;
    }

    public void setLocalProcurementLeadTime(double localProcurementLeadTime) {
        this.localProcurementLeadTime = localProcurementLeadTime;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getMonthsInFutureForAmc() {
        return monthsInFutureForAmc;
    }

    public void setMonthsInFutureForAmc(int monthsInFutureForAmc) {
        this.monthsInFutureForAmc = monthsInFutureForAmc;
    }

    public int getMonthsInPastForAmc() {
        return monthsInPastForAmc;
    }

    public void setMonthsInPastForAmc(int monthsInPastForAmc) {
        this.monthsInPastForAmc = monthsInPastForAmc;
    }

    public int getPlanBasedOn() {
        return planBasedOn;
    }

    public void setPlanBasedOn(int planBasedOn) {
        this.planBasedOn = planBasedOn;
    }

    public Integer getMinQty() {
        return minQty;
    }

    public void setMinQty(Integer minQty) {
        this.minQty = minQty;
    }

    public Double getDistributionLeadTime() {
        return distributionLeadTime;
    }

    public void setDistributionLeadTime(Double distributionLeadTime) {
        this.distributionLeadTime = distributionLeadTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<ConsumptionInfo> getConsumptionInfo() {
        return consumptionInfo;
    }

    public void setConsumptionInfo(List<ConsumptionInfo> consumptionInfo) {
        this.consumptionInfo = consumptionInfo;
    }

    public List<InventoryInfo> getInventoryInfo() {
        return inventoryInfo;
    }

    public void setInventoryInfo(List<InventoryInfo> inventoryInfo) {
        this.inventoryInfo = inventoryInfo;
    }

    public List<StockStatusVertical> getStockStatusVertical() {
        return stockStatusVertical;
    }

    public void setStockStatusVertical(List<StockStatusVertical> stockStatusVertical) {
        this.stockStatusVertical = stockStatusVertical;
    }

    public String getPpuNotes() {
        return ppuNotes;
    }

    public void setPpuNotes(String ppuNotes) {
        this.ppuNotes = ppuNotes;
    }

}
