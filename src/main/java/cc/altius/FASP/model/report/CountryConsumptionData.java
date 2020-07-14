/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class CountryConsumptionData implements Serializable {

    public CountryConsumptionData(SimpleCodeObject country, int forecastedConsumption, int actualConsumption) {
        this.country = country;
        this.forecastedConsumption = forecastedConsumption;
        this.actualConsumption = actualConsumption;
    }

    SimpleCodeObject country;
    int forecastedConsumption;
    int actualConsumption;

    public int getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(int forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

    public int getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(int actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public SimpleCodeObject getCountry() {
        return country;
    }

    public void setCountry(SimpleCodeObject country) {
        this.country = country;
    }
    
}
