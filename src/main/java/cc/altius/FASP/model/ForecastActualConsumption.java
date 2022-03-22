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
public class ForecastActualConsumption implements Serializable {

    private int actualConsumptionId;
    private SimplePlanningUnitProductCategoryObject planningUnit;
    private SimpleObject region;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date month;
    private Double amount;
    private Double reportingRate;
    private Integer daysOfStockOut;
    private boolean exclude;
    private BasicUser createdBy;
    @JsonDeserialize(using = JsonDateTimeDeserializer.class)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;

    public int getActualConsumptionId() {
        return actualConsumptionId;
    }

    public void setActualConsumptionId(int actualConsumptionId) {
        this.actualConsumptionId = actualConsumptionId;
    }

    public SimplePlanningUnitProductCategoryObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimplePlanningUnitProductCategoryObject planningUnit) {
        this.planningUnit = planningUnit;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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
