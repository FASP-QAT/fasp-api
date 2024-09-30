/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class DashboardStockStatus implements Serializable {

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int stockOut;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int underStock;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int adequate;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int overStock;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int na;
    private int total;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private List<DashboardPuWithCount> puStockOutList;

    public DashboardStockStatus() {
        this.puStockOutList = new LinkedList<>();
    }

    public DashboardStockStatus(int stockOut,  int underStock, int adequate, int overStock, int na, int total) {
        this.overStock = overStock;
        this.adequate = adequate;
        this.underStock = underStock;
        this.stockOut = stockOut;
        this.na = na;
        this.total = total;
        this.puStockOutList = new LinkedList<>();
    }

    public int getOverStock() {
        return overStock;
    }

    public void setOverStock(int overStock) {
        this.overStock = overStock;
    }

    public int getAdequate() {
        return adequate;
    }

    public void setAdequate(int adequate) {
        this.adequate = adequate;
    }

    public int getUnderStock() {
        return underStock;
    }

    public void setUnderStock(int underStock) {
        this.underStock = underStock;
    }

    public int getStockOut() {
        return stockOut;
    }

    public void setStockOut(int stockOut) {
        this.stockOut = stockOut;
    }

    public int getNa() {
        return na;
    }

    public void setNa(int na) {
        this.na = na;
    }

    @JsonView(Views.ReportView.class)
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    public double getOverStockPerc() {
        return (double)this.overStock / (double)this.total;
    }

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    public double getAdequatePerc() {
        return (double)this.adequate / (double)this.total;
    }

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    public double getUnderStockPerc() {
        return (double)this.underStock / (double)this.total;
    }

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    public double getStockOutPerc() {
        return (double)this.stockOut / (double)this.total;
    }

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    public double getNaPerc() {
        return (double)this.na / (double)this.total;
    }

    public List<DashboardPuWithCount> getPuStockOutList() {
        return puStockOutList;
    }

    public void setPuStockOutList(List<DashboardPuWithCount> puStockOutList) {
        this.puStockOutList = puStockOutList;
    }

}
