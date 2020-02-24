/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author altius
 */
public class PrgShipmentStatusDTO implements Serializable {

    private int shipmentStatusId;
    private PrgLabelDTO label;

    public int getShipmentStatusId() {
        return shipmentStatusId;
    }

    public void setShipmentStatusId(int shipmentStatusId) {
        this.shipmentStatusId = shipmentStatusId;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

}
