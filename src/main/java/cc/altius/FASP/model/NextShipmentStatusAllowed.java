/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author palash
 */
public class NextShipmentStatusAllowed {
   private int shipmentStatusAllowedId;
   private int shipmentStatusId;
   private int nextShipmentStatusId;

    public int getShipmentStatusAllowedId() {
        return shipmentStatusAllowedId;
    }

    public void setShipmentStatusAllowedId(int shipmentStatusAllowedId) {
        this.shipmentStatusAllowedId = shipmentStatusAllowedId;
    }

    public int getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(int shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
    }

    public int getNextShipmentStatusId() {
        return nextShipmentStatusId;
    }

    public void setNextShipmentStatusId(int nextShipmentStatusId) {
        this.nextShipmentStatusId = nextShipmentStatusId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.shipmentStatusAllowedId;
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
        final NextShipmentStatusAllowed other = (NextShipmentStatusAllowed) obj;
        if (this.shipmentStatusAllowedId != other.shipmentStatusAllowedId) {
            return false;
        }
        return true;
    }
    

    @Override
    public String toString() {
        return "NextShipmentStatusAllowed{" + "shipmentStatusAllowedId=" + shipmentStatusAllowedId + ", shipmentStatusId=" + shipmentStatusId + ", nextShipmentStatusId=" + nextShipmentStatusId + '}';
    }
   
   
}
