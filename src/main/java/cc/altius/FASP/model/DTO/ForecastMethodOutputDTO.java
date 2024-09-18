/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ForecastMethodOutputDTO implements Serializable {

    private double var1; // for Arima p, for TES alpha
    private double var2; // for Arima d, for TES beta
    private double var3; // for Arima q, for TES gamma
    private List<String> fits;
    private List<Double> forecast;
    private List<Double> ci;

    public double getVar1() {
        return var1;
    }

    public void setVar1(double var1) {
        this.var1 = var1;
    }

    public double getVar2() {
        return var2;
    }

    public void setVar2(double var2) {
        this.var2 = var2;
    }

    public double getVar3() {
        return var3;
    }

    public void setVar3(double var3) {
        this.var3 = var3;
    }

    public List<String> getFits() {
        return fits;
    }

    public void setFits(List<String> fits) {
        this.fits = null;
        this.fits = new LinkedList<>();
        fits.forEach(f -> {
            if (f.equals("NA")) {
                this.fits.add(null);
            } else {
                this.fits.add(f);
            }
        });
    }

    public List<Double> getForecast() {
        return forecast;
    }

    public void setForecast(List<Double> forecast) {
        this.forecast = forecast;
    }

    public List<Double> getCi() {
        return ci;
    }

    public void setCi(List<Double> ci) {
        this.ci = ci;
    }

    public Double getRMSE(List<Double> data) {
            double tmp = 0;
            int cnt = 0;
            for (int x = 0; x < data.size(); x++) {
                if (!fits.get(x).equals("NA")) {
                    tmp += Math.pow(Double.parseDouble(fits.get(x)) - data.get(x), 2);
                    cnt++;
                }
            }
            return Math.pow(tmp / cnt, 0.5);
    }

    @Override
    public String toString() {
        return "ForecastMethodOutputDTO{" + "var1=" + var1 + ", var2=" + var2 + ", var3=" + var3 + ", fits=" + fits + ", forecast=" + forecast + ", ci=" + ci + '}';
    }

}
