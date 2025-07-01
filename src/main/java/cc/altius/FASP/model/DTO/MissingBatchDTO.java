/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class MissingBatchDTO implements Serializable {

    private int programId;
    private int shipmentId;
    private int shipmentTransId;
    private int shipmentTransBatchInfoId;
    private int planningUnitId;
    private Date projectedExpiryDate;
    private long shipmentRcpuQty;
    private Date shipmentCreatedDate;
    private String batchNo;

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    public int getShipmentTransId() {
        return shipmentTransId;
    }

    public void setShipmentTransId(int shipmentTransId) {
        this.shipmentTransId = shipmentTransId;
    }

    public int getShipmentTransBatchInfoId() {
        return shipmentTransBatchInfoId;
    }

    public void setShipmentTransBatchInfoId(int shipmentTransBatchInfoId) {
        this.shipmentTransBatchInfoId = shipmentTransBatchInfoId;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Date getProjectedExpiryDate() {
        return projectedExpiryDate;
    }

    public void setProjectedExpiryDate(Date projectedExpiryDate) {
        this.projectedExpiryDate = projectedExpiryDate;
    }

    public long getShipmentRcpuQty() {
        return shipmentRcpuQty;
    }

    public void setShipmentRcpuQty(long shipmentRcpuQty) {
        this.shipmentRcpuQty = shipmentRcpuQty;
    }

    public Date getShipmentCreatedDate() {
        return shipmentCreatedDate;
    }

    public void setShipmentCreatedDate(Date shipmentCreatedDate) {
        this.shipmentCreatedDate = shipmentCreatedDate;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

}
