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
public class ErpOrderDTO {

    private int erpOrderId;
    private int shipmentId;

    public int getErpOrderId() {
        return erpOrderId;
    }

    public void setErpOrderId(int erpOrderId) {
        this.erpOrderId = erpOrderId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public String toString() {
        return "ErpOrderDTO{" + "erpOrderId=" + erpOrderId + ", shipmentId=" + shipmentId + '}';
    }

}
