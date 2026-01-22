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
public class CountryOrProgramConsumptionData implements Serializable {

    public CountryOrProgramConsumptionData(SimpleCodeObject label, double forecastedConsumption, double actualConsumption) {
        this.label = label;
        this.forecastedConsumption = forecastedConsumption;
        this.actualConsumption = actualConsumption;
    }
    
    @JsonView(Views.ReportView.class)
    SimpleCodeObject label;
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

    public SimpleCodeObject getLabel() {
        return label;
    }

    public void setLabel(SimpleCodeObject label) {
        this.label = label;
    }

}
