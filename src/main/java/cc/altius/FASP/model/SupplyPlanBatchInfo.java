/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 * @author akil
 */
public class SupplyPlanBatchInfo implements Serializable {

    private int supplyPlanId;
    private int batchId;
    private String expiryDate;
    private int manualPlannedShipmentQty;
    private int manualSubmittedShipmentQty;
    private int manualApprovedShipmentQty;
    private int manualShippedShipmentQty;
    private int manualReceivedShipmentQty;
    private int manualOnholdShipmentQty;
    private int erpPlannedShipmentQty;
    private int erpSubmittedShipmentQty;
    private int erpApprovedShipmentQty;
    private int erpShippedShipmentQty;
    private int erpReceivedShipmentQty;
    private int erpOnholdShipmentQty;
    private int shipmentQty;
    private int consumption;
    private int adjustment;
    private int stock;
    private int openingBalance;
    private int closingBalance;
    private int expiredStock;
    private int calculatedConsumption;
    private int unmetDemand;
    private int openingBalanceWps;
    private int closingBalanceWps;
    private int expiredStockWps;
    private int calculatedConsumptionWps;
    private int unmetDemandWps;

    private final SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");

    public SupplyPlanBatchInfo() {
    }

    public SupplyPlanBatchInfo(
            int supplyPlanId, int batchId, String expiryDate, 
            int manualPlannedShipmentQty, int manualSubmittedShipmentQty, int manualApprovedShipmentQty, int manualShippedShipmentQty, int manualReceivedShipmentQty, int manualOnholdShipmentQty, 
            int erpPlannedShipmentQty, int erpSubmittedShipmentQty, int erpApprovedShipmentQty, int erpShippedShipmentQty, int erpReceivedShipmentQty, int erpOnholdShipmentQty, 
            int shipmentQty, int consumption, int adjustment, int stock) {
        this.supplyPlanId = supplyPlanId;
        this.batchId = batchId;
        this.expiryDate = expiryDate;
        this.manualPlannedShipmentQty = manualPlannedShipmentQty;
        this.manualSubmittedShipmentQty = manualSubmittedShipmentQty;
        this.manualApprovedShipmentQty = manualApprovedShipmentQty;
        this.manualShippedShipmentQty = manualShippedShipmentQty;
        this.manualReceivedShipmentQty = manualReceivedShipmentQty;
        this.manualOnholdShipmentQty = manualOnholdShipmentQty;
        this.erpPlannedShipmentQty = erpPlannedShipmentQty;
        this.erpSubmittedShipmentQty = erpSubmittedShipmentQty;
        this.erpApprovedShipmentQty = erpApprovedShipmentQty;
        this.erpShippedShipmentQty = erpShippedShipmentQty;
        this.erpReceivedShipmentQty = erpReceivedShipmentQty;
        this.erpOnholdShipmentQty = erpOnholdShipmentQty;
        this.shipmentQty = shipmentQty;
        this.consumption = consumption;
        this.adjustment = adjustment;
        this.stock = stock;
    }

    public SupplyPlanBatchInfo(int batchId) {
        this.batchId = batchId;
    }

    public int getSupplyPlanId() {
        return supplyPlanId;
    }

    public void setSupplyPlanId(int supplyPlanId) {
        this.supplyPlanId = supplyPlanId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getManualPlannedShipmentQty() {
        return manualPlannedShipmentQty;
    }

    public void setManualPlannedShipmentQty(int manualPlannedShipmentQty) {
        this.manualPlannedShipmentQty = manualPlannedShipmentQty;
    }

    public int getManualApprovedShipmentQty() {
        return manualApprovedShipmentQty;
    }

    public void setManualApprovedShipmentQty(int manualApprovedShipmentQty) {
        this.manualApprovedShipmentQty = manualApprovedShipmentQty;
    }

    public int getManualSubmittedShipmentQty() {
        return manualSubmittedShipmentQty;
    }

    public void setManualSubmittedShipmentQty(int manualSubmittedShipmentQty) {
        this.manualSubmittedShipmentQty = manualSubmittedShipmentQty;
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

    public int getManualOnholdShipmentQty() {
        return manualOnholdShipmentQty;
    }

    public void setManualOnholdShipmentQty(int manualOnholdShipmentQty) {
        this.manualOnholdShipmentQty = manualOnholdShipmentQty;
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

    public int getErpOnholdShipmentQty() {
        return erpOnholdShipmentQty;
    }

    public void setErpOnholdShipmentQty(int erpOnholdShipmentQty) {
        this.erpOnholdShipmentQty = erpOnholdShipmentQty;
    }

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public int getConsumption() {
        return consumption;
    }

    public void setConsumption(int consumption) {
        this.consumption = consumption;
    }

    public int getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance, String transDate) {
        this.openingBalance = openingBalance;
        if (this.openingBalance > 0 && DateUtils.compareDates(transDate, expiryDate)>=0) {
            this.expiredStock += this.openingBalance;
            this.openingBalance = 0;
        }
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public int getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }

    public int getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(int openingBalanceWps, String transDate) {
        this.openingBalanceWps = openingBalanceWps;
        if (this.openingBalanceWps > 0 && DateUtils.compareDates(transDate, expiryDate)>=0) {
            this.expiredStockWps += this.openingBalanceWps;
            this.openingBalanceWps = 0;
        }
    }

    public int getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(int closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public int getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(int expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public int getCalculatedConsumptionWps() {
        return calculatedConsumptionWps;
    }

    public int getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public void setUnmetDemandWps(int unmetDemandWps) {
        this.unmetDemandWps = unmetDemandWps;
    }

    public int updateUnAllocatedCountAndExpiredStock(String transDate, int unallocatedConsumption) {
        if (this.openingBalance > 0 && DateUtils.compareDates(transDate, expiryDate) >= 0) {
            this.expiredStock = this.consumption;
            unallocatedConsumption += this.consumption;
        }

        int tempCB = this.openingBalance + this.shipmentQty - this.consumption + this.adjustment;
        if (tempCB < 0) {
            unallocatedConsumption += 0 - tempCB;
        }
        return unallocatedConsumption;
    }

    public int updateCB(int existingUnAllocatedConsumption) {
        int tempCB = this.openingBalance + this.shipmentQty - this.consumption + this.adjustment;
        if (existingUnAllocatedConsumption > 0 && tempCB > 0) {
            if (tempCB > existingUnAllocatedConsumption) {
                this.calculatedConsumption = existingUnAllocatedConsumption;
                tempCB -= existingUnAllocatedConsumption;
                existingUnAllocatedConsumption = 0;
            } else {
                this.calculatedConsumption = tempCB;
                existingUnAllocatedConsumption -= tempCB;
                tempCB = 0;
            }
        }
        if (tempCB >= 0) {
            closingBalance = tempCB;
        }
        return existingUnAllocatedConsumption;
    }

    public int updateUnAllocatedCountAndExpiredStockWps(String transDate, int unallocatedConsumptionWps) {
        if (this.openingBalanceWps > 0 && DateUtils.compareDates(transDate, expiryDate) >= 0) {
            this.expiredStockWps = this.consumption;
            unallocatedConsumptionWps += this.consumption;
        }

        int tempCBWps = this.openingBalanceWps + this.shipmentQty - this.manualPlannedShipmentQty - this.erpPlannedShipmentQty - this.consumption + this.adjustment;
        if (tempCBWps < 0) {
            unallocatedConsumptionWps += 0 - tempCBWps;
        }
        return unallocatedConsumptionWps;
    }

    public int updateCBWps(int existingUnAllocatedConsumptionWps) {
        int tempCBWps = this.openingBalanceWps + this.shipmentQty - this.manualPlannedShipmentQty - this.erpPlannedShipmentQty - this.consumption + this.adjustment;
        if (existingUnAllocatedConsumptionWps > 0 && tempCBWps > 0) {
            if (tempCBWps > existingUnAllocatedConsumptionWps) {
                this.calculatedConsumptionWps = existingUnAllocatedConsumptionWps;
                tempCBWps -= existingUnAllocatedConsumptionWps;
                existingUnAllocatedConsumptionWps = 0;
            } else {
                this.calculatedConsumptionWps = tempCBWps;
                existingUnAllocatedConsumptionWps -= tempCBWps;
                tempCBWps = 0;
            }
        }
        if (tempCBWps >= 0) {
            this.closingBalanceWps = tempCBWps;
        }
        return existingUnAllocatedConsumptionWps;
    }

    public int getCalculatedConsumption() {
        return calculatedConsumption;
    }

    public int getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(int unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public boolean hasData() {
        return !(this.shipmentQty == 0
                && this.consumption == 0
                && this.adjustment == 0
                && this.openingBalance == 0
                && this.openingBalanceWps == 0
                && this.closingBalance == 0
                && this.closingBalanceWps == 0
                && this.expiredStock == 0
                && this.expiredStockWps == 0
                && this.unmetDemand == 0
                && this.unmetDemandWps == 0);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.batchId;
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
        final SupplyPlanBatchInfo other = (SupplyPlanBatchInfo) obj;
        if (this.batchId != other.batchId) {
            return false;
        }
        return true;
    }

}
