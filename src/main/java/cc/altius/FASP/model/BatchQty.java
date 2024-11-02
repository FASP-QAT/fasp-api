/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class BatchQty implements Serializable {

    @JsonView({Views.InternalView.class})
    private int batchInventoryTransId;
    @JsonView({Views.InternalView.class})
    private Batch batch;
    @JsonView({Views.InternalView.class})
    private Double qty;

    public BatchQty() {
    }

    public BatchQty(int batchInventoryTransId, Batch batch, Double qty) {
        this.batchInventoryTransId = batchInventoryTransId;
        this.batch = batch;
        this.qty = qty;
    }

    public int getBatchInventoryTransId() {
        return batchInventoryTransId;
    }

    public void setBatchInventoryTransId(int batchInventoryTransId) {
        this.batchInventoryTransId = batchInventoryTransId;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

}
