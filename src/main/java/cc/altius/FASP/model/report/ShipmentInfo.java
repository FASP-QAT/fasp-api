/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ShipmentInfo implements Serializable {

    private int shipmentId;
    private int shipmentQty;
    private SimpleCodeObject fundingSource;
    private SimpleCodeObject procurementAgent;
    private SimpleObject shipmentStatus;

    public ShipmentInfo() {
    }

    public ShipmentInfo(int shipmentId, int shipmentQty, SimpleCodeObject fundingSource, SimpleCodeObject procurementAgent, SimpleObject shipmentStatus) {
        this.shipmentId = shipmentId;
        this.shipmentQty = shipmentQty;
        this.fundingSource = fundingSource;
        this.procurementAgent = procurementAgent;
        this.shipmentStatus = shipmentStatus;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleObject getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(SimpleObject shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.shipmentId;
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
        final ShipmentInfo other = (ShipmentInfo) obj;
        if (this.shipmentId != other.shipmentId) {
            return false;
        }
        return true;
    }

}
