/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class StockStatusVerticalAggregateOutput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date dt;
    @JsonView(Views.ReportView.class)
    private SimpleObject reportingUnit; // PU, ARU or EU
    @JsonView(Views.ReportView.class)
    private Double openingBalance;
    @JsonView(Views.ReportView.class)
    private Boolean actualConsumption;
    @JsonView(Views.ReportView.class)
    private Double actualConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Double forecastedConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Double finalConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Double shipmentQty;
    @JsonView(Views.ReportView.class)
    private List<ShipmentInfo> shipmentInfo;
    @JsonView(Views.ReportView.class)
    private List<ConsumptionInfo> consumptionInfo;
    @JsonView(Views.ReportView.class)
    private List<InventoryInfo> inventoryInfo;
    @JsonView(Views.ReportView.class)
    private Double adjustment;
    @JsonView(Views.ReportView.class)
    private Double expiredStock;
    @JsonView(Views.ReportView.class)
    private Double closingBalance;
    @JsonView(Views.ReportView.class)
    private Double amc;
    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private Double minStockMos;
    @JsonView(Views.ReportView.class)
    private Double maxStockMos;
    @JsonView(Views.ReportView.class)
    private Double minStockQty;
    @JsonView(Views.ReportView.class)
    private Double maxStockQty;
    @JsonView(Views.ReportView.class)
    private Double unmetDemand;
    @JsonView(Views.ReportView.class)
    private int regionCount;
    @JsonView(Views.ReportView.class)
    private int regionCountForStock;
    @JsonView(Views.ReportView.class)
    private Double nationalAdjustment;
    @JsonView(Views.ReportView.class)
    private int planBasedOn;
    @JsonView(Views.ReportView.class)
    private String ppuNotes;

    public StockStatusVerticalAggregateOutput() {
        this.shipmentInfo = new LinkedList<>();
        this.consumptionInfo = new LinkedList<>();
        this.inventoryInfo = new LinkedList<>();
    }

    public StockStatusVerticalAggregateOutput(Date dt) {
        this.dt = dt;
        this.shipmentInfo = new LinkedList<>();
        this.consumptionInfo = new LinkedList<>();
        this.inventoryInfo = new LinkedList<>();
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public SimpleObject getReportingUnit() {
        return reportingUnit;
    }

    public void setReportingUnit(SimpleObject reportingUnit) {
        this.reportingUnit = reportingUnit;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Boolean getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Double getActualConsumptionQty() {
        return actualConsumptionQty;
    }

    public void setActualConsumptionQty(Double actualConsumptionQty) {
        this.actualConsumptionQty = actualConsumptionQty;
    }

    public Double getForecastedConsumptionQty() {
        return forecastedConsumptionQty;
    }

    public void setForecastedConsumptionQty(Double forecastedConsumptionQty) {
        this.forecastedConsumptionQty = forecastedConsumptionQty;
    }

    public Double getFinalConsumptionQty() {
        return finalConsumptionQty;
    }

    public void setFinalConsumptionQty(Double finalConsumptionQty) {
        this.finalConsumptionQty = finalConsumptionQty;
    }

    public Double getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(Double shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public Double getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(Double expiredStock) {
        this.expiredStock = expiredStock;
    }

    public Double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Double closingBalance) {
        this.closingBalance = closingBalance;
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

    public Double getMinStockMos() {
        return minStockMos;
    }

    public void setMinStockMos(Double minStockMos) {
        this.minStockMos = minStockMos;
    }

    public Double getMaxStockMos() {
        return maxStockMos;
    }

    public void setMaxStockMos(Double maxStockMos) {
        this.maxStockMos = maxStockMos;
    }

    public Double getMinStockQty() {
        return minStockQty;
    }

    public void setMinStockQty(Double minStockQty) {
        this.minStockQty = minStockQty;
    }

    public Double getMaxStockQty() {
        return maxStockQty;
    }

    public void setMaxStockQty(Double maxStockQty) {
        this.maxStockQty = maxStockQty;
    }

    public Double getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(Double unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public int getRegionCountForStock() {
        return regionCountForStock;
    }

    public void setRegionCountForStock(int regionCountForStock) {
        this.regionCountForStock = regionCountForStock;
    }

    public Double getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(Double nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public List<ShipmentInfo> getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(List<ShipmentInfo> shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
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

    public int getPlanBasedOn() {
        return planBasedOn;
    }

    public void setPlanBasedOn(int planBasedOn) {
        this.planBasedOn = planBasedOn;
    }

    public String getPpuNotes() {
        return ppuNotes;
    }

    public void setPpuNotes(String ppuNotes) {
        this.ppuNotes = ppuNotes;
    }

    @Override
    public String toString() {
        return "StockStatusVerticalOutput{" + "dt=" + dt + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.dt);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        final StockStatusVerticalAggregateOutput other = (StockStatusVerticalAggregateOutput) obj;
        if (!Objects.equals(sdf.format(this.dt), sdf.format(other.getDt()))) {
            return false;
        }
        return true;
    }

}
