/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimplifiedSupplyPlan implements Serializable {

    @JsonView({Views.ArtmisView.class})
    private int supplyPlanId;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int programId;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int planningUnitId;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private String transDate;

    // Is null if there is no Consumption reported.
    // Is true if the Consumption reported is Actual Consumption
    // Is false if the Consumption reported is Forecasted Consumption
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private Boolean actualFlag;
    // Is null if there is no Consumption reported
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private Integer consumptionQty;
    // Is 0 if there is no Shipment in that period
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int receivedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int shippedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int approvedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int submittedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int plannedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int onholdShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int receivedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int shippedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int approvedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int submittedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int plannedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int onholdErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int stockQty;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int adjustmentQty;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int regionCount;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int regionCountForStock;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private Double amc;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int amcCount;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private double minStock;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private double minStockMoS;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private double maxStock;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private double maxStockMoS;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private List<SimpleBatchQuantity> batchDetails;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int expiredStock;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int expiredStockWps;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int unmetDemand;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int unmetDemandWps;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int openingBalance;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int openingBalanceWps;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int closingBalance;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int closingBalanceWps;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int nationalAdjustment;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private int nationalAdjustmentWps;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private Double mos;
    @JsonView({Views.ArtmisView.class,Views.InternalView.class})
    private Double mosWps;

    public SimplifiedSupplyPlan() {
        this.batchDetails = new LinkedList<>();
    }

    public SimplifiedSupplyPlan(int supplyPlanId, int programId, int versionId, int planningUnitId, String transDate) {
        this.supplyPlanId = supplyPlanId;
        this.programId = programId;
        this.versionId = versionId;
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.batchDetails = new LinkedList<>();
    }

    public int getSupplyPlanId() {
        return supplyPlanId;
    }

    public void setSupplyPlanId(int supplyPlanId) {
        this.supplyPlanId = supplyPlanId;
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

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
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

    @JsonView({Views.InternalView.class})
    public int getShipmentTotalQty() {
        return getManualTotalQty() + getErpTotalQty();
    }

    @JsonView({Views.InternalView.class})
    public int getManualTotalQty() {
        return this.plannedShipmentsTotalData + this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }

    public int getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void setReceivedShipmentsTotalData(int receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData = receivedShipmentsTotalData;
    }

    public int getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void setShippedShipmentsTotalData(int shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData = shippedShipmentsTotalData;
    }

    public int getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void setApprovedShipmentsTotalData(int approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData = approvedShipmentsTotalData;
    }

    public int getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void setSubmittedShipmentsTotalData(int submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData = submittedShipmentsTotalData;
    }

    public int getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void setPlannedShipmentsTotalData(int plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData = plannedShipmentsTotalData;
    }

    public int getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void setOnholdShipmentsTotalData(int onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData = onholdShipmentsTotalData;
    }

    @JsonView({Views.InternalView.class})
    public int getErpTotalQty() {
        return this.plannedErpShipmentsTotalData + this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
    }

    public int getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void setReceivedErpShipmentsTotalData(int receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData = receivedErpShipmentsTotalData;
    }

    public int getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void setShippedErpShipmentsTotalData(int shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData = shippedErpShipmentsTotalData;
    }

    public int getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void setApprovedErpShipmentsTotalData(int approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData = approvedErpShipmentsTotalData;
    }

    public int getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void setSubmittedErpShipmentsTotalData(int submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData = submittedErpShipmentsTotalData;
    }

    public int getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void setPlannedErpShipmentsTotalData(int plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData = plannedErpShipmentsTotalData;
    }

    public int getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void setOnholdErpShipmentsTotalData(int onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData = onholdErpShipmentsTotalData;
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

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public int getRegionCountForStock() {
        return regionCountForStock;
    }

    public void setRegionCountForStock(int regionCountForStock) {
        this.regionCountForStock = regionCountForStock;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
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

    public int getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(int nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public int getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void setNationalAdjustmentWps(int nationalAdjustmentWps) {
        this.nationalAdjustmentWps = nationalAdjustmentWps;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public Double getMosWps() {
        return mosWps;
    }

    public void setMosWps(Double mosWps) {
        this.mosWps = mosWps;
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
