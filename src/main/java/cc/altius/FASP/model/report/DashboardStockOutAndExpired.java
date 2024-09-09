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
    private int countOfExpiredPU;

    public DashboardStockOutAndExpired() {
    }

    public DashboardStockOutAndExpired(int countOfStockOutPU, int countOfExpiredPU) {
        this.countOfStockOutPU = countOfStockOutPU;
        this.countOfExpiredPU = countOfExpiredPU;
    }

    public int getCountOfStockOutPU() {
        return countOfStockOutPU;
    }

    public void setCountOfStockOutPU(int countOfStockOutPU) {
        this.countOfStockOutPU = countOfStockOutPU;
    }

    public int getCountOfExpiredPU() {
        return countOfExpiredPU;
    }

    public void setCountOfExpiredPU(int countOfExpiredPU) {
        this.countOfExpiredPU = countOfExpiredPU;
    }

}
