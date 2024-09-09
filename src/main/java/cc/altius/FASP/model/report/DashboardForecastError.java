/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class DashboardForecastError extends DashboardPuWithCount implements Serializable {

    @JsonView(Views.ReportView.class)
    private double errorPerc;

    public DashboardForecastError() {
        super();
    }

    public DashboardForecastError(SimpleObject planningUnit, int count, double errorPerc) {
        super(planningUnit, count);
        this.errorPerc = errorPerc;
    }

    public double getErrorPerc() {
        return errorPerc;
    }

    public void setErrorPerc(double errorPerc) {
        this.errorPerc = errorPerc;
    }

}
