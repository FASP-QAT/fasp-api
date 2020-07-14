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
import java.util.Date;

/**
 *
 * @author akil
 */
public class ConsumptionForecastVsActualOutput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date transDate;
    private int actualConsumption;
    private int forecastedConsumption;

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public int getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(int actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public int getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(int forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

}
