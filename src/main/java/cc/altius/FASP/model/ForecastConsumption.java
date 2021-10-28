/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.framework.JsonDateTimeDeserializer;
import cc.altius.FASP.framework.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author akil
 */
public class ForecastConsumption implements Serializable {

    private int forecastConsumptionId;
    private SimpleCodeObject program;
    private ForecastConsumptionUnit consumptionUnit;
    private SimpleObject region;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date month;
    private Double actualConsumption;
    private Double reportingRate;
    private Integer daysOfStockOut;
    private boolean exclude;
    private int versionId;
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;

    public int getForecastConsumptionId() {
        return forecastConsumptionId;
    }

    public void setForecastConsumptionId(int forecastConsumptionId) {
        this.forecastConsumptionId = forecastConsumptionId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public ForecastConsumptionUnit getConsumptionUnit() {
        return consumptionUnit;
    }

    public void setConsumptionUnit(ForecastConsumptionUnit consumptionUnit) {
        this.consumptionUnit = consumptionUnit;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Double getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Double actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Double getReportingRate() {
        return reportingRate;
    }

    public void setReportingRate(Double reportingRate) {
        this.reportingRate = reportingRate;
    }

    public Integer getDaysOfStockOut() {
        return daysOfStockOut;
    }

    public void setDaysOfStockOut(Integer daysOfStockOut) {
        this.daysOfStockOut = daysOfStockOut;
    }

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public BasicUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(BasicUser createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
