/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class SimplePlanningUnitForSupplyPlanObject extends SimpleObject {

    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private SimpleObject forecastingUnit;
    @JsonView({Views.InternalView.class, Views.ArtmisView.class, Views.GfpVanView.class})
    private boolean active;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private double conversionFactor;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private List<SimpleProcurementAgentSkuObject> procurementAgentSkuList;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private int monthsInFutureForAmc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private int monthsInPastForAmc;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private int reorderFrequencyInMonths;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private int minMonthsOfStock;
    @JsonView({Views.ArtmisView.class, Views.GfpVanView.class})
    private SimpleObject productCategory;
    @JsonView({Views.InternalView.class})
    private String notes;

    public SimplePlanningUnitForSupplyPlanObject() {
        super();
        procurementAgentSkuList = new LinkedList<>();
    }

    public SimpleObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public List<SimpleProcurementAgentSkuObject> getProcurementAgentSkuList() {
        return procurementAgentSkuList;
    }

    public void setProcurementAgentSkuList(List<SimpleProcurementAgentSkuObject> procurementAgentSkuList) {
        this.procurementAgentSkuList = procurementAgentSkuList;
    }

    public int getMonthsInFutureForAmc() {
        return monthsInFutureForAmc;
    }

    public void setMonthsInFutureForAmc(int monthsInFutureForAmc) {
        this.monthsInFutureForAmc = monthsInFutureForAmc;
    }

    public int getMonthsInPastForAmc() {
        return monthsInPastForAmc;
    }

    public void setMonthsInPastForAmc(int monthsInPastForAmc) {
        this.monthsInPastForAmc = monthsInPastForAmc;
    }

    public int getReorderFrequencyInMonths() {
        return reorderFrequencyInMonths;
    }

    public void setReorderFrequencyInMonths(int reorderFrequencyInMonths) {
        this.reorderFrequencyInMonths = reorderFrequencyInMonths;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public SimpleObject getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(SimpleObject productCategory) {
        this.productCategory = productCategory;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.getId();
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
        final SimplePlanningUnitForSupplyPlanObject other = (SimplePlanningUnitForSupplyPlanObject) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }

}
