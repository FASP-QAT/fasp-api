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
public class EquivalencyUnitMapping extends BaseModel implements Serializable {

    private int equivalencyUnitMappingId;
    private EquivalencyUnit equivalencyUnit;
    private SimpleObject forecastingUnit;
    private SimpleCodeObject unit;
    private SimpleObject tracerCategory;
    private double convertToEu;
    private String notes;
    private SimpleCodeObject program;

    public int getEquivalencyUnitMappingId() {
        return equivalencyUnitMappingId;
    }

    public void setEquivalencyUnitMappingId(int equivalencyUnitMappingId) {
        this.equivalencyUnitMappingId = equivalencyUnitMappingId;
    }

    public EquivalencyUnit getEquivalencyUnit() {
        return equivalencyUnit;
    }

    public void setEquivalencyUnit(EquivalencyUnit equivalencyUnit) {
        this.equivalencyUnit = equivalencyUnit;
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public double getConvertToEu() {
        return convertToEu;
    }

    public void setConvertToEu(double convertToEu) {
        this.convertToEu = convertToEu;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

}
