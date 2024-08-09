/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;

/**
 *
 * @author akil
 */
public class TreeNodeDataFu implements Serializable {

    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int nodeDataFuId;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleForecastingUnitObject forecastingUnit;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int lagInMonths;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private SimpleObject usageType;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private int noOfPersons;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private double noOfForecastingUnitsPerPerson;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private boolean oneTimeUsage;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double usageFrequency;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private UsagePeriod usagePeriod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Double repeatCount;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private UsagePeriod repeatUsagePeriod;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private Boolean oneTimeDispensing;

    public int getNodeDataFuId() {
        return nodeDataFuId;
    }

    public void setNodeDataFuId(int nodeDataFuId) {
        this.nodeDataFuId = nodeDataFuId;
    }

    public SimpleForecastingUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleForecastingUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public int getLagInMonths() {
        return lagInMonths;
    }

    public void setLagInMonths(int lagInMonths) {
        this.lagInMonths = lagInMonths;
    }

    public SimpleObject getUsageType() {
        return usageType;
    }

    public void setUsageType(SimpleObject usageType) {
        this.usageType = usageType;
    }

    public int getNoOfPersons() {
        return noOfPersons;
    }

    public void setNoOfPersons(int noOfPersons) {
        this.noOfPersons = noOfPersons;
    }

    public double getNoOfForecastingUnitsPerPerson() {
        return noOfForecastingUnitsPerPerson;
    }

    public void setNoOfForecastingUnitsPerPerson(double noOfForecastingUnitsPerPerson) {
        this.noOfForecastingUnitsPerPerson = noOfForecastingUnitsPerPerson;
    }

    public boolean isOneTimeUsage() {
        return oneTimeUsage;
    }

    public void setOneTimeUsage(boolean oneTimeUsage) {
        this.oneTimeUsage = oneTimeUsage;
    }

    public Double getUsageFrequency() {
        return usageFrequency;
    }

    public void setUsageFrequency(Double usageFrequency) {
        this.usageFrequency = usageFrequency;
    }

    public UsagePeriod getUsagePeriod() {
        return usagePeriod;
    }

    public void setUsagePeriod(UsagePeriod usagePeriod) {
        this.usagePeriod = usagePeriod;
    }

    public Double getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Double repeatCount) {
        this.repeatCount = repeatCount;
    }

    public UsagePeriod getRepeatUsagePeriod() {
        return repeatUsagePeriod;
    }

    public void setRepeatUsagePeriod(UsagePeriod repeatUsagePeriod) {
        this.repeatUsagePeriod = repeatUsagePeriod;
    }

    public Boolean getOneTimeDispensing() {
        return oneTimeDispensing;
    }

    public void setOneTimeDispensing(Boolean oneTimeDispensing) {
        this.oneTimeDispensing = oneTimeDispensing;
    }

    @Override
    public String toString() {
        return "TreeNodeDataFu{" + "nodeDataFuId=" + nodeDataFuId + '}';
    }

}
