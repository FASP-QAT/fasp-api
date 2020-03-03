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
public class PrgPlanningUnitDTO {

    private int planningUnitId;
    private PrgLabelDTO label;
    private PrgUnitDTO unit;
    private double qtyOfForecastingUnits;
    private double price;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PrgLabelDTO getLabel() {
        return label;
    }

    public void setLabel(PrgLabelDTO label) {
        this.label = label;
    }

    public int getPlanningUnitId() {
        return planningUnitId;
    }

    public void setPlanningUnitId(int planningUnitId) {
        this.planningUnitId = planningUnitId;
    }

    public PrgUnitDTO getUnit() {
        return unit;
    }

    public void setUnit(PrgUnitDTO unit) {
        this.unit = unit;
    }

    public double getQtyOfForecastingUnits() {
        return qtyOfForecastingUnits;
    }

    public void setQtyOfForecastingUnits(double qtyOfForecastingUnits) {
        this.qtyOfForecastingUnits = qtyOfForecastingUnits;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
