/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusMatrixOutput implements Serializable {

    private SimpleObject planningUnit;
    private SimpleCodeObject unit;
    private int minMonthsOfStock;
    private int reorderFrequency;
    private int multiplier;
    private int year;
    private Double jan;
    private Double feb;
    private Double mar;
    private Double apr;
    private Double may;
    private Double jun;
    private Double jul;
    private Double aug;
    private Double sep;
    private Double oct;
    private Double nov;
    private Double dec;

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
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

    
}
