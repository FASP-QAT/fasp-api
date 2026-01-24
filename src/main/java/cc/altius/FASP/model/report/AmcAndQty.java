/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class AmcAndQty implements Serializable {

    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private double closingBalance;
    @JsonView(Views.ReportView.class)
    private boolean actualStock;
    @JsonView(Views.ReportView.class)
    private double shipmentQty;
    @JsonView(Views.ReportView.class)
    private double expiredQty;
    @JsonView(Views.ReportView.class)
    private double amc;
    @JsonView(Views.ReportView.class)
    private int stockStatusId;  // -1 = NA, 0 = Stock out, 1 = Below Min, 2 = Stocked to plan, 3 = Above Max

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public boolean isActualStock() {
        return actualStock;
    }

    public void setActualStock(boolean actualStock) {
        this.actualStock = actualStock;
    }

    public double getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(double shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public double getExpiredQty() {
        return expiredQty;
    }

    public void setExpiredQty(double expiredQty) {
        this.expiredQty = expiredQty;
    }

    public double getAmc() {
        return amc;
    }

    public void setAmc(double amc) {
        this.amc = amc;
    }

    public int getStockStatusId() {
        return stockStatusId;
    }

    public void setStockStatusId(int stockStatusId) {
        this.stockStatusId = stockStatusId;
    }

    public AmcAndQty(Double mos, Double closingBalance, boolean actualStock, double shipmentQty, double expiredQty, double amc, int stockStatusId) {
        this.mos = mos;
        this.closingBalance = closingBalance;
        this.actualStock = actualStock;
        this.shipmentQty = shipmentQty;
        this.expiredQty = expiredQty;
        this.amc = amc;
        this.stockStatusId = stockStatusId;
    }

    public static final int getStockStatusId(int planBasedOn, int minMos, int reorderQtyinMonths, int minStockQty, int maxStockQty, Double mos, int stockQty) {
        if (planBasedOn == 1) {
            // MOS
            if (mos == null) {
                return -1; // NA
            } else if (mos == 0) {
                return 0; // Stock out
            } else if (mos < minMos) {
                return 1; // Below min
            } else if (mos <= minMos + reorderQtyinMonths) {
                return 2; // Adequately stocked
            } else {
                return 3; // Above max
            }
        } else {
            if (stockQty == 0) {
                return 0; // Stock out
            } else if (stockQty < minStockQty) {
                return 1; // Below min
            } else if (stockQty <= maxStockQty) {
                return 2; // Adequately stocked
            } else {
                return 3; // Above max
            }
        }
    }
}
