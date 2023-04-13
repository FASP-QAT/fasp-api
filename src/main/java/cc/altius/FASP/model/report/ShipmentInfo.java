/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ShipmentInfo implements Serializable {

    @JsonView(Views.ReportView.class)
    private int shipmentId;
    @JsonView(Views.ReportView.class)
    private long shipmentQty;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fundingSource;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject procurementAgent;
    @JsonView(Views.ReportView.class)
    private SimpleObject shipmentStatus;
    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private String orderNo;
    @JsonView(Views.ReportView.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date receivedDate; // If Status is Not Received then ERD if Receied then Received Date
    @JsonView(Views.ReportView.class)
    private SimpleObject dataSource;

    public ShipmentInfo() {
    }

    public ShipmentInfo(int shipmentId, long shipmentQty, SimpleCodeObject fundingSource, SimpleCodeObject procurementAgent, SimpleObject shipmentStatus, String notes, String orderNo, Date receivedDate, SimpleObject dataSource) {
        this.shipmentId = shipmentId;
        this.shipmentQty = shipmentQty;
        this.fundingSource = fundingSource;
        this.procurementAgent = procurementAgent;
        this.shipmentStatus = shipmentStatus;
        this.notes = notes;
        this.orderNo = orderNo;
        this.receivedDate = receivedDate;
        this.dataSource = dataSource;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(long shipmentQty) {
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public SimpleObject getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleObject dataSource) {
        this.dataSource = dataSource;
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
