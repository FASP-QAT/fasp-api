/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author akil
 */
public class ArimaInputDTO implements Serializable {

    private double p;
    private double d;
    private double q;
    private int frequency;
    private boolean seasonality;
    private int start;
    private List<Double> data;
    private double level;
    private int n;
    private boolean optimize;

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = Math.round(p * 100.0) / 100d;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = Math.round(d * 100.0) / 100d;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = Math.round(q * 100.0) / 100d;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public boolean isSeasonality() {
        return seasonality;
    }

    public void setSeasonality(boolean seasonality) {
        this.seasonality = seasonality;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public boolean isOptimize() {
        return optimize;
    }

    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

    @Override
    public String toString() {
        return "ArimaInputDTO{" + "p=" + p + ", d=" + d + ", q=" + q + ", frequency=" + frequency + ", seasonality=" + seasonality + ", start=" + start + ", data=" + data + ", level=" + level + ", n=" + n + ", optimize=" + optimize + '}';
    }

}
