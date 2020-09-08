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
    private Integer forecastedConsumption;
    private Integer actualConsumption;
    private Integer stock;
    private Integer adjustment;

    public RegionData() {
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public Integer getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Integer forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public Integer getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Integer actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Integer adjustment) {
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
