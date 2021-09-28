/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import java.io.Serializable;

/**
 *
 * @author akil
 */
public class NodeDataFu extends BaseModel implements Serializable {

    private int nodeDataFuId;
    private SimpleUnitAndTracerObject forecastingUnit;
    private int lagInMonths;
    private int usageTypeId;
    private int noOfPatients;
    private int noOfForecastingUnits;
    private double noOfUnits;
    private UsagePeriod usagePeriod;
    private Double repeatCount;
    private UsagePeriod repeatUsagePeriod;

    public int getNodeDataFuId() {
        return nodeDataFuId;
    }

    public void setNodeDataFuId(int nodeDataFuId) {
        this.nodeDataFuId = nodeDataFuId;
    }

    public SimpleUnitAndTracerObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleUnitAndTracerObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public int getLagInMonths() {
        return lagInMonths;
    }

    public void setLagInMonths(int lagInMonths) {
        this.lagInMonths = lagInMonths;
    }

    public int getUsageTypeId() {
        return usageTypeId;
    }

    public void setUsageTypeId(int usageTypeId) {
        this.usageTypeId = usageTypeId;
    }

    public int getNoOfPatients() {
        return noOfPatients;
    }

    public void setNoOfPatients(int noOfPatients) {
        this.noOfPatients = noOfPatients;
    }

    public int getNoOfForecastingUnits() {
        return noOfForecastingUnits;
    }

    public void setNoOfForecastingUnits(int noOfForecastingUnits) {
        this.noOfForecastingUnits = noOfForecastingUnits;
    }

    public double getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(double noOfUnits) {
        this.noOfUnits = noOfUnits;
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

}
