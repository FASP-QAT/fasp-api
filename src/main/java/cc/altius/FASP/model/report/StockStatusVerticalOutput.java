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
    private int openingBalance;
    private Boolean actualConsumption;
    private int consumptionQty;
    private int shipmentQty;
    private List<ShipmentInfo> shipmentInfo;
    private int adjustment;
    private int expiredStock;
    private int closingBalance;
    private double amc;
    private double mos;
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

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Boolean getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public int getConsumptionQty() {
        return consumptionQty;
    }

    public void setConsumptionQty(int consumptionQty) {
        this.consumptionQty = consumptionQty;
    }

    public int getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(int shipmentQty) {
        this.shipmentQty = shipmentQty;
    }


    public int getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }

    public int getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(int expiredStock) {
        this.expiredStock = expiredStock;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getAmc() {
        return amc;
    }

    public void setAmc(double amc) {
        this.amc = amc;
    }

    public double getMos() {
        return mos;
    }

    public void setMos(double mos) {
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
    
}
