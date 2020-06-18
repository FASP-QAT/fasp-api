/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class StockStatusMatrixOutput implements Serializable {

    private SimpleObject name;
    private SimpleObject unit;
    private int min;
    private int year;
    private double janMos;
    private double febMos;
    private double marMos;
    private double aprMos;
    private double mayMos;
    private double junMos;
    private double julMos;
    private double augMos;
    private double sepMos;
    private double octMos;
    private double novMos;
    private double decMos;

    public SimpleObject getName() {
        return name;
    }

    public void setName(SimpleObject name) {
        this.name = name;
    }

    public SimpleObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleObject unit) {
        this.unit = unit;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getJanMos() {
        return janMos;
    }

    public void setJanMos(double janMos) {
        this.janMos = janMos;
    }

    public double getFebMos() {
        return febMos;
    }

    public void setFebMos(double febMos) {
        this.febMos = febMos;
    }

    public double getMarMos() {
        return marMos;
    }

    public void setMarMos(double marMos) {
        this.marMos = marMos;
    }

    public double getAprMos() {
        return aprMos;
    }

    public void setAprMos(double aprMos) {
        this.aprMos = aprMos;
    }

    public double getMayMos() {
        return mayMos;
    }

    public void setMayMos(double mayMos) {
        this.mayMos = mayMos;
    }

    public double getJunMos() {
        return junMos;
    }

    public void setJunMos(double junMos) {
        this.junMos = junMos;
    }

    public double getJulMos() {
        return julMos;
    }

    public void setJulMos(double julMos) {
        this.julMos = julMos;
    }

    public double getAugMos() {
        return augMos;
    }

    public void setAugMos(double augMos) {
        this.augMos = augMos;
    }

    public double getSepMos() {
        return sepMos;
    }

    public void setSepMos(double sepMos) {
        this.sepMos = sepMos;
    }

    public double getOctMos() {
        return octMos;
    }

    public void setOctMos(double octMos) {
        this.octMos = octMos;
    }

    public double getNovMos() {
        return novMos;
    }

    public void setNovMos(double novMos) {
        this.novMos = novMos;
    }

    public double getDecMos() {
        return decMos;
    }

    public void setDecMos(double decMos) {
        this.decMos = decMos;
    }

}
