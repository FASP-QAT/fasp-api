/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author akil
 */
public class NewSupplyPlan implements Serializable {
    
    private int planningUnitId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date transDate;
    
    private Integer finalConsumption;
    private boolean actualConsumptionFlag;
    private Integer shipment;
    private Integer finalAdjustment;
    private Integer stock;
    private int regionCountForStock;
    private int regionCount;
    
    private int openingBalance;
    private int expiredStock;
    private int expectedStock;
    private int nationalAdjustment;
    private int closingBalance;
    private int unmetDemand;
    private List<RegionData> regionDataList;
    private List<BatchData> batchDataList;
    private int unallocatedConsumption;
    
    public NewSupplyPlan() {
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
    }
    
    public NewSupplyPlan(int planningUnitId, Date transDate) {
        this.planningUnitId = planningUnitId;
        this.transDate = transDate;
        this.regionDataList = new LinkedList<>();
        this.batchDataList = new LinkedList<>();
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
    
    public String getTransDateString() {
        if (transDate == null) {
            return "";
        } else {
            return new SimpleDateFormat("yyyy-MM-dd").format(this.transDate);
        }
    }
    
    public Integer getFinalConsumption() {
        return finalConsumption;
    }
    
    public void setFinalConsumption(Integer finalConsumption) {
        this.finalConsumption = finalConsumption;
    }
    
    public boolean isActualConsumptionFlag() {
        return actualConsumptionFlag;
    }
    
    public void setActualConsumptionFlag(boolean actualConsumptionFlag) {
        this.actualConsumptionFlag = actualConsumptionFlag;
    }
    
    public Integer getShipment() {
        return shipment;
    }
    
    public void setShipment(Integer shipment) {
        this.shipment = shipment;
    }
    
    public Integer getFinalAdjustment() {
        return finalAdjustment;
    }
    
    public void setFinalAdjustment(Integer finalAdjustment) {
        this.finalAdjustment = finalAdjustment;
    }
    
    public Integer getStock() {
        return stock;
    }
    
    public void setStock(Integer stock) {
        this.stock = stock;
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
    
    public int getOpeningBalance() {
        return openingBalance;
    }
    
    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }
    
    public int getExpiredStock() {
        return expiredStock;
    }
    
    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }
    
    public int getExpectedStock() {
        return expectedStock;
    }
    
    public void updateExpectedStock() {
        this.expectedStock = this.openingBalance - this.expiredStock + Optional.ofNullable(this.shipment).orElse(0) - Optional.ofNullable(this.finalConsumption).orElse(0) + Optional.ofNullable(this.finalAdjustment).orElse(0);
    }
    
    public int getNationalAdjustment() {
        return nationalAdjustment;
    }
    
    public void updateNationalAdjustment() {
        if ((this.regionCountForStock == this.regionCount && this.expectedStock != Optional.ofNullable(this.stock).orElse(0).intValue())
                || (this.regionCountForStock > 0 && this.regionCountForStock != this.regionCount && Optional.ofNullable(this.stock).orElse(0).intValue() > this.expectedStock)
                || (this.regionCountForStock > 0 && this.expectedStock < 0)) {
            this.nationalAdjustment = Optional.ofNullable(this.stock).orElse(0) - this.expectedStock;
        }
    }
    
    public int getClosingBalance() {
        return closingBalance;
    }
    
    public void updateClosingBalance() {
        if (this.regionCountForStock == this.regionCount) {
            this.closingBalance = Optional.ofNullable(this.stock).orElse(0);
        } else {
            this.closingBalance = Math.max(Optional.ofNullable(this.stock).orElse(0), this.expectedStock + this.nationalAdjustment);
        }
    }
    
    public int getUnmetDemand() {
        return unmetDemand;
    }
    
    public void updateUnmetDemand() {
        if (this.closingBalance == 0) {
            this.closingBalance = 0 - this.expectedStock;
        } else {
            this.unmetDemand = 0;
        }
        
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
    
    public void addFinalConsumption(Integer consumption) {
        if (consumption != null) {
            if (this.finalConsumption != null) {
                this.finalConsumption += consumption;
            } else {
                this.finalConsumption = consumption;
            }
        }
    }
    
    public void addStock(int stock) {
        if (this.stock == null) {
            this.stock = stock;
        } else {
            this.stock += stock;
        }
    }
    
    public void addAdjustment(Integer adjustment) {
        if (adjustment != null) {
            if (this.finalAdjustment == null) {
                this.finalAdjustment = adjustment;
            } else {
                this.finalAdjustment += adjustment;
            }
        }
    }
    
    public boolean isAllRegionsReportedStock() {
        return (this.regionCount == this.regionCountForStock);
    }
    
    public boolean isUseAdjustment() {
        return (this.stock == null);
    }
    
    @JsonIgnore
    public Date getPrevTransDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(this.transDate);
        c.add(Calendar.MONTH, -1);
        return c.getTime();
    }
    
    public void addOpeningBalance(int ob) {
        this.openingBalance += ob;
    }
    
    public void updateExpiredStock() {
        this.batchDataList.forEach(bd -> {
            if (bd.getExpiryDate() != null && DateUtils.compareDate(bd.getExpiryDate(), this.transDate) <= 0) {
                bd.setExpiredStock(Optional.ofNullable(bd.getShipment()).orElse(0) + bd.getOpeningBalance());
                this.expiredStock = Optional.ofNullable(this.expiredStock).orElse(0) + bd.getExpiredStock();
            }
        });
    }
    
    public void updateUnallocatedConsumption() {
        int totalConsumption = this.finalConsumption + this.finalAdjustment;
        this.batchDataList.forEach(bd -> {
            if (bd.getBatchId() != null) {
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                tempCB = Math.max(tempCB, Optional.ofNullable(bd.getStock()).orElse(0));
            } else {
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                tempCB = Math.max(tempCB, Optional.ofNullable(bd.getStock()).orElse(0));
                if (tempCB < 0) {
                    this.unallocatedConsumption -= tempCB;
                }
            }
        });
    }
    
    public void updateBatchData() {
        int periodConsumption = Optional.ofNullable(this.finalConsumption).orElse(0) - Optional.ofNullable(this.finalAdjustment).orElse(0) - Optional.ofNullable(this.nationalAdjustment).orElse(0);
        // draw down from the Batches that you have
        for (BatchData bd : this.getBatchDataList()) {
            bd.setUnallocatedConsumption(periodConsumption);
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
                bd.setCalculatedConsumption(0-periodConsumption);
                int tempCB
                        = bd.getOpeningBalance()
                        - bd.getExpiredStock()
                        + Optional.ofNullable(bd.getShipment()).orElse(0)
                        - (bd.isUseActualConsumption() ? Optional.ofNullable(bd.getActualConsumption()).orElse(0) : 0)
                        + (bd.isUseAdjustment() ? Optional.ofNullable(bd.getAdjustment()).orElse(0) : 0);
                bd.setClosingBalance(tempCB - periodConsumption);
                periodConsumption = 0;
            }
        }
        if (periodConsumption != 0) {
            System.out.println("We need to create a new Batch for this");
        }
        
    }
    
    public void removeUnusedBatches() {
        List<BatchData> removeList = new LinkedList<>();
        this.getBatchDataList().stream().filter(bd -> (bd.getOpeningBalance() == 0
                && Optional.ofNullable(bd.getShipment()).orElse(0) == 0
                && Optional.ofNullable(bd.getActualConsumption()).orElse(0) == 0
                && Optional.ofNullable(bd.getStock()).orElse(0) == 0
                && Optional.ofNullable(bd.getCalculatedConsumption()).orElse(0) == 0
                && Optional.ofNullable(bd.getClosingBalance()).orElse(0) == 0)).forEachOrdered(bd -> {
                    removeList.add(bd);
        });
        this.getBatchDataList().removeAll(removeList);
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
