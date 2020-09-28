/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ShipmentDetailsOutput implements Serializable {

    List<ShipmentDetailsList> shipmentDetailsList;
    List<ShipmentDetailsFundingSource> shipmentDetailsFundingSourceList;
    List<ShipmentDetailsMonth> shipmentDetailsMonthList;

    public List<ShipmentDetailsList> getShipmentDetailsList() {
        return shipmentDetailsList;
    }

    public void setShipmentDetailsList(List<ShipmentDetailsList> shipmentDetailsList) {
        this.shipmentDetailsList = shipmentDetailsList;
    }

    public List<ShipmentDetailsFundingSource> getShipmentDetailsFundingSourceList() {
        return shipmentDetailsFundingSourceList;
    }

    public void setShipmentDetailsFundingSourceList(List<ShipmentDetailsFundingSource> shipmentDetailsFundingSourceList) {
        this.shipmentDetailsFundingSourceList = shipmentDetailsFundingSourceList;
    }

    public List<ShipmentDetailsMonth> getShipmentDetailsMonthList() {
        return shipmentDetailsMonthList;
    }

    public void setShipmentDetailsMonthList(List<ShipmentDetailsMonth> shipmentDetailsMonthList) {
        this.shipmentDetailsMonthList = shipmentDetailsMonthList;
    }

}
