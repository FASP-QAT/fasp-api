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
    private Double errorPerc;
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

    public Double getErrorPerc() {
        return this.errorPerc;
    }

    public void calcErrorPerc() {
        Double diff = null;
        for (RegionForecastErrorOutput re : this.regionData) {

            if (this.actualQty != null) {
                this.actualQty += re.getActualQty();
            } else {
                this.actualQty = re.getActualQty();
            }
            if (this.forecastQty != null) {
                this.forecastQty += re.getForecastQty();
            } else {
                this.forecastQty = re.getForecastQty();
            }
            if (re.getActualQty() != null && re.getForecastQty() != null) {
                if (diff != null) {
                    diff += Math.abs(re.getActualQty() - re.getForecastQty());
                } else {
                    diff = Math.abs(re.getActualQty() - re.getForecastQty());
                }
            }
        }
        if (diff == null || this.actualQty == null || this.actualQty == 0.0) {
            this.errorPerc = null;
        } else {
            this.errorPerc = diff / this.actualQty;
        }
    }

    public void addRegionData(RegionForecastErrorOutput rfeo) {
        int idx = -1;
        idx = this.regionData.indexOf(rfeo);
        if (idx == -1) {
            this.regionData.add(rfeo);
        } else {
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
