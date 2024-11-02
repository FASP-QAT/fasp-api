/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

/**
 *
 * @author akil
 */
public class RegionData {

    private int regionId;
    private Double forecastedConsumption;
    private Double actualConsumption;
    private Double adjustedConsumption;
    private Double stock;
    private Double adjustment;

    public RegionData() {
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public Double getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Double forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public Double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Double getAdjustedConsumption() {
        return adjustedConsumption;
    }

    public void setAdjustedConsumption(Double adjustedConsumption) {
        this.adjustedConsumption = adjustedConsumption;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Double adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + this.regionId;
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
        final RegionData other = (RegionData) obj;
        if (this.regionId != other.regionId) {
            return false;
        }
        return true;
    }

}
