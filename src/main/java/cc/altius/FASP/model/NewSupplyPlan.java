/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class NewSupplyPlan implements Serializable {

    private int planningUnitId;
    private String transDate;
    private int shelfLife;
    private Integer actualConsumptionQty;
    private Integer forecastedConsumptionQty;
    private Integer finalConsumptionQty;
    private boolean actualConsumptionFlag;
    private int plannedShipmentsTotalData;
    private int submittedShipmentsTotalData;
    private int approvedShipmentsTotalData;
    private int shippedShipmentsTotalData;
    private int receivedShipmentsTotalData;
    private int onholdShipmentsTotalData;
    private int plannedErpShipmentsTotalData;
    private int submittedErpShipmentsTotalData;
    private int approvedErpShipmentsTotalData;
    private int shippedErpShipmentsTotalData;
    private int receivedErpShipmentsTotalData;
    private int onholdErpShipmentsTotalData;
    private Integer finalAdjustmentQty;
    private Integer adjustmentQty;
    private Integer stockQty;
    private int regionCountForStock;
    private int regionCount;

    private int openingBalance;
    private int expiredStock;
    private int expectedStock;
    private int nationalAdjustment;
    private int closingBalance;
    private int unmetDemand;

    private int openingBalanceWps;
    private int expiredStockWps;
    private int expectedStockWps;
    private int nationalAdjustmentWps;
    private int closingBalanceWps;
    private int unmetDemandWps;
    private int newBatchCounter;

    private List<RegionData> regionDataList;
    private List<BatchData> batchDataList;

    public NewSupplyPlan() {
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
        this.newBatchCounter = -1;
    }

    public NewSupplyPlan(int planningUnitId, String transDate) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
        this.newBatchCounter = -1;
    }

    public NewSupplyPlan(int planningUnitId, String transDate, int shelfLife) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.shelfLife = shelfLife;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
        this.newBatchCounter = -1;
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

    public Integer getActualConsumptionQty() {
        return actualConsumptionQty;
    }

    public void addActualConsumptionQty(Integer actualConsumptionQty) {
        if (actualConsumptionQty != null) {
            if (this.actualConsumptionQty == null) {
                this.actualConsumptionQty = actualConsumptionQty;
            } else {
                this.actualConsumptionQty += actualConsumptionQty;
            }
        }
    }

    public Integer getForecastedConsumptionQty() {
        return forecastedConsumptionQty;
    }

    public void addForecastedConsumptionQty(Integer forecastedConsumptionQty) {
        if (forecastedConsumptionQty != null) {
            if (this.forecastedConsumptionQty == null) {
                this.forecastedConsumptionQty = forecastedConsumptionQty;
            } else {
                this.forecastedConsumptionQty += forecastedConsumptionQty;
            }
        }
    }

    public boolean isActualConsumptionFlag() {
        return actualConsumptionFlag;
    }

    public void setActualConsumptionFlag(boolean actualConsumptionFlag) {
        this.actualConsumptionFlag = actualConsumptionFlag;
    }

    public Integer getFinalConsumptionQty() {
        return finalConsumptionQty;
    }

    public void setFinalConsumptionQty(Integer finalConsumptionQty) {
        this.finalConsumptionQty = finalConsumptionQty;
    }

    public Integer getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Integer adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public Integer getStockQty() {
        return stockQty;
    }

    public void setStockQty(Integer stockQty) {
        this.stockQty = stockQty;
    }

    public Integer getFinalAdjustmentQty() {
        return finalAdjustmentQty;
    }

    public void setFinalAdjustmentQty(Integer finalAdjustmentQty) {
        this.finalAdjustmentQty = finalAdjustmentQty;
    }

    public int getRegionCountForStock() {
        return regionCountForStock;
    }

    public void setRegionCountForStock(int regionCountForStock) {
        this.regionCountForStock = regionCountForStock;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public int getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void addReceivedShipmentsTotalData(int receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData += receivedShipmentsTotalData;
    }

    public int getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void addShippedShipmentsTotalData(int shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData += shippedShipmentsTotalData;
    }

    public int getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void addApprovedShipmentsTotalData(int approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData += approvedShipmentsTotalData;
    }

    public int getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void addSubmittedShipmentsTotalData(int submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData += submittedShipmentsTotalData;
    }

    public int getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void addPlannedShipmentsTotalData(int plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData += plannedShipmentsTotalData;
    }

    public int getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void addOnholdShipmentsTotalData(int onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData += onholdShipmentsTotalData;
    }

    public int getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void addReceivedErpShipmentsTotalData(int receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData += receivedErpShipmentsTotalData;
    }

    public int getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void addShippedErpShipmentsTotalData(int shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData += shippedErpShipmentsTotalData;
    }

    public int getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void addApprovedErpShipmentsTotalData(int approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData += approvedErpShipmentsTotalData;
    }

    public int getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void addSubmittedErpShipmentsTotalData(int submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData += submittedErpShipmentsTotalData;
    }

    public int getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void addPlannedErpShipmentsTotalData(int plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData += plannedErpShipmentsTotalData;
    }

    public int getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void addOnholdErpShipmentsTotalData(int onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData += onholdErpShipmentsTotalData;
    }

    public int getManualShipmentTotal() {
        return this.plannedShipmentsTotalData + this.getManualShipmentTotalWps();
    }

    public int getManualShipmentTotalWps() {
        return this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }

    public int getErpShipmentTotal() {
        return this.plannedErpShipmentsTotalData + getErpShipmentTotalWps();
    }

    public int getErpShipmentTotalWps() {
        return this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
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

    public int getExpectedStock() {
        return expectedStock;
    }

    public int getExpectedStockWps() {
        return expectedStockWps;
    }

    public void updateExpectedStock() {
        this.expectedStock = this.openingBalance - this.expiredStock + (getManualShipmentTotal() + getErpShipmentTotal()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0) + Optional.ofNullable(this.finalAdjustmentQty).orElse(0);
        this.expectedStockWps = this.openingBalanceWps - this.expiredStockWps + (getManualShipmentTotalWps() + getErpShipmentTotalWps()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0) + Optional.ofNullable(this.finalAdjustmentQty).orElse(0);
    }

    public int getNationalAdjustment() {
        return nationalAdjustment;
    }

    public int getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void updateNationalAdjustment() {
        if ((this.regionCountForStock == this.regionCount && this.expectedStock != Optional.ofNullable(this.stockQty).orElse(0).intValue())
                || (this.regionCountForStock > 0 && this.regionCountForStock != this.regionCount && Optional.ofNullable(this.stockQty).orElse(0).intValue() > this.expectedStock)
                || (this.regionCountForStock > 0 && this.expectedStock < 0)) {
            this.nationalAdjustment = Optional.ofNullable(this.stockQty).orElse(0) - this.expectedStock;
        }

        if ((this.regionCountForStock == this.regionCount && this.expectedStockWps != Optional.ofNullable(this.stockQty).orElse(0).intValue())
                || (this.regionCountForStock > 0 && this.regionCountForStock != this.regionCount && Optional.ofNullable(this.stockQty).orElse(0).intValue() > this.expectedStockWps)
                || (this.regionCountForStock > 0 && this.expectedStockWps < 0)) {
            this.nationalAdjustmentWps = Optional.ofNullable(this.stockQty).orElse(0) - this.expectedStockWps;
        }
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

    public int getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void updateClosingBalance() {
        if (this.regionCountForStock == this.regionCount) {
            this.closingBalance = Optional.ofNullable(this.stockQty).orElse(0);
        } else {
            this.closingBalance = Math.max(Optional.ofNullable(this.stockQty).orElse(0), Math.max(this.expectedStock + this.nationalAdjustment, 0));
        }

        if (this.regionCountForStock == this.regionCount) {
            this.closingBalanceWps = Optional.ofNullable(this.stockQty).orElse(0);
        } else {
            this.closingBalanceWps = Math.max(Optional.ofNullable(this.stockQty).orElse(0), Math.max(this.expectedStockWps + this.nationalAdjustmentWps, 0));
        }
    }

    public int getUnmetDemand() {
        return unmetDemand;
    }

    public void updateUnmetDemand() {
        if (this.closingBalance == 0) {
            this.unmetDemand = 0 - this.expectedStock;
        } else {
            this.unmetDemand = 0;
        }

        if (this.closingBalanceWps == 0) {
            this.unmetDemandWps = 0 - this.expectedStockWps;
        } else {
            this.unmetDemandWps = 0;
        }
    }

    public int getUnmetDemandWps() {
        return unmetDemandWps;
    }

    public List<RegionData> getRegionDataList() {
        return regionDataList;
    }

    public void setRegionDataList(List<RegionData> regionDataList) {
        this.regionDataList = regionDataList;
    }

    public List<BatchData> getBatchDataList() {
        return batchDataList;
    }

    public void setBatchDataList(List<BatchData> batchDataList) {
        this.batchDataList = batchDataList;
    }

    public void addFinalConsumptionQty(Integer consumption) {
        if (consumption != null) {
            if (this.finalConsumptionQty != null) {
                this.finalConsumptionQty += consumption;
            } else {
                this.finalConsumptionQty = consumption;
            }
        }
    }

    public void addStockQty(Integer stock) {
        if (stock != null) {
            if (this.stockQty == null) {
                this.stockQty = stock;
            } else {
                this.stockQty += stock;
            }
        }
    }

    public void addAdjustmentQty(Integer adjustment) {
        if (adjustment != null) {
            if (this.adjustmentQty == null) {
                this.adjustmentQty = adjustment;
            } else {
                this.adjustmentQty += adjustment;
            }
            if (!this.isAllRegionsReportedStock()) {
                addFinalAdjustmentQty(adjustment);
            }
        }
    }

    private void addFinalAdjustmentQty(Integer adjustemnt) {
        if (adjustemnt != null) {
            if (this.finalAdjustmentQty == null) {
                this.finalAdjustmentQty = adjustemnt;
            } else {
                this.finalAdjustmentQty += adjustemnt;
            }
        }
    }

    public boolean isAllRegionsReportedStock() {
        return (this.regionCount == this.regionCountForStock);
    }

    public boolean isUseAdjustment() {
        return (this.stockQty == null);
    }

    @JsonIgnore
    public String getPrevTransDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(transDate));
        c.add(Calendar.MONTH, -1);
        return sdf.format(c.getTime());
    }

    public void addOpeningBalance(int ob) {
        this.openingBalance += ob;
    }

    public void addOpeningBalanceWps(int ob) {
        this.openingBalanceWps += ob;
    }

    public void updateExpiredStock() {
        this.batchDataList.forEach(bd -> {
            if (bd.getExpiryDate() != null && DateUtils.compareDates(bd.getExpiryDate(), this.transDate) <= 0) {
                bd.setExpiredStock(Optional.ofNullable(bd.getShipment()).orElse(0) + bd.getOpeningBalance());
                this.expiredStock = Optional.ofNullable(this.expiredStock).orElse(0) + bd.getExpiredStock();
                bd.setExpiredStockWps(Optional.ofNullable(bd.getShipmentWps()).orElse(0) + bd.getOpeningBalanceWps());
                this.expiredStockWps = Optional.ofNullable(this.expiredStockWps).orElse(0) + bd.getExpiredStockWps();
            }
        });
    }

    public void updateBatchData() {
        int periodConsumption = Optional.ofNullable(this.finalConsumptionQty).orElse(0) - Optional.ofNullable(this.finalAdjustmentQty).orElse(0) - Optional.ofNullable(this.nationalAdjustment).orElse(0);
        int periodConsumptionWps = Optional.ofNullable(this.finalConsumptionQty).orElse(0) - Optional.ofNullable(this.finalAdjustmentQty).orElse(0) - Optional.ofNullable(this.nationalAdjustmentWps).orElse(0);
        // draw down from the Batches that you have
        for (BatchData bd : this.getBatchDataList().stream().sorted(new ComparatorBatchData()).collect(Collectors.toList())) {
            bd.setUnallocatedConsumption(periodConsumption);
            bd.setUnallocatedConsumptionWps(periodConsumptionWps);
            if (periodConsumption > 0) {
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                if (tempCB > periodConsumption) {
                    bd.setCalculatedConsumption(periodConsumption);
                    bd.setClosingBalance(tempCB - periodConsumption);
                    periodConsumption = 0;
                } else {
                    bd.setCalculatedConsumption(tempCB);
                    bd.setClosingBalance(0);
                    periodConsumption -= tempCB;
                }
            } else if (periodConsumption == 0) {
                bd.setCalculatedConsumption(0);
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                bd.setClosingBalance(tempCB - periodConsumption);
            } else if (bd.getOpeningBalance() - bd.getExpiredStock() + Optional.ofNullable(bd.getShipment()).orElse(0) > 0) {
                bd.setCalculatedConsumption(0 - periodConsumption);
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                bd.setClosingBalance(tempCB - periodConsumption);
                periodConsumption = 0;
            }

            if (periodConsumptionWps > 0) {
                int tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                if (tempCB > periodConsumptionWps) {
                    bd.setCalculatedConsumptionWps(periodConsumptionWps);
                    bd.setClosingBalanceWps(tempCB - periodConsumptionWps);
                    periodConsumptionWps = 0;
                } else {
                    bd.setCalculatedConsumptionWps(tempCB);
                    bd.setClosingBalanceWps(0);
                    periodConsumptionWps -= tempCB;
                }
            } else if (periodConsumptionWps == 0) {
                bd.setCalculatedConsumptionWps(0);
                int tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                bd.setClosingBalanceWps(tempCB - periodConsumptionWps);
            } else if (bd.getOpeningBalanceWps() - bd.getExpiredStockWps() + Optional.ofNullable(bd.getShipmentWps()).orElse(0) > 0) {
                bd.setCalculatedConsumptionWps(0 - periodConsumptionWps);
                int tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                bd.setClosingBalanceWps(tempCB - periodConsumptionWps);
                periodConsumptionWps = 0;
            }
        }
        if (periodConsumption < 0 || periodConsumptionWps < 0) {
            System.out.println("We need to create a new Batch for periodConsumptionWps:" + periodConsumptionWps + " PlanningUnitId:" + this.planningUnitId + " transDate:" + this.transDate);
            BatchData bdNew = new BatchData();
            bdNew.setBatchId(this.newBatchCounter);
            bdNew.setExpiryDate(this.calculateExpiryDate(this.transDate));
            bdNew.setOpeningBalance(0);
            bdNew.setOpeningBalanceWps(0);
            bdNew.setShelfLife(this.shelfLife);
            bdNew.setCalculatedConsumption(0 - periodConsumption);
            bdNew.setCalculatedConsumptionWps(0 - periodConsumptionWps);
            bdNew.setClosingBalance(0 - periodConsumption);
            bdNew.setClosingBalanceWps(0 - periodConsumptionWps);
            bdNew.setAllRegionsReportedStock(this.isAllRegionsReportedStock());
            bdNew.setUseAdjustment(this.isUseAdjustment());
            this.batchDataList.add(bdNew);
            this.newBatchCounter--;
        }
    }

    public void removeUnusedBatches() {
        List<BatchData> removeList = new LinkedList<>();
        this.getBatchDataList().stream().filter(bd -> (bd.getOpeningBalance() == 0
                && Optional.ofNullable(bd.getShipment()).orElse(0) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0) == 0
                && Optional.ofNullable(bd.getCalculatedConsumption()).orElse(0) == 0
                && Optional.ofNullable(bd.getClosingBalance()).orElse(0) == 0
                && bd.getOpeningBalanceWps() == 0
                && Optional.ofNullable(bd.getShipmentWps()).orElse(0) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0) == 0
                && Optional.ofNullable(bd.getCalculatedConsumptionWps()).orElse(0) == 0
                && Optional.ofNullable(bd.getClosingBalanceWps()).orElse(0) == 0)
        ).forEachOrdered(bd -> {
            removeList.add(bd);
        });
        this.getBatchDataList().removeAll(removeList);
    }

    String calculateExpiryDate(String transDate) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(DateUtils.getDateFromString(transDate, DateUtils.YMD));
        } catch (Exception e) {

        }
        cal.add(Calendar.MONTH, 24);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.planningUnitId;
        hash = 17 * hash + Objects.hashCode(this.transDate);
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
        final NewSupplyPlan other = (NewSupplyPlan) obj;
        if (this.planningUnitId != other.planningUnitId) {
            return false;
        }
        if (!Objects.equals(this.transDate, other.transDate)) {
            return false;
        }
        return true;
    }
}
