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
public class ProcurementUnitArtmisPull implements Serializable {

    private int procurementUnitId;
    private String label;
    private Double multiplier;
    private Integer unitId;
    private Integer planningUnitId;
    private Integer supplierId;
    private Double length;
    private Integer lengthUnitId;
    private Double height;
    private Double width;
    private Double weight;
    private Integer weightUnitId;
    private Double volume;
    private Integer volumeUnitId;
    private Integer unitsPerCase;
    private Integer unitsPerPalletEuro1;
    private Integer unitsPerPalletEuro2;
    private Integer unitsPerContainer;
    private String labelling;
    private String gtin;
    private String skuCode;
    private Double vendorPrice;
    private Double approvedToShippedLeadTime;
    private boolean found;

    public int getProcurementUnitId() {
        return procurementUnitId;
    }

    public void setProcurementUnitId(int procurementUnitId) {
        this.procurementUnitId = procurementUnitId;
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

    public Integer getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(Integer planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getLengthUnitId() {
        return lengthUnitId;
    }

    public void setLengthUnitId(Integer lengthUnitId) {
        this.lengthUnitId = lengthUnitId;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getWeightUnitId() {
        return weightUnitId;
    }

    public void setWeightUnitId(Integer weightUnitId) {
        this.weightUnitId = weightUnitId;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Integer getVolumeUnitId() {
        return volumeUnitId;
    }

    public void setVolumeUnitId(Integer volumeUnitId) {
        this.volumeUnitId = volumeUnitId;
    }

    public Integer getUnitsPerCase() {
        return unitsPerCase;
    }

    public void setUnitsPerCase(Integer unitsPerCase) {
        this.unitsPerCase = unitsPerCase;
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

    public String getLabelling() {
        return labelling;
    }

    public void setLabelling(String labelling) {
        this.labelling = labelling;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Double getVendorPrice() {
        return vendorPrice;
    }

    public void setVendorPrice(Double vendorPrice) {
        this.vendorPrice = vendorPrice;
    }

    public Double getApprovedToShippedLeadTime() {
        return approvedToShippedLeadTime;
    }

    public void setApprovedToShippedLeadTime(Double approvedToShippedLeadTime) {
        this.approvedToShippedLeadTime = approvedToShippedLeadTime;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

}
