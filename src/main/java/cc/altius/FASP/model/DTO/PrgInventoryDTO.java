/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.User;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author altius
 */
public class PrgInventoryDTO implements Serializable {

    private int inventoryId;
    private PrgRegionDTO region;
    private PrgLogisticsUnitDTO logisticsUnit;
    private PrgUnitDTO unit;
    private PrgDataSourceDTO dataSource;
    private double packSize;
    private double actualQty;
    private double adjustmentQty;
    private String batchNo;
    private Date expiryDate;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public PrgRegionDTO getRegion() {
        return region;
    }

    public void setRegion(PrgRegionDTO region) {
        this.region = region;
    }

    public PrgLogisticsUnitDTO getLogisticsUnit() {
        return logisticsUnit;
    }

    public void setLogisticsUnit(PrgLogisticsUnitDTO logisticsUnit) {
        this.logisticsUnit = logisticsUnit;
    }

    public PrgUnitDTO getUnit() {
        return unit;
    }

    public void setUnit(PrgUnitDTO unit) {
        this.unit = unit;
    }

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public double getActualQty() {
        return actualQty;
    }

    public void setActualQty(double actualQty) {
        this.actualQty = actualQty;
    }

    public double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(double adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public PrgDataSourceDTO getDataSource() {
        return dataSource;
    }

    public void setDataSource(PrgDataSourceDTO dataSource) {
        this.dataSource = dataSource;
    }

}
