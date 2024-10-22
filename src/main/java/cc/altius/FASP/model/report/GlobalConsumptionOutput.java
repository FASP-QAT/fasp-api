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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class GlobalConsumptionOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    Date transDate;
    @JsonView(Views.ReportView.class)
    Map<String, CountryConsumptionData> countryConsumption;

    public GlobalConsumptionOutput() {
        countryConsumption = new HashMap<>();
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Map<String, CountryConsumptionData> getCountryConsumption() {
        return countryConsumption;
    }

    public void setCountryConsumption(Map<String, CountryConsumptionData> countryConsumption) {
        this.countryConsumption = countryConsumption;
    }

    public double getTotalForecastedConsumption() {
        return this.countryConsumption.values().stream().map(x -> x.getForecastedConsumption()).reduce((double)0.0, Double::sum);
    }

    public double getTotalActualConsumption() {
        return this.countryConsumption.values().stream().map(x -> x.getActualConsumption()).reduce((double)0.0, Double::sum);
    }

    public double getTotalConsumption() {
        return this.getTotalForecastedConsumption() + this.getTotalActualConsumption();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.transDate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GlobalConsumptionOutput other = (GlobalConsumptionOutput) obj;
        if (!Objects.equals(this.transDate, other.transDate)) {
            return false;
        }
        return true;
    }

}
