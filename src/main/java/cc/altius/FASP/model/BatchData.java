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
    private Double actualConsumption;
    private double shipment;
    private double shipmentWps;
    private Double adjustment;
    private Double stock;

    private boolean allRegionsReportedStock;
    private boolean useActualConsumption;

    private double openingBalance;
    private double expiredStock;
    private double unallocatedFEFO;
    private double unallocatedLEFO;
    private double calculatedFEFO;
    private double calculatedLEFO;
    private double closingBalance;

    private double openingBalanceWps;
    private double expiredStockWps;
    private double unallocatedFEFOWps;
    private double unallocatedLEFOWps;
    private double calculatedFEFOWps;
    private double calculatedLEFOWps;
    private double closingBalanceWps;

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

    public Double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public double getShipment() {
        return shipment;
    }

    public void setShipment(double shipment) {
        this.shipment = shipment;
    }

    public double getShipmentWps() {
        return shipmentWps;
    }

    public void setShipmentWps(double shipmentWps) {
        this.shipmentWps = shipmentWps;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
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

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(double expiredStock) {
        this.expiredStock = expiredStock;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(double openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
    }

    public double getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(double expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public double getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(double closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public double getUnallocatedFEFO() {
        return unallocatedFEFO;
    }

    public void setUnallocatedFEFO(double unallocatedFEFO) {
        this.unallocatedFEFO = unallocatedFEFO;
    }

    public double getUnallocatedLEFO() {
        return unallocatedLEFO;
    }

    public void setUnallocatedLEFO(double unallocatedLEFO) {
        this.unallocatedLEFO = unallocatedLEFO;
    }

    public double getCalculatedFEFO() {
        return calculatedFEFO;
    }

    public void setCalculatedFEFO(double calculatedFEFO) {
        this.calculatedFEFO = calculatedFEFO;
    }

    public double getCalculatedLEFO() {
        return calculatedLEFO;
    }

    public void setCalculatedLEFO(double calculatedLEFO) {
        this.calculatedLEFO = calculatedLEFO;
    }

    public double getUnallocatedFEFOWps() {
        return unallocatedFEFOWps;
    }

    public void setUnallocatedFEFOWps(double unallocatedFEFOWps) {
        this.unallocatedFEFOWps = unallocatedFEFOWps;
    }

    public double getUnallocatedLEFOWps() {
        return unallocatedLEFOWps;
    }

    public void setUnallocatedLEFOWps(double unallocatedLEFOWps) {
        this.unallocatedLEFOWps = unallocatedLEFOWps;
    }

    public double getCalculatedFEFOWps() {
        return calculatedFEFOWps;
    }

    public void setCalculatedFEFOWps(double calculatedFEFOWps) {
        this.calculatedFEFOWps = calculatedFEFOWps;
    }

    public double getCalculatedLEFOWps() {
        return calculatedLEFOWps;
    }

    public void setCalculatedLEFOWps(double calculatedLEFOWps) {
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

