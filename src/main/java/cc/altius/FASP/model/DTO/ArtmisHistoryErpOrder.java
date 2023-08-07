/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ArtmisHistoryErpOrder implements Serializable {

    private String procurementAgentOrderNo;
    private String planningUnitName;
    private Date expectedDeliveryDate;
    private String status;
    private int qty;
    private Double cost;
    private String changeCode;
    private Date dataReceivedOn;

    public String getProcurementAgentOrderNo() {
        return procurementAgentOrderNo;
    }

    public void setProcurementAgentOrderNo(String procurementAgentOrderNo) {
        this.procurementAgentOrderNo = procurementAgentOrderNo;
    }

    public String getPlanningUnitName() {
        return planningUnitName;
    }

    public void setPlanningUnitName(String planningUnitName) {
        this.planningUnitName = planningUnitName;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getChangeCode() {
        return changeCode;
    }

    public void setChangeCode(String changeCode) {
        this.changeCode = changeCode;
    }

    public Date getDataReceivedOn() {
        return dataReceivedOn;
    }

    public void setDataReceivedOn(Date dataReceivedOn) {
        this.dataReceivedOn = dataReceivedOn;
    }

}
