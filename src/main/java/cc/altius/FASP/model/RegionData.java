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
    private Long forecastedConsumption;
    private Long actualConsumption;
    private Long adjustedConsumption;
    private Long stock;
    private Long adjustment;

    public RegionData() {
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public Long getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Long forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public Long getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Long actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Long getAdjustedConsumption() {
        return adjustedConsumption;
    }

    public void setAdjustedConsumption(Long adjustedConsumption) {
        this.adjustedConsumption = adjustedConsumption;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Long adjustment) {
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
