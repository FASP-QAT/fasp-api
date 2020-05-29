/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ConsumptionBatchInfo implements Serializable {

    private int consumptionTransBatchInfoId;
    private String batchNo;
    private Date expiryDate;
    private int consumptionQty;

    public int getConsumptionTransBatchInfoId() {
        return consumptionTransBatchInfoId;
    }

    public void setConsumptionTransBatchInfoId(int consumptionTransBatchInfoId) {
        this.consumptionTransBatchInfoId = consumptionTransBatchInfoId;
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

    public int getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(int consumptionQty) {
        this.consumptionQty = consumptionQty;
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
