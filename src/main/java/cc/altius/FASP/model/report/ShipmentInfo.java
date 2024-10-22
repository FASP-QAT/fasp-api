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
    private double shipmentQty;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject fundingSource;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject procurementAgent;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private SimpleObject shipmentStatus;
    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private String orderNo;
    @JsonView(Views.ReportView.class)
    private String primeLineNo;
    @JsonView(Views.ReportView.class)
    private String roNo;
    @JsonView(Views.ReportView.class)
    private String roPrimeLineNo;
    @JsonView(Views.ReportView.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date receivedDate; // If Status is Not Received then ERD if Receied then Received Date
    @JsonView(Views.ReportView.class)
    private SimpleObject dataSource;

    public ShipmentInfo() {
    }

    public ShipmentInfo(int shipmentId, double shipmentQty, SimpleCodeObject fundingSource, SimpleCodeObject procurementAgent, SimpleCodeObject program, SimpleObject planningUnit, SimpleObject shipmentStatus, String notes, String orderNo, String primeLineNo, String roNo, String roPrimeLineNo, Date receivedDate, SimpleObject dataSource) {
        this.shipmentId = shipmentId;
        this.shipmentQty = shipmentQty;
        this.fundingSource = fundingSource;
        this.procurementAgent = procurementAgent;
        this.program = program;
        this.planningUnit = planningUnit;
        this.shipmentStatus = shipmentStatus;
        this.notes = notes;
        this.orderNo = orderNo;
        this.primeLineNo = primeLineNo;
        this.roNo = roNo;
        this.roPrimeLineNo = roPrimeLineNo;
        this.receivedDate = receivedDate;
        this.dataSource = dataSource;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public double getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(double shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public SimpleCodeObject getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(SimpleCodeObject fundingSource) {
        this.fundingSource = fundingSource;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
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

    public String getPrimeLineNo() {
        return primeLineNo;
    }

    public void setPrimeLineNo(String primeLineNo) {
        this.primeLineNo = primeLineNo;
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

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getRoPrimeLineNo() {
        return roPrimeLineNo;
    }

    public void setRoPrimeLineNo(String roPrimeLineNo) {
        this.roPrimeLineNo = roPrimeLineNo;
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
