/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class ProgramProductCatalogOutput implements Serializable {

    private SimpleCodeObject program;
    private SimpleObject tracerCategory;
    private SimpleObject productCategory;
    private SimpleObject forecastingUnit;
    private SimpleCodeObject fUnit;
    private Label genericName;
    private SimpleObject planningUnit;
    private SimpleCodeObject pUnit;
    private int forecastingtoPlanningUnitMultiplier;
    private int minMonthsOfStock;
    private int reorderFrequencyInMonths;
    private double catalogPrice;
    private int shelfLife;
    private boolean active;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleCodeObject getfUnit() {
        return fUnit;
    }

    public void setfUnit(SimpleCodeObject fUnit) {
        this.fUnit = fUnit;
    }

    public Label getGenericName() {
        return genericName;
    }

    public void setGenericName(Label genericName) {
        this.genericName = genericName;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleCodeObject getpUnit() {
        return pUnit;
    }

    public void setpUnit(SimpleCodeObject pUnit) {
        this.pUnit = pUnit;
    }

    public int getForecastingtoPlanningUnitMultiplier() {
        return forecastingtoPlanningUnitMultiplier;
    }

    public void setForecastingtoPlanningUnitMultiplier(int forecastingtoPlanningUnitMultiplier) {
        this.forecastingtoPlanningUnitMultiplier = forecastingtoPlanningUnitMultiplier;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public double getCatalogPrice() {
        return catalogPrice;
    }

    public void setCatalogPrice(double catalogPrice) {
        this.catalogPrice = catalogPrice;
    }

    public int getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(int shelfLife) {
        this.shelfLife = shelfLife;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
