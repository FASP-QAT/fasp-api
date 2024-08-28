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
    // Adjusted for Days of Stock out
    private Long adjustedConsumptionQty;
    // If Actual is available then Actual otherwise Forecasted
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

    private List<RegionData> regionDataList;
    private List<BatchData> batchDataList;

    public NewSupplyPlan() {
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
    }

    public NewSupplyPlan(int planningUnitId, String transDate) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
    }

    public NewSupplyPlan(int planningUnitId, String transDate, int shelfLife) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.shelfLife = shelfLife;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
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

    public Long getAdjustedConsumptionQty() {
        return adjustedConsumptionQty;
    }

    public void addAdjustedConsumptionQty(Long adjustedConsumptionQty) {
        if (adjustedConsumptionQty != null) {
            if (this.adjustedConsumptionQty == null) {
                this.adjustedConsumptionQty = adjustedConsumptionQty;
            } else {
                this.adjustedConsumptionQty += adjustedConsumptionQty;
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
        this.expectedStock = this.openingBalance - this.expiredStock + (getManualShipmentTotal() + getErpShipmentTotal()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0L) + Optional.ofNullable(this.adjustmentQty).orElse(0L);
        this.expectedStockWps = this.openingBalanceWps - this.expiredStockWps + (getManualShipmentTotalWps() + getErpShipmentTotalWps()) - Optional.ofNullable(this.finalConsumptionQty).orElse(0L) + Optional.ofNullable(this.adjustmentQty).orElse(0L);
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

//    public void updateUnmetDemand() {
//        if (!isAllRegionsReportedStock()) {
//            if (this.closingBalance == 0) {
//                this.unmetDemand = 0 - this.expectedStock + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//            } else {
//                this.unmetDemand = 0 + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//            }
//
//            if (this.closingBalanceWps == 0) {
//                this.unmetDemandWps = 0 - this.expectedStockWps + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//            } else {
//                this.unmetDemandWps = 0 + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//            }
//        } else {
//            this.unmetDemand = 0 + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//            this.unmetDemandWps = 0 + ((this.adjustedConsumptionQty != null ? this.adjustedConsumptionQty : 0) - (this.actualConsumptionQty != null ? this.actualConsumptionQty : 0));
//        }
//    }
    public void updateUnmetDemand() {
        if (isAllRegionsReportedStock() || (!isAllRegionsReportedStock() && this.closingBalance != 0)) {
            this.unmetDemand = Optional.ofNullable(this.adjustedConsumptionQty).orElse(0L) - Optional.ofNullable(this.actualConsumptionQty).orElse(0L);
            this.unmetDemandWps = this.unmetDemand;
        } else {
            this.unmetDemand = 0 - this.expectedStock + (Optional.ofNullable(this.adjustedConsumptionQty).orElse(0L) - Optional.ofNullable(this.actualConsumptionQty).orElse(0L));
            this.unmetDemandWps = 0 - this.expectedStockWps + (Optional.ofNullable(this.adjustedConsumptionQty).orElse(0L) - Optional.ofNullable(this.actualConsumptionQty).orElse(0L));
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
        this.batchDataList = batchDataList.stream().sorted(new ComparatorBatchData()).collect(Collectors.toList());
    }

    private int getSizeOfBatchDataList() {
        return this.getBatchDataList().size();
    }

    private int getNextBatchDataIndexForFEFO(int prevIndex) {
        int curIndex = 0;
        if (prevIndex == -1) { // is the first request
            if (getSizeOfBatchDataList() > curIndex) {
                // there is one batch
                return curIndex;
            } else {
                // no batch available
                return -1;
            }
        } else {
            curIndex = prevIndex + 1;
            if (getSizeOfBatchDataList() > curIndex) {
                // there is one more batch
                return curIndex;
            } else {
                // no batch available
                return -1;
            }
        }
    }

    private int getNextBatchDataIndexForLEFO(int prevIndex) {
        int curIndex = 0;
        if (prevIndex == -1) { // is the first request
            if (getSizeOfBatchDataList() > curIndex) {
                // there is one batch
                return curIndex;
            } else {
                // no batch available
                return -1;
            }
        } else {
            curIndex = prevIndex - 1;
            if (curIndex >= 0) {
                // there is one more batch
                return curIndex;
            } else {
                // no batch available
                return -1;
            }
        }
    }

    private BatchData getBatchData(int index) {
        return this.batchDataList.get(index);
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
            /**
             * Earlier we were using finalAdjustments where only if all regions
             * had reported stock then the adjustment was not being considered,
             * and in conditions where all regions had not reported stock then
             * we considered the adjustment
             */
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
            if (bd.getExpiryDate() != null && DateUtils.compareDates(bd.getExpiryDate().substring(0, 7) + "-01", this.transDate) <= 0) {
                bd.setExpiredStock(Optional.ofNullable(bd.getShipment()).orElse(0L) + bd.getOpeningBalance());
                this.expiredStock = Optional.ofNullable(this.expiredStock).orElse(0L) + bd.getExpiredStock();
                bd.setExpiredStockWps(Optional.ofNullable(bd.getShipmentWps()).orElse(0L) + bd.getOpeningBalanceWps());
                this.expiredStockWps = Optional.ofNullable(this.expiredStockWps).orElse(0L) + bd.getExpiredStockWps();
            }
        });
    }

    public int updateBatchData(int newBatchCounter) {
        Long inventoryQty=0L;
        for (int x = 0; x < getSizeOfBatchDataList(); x++) {
            BatchData bd = getBatchData(x);
            inventoryQty+=Optional.ofNullable(bd.getInventoryQty()).orElse(0L);
        }
        System.out.println("closing balance "+this.closingBalance+" Inventory qty"+inventoryQty);
        if (this.closingBalance==inventoryQty) {
            for (int x = 0; x < getSizeOfBatchDataList(); x++) {
                BatchData bd = getBatchData(x);
                bd.setClosingBalance(Optional.ofNullable(bd.getInventoryQty()).orElse(0L));
                bd.setClosingBalanceWps(bd.getClosingBalance());
                bd.setUnallocatedFEFO(0);
                bd.setUnallocatedFEFOWps(0);
                bd.setUnallocatedLEFO(0);
                bd.setUnallocatedLEFOWps(0);
                bd.setCalculatedFEFO(0);
                bd.setCalculatedFEFOWps(0);
                bd.setCalculatedLEFO(0);
                bd.setCalculatedLEFOWps(0);
            }
        } else {
            long unallocatedFEFO = Optional.ofNullable(this.finalConsumptionQty).orElse(0L) - Math.min(0, Optional.ofNullable(this.adjustmentQty).orElse(0L) + Optional.ofNullable(this.nationalAdjustment).orElse(0L)); // FEFO
            long unallocatedLEFO = 0L - Math.max(0, Optional.ofNullable(this.adjustmentQty).orElse(0L) + Optional.ofNullable(this.nationalAdjustment).orElse(0L)); // LEFO
            long unallocatedFEFOWps = Optional.ofNullable(this.finalConsumptionQty).orElse(0L) - Math.min(0, Optional.ofNullable(this.adjustmentQty).orElse(0L) + Optional.ofNullable(this.nationalAdjustment).orElse(0L)); // FEFO
            long unallocatedLEFOWps = 0L - Math.max(0, Optional.ofNullable(this.adjustmentQty).orElse(0L) + Optional.ofNullable(this.nationalAdjustment).orElse(0L)); // LEFO
            for (int x = 0; x < getSizeOfBatchDataList(); x++) {
                BatchData bd = getBatchData(x);
                long tempOB = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0L);
                long consumption = (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0L) : 0);
                long adjustment = Optional.ofNullable(bd.getAdjustment()).orElse(0L);
                if (adjustment + Optional.ofNullable(this.nationalAdjustment).orElse(0L) > 0) {
                    if ((tempOB + adjustment) >= 0) {
                        unallocatedLEFO += adjustment;
                    } else {
                        unallocatedLEFO -= tempOB;
                    }
                } else {
                    if ((tempOB + adjustment) >= 0) {
                        unallocatedFEFO += adjustment;
                    } else {
                        unallocatedFEFO -= tempOB;
                    }
                }

                if ((tempOB - consumption + adjustment) >= 0) {
                    unallocatedFEFO -= consumption;
                } else {
                    unallocatedFEFO -= tempOB + adjustment > 0 ? tempOB + adjustment : 0;
                }

                if (tempOB - consumption + adjustment > 0) {
                    bd.setClosingBalance(tempOB - consumption + adjustment);
                } else {
                    bd.setClosingBalance(0);
                }

//            WPS Calculations
                long tempOBWps = bd.getOpeningBalanceWps()
                        - bd.getExpiredStockWps()
                        + Optional.ofNullable(bd.getShipmentWps()).orElse(0L);
                if (adjustment + Optional.ofNullable(this.nationalAdjustment).orElse(0L) > 0) {
                    if ((tempOBWps + adjustment) >= 0) {
                        unallocatedLEFOWps += adjustment;
                    } else {
                        unallocatedLEFOWps -= tempOBWps;
                    }
                } else {
                    if ((tempOBWps + adjustment) >= 0) {
                        unallocatedFEFOWps += adjustment;
                    } else {
                        unallocatedFEFOWps -= tempOBWps;
                    }
                }

                if ((tempOBWps - consumption + adjustment) >= 0) {
                    unallocatedFEFOWps -= consumption;
                } else {
                    unallocatedFEFOWps -= tempOBWps + adjustment > 0 ? tempOBWps + adjustment : 0;
                }

                if (tempOBWps - consumption + adjustment > 0) {
                    bd.setClosingBalanceWps(tempOBWps - consumption + adjustment);
                } else {
                    bd.setClosingBalanceWps(0);
                }

            }

            if (unallocatedLEFO != 0) {
                for (int x = getSizeOfBatchDataList() - 1; x >= 0; x--) {
                    if (unallocatedLEFO != 0) {
                        BatchData bd = getBatchData(x);
                        long tempCB = bd.getClosingBalance();
                        bd.setUnallocatedLEFO(unallocatedLEFO);
                        if (tempCB >= unallocatedLEFO && DateUtils.compareDates(bd.getExpiryDate().substring(0, 7) + "-01", this.transDate) > 0) { // There is equal or more stock than Adjustment 
                            bd.setClosingBalance(tempCB - unallocatedLEFO);
                            bd.setCalculatedLEFO(unallocatedLEFO);
                            unallocatedLEFO = 0;
                        } else {
                            bd.setClosingBalance(0);
                            bd.setCalculatedLEFO(tempCB);
                            unallocatedLEFO -= tempCB;
                        }
                    }
                }
            }

            if (unallocatedLEFOWps != 0) {
                for (int x = getSizeOfBatchDataList() - 1; x >= 0; x--) {
                    if (unallocatedLEFOWps != 0) {
                        BatchData bd = getBatchData(x);
                        long tempCB = bd.getClosingBalanceWps();
                        bd.setUnallocatedLEFOWps(unallocatedLEFOWps);
                        if (tempCB >= unallocatedLEFOWps && DateUtils.compareDates(bd.getExpiryDate().substring(0, 7) + "-01", this.transDate) > 0) { // There is equal or more stock than Adjustment 
                            bd.setClosingBalanceWps(tempCB - unallocatedLEFOWps);
                            bd.setCalculatedLEFOWps(unallocatedLEFOWps);
                            unallocatedLEFOWps = 0;
                        } else {
                            bd.setClosingBalanceWps(0);
                            bd.setCalculatedLEFOWps(tempCB);
                            unallocatedLEFOWps -= tempCB;
                        }
                    }
                }
            }

            if (unallocatedLEFO < 0 || unallocatedLEFOWps < 0) {
                System.out.println("We need to create a new Batch for unallocatedFEFO:" + unallocatedFEFO + " PlanningUnitId:" + this.planningUnitId + " transDate:" + this.transDate);
                BatchData bdNew = new BatchData();
                bdNew.setBatchId(newBatchCounter);
                bdNew.setShelfLife(this.shelfLife);
                bdNew.setExpiryDate(this.calculateExpiryDate(this.transDate));
                bdNew.setOpeningBalance(0);
                bdNew.setOpeningBalanceWps(0);
                bdNew.setUnallocatedLEFO(unallocatedLEFO < 0 ? unallocatedLEFO : 0);
                bdNew.setCalculatedLEFO(unallocatedLEFO < 0 ? unallocatedLEFO : 0);
                bdNew.setUnallocatedLEFOWps(unallocatedLEFOWps < 0 ? unallocatedLEFOWps : 0);
                bdNew.setCalculatedLEFOWps(unallocatedLEFOWps < 0 ? unallocatedLEFOWps : 0);
                bdNew.setClosingBalance(unallocatedLEFO < 0 ? 0 - unallocatedLEFO : 0);
                bdNew.setClosingBalanceWps(unallocatedLEFOWps < 0 ? 0 - unallocatedLEFOWps : 0);
                bdNew.setAllRegionsReportedStock(this.isAllRegionsReportedStock());
                this.batchDataList.add(bdNew);
                newBatchCounter--;
            }

            for (int x = 0; x < getSizeOfBatchDataList(); x++) {
                BatchData bd = getBatchData(x);
                long tempCB = bd.getClosingBalance();
                bd.setUnallocatedFEFO(unallocatedFEFO);
                if (tempCB >= unallocatedFEFO && DateUtils.compareDates(bd.getExpiryDate().substring(0, 7) + "-01", this.transDate) > 0) { // There is equal or more stock than Consumption 
                    bd.setClosingBalance(tempCB - unallocatedFEFO);
                    bd.setCalculatedFEFO(unallocatedFEFO);
                    unallocatedFEFO = 0;
                } else {
                    bd.setClosingBalance(0);
                    bd.setCalculatedFEFO(tempCB);
                    unallocatedFEFO -= tempCB;
                }
            }

            for (int x = 0; x < getSizeOfBatchDataList(); x++) {
                BatchData bd = getBatchData(x);
                long tempCB = bd.getClosingBalanceWps();
                bd.setUnallocatedFEFOWps(unallocatedFEFOWps);
                if (tempCB >= unallocatedFEFOWps && DateUtils.compareDates(bd.getExpiryDate().substring(0, 7) + "-01", this.transDate) > 0) { // There is equal or more stock than Consumption 
                    bd.setClosingBalanceWps(tempCB - unallocatedFEFOWps);
                    bd.setCalculatedFEFOWps(unallocatedFEFOWps);
                    unallocatedFEFOWps = 0;
                } else {
                    bd.setClosingBalanceWps(0);
                    bd.setCalculatedFEFOWps(tempCB);
                    unallocatedFEFOWps -= tempCB;
                }
            }
        }
        return newBatchCounter;
    }

    public void removeUnusedBatches() {
        List<BatchData> removeList = new LinkedList<>();
        this.getBatchDataList().stream().filter(bd -> (bd.getOpeningBalance() == 0
                && Optional.ofNullable(bd.getShipment()).orElse(0L) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0L) == 0
                && Optional.ofNullable(bd.getAdjustment()).orElse(0L) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedFEFO()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedLEFO()).orElse(0L) == 0
                && Optional.ofNullable(bd.getClosingBalance()).orElse(0L) == 0
                && bd.getOpeningBalanceWps() == 0
                && Optional.ofNullable(bd.getShipmentWps()).orElse(0L) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0L) == 0
                && Optional.ofNullable(bd.getAdjustment()).orElse(0L) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedFEFOWps()).orElse(0L) == 0
                && Optional.ofNullable(bd.getCalculatedLEFOWps()).orElse(0L) == 0
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
