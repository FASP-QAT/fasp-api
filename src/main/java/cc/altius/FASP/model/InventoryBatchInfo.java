/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class InventoryBatchInfo implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int inventoryTransBatchInfoId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Batch batch;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double adjustmentQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double actualQty;

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

    public Double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Double adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
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
