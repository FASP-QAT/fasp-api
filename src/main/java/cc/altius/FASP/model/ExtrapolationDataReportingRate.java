/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class ExtrapolationDataReportingRate extends ExtrapolationData implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double reportingRate;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double manualChange;

    public ExtrapolationDataReportingRate() {
    }

    public ExtrapolationDataReportingRate(Date month) {
        super(month);
    }

    public Double getReportingRate() {
        return reportingRate;
    }

    public void setReportingRate(Double reportingRate) {
        this.reportingRate = reportingRate;
    }

    public Double getManualChange() {
        return manualChange;
    }

    public void setManualChange(Double manualChange) {
        this.manualChange = manualChange;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.getMonth());
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
        final ExtrapolationDataReportingRate other = (ExtrapolationDataReportingRate) obj;
        if (!Objects.equals(this.getMonth(), other.getMonth())) {
            return false;
        }
        return true;
    }

}
