/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimpleObjectWithMultiplier;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ForecastSummaryOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObjectWithMultiplier planningUnit;
    @JsonView(Views.ReportView.class)
    private SimpleObject tracerCategory;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private SimpleObject selectedForecast;
    @JsonView(Views.ReportView.class)
    private Double totalForecast;
    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private Integer stock;
    @JsonView(Views.ReportView.class)
    private Integer existingShipments;
    @JsonView(Views.ReportView.class)
    private Integer monthsOfStock;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject procurementAgent;
    @JsonView(Views.ReportView.class)
    private Double price;

    public SimpleObjectWithMultiplier getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObjectWithMultiplier planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public SimpleObject getSelectedForecast() {
        return selectedForecast;
    }

    public void setSelectedForecast(SimpleObject selectedForecast) {
        this.selectedForecast = selectedForecast;
    }

    public Double getTotalForecast() {
        return totalForecast;
    }

    public void setTotalForecast(Double totalForecast) {
        this.totalForecast = totalForecast;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getExistingShipments() {
        return existingShipments;
    }

    public void setExistingShipments(Integer existingShipments) {
        this.existingShipments = existingShipments;
    }

    public Integer getMonthsOfStock() {
        return monthsOfStock;
    }

    public void setMonthsOfStock(Integer monthsOfStock) {
        this.monthsOfStock = monthsOfStock;
    }

    public SimpleCodeObject getProcurementAgent() {
        return procurementAgent;
    }

    public void setProcurementAgent(SimpleCodeObject procurementAgent) {
        this.procurementAgent = procurementAgent;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.planningUnit);
        hash = 67 * hash + Objects.hashCode(this.region);
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
        final ForecastSummaryOutput other = (ForecastSummaryOutput) obj;
        if (!Objects.equals(this.planningUnit, other.planningUnit)) {
            return false;
        }
        if (!Objects.equals(this.region, other.region)) {
            return false;
        }
        return true;
    }

}
