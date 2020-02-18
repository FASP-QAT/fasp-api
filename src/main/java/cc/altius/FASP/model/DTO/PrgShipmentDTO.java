/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import cc.altius.FASP.model.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author altius
 */
public class PrgShipmentDTO implements Serializable {

    private int shipmentId;
    private PrgRegionDTO region;
    private double suggestedQty;
    private PrgLogisticsUnitDTO logisticsUnit;
    private double qty;
    private PrgProcurementAgentDTO procurementAgent;
    private String poroNumber;
    private double shipmentPrice;
    private double frieghtPrice;
    private Date orderDate;
    private Date shipDate;
    private Date arriveDate;
    private Date receiveDate;
    private String notes;
    private PrgShipmentStatusDTO shipmentStatus;
    private PrgDataSourceDTO dataSource;
    private PrgShipmentBudgetDTO shipmentBudget;

    public PrgShipmentBudgetDTO getShipmentBudget() {
        return shipmentBudget;
    }

    public void setShipmentBudget(PrgShipmentBudgetDTO shipmentBudget) {
        this.shipmentBudget = shipmentBudget;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public PrgRegionDTO getRegion() {
        return region;
    }

    public void setRegion(PrgRegionDTO region) {
        this.region = region;
    }

    public double getSuggestedQty() {
        return suggestedQty;
    }

    public void setSuggestedQty(double suggestedQty) {
        this.suggestedQty = suggestedQty;
    }

    public PrgLogisticsUnitDTO getLogisticsUnit() {
        return logisticsUnit;
    }

    public void setLogisticsUnit(PrgLogisticsUnitDTO logisticsUnit) {
        this.logisticsUnit = logisticsUnit;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public PrgProcurementAgentDTO getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(PrgProcurementAgentDTO procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public String getPoroNumber() {
        return poroNumber;
    }

    public void setPoroNumber(String poroNumber) {
        this.poroNumber = poroNumber;
    }

    public double getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(double shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public double getFrieghtPrice() {
        return frieghtPrice;
    }

    public void setFrieghtPrice(double frieghtPrice) {
        this.frieghtPrice = frieghtPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public PrgShipmentStatusDTO getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(PrgShipmentStatusDTO shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public PrgDataSourceDTO getDataSource() {
        return dataSource;
    }

    public void setDataSource(PrgDataSourceDTO dataSource) {
        this.dataSource = dataSource;
    }

}
