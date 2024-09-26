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
    private Long inventoryQty;

    private boolean allRegionsReportedStock;
    private boolean useActualConsumption;

    private long openingBalance;
    private long expiredStock;
    private long unallocatedFEFO;
    private long unallocatedLEFO;
    private long calculatedFEFO;
    private long calculatedLEFO;
    private long closingBalance;

    private long openingBalanceWps;
    private long expiredStockWps;
    private long unallocatedFEFOWps;
    private long unallocatedLEFOWps;
    private long calculatedFEFOWps;
    private long calculatedLEFOWps;
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

    public Long getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(Long inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public boolean isAllRegionsReportedStock() {
        return allRegionsReportedStock;
    }

    public void setAllRegionsReportedStock(boolean allRegionsReportedStock) {
        this.allRegionsReportedStock = allRegionsReportedStock;
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

    public long getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(long closingBalance) {
        this.closingBalance = closingBalance;
    }

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

    public long getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(long closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public long getUnallocatedFEFO() {
        return unallocatedFEFO;
    }

    public void setUnallocatedFEFO(long unallocatedFEFO) {
        this.unallocatedFEFO = unallocatedFEFO;
    }

    public long getUnallocatedLEFO() {
        return unallocatedLEFO;
    }

    public void setUnallocatedLEFO(long unallocatedLEFO) {
        this.unallocatedLEFO = unallocatedLEFO;
    }

    public long getCalculatedFEFO() {
        return calculatedFEFO;
    }

    public void setCalculatedFEFO(long calculatedFEFO) {
        this.calculatedFEFO = calculatedFEFO;
    }

    public long getCalculatedLEFO() {
        return calculatedLEFO;
    }

    public void setCalculatedLEFO(long calculatedLEFO) {
        this.calculatedLEFO = calculatedLEFO;
    }

    public long getUnallocatedFEFOWps() {
        return unallocatedFEFOWps;
    }

    public void setUnallocatedFEFOWps(long unallocatedFEFOWps) {
        this.unallocatedFEFOWps = unallocatedFEFOWps;
    }

    public long getUnallocatedLEFOWps() {
        return unallocatedLEFOWps;
    }

    public void setUnallocatedLEFOWps(long unallocatedLEFOWps) {
        this.unallocatedLEFOWps = unallocatedLEFOWps;
    }

    public long getCalculatedFEFOWps() {
        return calculatedFEFOWps;
    }

    public void setCalculatedFEFOWps(long calculatedFEFOWps) {
        this.calculatedFEFOWps = calculatedFEFOWps;
    }

    public long getCalculatedLEFOWps() {
        return calculatedLEFOWps;
    }

    public void setCalculatedLEFOWps(long calculatedLEFOWps) {
        this.calculatedLEFOWps = calculatedLEFOWps;
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

