/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusMatrixOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private int tracerCategoryId;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject unit;
    @JsonView(Views.ReportView.class)
    private int minMonthsOfStock;
    @JsonView(Views.ReportView.class)
    private int reorderFrequency;
    @JsonView(Views.ReportView.class)
    private int multiplier;
    @JsonView(Views.ReportView.class)
    private int year;
    @JsonView(Views.ReportView.class)
    private Double jan;
    @JsonView(Views.ReportView.class)
    private Double feb;
    @JsonView(Views.ReportView.class)
    private Double mar;
    @JsonView(Views.ReportView.class)
    private Double apr;
    @JsonView(Views.ReportView.class)
    private Double may;
    @JsonView(Views.ReportView.class)
    private Double jun;
    @JsonView(Views.ReportView.class)
    private Double jul;
    @JsonView(Views.ReportView.class)
    private Double aug;
    @JsonView(Views.ReportView.class)
    private Double sep;
    @JsonView(Views.ReportView.class)
    private Double oct;
    @JsonView(Views.ReportView.class)
    private Double nov;
    @JsonView(Views.ReportView.class)
    private Double dec;
    @JsonView(Views.ReportView.class)
    private long janStock;
    @JsonView(Views.ReportView.class)
    private long febStock;
    @JsonView(Views.ReportView.class)
    private long marStock;
    @JsonView(Views.ReportView.class)
    private long aprStock;
    @JsonView(Views.ReportView.class)
    private long mayStock;
    @JsonView(Views.ReportView.class)
    private long junStock;
    @JsonView(Views.ReportView.class)
    private long julStock;
    @JsonView(Views.ReportView.class)
    private long augStock;
    @JsonView(Views.ReportView.class)
    private long sepStock;
    @JsonView(Views.ReportView.class)
    private long octStock;
    @JsonView(Views.ReportView.class)
    private long novStock;
    @JsonView(Views.ReportView.class)
    private long decStock;
    @JsonView(Views.ReportView.class)
    private int planBasedOn;
    @JsonView(Views.ReportView.class)
    private Double maxStock;

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public int getTracerCategoryId() {
        return tracerCategoryId;
    }

    public void setTracerCategoryId(int tracerCategoryId) {
        this.tracerCategoryId = tracerCategoryId;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public int getMinMonthsOfStock() {
        return minMonthsOfStock;
    }

    public void setMinMonthsOfStock(int minMonthsOfStock) {
        this.minMonthsOfStock = minMonthsOfStock;
    }

    public int getReorderFrequency() {
        return reorderFrequency;
    }

    public void setReorderFrequency(int reorderFrequency) {
        this.reorderFrequency = reorderFrequency;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getJan() {
        return jan;
    }

    public void setJan(Double jan) {
        this.jan = jan;
    }

    public Double getFeb() {
        return feb;
    }

    public void setFeb(Double feb) {
        this.feb = feb;
    }

    public Double getMar() {
        return mar;
    }

    public void setMar(Double mar) {
        this.mar = mar;
    }

    public Double getApr() {
        return apr;
    }

    public void setApr(Double apr) {
        this.apr = apr;
    }

    public Double getMay() {
        return may;
    }

    public void setMay(Double may) {
        this.may = may;
    }

    public Double getJun() {
        return jun;
    }

    public void setJun(Double jun) {
        this.jun = jun;
    }

    public Double getJul() {
        return jul;
    }

    public void setJul(Double jul) {
        this.jul = jul;
    }

    public Double getAug() {
        return aug;
    }

    public void setAug(Double aug) {
        this.aug = aug;
    }

    public Double getSep() {
        return sep;
    }

    public void setSep(Double sep) {
        this.sep = sep;
    }

    public Double getOct() {
        return oct;
    }

    public void setOct(Double oct) {
        this.oct = oct;
    }

    public Double getNov() {
        return nov;
    }

    public void setNov(Double nov) {
        this.nov = nov;
    }

    public Double getDec() {
        return dec;
    }

    public void setDec(Double dec) {
        this.dec = dec;
    }

    public int getPlanBasedOn() {
        return planBasedOn;
    }

    public void setPlanBasedOn(int planBasedOn) {
        this.planBasedOn = planBasedOn;
    }

    public long getJanStock() {
        return janStock;
    }

    public void setJanStock(long janStock) {
        this.janStock = janStock;
    }

    public long getFebStock() {
        return febStock;
    }

    public void setFebStock(long febStock) {
        this.febStock = febStock;
    }

    public long getMarStock() {
        return marStock;
    }

    public void setMarStock(long marStock) {
        this.marStock = marStock;
    }

    public long getAprStock() {
        return aprStock;
    }

    public void setAprStock(long aprStock) {
        this.aprStock = aprStock;
    }

    public long getMayStock() {
        return mayStock;
    }

    public void setMayStock(long mayStock) {
        this.mayStock = mayStock;
    }

    public long getJunStock() {
        return junStock;
    }

    public void setJunStock(long junStock) {
        this.junStock = junStock;
    }

    public long getJulStock() {
        return julStock;
    }

    public void setJulStock(long julStock) {
        this.julStock = julStock;
    }

    public long getAugStock() {
        return augStock;
    }

    public void setAugStock(long augStock) {
        this.augStock = augStock;
    }

    public long getSepStock() {
        return sepStock;
    }

    public void setSepStock(long sepStock) {
        this.sepStock = sepStock;
    }

    public long getOctStock() {
        return octStock;
    }

    public void setOctStock(long octStock) {
        this.octStock = octStock;
    }

    public long getNovStock() {
        return novStock;
    }

    public void setNovStock(long novStock) {
        this.novStock = novStock;
    }

    public long getDecStock() {
        return decStock;
    }

    public void setDecStock(long decStock) {
        this.decStock = decStock;
    }

    public Double getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Double maxStock) {
        this.maxStock = maxStock;
    }

}
