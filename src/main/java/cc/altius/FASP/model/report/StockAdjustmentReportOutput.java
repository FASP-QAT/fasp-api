/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.framework.JsonDateDeserializer;
import cc.altius.FASP.framework.JsonDateSerializer;
import cc.altius.FASP.model.BasicUser;
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
public class StockAdjustmentReportOutput implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleObject program;
    @JsonView(Views.ReportView.class)
    private SimpleObject planningUnit;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date inventoryDate;
    @JsonView(Views.ReportView.class)
    private double stockAdjustemntQty;
    @JsonView(Views.ReportView.class)
    private SimpleObject dataSource;
    @JsonView(Views.ReportView.class)
    private BasicUser lastModifiedBy;
    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date lastModifiedDate;
    @JsonView(Views.ReportView.class)
    private String notes;

    public SimpleObject getProgram() {
        return program;
    }

    public void setProgram(SimpleObject program) {
        this.program = program;
    }

    public SimpleObject getPlanningUnit() {
        return planningUnit;
    }

    public void setPlanningUnit(SimpleObject planningUnit) {
        this.planningUnit = planningUnit;
    }

    public Date getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(Date inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public double getStockAdjustemntQty() {
        return stockAdjustemntQty;
    }

    public void setStockAdjustemntQty(double stockAdjustemntQty) {
        this.stockAdjustemntQty = stockAdjustemntQty;
    }

    public BasicUser getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(BasicUser lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public SimpleObject getDataSource() {
        return dataSource;
    }

    public void setDataSource(SimpleObject dataSource) {
        this.dataSource = dataSource;
    }

}
