/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author akil
 */
public class DashboardTopData {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private boolean stockOut;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private double valueOfExpiredStock;

    public boolean isStockOut() {
        return stockOut;
    }

    public void setStockOut(boolean stockOut) {
        this.stockOut = stockOut;
    }

    public double getValueOfExpiredStock() {
        return valueOfExpiredStock;
    }

    public void setValueOfExpiredStock(double valueOfExpiredStock) {
        this.valueOfExpiredStock = valueOfExpiredStock;
    }

}
