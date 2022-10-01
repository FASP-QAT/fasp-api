/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class LinkedShipmentBatchDetails extends RoAndRoPrimeLineNo implements Serializable {

    private int quantity;
    private List<BatchDetails> batchDetailsList;

    public LinkedShipmentBatchDetails() {
        this.batchDetailsList = new LinkedList<>();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<BatchDetails> getBatchDetailsList() {
        return batchDetailsList;
    }

    public void setBatchDetailsList(List<BatchDetails> batchDetailsList) {
        this.batchDetailsList = batchDetailsList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getRoNo());
        hash = 67 * hash + Objects.hashCode(this.getRoPrimeLineNo());
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
        final LinkedShipmentBatchDetails other = (LinkedShipmentBatchDetails) obj;
        if (!Objects.equals(this.getRoNo(), other.getRoNo())) {
            return false;
        }
        if (!Objects.equals(this.getRoPrimeLineNo(), other.getRoPrimeLineNo())) {
            return false;
        }
        return true;
    }

}
