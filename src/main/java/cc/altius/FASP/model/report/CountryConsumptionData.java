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
public class CountryConsumptionData implements Serializable {

    public CountryConsumptionData(SimpleCodeObject country, double forecastedConsumption, double actualConsumption) {
        this.country = country;
        this.forecastedConsumption = forecastedConsumption;
        this.actualConsumption = actualConsumption;
    }

    @JsonView(Views.ReportView.class)
    SimpleCodeObject country;
    @JsonView(Views.ReportView.class)
    double forecastedConsumption;
    @JsonView(Views.ReportView.class)
    double actualConsumption;

    public double getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(double forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }

}
