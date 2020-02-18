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
public class ShipmentStatus {
    
    private int shipmentStatusId;
    private Label label;
    private boolean  active;

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
    public String toString() {
        return "ShipmentStatus{" + "shipmentStatusId=" + shipmentStatusId + ", label=" + label + ", active=" + active + '}';
    }
    
    
    
}
