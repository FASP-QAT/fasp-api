/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DashboardStockOutAndExpired implements Serializable {

    private int countOfStockOutPU;
    private double valueOfExpiredPU;

    public DashboardStockOutAndExpired() {
    }

    public DashboardStockOutAndExpired(int countOfStockOutPU, double valueOfExpiredPU) {
        this.countOfStockOutPU = countOfStockOutPU;
        this.valueOfExpiredPU = valueOfExpiredPU;
    }

    public int getCountOfStockOutPU() {
        return countOfStockOutPU;
    }

    public void setCountOfStockOutPU(int countOfStockOutPU) {
        this.countOfStockOutPU = countOfStockOutPU;
    }

    public double getValueOfExpiredPU() {
        return valueOfExpiredPU;
    }

    public void setValueOfExpiredPU(double valueOfExpiredPU) {
        this.valueOfExpiredPU = valueOfExpiredPU;
    }

}
