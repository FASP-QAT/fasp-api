/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgShipmentStatusAllowedDTO {

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

}
