/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.DTO;

/**
 *
 * @author altius
 */
public class PrgLogisticsUnitDTO {

    private int logisticsUnitId;
    private PrgPlanningUnitDTO planningUnit;
    private PrgLabelDTO label;
    private PrgUnitDTO unit;
    private double qtyOfPlanningUnits;
    private String variant;
    private PrgManufacturerDTO manufacturer;
    private PrgUnitDTO widthUnit;
    private double widthQty;
    private PrgUnitDTO heightUnit;
    private double heightQty;
    private PrgUnitDTO lengthUnit;
    private double lengthQty;
    private PrgUnitDTO weightUnit;
    private double weightQty;
    private double qtyInEuro1;
    private double qtyInEuro2;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PrgPlanningUnitDTO getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(PrgPlanningUnitDTO planningUnit) {
        this.planningUnit = planningUnit;
    }

    public PrgUnitDTO getUnit() {
        return unit;
    }

    public void setUnit(PrgUnitDTO unit) {
        this.unit = unit;
    }

    public double getQtyOfPlanningUnits() {
        return qtyOfPlanningUnits;
    }

    public void setQtyOfPlanningUnits(double qtyOfPlanningUnits) {
        this.qtyOfPlanningUnits = qtyOfPlanningUnits;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public PrgManufacturerDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(PrgManufacturerDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    public PrgUnitDTO getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(PrgUnitDTO widthUnit) {
        this.widthUnit = widthUnit;
    }

    public double getWidthQty() {
        return widthQty;
    }

    public void setWidthQty(double widthQty) {
        this.widthQty = widthQty;
    }

    public PrgUnitDTO getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(PrgUnitDTO heightUnit) {
        this.heightUnit = heightUnit;
    }

    public double getHeightQty() {
        return heightQty;
    }

    public void setHeightQty(double heightQty) {
        this.heightQty = heightQty;
    }

    public PrgUnitDTO getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(PrgUnitDTO lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public double getLengthQty() {
        return lengthQty;
    }

    public void setLengthQty(double lengthQty) {
        this.lengthQty = lengthQty;
    }

    public PrgUnitDTO getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(PrgUnitDTO weightUnit) {
        this.weightUnit = weightUnit;
    }

    public double getWeightQty() {
        return weightQty;
    }

    public void setWeightQty(double weightQty) {
        this.weightQty = weightQty;
    }

    public double getQtyInEuro1() {
        return qtyInEuro1;
    }

    public void setQtyInEuro1(double qtyInEuro1) {
        this.qtyInEuro1 = qtyInEuro1;
    }

    public double getQtyInEuro2() {
        return qtyInEuro2;
    }

    public void setQtyInEuro2(double qtyInEuro2) {
        this.qtyInEuro2 = qtyInEuro2;
    }

    public int getLogisticsUnitId() {
        return logisticsUnitId;
    }

    public void setLogisticsUnitId(int logisticsUnitId) {
        this.logisticsUnitId = logisticsUnitId;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

}
