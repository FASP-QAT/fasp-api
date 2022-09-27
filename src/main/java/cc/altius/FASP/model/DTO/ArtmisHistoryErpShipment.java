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
public class ArtmisHistoryErpShipment implements Serializable {

    private String procurementAgentShipmentNo;
    private Date deliveryDate;
    private int qty;
    private Date expiryDate;
    private String batchNo;
    private String changeCode;
    private Date dataReceivedOn;

    public String getProcurementAgentShipmentNo() {
        return procurementAgentShipmentNo;
    }

    public void setProcurementAgentShipmentNo(String procurementAgentShipmentNo) {
        this.procurementAgentShipmentNo = procurementAgentShipmentNo;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
