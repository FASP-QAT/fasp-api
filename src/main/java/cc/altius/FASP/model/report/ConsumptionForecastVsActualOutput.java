/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView({Views.ReportView.class})
    private Date transDate;
    @JsonView({Views.ReportView.class})
    private Long actualConsumption;
    @JsonView({Views.ReportView.class})
    private Long forecastedConsumption;

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Long getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Long actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Long getForecastedConsumption() {
        return forecastedConsumption;
    }

    public void setForecastedConsumption(Long forecastedConsumption) {
        this.forecastedConsumption = forecastedConsumption;
    }

}
