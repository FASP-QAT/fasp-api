/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

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
public class StockStatusVerticalOutput implements Serializable {
    
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dt;
    private Integer openingBalance;
    private Boolean actualConsumption;
    private Integer consumptionQty;
    private Integer shipmentQty;
    private List<ShipmentInfo> shipmentInfo;
    private Integer adjustment;
    private Integer expiredStock;
    private Integer closingBalance;
    private Double amc;
    private Double mos;
    private int minMos;
    private int maxMos;

    public StockStatusVerticalOutput() {
        this.shipmentInfo = new LinkedList<>();
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Integer getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Integer openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Boolean getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Integer getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(Integer consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public Integer getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(Integer shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
        this.adjustment = adjustment;
    }

    public Integer getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(Integer expiredStock) {
        this.expiredStock = expiredStock;
    }

    public Integer getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Integer closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
        this.amc = amc;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public int getMinMos() {
        return minMos;
    }

    public void setMinMos(int minMos) {
        this.minMos = minMos;
    }

    public int getMaxMos() {
        return maxMos;
    }

    public void setMaxMos(int maxMos) {
        this.maxMos = maxMos;
    }

    public List<ShipmentInfo> getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(List<ShipmentInfo> shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.dt);
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
        final StockStatusVerticalOutput other = (StockStatusVerticalOutput) obj;
        if (!Objects.equals(this.dt, other.dt)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StockStatusVerticalOutput{" + "dt=" + dt + ", openingBalance=" + openingBalance + ", actualConsumption=" + actualConsumption + ", consumptionQty=" + consumptionQty + ", shipmentQty=" + shipmentQty + ", shipmentInfo=" + shipmentInfo + ", adjustment=" + adjustment + ", expiredStock=" + expiredStock + ", closingBalance=" + closingBalance + ", amc=" + amc + ", mos=" + mos + ", minMos=" + minMos + ", maxMos=" + maxMos + '}';
    }
    
}
