/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DashboardShipmentDetailsReportBy implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject reportBy;
    @JsonView(Views.ReportView.class)
    private int orderCount;
    @JsonView(Views.ReportView.class)
    private long quantity;
    @JsonView(Views.ReportView.class)
    private double cost;

    public SimpleCodeObject getReportBy() {
        return reportBy;
    }

    public void setReportBy(SimpleCodeObject reportBy) {
        this.reportBy = reportBy;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}
