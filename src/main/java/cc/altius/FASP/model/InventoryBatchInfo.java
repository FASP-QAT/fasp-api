/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class InventoryBatchInfo implements Serializable {

    private int inventoryTransBatchInfoId;
    private Batch batch;
    private int adjustmentQty;
    private Integer actualQty;

    public int getInventoryTransBatchInfoId() {
        return inventoryTransBatchInfoId;
    }

    public void setInventoryTransBatchInfoId(int inventoryTransBatchInfoId) {
        this.inventoryTransBatchInfoId = inventoryTransBatchInfoId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public int getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(int adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.inventoryTransBatchInfoId;
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
        final InventoryBatchInfo other = (InventoryBatchInfo) obj;
        if (this.inventoryTransBatchInfoId != other.inventoryTransBatchInfoId) {
            return false;
        }
        return true;
    }

}
