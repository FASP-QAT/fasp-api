/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author altius
 */
public class ARTMISHistoryDTO extends ManualTaggingDTO {

    private List<ErpShipmentDTO> shipmentList;
    private double totalCost;

    public ARTMISHistoryDTO() {
        this.shipmentList = new LinkedList<>();
    }

    public List<ErpShipmentDTO> getShipmentList() {
        return shipmentList;
    }

    public void setShipmentList(List<ErpShipmentDTO> shipmentList) {
        this.shipmentList = shipmentList;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getProcurementAgentOrderNo() {
        return (this.getRoNo() + " - " + this.getRoPrimeLineNo() + " | " + this.getOrderNo() + " - " + this.getPrimeLineNo());
    }

}
