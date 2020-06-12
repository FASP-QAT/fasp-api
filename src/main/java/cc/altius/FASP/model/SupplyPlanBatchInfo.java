/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class SupplyPlanBatchInfo implements Serializable {

    private int supplyPlanId;
    private int batchId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date expiryDate;
    private int plannedShipmentQty;
    private int orderedShipmentQty;
    private int shippedShipmentQty;
    private int deliveredShipmentQty;
    private int shipmentQty;
    private int consumption;
    private int adjustment;
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

    public SupplyPlanBatchInfo(int supplyPlanId, int batchId, Date expiryDate, int plannedShipmentQty, int orderedShipmentQty, int shippedShipmentQty, int deliveredShipmentQty, int shipmentQty, int consumption, int adjustment) {
        this.supplyPlanId = supplyPlanId;
        this.batchId = batchId;
        this.expiryDate = expiryDate;
        this.plannedShipmentQty = plannedShipmentQty;
        this.orderedShipmentQty = orderedShipmentQty;
        this.shippedShipmentQty = shippedShipmentQty;
        this.deliveredShipmentQty = deliveredShipmentQty;
        this.shipmentQty = shipmentQty;
        this.consumption = consumption;
        this.adjustment = adjustment;
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

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getPlannedShipmentQty() {
        return plannedShipmentQty;
    }

    public void setPlannedShipmentQty(int plannedShipmentQty) {
        this.plannedShipmentQty = plannedShipmentQty;
    }

    public int getOrderedShipmentQty() {
        return orderedShipmentQty;
    }

    public void setOrderedShipmentQty(int orderedShipmentQty) {
        this.orderedShipmentQty = orderedShipmentQty;
    }

    public int getShippedShipmentQty() {
        return shippedShipmentQty;
    }

    public void setShippedShipmentQty(int shippedShipmentQty) {
        this.shippedShipmentQty = shippedShipmentQty;
    }

    public int getDeliveredShipmentQty() {
        return deliveredShipmentQty;
    }

    public void setDeliveredShipmentQty(int deliveredShipmentQty) {
        this.deliveredShipmentQty = deliveredShipmentQty;
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

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
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

    public void setOpeningBalanceWps(int openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
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

    @JsonIgnore
    public String getExpiryDateStr() {
        return this.sdf.format(this.expiryDate);
    }

    public int updateUnAllocatedCountAndExpiredStock(Date transDate, int unallocatedConsumption) {
        if (this.openingBalance > 0 && DateUtils.compareDate(transDate, expiryDate) >= 0) {
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

    public int updateUnAllocatedCountAndExpiredStockWps(Date transDate, int unallocatedConsumptionWps) {
        if (this.openingBalanceWps > 0 && DateUtils.compareDate(transDate, expiryDate) >= 0) {
            this.expiredStockWps = this.consumption;
            unallocatedConsumptionWps += this.consumption;
        }

        int tempCBWps = this.openingBalanceWps + this.shipmentQty - this.plannedShipmentQty - this.consumption + this.adjustment;
        if (tempCBWps < 0) {
            unallocatedConsumptionWps += 0 - tempCBWps;
        }
        return unallocatedConsumptionWps;
    }

    public int updateCBWps(int existingUnAllocatedConsumptionWps) {
        int tempCBWps = this.openingBalanceWps + this.shipmentQty - this.plannedShipmentQty - this.consumption + this.adjustment;
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
