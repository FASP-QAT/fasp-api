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
    private Double errorPerc;
    private Double forecastErrorThreshold;
    @JsonView(Views.ReportView.class)
    private boolean aboveForecastThreshold;

    public DashboardForecastError() {
        super();
    }

    public DashboardForecastError(SimpleObject planningUnit, int count, Double errorPerc, Double forecastErrorThreshold) {
        super(planningUnit, count);
        this.errorPerc = errorPerc;
        this.forecastErrorThreshold = forecastErrorThreshold;
    }

    public Double getErrorPerc() {
        return errorPerc;
    }

    public void setErrorPerc(Double errorPerc) {
        this.errorPerc = errorPerc;
    }

    public Double getForecastErrorThreshold() {
        return forecastErrorThreshold;
    }

    public void setForecastErrorThreshold(Double forecastErrorThreshold) {
        this.forecastErrorThreshold = forecastErrorThreshold;
    }

    public boolean isAboveForecastThreshold() {
        return this.errorPerc!=null && this.forecastErrorThreshold!=null && this.errorPerc * 100 > this.forecastErrorThreshold;
    }

}
