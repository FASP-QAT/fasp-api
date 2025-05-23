/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ErpShipmentDTO implements Serializable {

    private String batchNo;
    private Date expiryDate;
    private Long batchQty;
    private boolean autoGenerated;
    private int status;
    private int changeCode;
    private int existingBatchId;
    private int existingShipmentTransBatchInfoId;
    private Date eoActualShipmentDate;
    private Date eoActualDeliveryDate;
    private Date eoArrivalAtDestinationDate;
    private String fileName;

    /**
     * -1 -- Delete 0 -- Leave it alone 1 -- Update 2 -- Insert
     */
    public ErpShipmentDTO() {
        this.status = 2;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(expiryDate);
        c.set(Calendar.DATE, 1);
        this.expiryDate = c.getTime();
    }

    public Long getBatchQty() {
        return batchQty;
    }

    public void setBatchQty(Long batchQty) {
        this.batchQty = batchQty;
    }

    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    public void setAutoGenerated(boolean autoGenerated) {
        this.autoGenerated = autoGenerated;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getChangeCode() {
        return changeCode;
    }

    public void setChangeCode(int changeCode) {
        this.changeCode = changeCode;
    }

    public int getExistingBatchId() {
        return existingBatchId;
    }

    public void setExistingBatchId(int existingBatchId) {
        this.existingBatchId = existingBatchId;
    }

    public int getExistingShipmentTransBatchInfoId() {
        return existingShipmentTransBatchInfoId;
    }

    public void setExistingShipmentTransBatchInfoId(int existingShipmentTransBatchInfoId) {
        this.existingShipmentTransBatchInfoId = existingShipmentTransBatchInfoId;
    }

    public Date getEoActualShipmentDate() {
        return eoActualShipmentDate;
    }

    public void setEoActualShipmentDate(Date eoActualShipmentDate) {
        this.eoActualShipmentDate = eoActualShipmentDate;
    }

    public Date getEoActualDeliveryDate() {
        return eoActualDeliveryDate;
    }

    public void setEoActualDeliveryDate(Date eoActualDeliveryDate) {
        this.eoActualDeliveryDate = eoActualDeliveryDate;
    }

    public Date getEoArrivalAtDestinationDate() {
        return eoArrivalAtDestinationDate;
    }

    public void setEoArrivalAtDestinationDate(Date eoArrivalAtDestinationDate) {
        this.eoArrivalAtDestinationDate = eoArrivalAtDestinationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.batchNo);
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
        final ErpShipmentDTO other = (ErpShipmentDTO) obj;
        return Objects.equals(this.batchNo, other.batchNo);
    }

    @Override
    public String toString() {
        return "ErpShipmentDTO{" + "batchNo=" + batchNo + ", expiryDate=" + expiryDate + ", batchQty=" + batchQty + ", autoGenerated=" + autoGenerated + ", status=" + status + ", changeCode=" + changeCode + ", existingBatchId=" + existingBatchId + ", existingShipmentTransBatchInfoId=" + existingShipmentTransBatchInfoId + ", eoActualShipmentDate=" + eoActualShipmentDate + ", eoActualDeliveryDate=" + eoActualDeliveryDate + ", eoArrivalAtDestinationDate=" + eoArrivalAtDestinationDate + ", fileName=" + fileName + '}';
    }

   

}
