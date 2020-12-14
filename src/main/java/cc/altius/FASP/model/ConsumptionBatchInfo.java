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
public class ConsumptionBatchInfo implements Serializable {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int consumptionTransBatchInfoId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Batch batch;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double consumptionQty;

    public int getConsumptionTransBatchInfoId() {
        return consumptionTransBatchInfoId;
    }

    public void setConsumptionTransBatchInfoId(int consumptionTransBatchInfoId) {
        this.consumptionTransBatchInfoId = consumptionTransBatchInfoId;
    }

    public double getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(double consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.consumptionTransBatchInfoId;
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
        final ConsumptionBatchInfo other = (ConsumptionBatchInfo) obj;
        if (this.consumptionTransBatchInfoId != other.consumptionTransBatchInfoId) {
            return false;
        }
        return true;
    }

}
