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
public class ShipmentBatchInfo implements Serializable {

    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private int shipmentTransBatchInfoId;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private Batch batch;
    @JsonView({Views.ArtmisView.class,Views.GfpVanView.class, Views.InternalView.class})
    private long shipmentQty;

    public int getShipmentTransBatchInfoId() {
        return shipmentTransBatchInfoId;
    }

    public void setShipmentTransBatchInfoId(int shipmentTransBatchInfoId) {
        this.shipmentTransBatchInfoId = shipmentTransBatchInfoId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(long shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.shipmentTransBatchInfoId;
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
        final ShipmentBatchInfo other = (ShipmentBatchInfo) obj;
        if (this.shipmentTransBatchInfoId != other.shipmentTransBatchInfoId) {
            return false;
        }
        return true;
    }
}
