/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.DTO;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ForecastMethodOptimizationDTO implements Serializable {

    private double var1; // for Arima p, for TES alpha
    private double var2; // for Arima d, for TES beta
    private double var3; // for Arima q, for TES gamma
    private Double error;

    public ForecastMethodOptimizationDTO(double var1, double var2, double var3, Double error) {
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.error = error;
    }

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

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.var1) ^ (Double.doubleToLongBits(this.var1) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.var2) ^ (Double.doubleToLongBits(this.var2) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.var3) ^ (Double.doubleToLongBits(this.var3) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.error);
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
        final ForecastMethodOptimizationDTO other = (ForecastMethodOptimizationDTO) obj;
        if (Double.doubleToLongBits(this.var1) != Double.doubleToLongBits(other.var1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.var2) != Double.doubleToLongBits(other.var2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.var3) != Double.doubleToLongBits(other.var3)) {
            return false;
        }
        return Objects.equals(this.error, other.error);
    }

    @Override
    public String toString() {
        return "ForecastMethodOptimizationDTO{" + "var1=" + var1 + ", var2=" + var2 + ", var3=" + var3 + ", error=" + error + '}';
    }

}
