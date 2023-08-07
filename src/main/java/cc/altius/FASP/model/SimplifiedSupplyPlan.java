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
    private Long consumptionQty;
    // Is 0 if there is no Shipment in that period
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long receivedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long shippedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long approvedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long submittedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long plannedShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long onholdShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long receivedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long shippedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long approvedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long submittedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long plannedErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long onholdErpShipmentsTotalData;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Long stockQty;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private Long adjustmentQty;
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
    private long expiredStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long expiredStockWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long unmetDemand;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long unmetDemandWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long openingBalance;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long openingBalanceWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long closingBalance;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long closingBalanceWps;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long nationalAdjustment;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class, Views.InternalView.class})
    private long nationalAdjustmentWps;
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

    public Long getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Long consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    @JsonView({Views.InternalView.class})
    public long getShipmentTotalQty() {
        return getManualTotalQty() + getErpTotalQty();
    }

    @JsonView({Views.InternalView.class})
    public long getManualTotalQty() {
        return this.plannedShipmentsTotalData + this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }

    public long getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void setReceivedShipmentsTotalData(long receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData = receivedShipmentsTotalData;
    }

    public long getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void setShippedShipmentsTotalData(long shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData = shippedShipmentsTotalData;
    }

    public long getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void setApprovedShipmentsTotalData(long approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData = approvedShipmentsTotalData;
    }

    public long getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void setSubmittedShipmentsTotalData(long submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData = submittedShipmentsTotalData;
    }

    public long getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void setPlannedShipmentsTotalData(long plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData = plannedShipmentsTotalData;
    }

    public long getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void setOnholdShipmentsTotalData(long onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData = onholdShipmentsTotalData;
    }

    @JsonView({Views.InternalView.class})
    public long getErpTotalQty() {
        return this.plannedErpShipmentsTotalData + this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
    }

    public long getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void setReceivedErpShipmentsTotalData(long receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData = receivedErpShipmentsTotalData;
    }

    public long getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void setShippedErpShipmentsTotalData(long shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData = shippedErpShipmentsTotalData;
    }

    public long getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void setApprovedErpShipmentsTotalData(long approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData = approvedErpShipmentsTotalData;
    }

    public long getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void setSubmittedErpShipmentsTotalData(long submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData = submittedErpShipmentsTotalData;
    }

    public long getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void setPlannedErpShipmentsTotalData(long plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData = plannedErpShipmentsTotalData;
    }

    public long getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void setOnholdErpShipmentsTotalData(long onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData = onholdErpShipmentsTotalData;
    }

    public Long getStockQty() {
        return stockQty;
    }

    public void setStockQty(Long stockQty) {
        this.stockQty = stockQty;
    }

    public Long getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Long adjustmentQty) {
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

    public long getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(long expiredStock) {
        this.expiredStock = expiredStock;
    }

    public long getExpiredStockWps() {
        return expiredStockWps;
    }

    public void setExpiredStockWps(long expiredStockWps) {
        this.expiredStockWps = expiredStockWps;
    }

    public long getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(long unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public long getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public void setUnmetDemandWps(long unmetDemandWps) {
        this.unmetDemandWps = unmetDemandWps;
    }

    public long getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(long openingBalance) {
        this.openingBalance = openingBalance;
    }

    public long getOpeningBalanceWps() {
        return openingBalanceWps;
    }

    public void setOpeningBalanceWps(long openingBalanceWps) {
        this.openingBalanceWps = openingBalanceWps;
    }

    public long getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(long closingBalance) {
        this.closingBalance = closingBalance;
    }

    public long getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void setClosingBalanceWps(long closingBalanceWps) {
        this.closingBalanceWps = closingBalanceWps;
    }

    public long getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(long nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public long getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void setNationalAdjustmentWps(long nationalAdjustmentWps) {
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
