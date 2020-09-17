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
    private Integer heightUnitId;
    private Double width;
    private Integer widthUnitId;
    private Double weight;
    private Integer weightUnitId;
    private Integer unitsPerCase;
    private Integer unitsPerPallet;
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

    public Integer getHeightUnitId() {
        return heightUnitId;
    }

    public void setHeightUnitId(Integer heightUnitId) {
        this.heightUnitId = heightUnitId;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Integer getWidthUnitId() {
        return widthUnitId;
    }

    public void setWidthUnitId(Integer widthUnitId) {
        this.widthUnitId = widthUnitId;
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

    public Integer getUnitsPerCase() {
        return unitsPerCase;
    }

    public void setUnitsPerCase(Integer unitsPerCase) {
        this.unitsPerCase = unitsPerCase;
    }

    public Integer getUnitsPerPallet() {
        return unitsPerPallet;
    }

    public void setUnitsPerPallet(Integer unitsPerPallet) {
        this.unitsPerPallet = unitsPerPallet;
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
