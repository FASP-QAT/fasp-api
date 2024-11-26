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

    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private SimpleCodeObject reportBy;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private int orderCount;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private double quantity;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private double cost;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private String colorHtmlCode;
    @JsonView({Views.InternalView.class, Views.ReportView.class})
    private String colorHtmlDarkCode;

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

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getColorHtmlCode() {
        return colorHtmlCode;
    }

    public void setColorHtmlCode(String colorHtmlCode) {
        this.colorHtmlCode = colorHtmlCode;
    }

    public String getColorHtmlDarkCode() {
        return colorHtmlDarkCode;
    }

    public void setColorHtmlDarkCode(String colorHtmlDarkCode) {
        this.colorHtmlDarkCode = colorHtmlDarkCode;
    }

}
