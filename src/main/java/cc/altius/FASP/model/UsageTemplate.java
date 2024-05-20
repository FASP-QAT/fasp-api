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
public class UsageTemplate extends BaseModel implements Serializable {

    private int usageTemplateId;
    private Label label;
    private int realmId;
    private SimpleCodeObject program;
    private SimpleUnitObject forecastingUnit;
    private SimpleCodeObject unit;
    private SimpleObject tracerCategory;
    private int lagInMonths;
    private SimpleObject usageType;
    private int noOfPatients;
    private double noOfForecastingUnits;
    private boolean oneTimeUsage;
    private UsagePeriod usageFrequencyUsagePeriod;
    private Double usageFrequencyCount;
    private UsagePeriod repeatUsagePeriod;
    private Double repeatCount;
    private String notes;

    public UsageTemplate() {
    }

    public int getUsageTemplateId() {
        return usageTemplateId;
    }

    public void setUsageTemplateId(int usageTemplateId) {
        this.usageTemplateId = usageTemplateId;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getRealmId() {
        return realmId;
    }

    public void setRealmId(int realmId) {
        this.realmId = realmId;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleUnitObject getForecastingUnit() {
        return forecastingUnit;
    }

    public void setForecastingUnit(SimpleUnitObject forecastingUnit) {
        this.forecastingUnit = forecastingUnit;
    }

    public SimpleCodeObject getUnit() {
        return unit;
    }

    public void setUnit(SimpleCodeObject unit) {
        this.unit = unit;
    }

    public SimpleObject getTracerCategory() {
        return tracerCategory;
    }

    public void setTracerCategory(SimpleObject tracerCategory) {
        this.tracerCategory = tracerCategory;
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

    public int getNoOfPatients() {
        return noOfPatients;
    }

    public void setNoOfPatients(int noOfPatients) {
        this.noOfPatients = noOfPatients;
    }

    public double getNoOfForecastingUnits() {
        return noOfForecastingUnits;
    }

    public void setNoOfForecastingUnits(double noOfForecastingUnits) {
        this.noOfForecastingUnits = noOfForecastingUnits;
    }

    public boolean isOneTimeUsage() {
        return oneTimeUsage;
    }

    public void setOneTimeUsage(boolean oneTimeUsage) {
        this.oneTimeUsage = oneTimeUsage;
    }

    public UsagePeriod getUsageFrequencyUsagePeriod() {
        return usageFrequencyUsagePeriod;
    }

    public void setUsageFrequencyUsagePeriod(UsagePeriod usageFrequencyUsagePeriod) {
        this.usageFrequencyUsagePeriod = usageFrequencyUsagePeriod;
    }

    public Double getUsageFrequencyCount() {
        return usageFrequencyCount;
    }

    public void setUsageFrequencyCount(Double usageFrequencyCount) {
        this.usageFrequencyCount = usageFrequencyCount;
    }

    public UsagePeriod getRepeatUsagePeriod() {
        return repeatUsagePeriod;
    }

    public void setRepeatUsagePeriod(UsagePeriod repeatUsagePeriod) {
        this.repeatUsagePeriod = repeatUsagePeriod;
    }

    public Double getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Double repeatCount) {
        this.repeatCount = repeatCount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Label getUsageInWords() {
        StringBuilder sb = new StringBuilder();
        if (this.usageType.getId() == 2) { // Continuous
            sb.append("Every 1 Patient - requires ").append(this.noOfForecastingUnits).append(" ").append(this.unit.getCode()).append(" ").append(this.usageFrequencyCount).append(" time(s) per ").append(this.usageFrequencyUsagePeriod.getLabel().getLabel_en()).append(" indefinitely");
        } else if (this.oneTimeUsage == true) {
            sb.append("Every ").append(this.noOfPatients).append(" Patient - requires ").append(this.noOfForecastingUnits).append(" ").append(this.unit.getCode());
        } else {
            sb.append("Every ").append(this.noOfPatients).append(" Patient - requires ").append(this.noOfForecastingUnits).append(" ").append(this.unit.getCode()).append(" ").append(this.usageFrequencyCount).append(" time(s) per ").append(this.usageFrequencyUsagePeriod.getLabel().getLabel_en()).append(" for ").append(this.repeatCount).append(" ").append(this.repeatUsagePeriod.getLabel().getLabel_en());
        }
        return new Label(0, sb.toString(), null, null, null);
    }

    public double getNoOfFUPerPersonPerTime() {
        return (double) this.noOfForecastingUnits / (double) this.noOfPatients;
    }

    public double getNoOfFUPerPersonPerMonth() {
        if (this.oneTimeUsage) {
            return getNoOfFUPerPersonPerTime();
        } else {
            return getNoOfFUPerPersonPerTime() * this.usageFrequencyUsagePeriod.getConvertToMonth() * this.usageFrequencyCount;
        }
    }

    public Double getNoOfFURequired() {
        if (this.usageType.getId() == 2) { // Continuous
            return null;
        } else if (this.oneTimeUsage == false) {
            return this.repeatCount / this.repeatUsagePeriod.getConvertToMonth() * getNoOfFUPerPersonPerMonth();
        } else {
            return getNoOfFUPerPersonPerTime();
        }
    }
}
