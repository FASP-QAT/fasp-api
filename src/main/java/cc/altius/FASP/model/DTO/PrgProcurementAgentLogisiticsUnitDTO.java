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
public class PrgProcurementAgentLogisiticsUnitDTO {

    private int procurementAgentSkuId;
    private String skuCode;
    private double price;
    private int approvedToShipLeadTime;

    public int getProcurementAgentSkuId() {
        return procurementAgentSkuId;
    }

    public void setProcurementAgentSkuId(int procurementAgentSkuId) {
        this.procurementAgentSkuId = procurementAgentSkuId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getApprovedToShipLeadTime() {
        return approvedToShipLeadTime;
    }

    public void setApprovedToShipLeadTime(int approvedToShipLeadTime) {
        this.approvedToShipLeadTime = approvedToShipLeadTime;
    }

}
