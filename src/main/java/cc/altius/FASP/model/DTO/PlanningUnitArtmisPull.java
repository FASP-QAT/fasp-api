/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class PlanningUnitArtmisPull implements Serializable {

    private int id;
    private int planningUnitId;
    private String label;
    private Double multiplier;
    private Integer unitId;
    private String skuCode;
    private Integer forecastingUnitId;
    private Double catalogPrice;
    private Integer moq;
    private Integer unitsPerPalletEuro1;
    private Integer unitsPerPalletEuro2;
    private Integer unitsPerContainer;
    private Double volume;
    private Double weight;
    private boolean found;
    private boolean duplicate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getMultiplier() {
        return multiplier;
}

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getForecastingUnitId() {
        return forecastingUnitId;
    }

    public void setForecastingUnitId(Integer forecastingUnitId) {
        this.forecastingUnitId = forecastingUnitId;
    }

    public Double getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(Double catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public Integer getMoq() {
        return moq;
    }

    public void setMoq(Integer moq) {
        this.moq = moq;
    }

    public Integer getUnitsPerPalletEuro1() {
        return unitsPerPalletEuro1;
    }

    public void setUnitsPerPalletEuro1(Integer unitsPerPalletEuro1) {
        this.unitsPerPalletEuro1 = unitsPerPalletEuro1;
    }

    public Integer getUnitsPerPalletEuro2() {
        return unitsPerPalletEuro2;
    }

    public void setUnitsPerPalletEuro2(Integer unitsPerPalletEuro2) {
        this.unitsPerPalletEuro2 = unitsPerPalletEuro2;
    }

    public Integer getUnitsPerContainer() {
        return unitsPerContainer;
    }

    public void setUnitsPerContainer(Integer unitsPerContainer) {
        this.unitsPerContainer = unitsPerContainer;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

}
