/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProcurementUnit extends BaseModel implements Serializable {

    private int procurementUnitId;
    private PlanningUnit planningUnit;
    private Label label;
    private Unit unit;
    private Double multiplier;
    private Supplier supplier;
    private Double widthQty;
    private Unit widthUnit;
    private Double heightQty;
    private Unit heightUnit;
    private Double lengthQty;
    private Unit lengthUnit;
    private Double weightQty;
    private Unit weightUnit;
    private Double unitsPerContainer;
    private String labeling;

    public ProcurementUnit() {
    }

    public ProcurementUnit(int procurementUnitId, Label label) {
        this.procurementUnitId = procurementUnitId;
        this.label = label;
    }

    public ProcurementUnit(int procurementUnitId, PlanningUnit planningUnit, Label label, Unit unit, double multiplier) {
        this.procurementUnitId = procurementUnitId;
        this.planningUnit = planningUnit;
        this.label = label;
        this.unit = unit;
        this.multiplier = multiplier;
    }

    public int getProcurementUnitId() {
        return procurementUnitId;
    }

    public void setProcurementUnitId(int procurementUnitId) {
        this.procurementUnitId = procurementUnitId;
    }

    public PlanningUnit getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(PlanningUnit planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Double getWidthQty() {
        return widthQty;
    }

    public void setWidthQty(Double widthQty) {
        this.widthQty = widthQty;
    }

    public Unit getWidthUnit() {
        return widthUnit;
    }

    public void setWidthUnit(Unit widthUnit) {
        this.widthUnit = widthUnit;
    }

    public Double getHeightQty() {
        return heightQty;
    }

    public void setHeightQty(Double heightQty) {
        this.heightQty = heightQty;
    }

    public Unit getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(Unit heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Double getLengthQty() {
        return lengthQty;
    }

    public void setLengthQty(Double lengthQty) {
        this.lengthQty = lengthQty;
    }

    public Unit getLengthUnit() {
        return lengthUnit;
    }

    public void setLengthUnit(Unit lengthUnit) {
        this.lengthUnit = lengthUnit;
    }

    public Double getWeightQty() {
        return weightQty;
    }

    public void setWeightQty(Double weightQty) {
        this.weightQty = weightQty;
    }

    public Unit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Unit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Double getUnitsPerContainer() {
        return unitsPerContainer;
    }

    public void setUnitsPerContainer(Double unitsPerContainer) {
        this.unitsPerContainer = unitsPerContainer;
    }

    public String getLabeling() {
        return labeling;
    }

    public void setLabeling(String labeling) {
        this.labeling = labeling;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.procurementUnitId;
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
        final ProcurementUnit other = (ProcurementUnit) obj;
        if (this.procurementUnitId != other.procurementUnitId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProcurementUnit{" + "procurementUnitId=" + procurementUnitId + ", planningUnit=" + planningUnit + ", label=" + label + ", unit=" + unit + ", multiplier=" + multiplier + ", supplier=" + supplier + ", widthQty=" + widthQty + '}';
    }

}
