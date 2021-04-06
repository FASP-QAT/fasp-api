/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

/**
 *
 * @author altius
 */
public class InventoryInfo {

    @JsonView(Views.ReportView.class)
    private String notes;
    @JsonView(Views.ReportView.class)
    private String inventoryDate;
    @JsonView(Views.ReportView.class)
    private int inventoryId;
    @JsonView(Views.ReportView.class)
    private SimpleObject dataSource;
    @JsonView(Views.ReportView.class)
    private SimpleObject region;
    @JsonView(Views.ReportView.class)
    private Double adjustmentQty;
    @JsonView(Views.ReportView.class)
    private Double actualQty;

    public InventoryInfo() {
    }

    public InventoryInfo(String notes, String inventoryDate, int inventoryId, SimpleObject dataSource, SimpleObject region, Double adjustmentQty, Double actualQty) {
        this.notes = notes;
        this.inventoryDate = inventoryDate;
        this.inventoryId = inventoryId;
        this.dataSource = dataSource;
        this.region = region;
        this.adjustmentQty = adjustmentQty;
        this.actualQty = actualQty;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(String inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.inventoryId;
        return hash;
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

    public Double getAdjustmentQty() {
        return adjustmentQty;
    }

    public void setAdjustmentQty(Double adjustmentQty) {
        this.adjustmentQty = adjustmentQty;
    }

    public Double getActualQty() {
        return actualQty;
    }

    public void setActualQty(Double actualQty) {
        this.actualQty = actualQty;
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
        final InventoryInfo other = (InventoryInfo) obj;
        if (this.inventoryId != other.inventoryId) {
            return false;
        }
        return true;
    }

}
