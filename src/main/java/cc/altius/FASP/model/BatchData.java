/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class BatchData implements Serializable {

    private Integer batchId;
    private String expiryDate;
    private int shelfLife;
    private Integer actualConsumption;
    private Integer shipment;
    private Integer adjustment;
    private Integer stock;

    private boolean allRegionsReportedStock;
    private boolean useAdjustment;
    private boolean useActualConsumption;

    private int openingBalance;
    private int expiredStock;
    private int unallocatedConsumption;
    private int calculatedConsumption;
    private int closingBalance;

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

    public Integer getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Integer actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Integer getShipment() {
        return shipment;
    }

    public void setShipment(Integer shipment) {
        this.shipment = shipment;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
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

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }

    public int getCalculatedConsumption() {
        return calculatedConsumption;
    }

    public void setCalculatedConsumption(int calculatedConsumption) {
        this.calculatedConsumption = calculatedConsumption;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public int getUnallocatedConsumption() {
        return unallocatedConsumption;
    }

    public void setUnallocatedConsumption(int unallocatedConsumption) {
        this.unallocatedConsumption = unallocatedConsumption;
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
