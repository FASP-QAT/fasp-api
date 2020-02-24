/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author palash
 */
public class ShipmentStatus {
    
    private int shipmentStatusId;
    private Label label;
    private boolean  active;
    private String [] nextShipmentStatusAllowed;
    private List<NextShipmentStatusAllowed> nextShipmentStatusAllowedList;
    
     public ShipmentStatus() {
        this.nextShipmentStatusAllowedList = new LinkedList<NextShipmentStatusAllowed>();
       
    }

    public List<NextShipmentStatusAllowed> getNextShipmentStatusAllowedList() {
        return nextShipmentStatusAllowedList;
    }

    public void setNextShipmentStatusAllowedList(List<NextShipmentStatusAllowed> nextShipmentStatusAllowedList) {
        this.nextShipmentStatusAllowedList = nextShipmentStatusAllowedList;
    }

    
    
    public String[] getNextShipmentStatusAllowed() {
        return nextShipmentStatusAllowed;
    }

    public void setNextShipmentStatusAllowed(String[] nextShipmentStatusAllowed) {
        this.nextShipmentStatusAllowed = nextShipmentStatusAllowed;
    }

 
    
    public int getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(int shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.shipmentStatusId;
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
        final ShipmentStatus other = (ShipmentStatus) obj;
        if (this.shipmentStatusId != other.shipmentStatusId) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "ShipmentStatus{" + "shipmentStatusId=" + shipmentStatusId + ", label=" + label + ", active=" + active + ", nextShipmentStatusAllowed=" + nextShipmentStatusAllowed + ", nextShipmentStatusAllowedList=" + nextShipmentStatusAllowedList + '}';
    }



}
