/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SupplyPlanExportDataDTO implements Serializable {

    
    @JsonView(Views.ExportApiView.class)
    private String transDate;
    @JsonView(Views.ExportApiView.class)
    private int regionCount;
    @JsonView(Views.ExportApiView.class)
    private int regionCountForStock;
    @JsonView(Views.ExportApiView.class)
    private int openingBalance;
    @JsonView(Views.ExportApiView.class)
    private Integer actualConsumptionQty;
    @JsonView(Views.ExportApiView.class)
    private Integer forecastedConsumptionQty;
    @JsonView(Views.ExportApiView.class)
    private int nationalAdjustment;
    @JsonView(Views.ExportApiView.class)
    private int manualPlannedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int manualSubmittedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int manualApprovedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int manualOnholdShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int manualShippedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int manualReceivedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpPlannedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpSubmittedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpApprovedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpOnholdShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpShippedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int erpReceivedShipmentQty;
    @JsonView(Views.ExportApiView.class)
    private int expiredStock;
    @JsonView(Views.ExportApiView.class)
    private int closingBalance;
    @JsonView(Views.ExportApiView.class)
    private double mos;
    @JsonView(Views.ExportApiView.class)
    private double amc;
    @JsonView(Views.ExportApiView.class)
    private int amcCount;
    @JsonView(Views.ExportApiView.class)
    private int unmetDemand;
    @JsonView(Views.ExportApiView.class)
    private double minStockMos;
    @JsonView(Views.ExportApiView.class)
    private double minStockQty;
    @JsonView(Views.ExportApiView.class)
    private double maxStockMos;
    @JsonView(Views.ExportApiView.class)
    private double maxStockQty;

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
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

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Integer getActualConsumptionQty() {
        return actualConsumptionQty;
    }

    public void setActualConsumptionQty(Integer actualConsumptionQty) {
        this.actualConsumptionQty = actualConsumptionQty;
    }

    public Integer getForecastedConsumptionQty() {
        return forecastedConsumptionQty;
    }

    public void setForecastedConsumptionQty(Integer forecastedConsumptionQty) {
        this.forecastedConsumptionQty = forecastedConsumptionQty;
    }

    public int getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(int nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public int getManualPlannedShipmentQty() {
        return manualPlannedShipmentQty;
    }

    public void setManualPlannedShipmentQty(int manualPlannedShipmentQty) {
        this.manualPlannedShipmentQty = manualPlannedShipmentQty;
    }

    public int getManualSubmittedShipmentQty() {
        return manualSubmittedShipmentQty;
    }

    public void setManualSubmittedShipmentQty(int manualSubmittedShipmentQty) {
        this.manualSubmittedShipmentQty = manualSubmittedShipmentQty;
    }

    public int getManualApprovedShipmentQty() {
        return manualApprovedShipmentQty;
    }

    public void setManualApprovedShipmentQty(int manualApprovedShipmentQty) {
        this.manualApprovedShipmentQty = manualApprovedShipmentQty;
    }

    public int getManualOnholdShipmentQty() {
        return manualOnholdShipmentQty;
    }

    public void setManualOnholdShipmentQty(int manualOnholdShipmentQty) {
        this.manualOnholdShipmentQty = manualOnholdShipmentQty;
    }

    public int getManualShippedShipmentQty() {
        return manualShippedShipmentQty;
    }

    public void setManualShippedShipmentQty(int manualShippedShipmentQty) {
        this.manualShippedShipmentQty = manualShippedShipmentQty;
    }

    public int getManualReceivedShipmentQty() {
        return manualReceivedShipmentQty;
    }

    public void setManualReceivedShipmentQty(int manualReceivedShipmentQty) {
        this.manualReceivedShipmentQty = manualReceivedShipmentQty;
    }

    public int getErpPlannedShipmentQty() {
        return erpPlannedShipmentQty;
    }

    public void setErpPlannedShipmentQty(int erpPlannedShipmentQty) {
        this.erpPlannedShipmentQty = erpPlannedShipmentQty;
    }

    public int getErpSubmittedShipmentQty() {
        return erpSubmittedShipmentQty;
    }

    public void setErpSubmittedShipmentQty(int erpSubmittedShipmentQty) {
        this.erpSubmittedShipmentQty = erpSubmittedShipmentQty;
    }

    public int getErpApprovedShipmentQty() {
        return erpApprovedShipmentQty;
    }

    public void setErpApprovedShipmentQty(int erpApprovedShipmentQty) {
        this.erpApprovedShipmentQty = erpApprovedShipmentQty;
    }

    public int getErpOnholdShipmentQty() {
        return erpOnholdShipmentQty;
    }

    public void setErpOnholdShipmentQty(int erpOnholdShipmentQty) {
        this.erpOnholdShipmentQty = erpOnholdShipmentQty;
    }

    public int getErpShippedShipmentQty() {
        return erpShippedShipmentQty;
    }

    public void setErpShippedShipmentQty(int erpShippedShipmentQty) {
        this.erpShippedShipmentQty = erpShippedShipmentQty;
    }

    public int getErpReceivedShipmentQty() {
        return erpReceivedShipmentQty;
    }

    public void setErpReceivedShipmentQty(int erpReceivedShipmentQty) {
        this.erpReceivedShipmentQty = erpReceivedShipmentQty;
    }

    public int getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getMos() {
        return mos;
    }

    public void setMos(double mos) {
        this.mos = mos;
    }

    public double getAmc() {
        return amc;
    }

    public void setAmc(double amc) {
        this.amc = amc;
    }

    public int getAmcCount() {
        return amcCount;
    }

    public void setAmcCount(int amcCount) {
        this.amcCount = amcCount;
    }

    public int getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(int unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public double getMinStockMos() {
        return minStockMos;
    }

    public void setMinStockMos(double minStockMos) {
        this.minStockMos = minStockMos;
    }

    public double getMinStockQty() {
        return minStockQty;
    }

    public void setMinStockQty(double minStockQty) {
        this.minStockQty = minStockQty;
    }

    public double getMaxStockMos() {
        return maxStockMos;
    }

    public void setMaxStockMos(double maxStockMos) {
        this.maxStockMos = maxStockMos;
    }

    public double getMaxStockQty() {
        return maxStockQty;
    }

    public void setMaxStockQty(double maxStockQty) {
        this.maxStockQty = maxStockQty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.transDate);
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
        final SupplyPlanExportDataDTO other = (SupplyPlanExportDataDTO) obj;
        return Objects.equals(this.transDate, other.transDate);
    }

}
