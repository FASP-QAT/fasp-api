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

    /* new batch counter comes frmo MasterSupplyPlan*/
    private int planningUnitId;
    private String transDate;
    private int shelfLife;
    private Long actualConsumptionQty;
    private Long forecastedConsumptionQty;
    private Long finalConsumptionQty;
    private boolean actualConsumptionFlag;
    private long plannedShipmentsTotalData;
    private long submittedShipmentsTotalData;
    private long approvedShipmentsTotalData;
    private long shippedShipmentsTotalData;
    private long receivedShipmentsTotalData;
    private long onholdShipmentsTotalData;
    private long plannedErpShipmentsTotalData;
    private long submittedErpShipmentsTotalData;
    private long approvedErpShipmentsTotalData;
    private long shippedErpShipmentsTotalData;
    private long receivedErpShipmentsTotalData;
    private long onholdErpShipmentsTotalData;
    private Long finalAdjustmentQty;
    private Long adjustmentQty;
    private Long stockQty;
    private int regionCountForStock;
    private int regionCount;

    private long openingBalance;
    private long expiredStock;
    private long expectedStock;
    private long nationalAdjustment;
    private long closingBalance;
    private long unmetDemand;

    private long openingBalanceWps;
    private long expiredStockWps;
    private long expectedStockWps;
    private long nationalAdjustmentWps;
    private long closingBalanceWps;
    private long unmetDemandWps;
//    private int newBatchCounter;

    private List<RegionData> regionDataList;
    private List<BatchData> batchDataList;

    public NewSupplyPlan() {
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
//        this.newBatchCounter = -1;
    }

    public NewSupplyPlan(int planningUnitId, String transDate) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
//        this.newBatchCounter = -1;
    }

    public NewSupplyPlan(int planningUnitId, String transDate, int shelfLife) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.shelfLife = shelfLife;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
//        this.newBatchCounter = -1;
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

    public Long getActualConsumptionQty() {
        return actualConsumptionQty;
    }

    public void addActualConsumptionQty(Long actualConsumptionQty) {
        if (actualConsumptionQty != null) {
            if (this.actualConsumptionQty == null) {
                this.actualConsumptionQty = actualConsumptionQty;
            } else {
                this.actualConsumptionQty += actualConsumptionQty;
            }
        }
    }

    public Long getForecastedConsumptionQty() {
        return forecastedConsumptionQty;
    }

    public void addForecastedConsumptionQty(Long forecastedConsumptionQty) {
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

    public Long getFinalConsumptionQty() {
        return finalConsumptionQty;
    }

    public void setFinalConsumptionQty(Long finalConsumptionQty) {
        this.finalConsumptionQty = finalConsumptionQty;
    }

    public Long getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Long adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public Long getStockQty() {
        return stockQty;
    }

    public void setStockQty(Long stockQty) {
        this.stockQty = stockQty;
    }

    public Long getFinalAdjustmentQty() {
        return finalAdjustmentQty;
    }

    public void setFinalAdjustmentQty(Long finalAdjustmentQty) {
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

    public long getReceivedShipmentsTotalData() {
        return receivedShipmentsTotalData;
    }

    public void addReceivedShipmentsTotalData(long receivedShipmentsTotalData) {
        this.receivedShipmentsTotalData += receivedShipmentsTotalData;
    }

    public long getShippedShipmentsTotalData() {
        return shippedShipmentsTotalData;
    }

    public void addShippedShipmentsTotalData(long shippedShipmentsTotalData) {
        this.shippedShipmentsTotalData += shippedShipmentsTotalData;
    }

    public long getApprovedShipmentsTotalData() {
        return approvedShipmentsTotalData;
    }

    public void addApprovedShipmentsTotalData(long approvedShipmentsTotalData) {
        this.approvedShipmentsTotalData += approvedShipmentsTotalData;
    }

    public long getSubmittedShipmentsTotalData() {
        return submittedShipmentsTotalData;
    }

    public void addSubmittedShipmentsTotalData(long submittedShipmentsTotalData) {
        this.submittedShipmentsTotalData += submittedShipmentsTotalData;
    }

    public long getPlannedShipmentsTotalData() {
        return plannedShipmentsTotalData;
    }

    public void addPlannedShipmentsTotalData(long plannedShipmentsTotalData) {
        this.plannedShipmentsTotalData += plannedShipmentsTotalData;
    }

    public long getOnholdShipmentsTotalData() {
        return onholdShipmentsTotalData;
    }

    public void addOnholdShipmentsTotalData(long onholdShipmentsTotalData) {
        this.onholdShipmentsTotalData += onholdShipmentsTotalData;
    }

    public long getReceivedErpShipmentsTotalData() {
        return receivedErpShipmentsTotalData;
    }

    public void addReceivedErpShipmentsTotalData(long receivedErpShipmentsTotalData) {
        this.receivedErpShipmentsTotalData += receivedErpShipmentsTotalData;
    }

    public long getShippedErpShipmentsTotalData() {
        return shippedErpShipmentsTotalData;
    }

    public void addShippedErpShipmentsTotalData(long shippedErpShipmentsTotalData) {
        this.shippedErpShipmentsTotalData += shippedErpShipmentsTotalData;
    }

    public long getApprovedErpShipmentsTotalData() {
        return approvedErpShipmentsTotalData;
    }

    public void addApprovedErpShipmentsTotalData(long approvedErpShipmentsTotalData) {
        this.approvedErpShipmentsTotalData += approvedErpShipmentsTotalData;
    }

    public long getSubmittedErpShipmentsTotalData() {
        return submittedErpShipmentsTotalData;
    }

    public void addSubmittedErpShipmentsTotalData(long submittedErpShipmentsTotalData) {
        this.submittedErpShipmentsTotalData += submittedErpShipmentsTotalData;
    }

    public long getPlannedErpShipmentsTotalData() {
        return plannedErpShipmentsTotalData;
    }

    public void addPlannedErpShipmentsTotalData(long plannedErpShipmentsTotalData) {
        this.plannedErpShipmentsTotalData += plannedErpShipmentsTotalData;
    }

    public long getOnholdErpShipmentsTotalData() {
        return onholdErpShipmentsTotalData;
    }

    public void addOnholdErpShipmentsTotalData(long onholdErpShipmentsTotalData) {
        this.onholdErpShipmentsTotalData += onholdErpShipmentsTotalData;
    }

    public long getManualShipmentTotal() {
        return this.plannedShipmentsTotalData + this.getManualShipmentTotalWps();
    }

    public long getManualShipmentTotalWps() {
        return this.submittedShipmentsTotalData + this.approvedShipmentsTotalData + this.shippedShipmentsTotalData + this.receivedShipmentsTotalData + this.onholdShipmentsTotalData;
    }

    public long getErpShipmentTotal() {
        return this.plannedErpShipmentsTotalData + getErpShipmentTotalWps();
    }

    public long getErpShipmentTotalWps() {
        return this.submittedErpShipmentsTotalData + this.approvedErpShipmentsTotalData + this.shippedErpShipmentsTotalData + this.receivedErpShipmentsTotalData + this.onholdErpShipmentsTotalData;
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

    public long getExpectedStock() {
        return expectedStock;
    }

    public long getExpectedStockWps() {
        return expectedStockWps;
    }

    public void updateExpectedStock() {
        this.expectedStock = this.openingBalance - this.expiredStock + (getManualShipmentTotal() + getErpShipmentTotal()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0L) + Optional.ofNullable(this.finalAdjustmentQty).orElse(0L);
        this.expectedStockWps = this.openingBalanceWps - this.expiredStockWps + (getManualShipmentTotalWps() + getErpShipmentTotalWps()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0L) + Optional.ofNullable(this.finalAdjustmentQty).orElse(0L);
    }

    public long getNationalAdjustment() {
        return nationalAdjustment;
    }

    public long getNationalAdjustmentWps() {
        return nationalAdjustmentWps;
    }

    public void updateNationalAdjustment() {
        if ((this.regionCountForStock == this.regionCount && this.expectedStock != Optional.ofNullable(this.stockQty).orElse(0L).longValue())
                || (this.regionCountForStock > 0 && this.regionCountForStock != this.regionCount && Optional.ofNullable(this.stockQty).orElse(0L).longValue() > this.expectedStock)
                || (this.regionCountForStock > 0 && this.expectedStock < 0)) {
            this.nationalAdjustment = Optional.ofNullable(this.stockQty).orElse(0L) - this.expectedStock;
        }

        if ((this.regionCountForStock == this.regionCount && this.expectedStockWps != Optional.ofNullable(this.stockQty).orElse(0L).longValue())
                || (this.regionCountForStock > 0 && this.regionCountForStock != this.regionCount && Optional.ofNullable(this.stockQty).orElse(0L).longValue() > this.expectedStockWps)
                || (this.regionCountForStock > 0 && this.expectedStockWps < 0)) {
            this.nationalAdjustmentWps = Optional.ofNullable(this.stockQty).orElse(0L) - this.expectedStockWps;
        }
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

    public long getClosingBalanceWps() {
        return closingBalanceWps;
    }

    public void updateClosingBalance() {
        if (this.regionCountForStock == this.regionCount) {
            this.closingBalance = Optional.ofNullable(this.stockQty).orElse(0L);
        } else {
            this.closingBalance = Math.max(Optional.ofNullable(this.stockQty).orElse(0L), Math.max(this.expectedStock + this.nationalAdjustment, 0));
        }

        if (this.regionCountForStock == this.regionCount) {
            this.closingBalanceWps = Optional.ofNullable(this.stockQty).orElse(0L);
        } else {
            this.closingBalanceWps = Math.max(Optional.ofNullable(this.stockQty).orElse(0L), Math.max(this.expectedStockWps + this.nationalAdjustmentWps, 0));
        }
    }

    public long getUnmetDemand() {
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

    public long getUnmetDemandWps() {
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

    public void addFinalConsumptionQty(Long consumption) {
        if (consumption != null) {
            if (this.finalConsumptionQty != null) {
                this.finalConsumptionQty += consumption;
            } else {
                this.finalConsumptionQty = consumption;
            }
        }
    }

    public void addStockQty(Long stock) {
        if (stock != null) {
            if (this.stockQty == null) {
                this.stockQty = stock;
            } else {
                this.stockQty += stock;
            }
        }
    }

    public void addAdjustmentQty(Long adjustment) {
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

    private void addFinalAdjustmentQty(Long adjustemnt) {
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

    public void addOpeningBalance(long ob) {
        this.openingBalance += ob;
    }

    public void addOpeningBalanceWps(long ob) {
        this.openingBalanceWps += ob;
    }

    public void updateExpiredStock() {
        this.batchDataList.forEach(bd -> {
            if (bd.getExpiryDate() != null && DateUtils.compareDates(bd.getExpiryDate(), this.transDate) <= 0) {
                bd.setExpiredStock(Optional.ofNullable(bd.getShipment()).orElse(0L) + bd.getOpeningBalance());
                this.expiredStock = Optional.ofNullable(this.expiredStock).orElse(0L) + bd.getExpiredStock();
                bd.setExpiredStockWps(Optional.ofNullable(bd.getShipmentWps()).orElse(0L) + bd.getOpeningBalanceWps());
                this.expiredStockWps = Optional.ofNullable(this.expiredStockWps).orElse(0L) + bd.getExpiredStockWps();
            }
        });
    }

    public int updateBatchData(int newBatchCounter) {
        long periodConsumption = Optional.ofNullable(this.finalConsumptionQty).orElse(0L) - Optional.ofNullable(this.finalAdjustmentQty).orElse(0L) - Optional.ofNullable(this.nationalAdjustment).orElse(0L);
        long periodConsumptionWps = Optional.ofNullable(this.finalConsumptionQty).orElse(0L) - Optional.ofNullable(this.finalAdjustmentQty).orElse(0L) - Optional.ofNullable(this.nationalAdjustmentWps).orElse(0L);
        // draw down from the Batches that you have
        for (BatchData bd : this.getBatchDataList().stream().sorted(new ComparatorBatchData()).collect(Collectors.toList())) {
            bd.setUnallocatedConsumption(periodConsumption);
            bd.setUnallocatedConsumptionWps(periodConsumptionWps);
            if (periodConsumption > 0) {
                long tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
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
                long tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
                bd.setClosingBalance(tempCB - periodConsumption);
            } else if (bd.getOpeningBalance() - bd.getExpiredStock() + Optional.ofNullable(bd.getShipment()).orElse(0L) > 0) {
                bd.setCalculatedConsumption(0 - periodConsumption);
                long tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
                bd.setClosingBalance(tempCB - periodConsumption);
                periodConsumption = 0;
            }

            if (periodConsumptionWps > 0) {
                long tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
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
                long tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
                bd.setClosingBalanceWps(tempCB - periodConsumptionWps);
            } else if (bd.getOpeningBalanceWps() - bd.getExpiredStockWps() + Optional.ofNullable(bd.getShipmentWps()).orElse(0L) > 0) {
                bd.setCalculatedConsumptionWps(0 - periodConsumptionWps);
                long tempCB
                        = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0L)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0L) : 0);
                bd.setClosingBalanceWps(tempCB - periodConsumptionWps);
                periodConsumptionWps = 0;
            }
        }
        if (periodConsumption < 0 || periodConsumptionWps < 0) {
            System.out.println("We need to create a new Batch for periodConsumptionWps:" + periodConsumptionWps + " PlanningUnitId:" + this.planningUnitId + " transDate:" + this.transDate);
            BatchData bdNew = new BatchData();
            bdNew.setBatchId(newBatchCounter);
            bdNew.setShelfLife(this.shelfLife);
            bdNew.setExpiryDate(this.calculateExpiryDate(this.transDate));
            bdNew.setOpeningBalance(0);
            bdNew.setOpeningBalanceWps(0);
            bdNew.setCalculatedConsumption(0 - periodConsumption);
            bdNew.setCalculatedConsumptionWps(0 - periodConsumptionWps);
            bdNew.setClosingBalance(0 - periodConsumption);
            bdNew.setClosingBalanceWps(0 - periodConsumptionWps);
            bdNew.setAllRegionsReportedStock(this.isAllRegionsReportedStock());
            bdNew.setUseAdjustment(this.isUseAdjustment());
            this.batchDataList.add(bdNew);
            newBatchCounter--;
        }
        return newBatchCounter;
    }

    public void removeUnusedBatches() {
        List<BatchData> removeList = new LinkedList<>();
        this.getBatchDataList().stream().filter(bd -> (bd.getOpeningBalance() == 0
                && Optional.ofNullable(bd.getShipment()).orElse(0L) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0L) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedConsumption()).orElse(0L) == 0
                && Optional.ofNullable(bd.getClosingBalance()).orElse(0L) == 0
                && bd.getOpeningBalanceWps() == 0
                && Optional.ofNullable(bd.getShipmentWps()).orElse(0L) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0L) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedConsumptionWps()).orElse(0L) == 0
                && Optional.ofNullable(bd.getClosingBalanceWps()).orElse(0L) == 0)
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
        cal.add(Calendar.MONTH, this.shelfLife);
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
