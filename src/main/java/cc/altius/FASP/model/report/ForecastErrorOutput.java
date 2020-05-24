/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ForecastErrorOutput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date consumptionDate;
    private double forecastedConsumption;
    private double actualConsumption;
    private double forecastError;
    private int monthsInCalc;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MMM-yy");

    public ForecastErrorOutput() {
    }

    public ForecastErrorOutput(Date consumptionDate, double forecastedConsumption, double actualConsumption, double forecastError, int monthsInCalc) {
        this.consumptionDate = consumptionDate;
        this.forecastedConsumption = forecastedConsumption;
        this.actualConsumption = actualConsumption;
        this.forecastError = forecastError;
        this.monthsInCalc = monthsInCalc;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

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

    public double getForecastError() {
        return forecastError;
    }

    public void setForecastError(double forecastError) {
        this.forecastError = forecastError;
    }

    public int getMonthsInCalc() {
        return monthsInCalc;
    }

    public void setMonthsInCalc(int monthsInCalc) {
        this.monthsInCalc = monthsInCalc;
    }

    public String getConsumptionDateString() {
        return sdf.format(this.consumptionDate);
    }
}
