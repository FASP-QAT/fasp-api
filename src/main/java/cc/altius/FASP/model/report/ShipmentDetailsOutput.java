/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ShipmentDetailsOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    List<ShipmentDetailsList> shipmentDetailsList;
    @JsonView(Views.ReportView.class)
    List<ShipmentDetailsFundingSourceProcurementAgent> shipmentDetailsFundingSourceList;
    @JsonView(Views.ReportView.class)
    List<ShipmentDetailsMonth> shipmentDetailsMonthList;

    public List<ShipmentDetailsList> getShipmentDetailsList() {
        return shipmentDetailsList;
    }

    public void setShipmentDetailsList(List<ShipmentDetailsList> shipmentDetailsList) {
        this.shipmentDetailsList = shipmentDetailsList;
    }

    public List<ShipmentDetailsFundingSourceProcurementAgent> getShipmentDetailsFundingSourceList() {
        return shipmentDetailsFundingSourceList;
    }

    public void setShipmentDetailsFundingSourceList(List<ShipmentDetailsFundingSourceProcurementAgent> shipmentDetailsFundingSourceList) {
        this.shipmentDetailsFundingSourceList = shipmentDetailsFundingSourceList;
    }

    public List<ShipmentDetailsMonth> getShipmentDetailsMonthList() {
        return shipmentDetailsMonthList;
    }

    public void setShipmentDetailsMonthList(List<ShipmentDetailsMonth> shipmentDetailsMonthList) {
        this.shipmentDetailsMonthList = shipmentDetailsMonthList;
    }

}
