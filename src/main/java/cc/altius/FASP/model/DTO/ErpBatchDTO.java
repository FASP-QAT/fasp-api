/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ErpBatchDTO implements Serializable {

    private int batchId;
    private int shipmentTransBatchInfoId;
    private String batchNo;
    private Date expiryDate;
    private long qty;
    private int status;

    /**
     * -1 -- Delete 0 -- Leave it alone 1 -- Update 2 -- Insert
     */
    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public int getShipmentTransBatchInfoId() {
        return shipmentTransBatchInfoId;
    }

    public void setShipmentTransBatchInfoId(int shipmentTransBatchInfoId) {
        this.shipmentTransBatchInfoId = shipmentTransBatchInfoId;
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

    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.batchNo);
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
        final ErpBatchDTO other = (ErpBatchDTO) obj;
        if (!Objects.equals(this.batchNo, other.batchNo)) {
            return false;
        }
        return true;
    }

}
