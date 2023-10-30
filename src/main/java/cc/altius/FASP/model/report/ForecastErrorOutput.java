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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ForecastErrorOutput implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date month;
    @JsonView(Views.ReportView.class)
    private Double actualQty;
    @JsonView(Views.ReportView.class)
    private Double forecastQty;
    @JsonView(Views.ReportView.class)
    private Double sumOfForecast;
    @JsonView(Views.ReportView.class)
    private Double sumOfActual;
    private Double sumOfAbsDiff;
    @JsonView(Views.ReportView.class)
    private List<RegionForecastErrorOutput> regionData;

    public ForecastErrorOutput() {
        this.regionData = new LinkedList<>();
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
    }

    public Double getForecastQty() {
        return forecastQty;
    }

    public void setForecastQty(Double forecastQty) {
        this.forecastQty = forecastQty;
    }

    public Double getSumOfForecast() {
        return sumOfForecast;
    }

    public void setSumOfForecast(Double sumOfForecast) {
        this.sumOfForecast = sumOfForecast;
    }

    public Double getSumOfActual() {
        return sumOfActual;
    }

    public void setSumOfActual(Double sumOfActual) {
        this.sumOfActual = sumOfActual;
    }

    public Double getSumOfAbsDiff() {
        return sumOfAbsDiff;
    }

    public void setSumOfAbsDiff(Double sumOfAbsDiff) {
        this.sumOfAbsDiff = sumOfAbsDiff;
    }

    @JsonView(Views.ReportView.class)
    public Double getErrorPerc() {
        if (this.sumOfActual == null || this.sumOfActual == 0 || this.sumOfAbsDiff == null) {
            return null;
        } else {
            return this.sumOfAbsDiff / this.sumOfActual;
        }
    }

    public void addRegionData(RegionForecastErrorOutput rfeo) {
        if (this.regionData.indexOf(rfeo) == -1) {
            this.regionData.add(rfeo);
//            if (rfeo.getActualQty() != null) {
//                this.actualQty = (this.actualQty == null ? 0 : this.actualQty) + (rfeo.getActualQty() == null ? 0 : rfeo.getActualQty());
//            }
//            if (rfeo.getForecastQty() != null) {
//                this.forecastQty = (this.forecastQty == null ? 0 : this.forecastQty) + (rfeo.getForecastQty() == null ? 0 : rfeo.getForecastQty());
//            }
//            if (rfeo.getSumOfActual() != null) {
//                this.sumOfActual = (this.sumOfActual == null ? 0 : this.sumOfActual) + (rfeo.getSumOfActual() == null ? 0 : rfeo.getSumOfActual());
//            }
//            if (rfeo.getSumOfForecast() != null) {
//                this.sumOfForecast = (this.sumOfForecast == null ? 0 : this.sumOfForecast) + (rfeo.getSumOfForecast() == null ? 0 : rfeo.getSumOfForecast());
//            }
        }
    }

    public List<RegionForecastErrorOutput> getRegionData() {
        return regionData;
    }

    public void setRegionData(List<RegionForecastErrorOutput> regionData) {
        this.regionData = regionData;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.month);
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
        final ForecastErrorOutput other = (ForecastErrorOutput) obj;
        if (!Objects.equals(this.month, other.month)) {
            return false;
        }
        return true;
    }

}
