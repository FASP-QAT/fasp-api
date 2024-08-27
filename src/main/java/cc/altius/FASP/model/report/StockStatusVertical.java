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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author akil
 */
public class StockStatusVertical implements Serializable {

    @JsonDeserialize(using = JsonDateDeserializer.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    @JsonView(Views.ReportView.class)
    private Date dt;

    @JsonView(Views.ReportView.class)
    private Long openingBalance;
    @JsonView(Views.ReportView.class)
    private Boolean actualConsumption;
    @JsonView(Views.ReportView.class)
    private Long actualConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Long forecastedConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Long finalConsumptionQty;
    @JsonView(Views.ReportView.class)
    private Long shipmentQty;
    @JsonView(Views.ReportView.class)
    private Long adjustment;
    @JsonView(Views.ReportView.class)
    private Long expiredStock;
    @JsonView(Views.ReportView.class)
    private Long closingBalance;
    @JsonView(Views.ReportView.class)
    private Double amc;
    @JsonView(Views.ReportView.class)
    private Double mos;
    @JsonView(Views.ReportView.class)
    private Double minMos;
    @JsonView(Views.ReportView.class)
    private Double maxMos;
    @JsonView(Views.ReportView.class)
    private Long unmetDemand;
    @JsonView(Views.ReportView.class)
    private int regionCount;
    @JsonView(Views.ReportView.class)
    private int regionCountForStock;
    @JsonView(Views.ReportView.class)
    private Long nationalAdjustment;
    @JsonView(Views.ReportView.class)
    private Double minStock;
    @JsonView(Views.ReportView.class)
    private Double maxStock;
    @JsonView(Views.ReportView.class)
    private List<ShipmentInfo> shipmentInfo;

    public StockStatusVertical() {
        this.shipmentInfo = new LinkedList<>();
    }

    public StockStatusVertical(Date dt) {
        this.dt = dt;
        this.shipmentInfo = new LinkedList<>();
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Long getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Long openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Boolean getActualConsumption() {
        return actualConsumption;
    }

    public void setActualConsumption(Boolean actualConsumption) {
        this.actualConsumption = actualConsumption;
    }

    public Long getActualConsumptionQty() {
        return actualConsumptionQty;
    }

    public void setActualConsumptionQty(Long actualConsumptionQty) {
        this.actualConsumptionQty = actualConsumptionQty;
    }

    public Long getForecastedConsumptionQty() {
        return forecastedConsumptionQty;
    }

    public void setForecastedConsumptionQty(Long forecastedConsumptionQty) {
        this.forecastedConsumptionQty = forecastedConsumptionQty;
    }

    public Long getFinalConsumptionQty() {
        return finalConsumptionQty;
    }

    public void setFinalConsumptionQty(Long finalConsumptionQty) {
        this.finalConsumptionQty = finalConsumptionQty;
    }

    public Long getShipmentQty() {
        return shipmentQty;
    }

    public void setShipmentQty(Long shipmentQty) {
        this.shipmentQty = shipmentQty;
    }

    public Long getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Long adjustment) {
        this.adjustment = adjustment;
    }

    public Long getExpiredStock() {
        return expiredStock;
    }

    public void setExpiredStock(Long expiredStock) {
        this.expiredStock = expiredStock;
    }

    public Long getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Long closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Double getAmc() {
        return amc;
    }

    public void setAmc(Double amc) {
        this.amc = amc;
    }

    public Double getMos() {
        return mos;
    }

    public void setMos(Double mos) {
        this.mos = mos;
    }

    public Double getMinMos() {
        return minMos;
    }

    public void setMinMos(Double minMos) {
        this.minMos = minMos;
    }

    public Double getMaxMos() {
        return maxMos;
    }

    public void setMaxMos(Double maxMos) {
        this.maxMos = maxMos;
    }

    public Long getUnmetDemand() {
        return unmetDemand;
    }

    public void setUnmetDemand(Long unmetDemand) {
        this.unmetDemand = unmetDemand;
    }

    public int getRegionCount() {
        return regionCount;
    }

    public void setRegionCount(int regionCount) {
        this.regionCount = regionCount;
    }

    public int getRegionCountForStock() {
        return regionCountForStock;
    }

    public void setRegionCountForStock(int regionCountForStock) {
        this.regionCountForStock = regionCountForStock;
    }

    public Long getNationalAdjustment() {
        return nationalAdjustment;
    }

    public void setNationalAdjustment(Long nationalAdjustment) {
        this.nationalAdjustment = nationalAdjustment;
    }

    public Double getMinStock() {
        return minStock;
    }

    public void setMinStock(Double minStock) {
        this.minStock = minStock;
    }

    public Double getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Double maxStock) {
        this.maxStock = maxStock;
    }
    
    public List<ShipmentInfo> getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(List<ShipmentInfo> shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    @Override
    public String toString() {
        return "StockStatusVerticalOutput{" + "dt=" + dt + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 91 * hash + Objects.hashCode(this.dt);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        final StockStatusVertical other = (StockStatusVertical) obj;
        if (!Objects.equals(sdf.format(this.dt), sdf.format(other.dt))) {
            return false;
        }
        return true;
    }

}
