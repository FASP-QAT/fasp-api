/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.SimpleObject;
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
public class ConsumptionInfo implements Serializable {

    @JsonView(Views.ReportView.class)
    private int consumptionId;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date consumptionDate;
    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonView(Views.ReportView.class)
    private SimpleObject dataSource;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private boolean actualFlag;

    public ConsumptionInfo() {
    }

    public ConsumptionInfo(int consumptionId, Date consumptionDate, SimpleCodeObject program, SimpleObject planningUnit, SimpleObject dataSource, SimpleObject region, String notes, boolean actualFlag) {
        this.consumptionId = consumptionId;
        this.consumptionDate = consumptionDate;
        this.program = program;
        this.planningUnit = planningUnit;
        this.dataSource = dataSource;
        this.region = region;
        this.notes = notes;
        this.actualFlag = actualFlag;
    }

    public int getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(int consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public SimpleObject getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleObject dataSource) {
        this.dataSource = dataSource;
    }

    public SimpleObject getRegion() {
        return region;
    }

    public void setRegion(SimpleObject region) {
        this.region = region;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isActualFlag() {
        return actualFlag;
    }

    public void setActualFlag(boolean actualFlag) {
        this.actualFlag = actualFlag;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.consumptionId;
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
        final ConsumptionInfo other = (ConsumptionInfo) obj;
        if (this.consumptionId != other.consumptionId) {
            return false;
        }
        return true;
    }

}
