/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class BatchData implements Serializable {

    private Integer batchId;
    private String expiryDate;
    private int shelfLife;
    private Long actualConsumption;
    private long shipment;
    private long shipmentWps;
    private Long adjustment;
    private Long stock;

    private boolean allRegionsReportedStock;
    private boolean useAdjustment;
    private boolean useActualConsumption;

    private long openingBalance;
    private long expiredStock;
//    private long unallocatedConsumption;
//    private long unallocatedAdjustment;
//    private long calculatedConsumption;
//    private long calculatedAdjustment;
    private long tempCB;
    private long closingBalance;

    private long openingBalanceWps;
    private long expiredStockWps;
//    private long unallocatedConsumptionWps;
//    private long unallocatedAdjustmentWps;
//    private long calculatedConsumptionWps;
//    private long calculatedAdjustmentWps;
    private long tempCBWps;
    private long closingBalanceWps;

    public BatchData() {
    }

    public BatchData(Integer batchId, String expiryDate) throws ParseException {
        this.batchId = batchId;
        if (expiryDate == null) {
            this.expiryDate = null;
        } else {
            this.expiryDate = expiryDate;
        }
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public Long getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Long actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public long getShipment() {
        return shipment;
    }

    public void setShipment(long shipment) {
        this.shipment = shipment;
    }

    public long getShipmentWps() {
        return shipmentWps;
    }

    public void setShipmentWps(long shipmentWps) {
        this.shipmentWps = shipmentWps;
    }

    public Long getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Long adjustment) {
        this.adjustment = adjustment;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public boolean isAllRegionsReportedStock() {
        return allRegionsReportedStock;
    }

    public void setAllRegionsReportedStock(boolean allRegionsReportedStock) {
        this.allRegionsReportedStock = allRegionsReportedStock;
    }

    public boolean isUseAdjustment() {
        return useAdjustment;
    }

    public void setUseAdjustment(boolean useAdjustment) {
        this.useAdjustment = useAdjustment;
    }

    public boolean isUseActualConsumption() {
        return useActualConsumption;
    }

    public void setUseActualConsumption(boolean useActualConsumption) {
        this.useActualConsumption = useActualConsumption;
    }

    public long getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(long openingBalance) {
        this.openingBalance = openingBalance;
    }

    public long getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(long expiredStock) {
        this.expiredStock = expiredStock;
    }

//    public long getCalculatedConsumption() {
//        return calculatedConsumption;
//    }
//
//    public void setCalculatedConsumption(long calculatedConsumption) {
//        this.calculatedConsumption = calculatedConsumption;
//    }

    public long getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(long closingBalance) {
        this.closingBalance = closingBalance;
    }

//    public long getUnallocatedConsumption() {
//        return unallocatedConsumption;
//    }
//
//    public void setUnallocatedConsumption(long unallocatedConsumption) {
//        this.unallocatedConsumption = unallocatedConsumption;
//    }

    public long getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(long openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
    }

    public long getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(long expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

//    public long getUnallocatedConsumptionWps() {
//        return unallocatedConsumptionWps;
//    }
//
//    public void setUnallocatedConsumptionWps(long unallocatedConsumptionWps) {
//        this.unallocatedConsumptionWps = unallocatedConsumptionWps;
//    }
//
//    public long getCalculatedConsumptionWps() {
//        return calculatedConsumptionWps;
//    }
//
//    public void setCalculatedConsumptionWps(long calculatedConsumptionWps) {
//        this.calculatedConsumptionWps = calculatedConsumptionWps;
//    }

    public long getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(long closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

//    public long getUnallocatedAdjustment() {
//        return unallocatedAdjustment;
//    }
//
//    public void setUnallocatedAdjustment(long unallocatedAdjustment) {
//        this.unallocatedAdjustment = unallocatedAdjustment;
//    }

//    public long getCalculatedAdjustment() {
//        return calculatedAdjustment;
//    }
//
//    public void setCalculatedAdjustment(long calculatedAdjustment) {
//        this.calculatedAdjustment = calculatedAdjustment;
//    }
//
//    public long getUnallocatedAdjustmentWps() {
//        return unallocatedAdjustmentWps;
//    }
//
//    public void setUnallocatedAdjustmentWps(long unallocatedAdjustmentWps) {
//        this.unallocatedAdjustmentWps = unallocatedAdjustmentWps;
//    }
//
//    public long getCalculatedAdjustmentWps() {
//        return calculatedAdjustmentWps;
//    }
//
//    public void setCalculatedAdjustmentWps(long calculatedAdjustmentWps) {
//        this.calculatedAdjustmentWps = calculatedAdjustmentWps;
//    }

    public long getTempCB() {
        return tempCB;
    }

    public void setTempCB(long tempCB) {
        this.tempCB = tempCB;
    }

    public long getTempCBWps() {
        return tempCBWps;
    }

    public void setTempCBWps(long tempCBWps) {
        this.tempCBWps = tempCBWps;
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.batchId);
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
        final BatchData other = (BatchData) obj;
        if (!Objects.equals(this.batchId, other.batchId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BatchData{" + "batchId=" + batchId + ", expiryDate=" + expiryDate + '}';
    }

}
