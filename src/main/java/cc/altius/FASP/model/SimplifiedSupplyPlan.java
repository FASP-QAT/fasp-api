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

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private int supplyPlanId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int programId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int versionId;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int planningUnitId;
    @JsonView({Views.GfpVanView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private String transDate;

    // Is null if there is no Consumption reported.
    // Is true if the Consumption reported is Actual Consumption
    // Is false if the Consumption reported is Forecasted Consumption
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Boolean actualFlag;
    // Is null if there is no Consumption reported
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double consumptionQty;
    // Is 0 if there is no Shipment in that period
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double receivedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double shippedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double approvedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double submittedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double plannedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double onholdShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double receivedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double shippedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double approvedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double submittedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double plannedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double onholdErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double stockQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double adjustmentQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int regionCount;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int regionCountForStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double amc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private int amcCount;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double minStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double minStockMoS;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double maxStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double maxStockMoS;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private List<SimpleBatchQuantity> batchDetails;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double expiredStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double expiredStockWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double unmetDemand;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double unmetDemandWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double openingBalance;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double openingBalanceWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double closingBalance;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double closingBalanceWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double nationalAdjustment;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double nationalAdjustmentWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double mos;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Double mosWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double minQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private double maxQty;

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

    public Double getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Double consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    @JsonView({Views.InternalView.class})
    public double getShipmentTotalQty() {
        return getManualTotalQty() + getErpTotalQty();
    }

    @JsonView({Views.InternalView.class})
    public double getManualTotalQty() {
        return this.plannedShipmentsTotalData + this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }

    public double getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void setReceivedShipmentsTotalData(double receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData = receivedShipmentsTotalData;
    }

    public double getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void setShippedShipmentsTotalData(double shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData = shippedShipmentsTotalData;
    }

    public double getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void setApprovedShipmentsTotalData(double approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData = approvedShipmentsTotalData;
    }

    public double getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void setSubmittedShipmentsTotalData(double submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData = submittedShipmentsTotalData;
    }

    public double getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void setPlannedShipmentsTotalData(double plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData = plannedShipmentsTotalData;
    }

    public double getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void setOnholdShipmentsTotalData(double onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData = onholdShipmentsTotalData;
    }

    @JsonView({Views.InternalView.class})
    public double getErpTotalQty() {
        return this.plannedErpShipmentsTotalData + this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
    }

    public double getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void setReceivedErpShipmentsTotalData(double receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData = receivedErpShipmentsTotalData;
    }

    public double getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void setShippedErpShipmentsTotalData(double shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData = shippedErpShipmentsTotalData;
    }

    public double getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void setApprovedErpShipmentsTotalData(double approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData = approvedErpShipmentsTotalData;
    }

    public double getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void setSubmittedErpShipmentsTotalData(double submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData = submittedErpShipmentsTotalData;
    }

    public double getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void setPlannedErpShipmentsTotalData(double plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData = plannedErpShipmentsTotalData;
    }

    public double getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void setOnholdErpShipmentsTotalData(double onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData = onholdErpShipmentsTotalData;
    }

    public Double getStockQty() {
        return stockQty;
    }

    public void setStockQty(Double stockQty) {
        this.stockQty = stockQty;
    }

    public Double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Double adjustmentQty) {
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

    public double getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(double expiredStock) {
        this.expiredStock = expiredStock;
    }

    public double getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(double expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public double getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(double unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public double getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public void setUnmetDemandWps(double unmetDemandWps) {
        this.unmetDemandWps = unmetDemandWps;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(double openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(double closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public double getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(double nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public double getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void setNationalAdjustmentWps(double nationalAdjustmentWps) {
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

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getMinQty() {
        return minQty;
    }

    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    public double getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(double maxQty) {
        this.maxQty = maxQty;
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
