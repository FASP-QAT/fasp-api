/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimplifiedSupplyPlan implements Serializable {

    private int programId;
    private int versionId;
    private int planningUnitId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date transDate;

    // Is null if there is no Consumption reported.
    // Is true if the Consumption reported is Actual Consumption
    // Is false if the Consumption reported is Forecasted Consumption
    private Boolean actualFlag;
    // Is null if there is no Consumption reported
    private Integer consumptionQty;
    // Is 0 if there is no Shipment in that period
    private int shipmentTotalQty;
    private int manualTotalQty;
    private int receivedShipmentsTotalData;
    private int shippedShipmentsTotalData;
    private int approvedShipmentsTotalData;
    private int submittedShipmentsTotalData;
    private int plannedShipmentsTotalData;
    private int onholdShipmentsTotalData;
    private int erpTotalQty;
    private int receivedErpShipmentsTotalData;
    private int shippedErpShipmentsTotalData;
    private int approvedErpShipmentsTotalData;
    private int submittedErpShipmentsTotalData;
    private int plannedErpShipmentsTotalData;
    private int onholdErpShipmentsTotalData;
    private int stockQty;
    private int adjustmentQty;
    private double amc;
    private int amcCount;
    private double minStock;
    private double minStockMoS;
    private double maxStock;
    private double maxStockMoS;
    private List<SimpleBatchQuantity> batchDetails;
    private int expiredStock;
    private int expiredStockWps;
    private int unmetDemand;
    private int unmetDemandWps;
    private int openingBalance;
    private int openingBalanceWps;
    private int closingBalance;
    private int closingBalanceWps;
    private double mos;

    public SimplifiedSupplyPlan() {
        this.batchDetails = new LinkedList<>();
    }

    public SimplifiedSupplyPlan(int programId, int versionId, int planningUnitId, Date transDate) {
        this.programId = programId;
        this.versionId = versionId;
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.batchDetails = new LinkedList<>();
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Boolean getActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(Boolean actualFlag) {
        this.actualFlag = actualFlag;
    }

    public Integer getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Integer consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public int getShipmentTotalQty() {
        return shipmentTotalQty;
    }

    public void setShipmentTotalQty(int shipmentTotalQty) {
        this.shipmentTotalQty = shipmentTotalQty;
    }

    public int getManualTotalQty() {
        return manualTotalQty;
    }

    private void updateManualTotalQty() {
        this.manualTotalQty = this.plannedShipmentsTotalData + this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }
    
    private void updateErpTotalQty() {
        this.erpTotalQty = this.plannedErpShipmentsTotalData + this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
    }
    
    public int getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void setReceivedShipmentsTotalData(int receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData = receivedShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void setShippedShipmentsTotalData(int shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData = shippedShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void setApprovedShipmentsTotalData(int approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData = approvedShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void setSubmittedShipmentsTotalData(int submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData = submittedShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void setPlannedShipmentsTotalData(int plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData = plannedShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void setOnholdShipmentsTotalData(int onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData = onholdShipmentsTotalData;
        updateManualTotalQty();
    }

    public int getErpTotalQty() {
        return erpTotalQty;
    }

    public int getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void setReceivedErpShipmentsTotalData(int receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData = receivedErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void setShippedErpShipmentsTotalData(int shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData = shippedErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void setApprovedErpShipmentsTotalData(int approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData = approvedErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void setSubmittedErpShipmentsTotalData(int submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData = submittedErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void setPlannedErpShipmentsTotalData(int plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData = plannedErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void setOnholdErpShipmentsTotalData(int onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData = onholdErpShipmentsTotalData;
        updateErpTotalQty();
    }

    public int getStockQty() {
        return stockQty;
    }

    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    public int getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(int adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public double getAmc() {
        return amc;
    }

    public void setAmc(double amc) {
        this.amc = amc;
    }

    public int getAmcCount() {
        return amcCount;
    }

    public void setAmcCount(int amcCount) {
        this.amcCount = amcCount;
    }

    public double getMinStock() {
        return minStock;
    }

    public void setMinStock(double minStock) {
        this.minStock = minStock;
    }

    public double getMinStockMoS() {
        return minStockMoS;
    }

    public void setMinStockMoS(double minStockMoS) {
        this.minStockMoS = minStockMoS;
    }

    public double getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(double maxStock) {
        this.maxStock = maxStock;
    }

    public double getMaxStockMoS() {
        return maxStockMoS;
    }

    public void setMaxStockMoS(double maxStockMoS) {
        this.maxStockMoS = maxStockMoS;
    }

    public List<SimpleBatchQuantity> getBatchDetails() {
        return batchDetails;
    }

    public void setBatchDetails(List<SimpleBatchQuantity> batchDetails) {
        this.batchDetails = batchDetails;
    }

    public int getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }

    public int getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(int expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public int getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(int unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public int getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public void setUnmetDemandWps(int unmetDemandWps) {
        this.unmetDemandWps = unmetDemandWps;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(int openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public int getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(int closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public double getMos() {
        return mos;
    }

    public void setMos(double mos) {
        this.mos = mos;
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.programId;
        hash = 73 * hash + this.versionId;
        hash = 73 * hash + this.planningUnitId;
        hash = 73 * hash + Objects.hashCode(this.transDate);
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
        final SimplifiedSupplyPlan other = (SimplifiedSupplyPlan) obj;
        if (this.programId != other.programId) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        if (!Objects.equals(this.transDate, other.transDate)) {
            return false;
        }
        return true;
    }

    
}
