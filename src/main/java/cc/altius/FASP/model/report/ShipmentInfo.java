/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentInfo implements Serializable {

    private int shipmentQty;
    private SimpleObject fundingSource;
    private SimpleObject shipmentStatus;

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public SimpleObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

}
