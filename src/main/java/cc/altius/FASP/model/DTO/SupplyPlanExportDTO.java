/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class SupplyPlanExportDTO implements Serializable {

    private int planningUnitId;
    private String transDate;
    private int regionCount;
    private int regionCountForStock;
    private int openingBalanceWps;
    private Integer actualConsumptionQty;
    private Integer forecastedConsumptionQty;
    private int nationalAdjustmentWps;
    private int manualPlannedShipmentQty;
    private int manualSubmittedShipmentQty;
    private int manualApprovedShipmentQty;
    private int manualOnholdShipmentQty;
    private int manualShippedShipmentQty;
    private int manualReceivedShipmentQty;
    private int erpPlannedShipmentQty;
    private int erpSubmittedShipmentQty;
    private int erpApprovedShipmentQty;
    private int erpOnholdShipmentQty;
    private int erpShippedShipmentQty;
    private int erpReceivedShipmentQty;
    private int expiredStockWps;
    private int closingBalanceWps;
    private double mosWps;
    private double amc;
    private int amcCount;
    private int unmetDemandWps;
    private double minStockMos;
    private double minStockQty;
    private double maxStockMos;
    private double maxStockQty;

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

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

    public int getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(int openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
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

    public int getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void setNationalAdjustmentWps(int nationalAdjustmentWps) {
        this.nationalAdjustmentWps = nationalAdjustmentWps;
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

    public int getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(int expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public int getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(int closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public double getMosWps() {
        return mosWps;
    }

    public void setMosWps(double mosWps) {
        this.mosWps = mosWps;
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

    public int getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public void setUnmetDemandWps(int unmetDemandWps) {
        this.unmetDemandWps = unmetDemandWps;
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

}
