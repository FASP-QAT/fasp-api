/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author altius
 */
public class PrgProductDTO implements Serializable {

    private int productId;
    private PrgLabelDTO genericLabel;
    private PrgLabelDTO label;
    private PrgUnitDTO forecastUnit;
    private PrgProductCategoryDTO productCategory;
    private List<PrgInventoryDTO> inventoryData;
    private List<PrgConsumptionDTO> consumptionData;
    private List<PrgShipmentDTO> shipmentData;
    private boolean active;
    private int realmId;

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public PrgProductCategoryDTO getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(PrgProductCategoryDTO productCategory) {
        this.productCategory = productCategory;
    }

    public PrgUnitDTO getForecastUnit() {
        return forecastUnit;
    }

    public void setForecastUnit(PrgUnitDTO forecastUnit) {
        this.forecastUnit = forecastUnit;
    }

    public List<PrgInventoryDTO> getInventoryData() {
        return inventoryData;
    }

    public void setInventoryData(List<PrgInventoryDTO> inventoryData) {
        this.inventoryData = inventoryData;
    }

    public List<PrgConsumptionDTO> getConsumptionData() {
        return consumptionData;
    }

    public void setConsumptionData(List<PrgConsumptionDTO> consumptionData) {
        this.consumptionData = consumptionData;
    }

    public List<PrgShipmentDTO> getShipmentData() {
        return shipmentData;
    }

    public void setShipmentData(List<PrgShipmentDTO> shipmentData) {
        this.shipmentData = shipmentData;
    }

    public PrgLabelDTO getGenericLabel() {
        return genericLabel;
    }

    public void setGenericLabel(PrgLabelDTO genericLabel) {
        this.genericLabel = genericLabel;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

}
