/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.model.report;

import cc.altius.FASP.model.SimpleCodeObject;
import cc.altius.FASP.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class DashboardBottom implements Serializable {

    @JsonView(Views.ReportView.class)
    private SimpleCodeObject program;
    @JsonView(Views.ReportView.class)
    private DashboardStockStatus stockStatus;
    @JsonView(Views.ReportView.class)
    List<DashboardExpiredPu> expiriesList;
    @JsonView(Views.ReportView.class)
    List<DashboardShipmentDetailsReportBy> shipmentDetailsList;
    @JsonView(Views.ReportView.class)
    List<DashboardPuWithCount> shipmentWithFundingSourceTbd;
    @JsonView(Views.ReportView.class)
    List<DashboardForecastError> forecastErrorList;
    @JsonView(Views.ReportView.class)
    DashboardQpl forecastConsumptionQpl;
    @JsonView(Views.ReportView.class)
    DashboardQpl actualConsumptionQpl;
    @JsonView(Views.ReportView.class)
    DashboardQpl inventoryQpl;
    @JsonView(Views.ReportView.class)
    DashboardQpl shipmentQpl;

    public SimpleCodeObject getProgram() {
        return program;
    }

    public void setProgram(SimpleCodeObject program) {
        this.program = program;
    }

    public DashboardStockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(DashboardStockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    public List<DashboardExpiredPu> getExpiriesList() {
        return expiriesList;
    }

    public void setExpiriesList(List<DashboardExpiredPu> expiriesList) {
        this.expiriesList = expiriesList;
    }

    @JsonView(Views.ReportView.class)
    public double getExpiryTotal() {
        return this.expiriesList.stream().map(e -> e.getExpiryAmt()).collect(Collectors.summingDouble(Double::doubleValue));
    }

    public List<DashboardShipmentDetailsReportBy> getShipmentDetailsList() {
        return shipmentDetailsList;
    }

    public void setShipmentDetailsList(List<DashboardShipmentDetailsReportBy> shipmentDetailsList) {
        this.shipmentDetailsList = shipmentDetailsList;
    }

    @JsonView(Views.ReportView.class)
    public double getShipmentTotal() {
        return this.shipmentDetailsList.stream().map(s -> s.getCost()).collect(Collectors.summingDouble(Double::doubleValue));
    }

    public List<DashboardPuWithCount> getShipmentWithFundingSourceTbd() {
        return shipmentWithFundingSourceTbd;
    }

    public void setShipmentWithFundingSourceTbd(List<DashboardPuWithCount> shipmentWithFundingSourceTbd) {
        this.shipmentWithFundingSourceTbd = shipmentWithFundingSourceTbd;
    }

    public List<DashboardForecastError> getForecastErrorList() {
        return forecastErrorList;
    }

    public void setForecastErrorList(List<DashboardForecastError> forecastErrorList) {
        this.forecastErrorList = forecastErrorList;
    }

    public DashboardQpl getForecastConsumptionQpl() {
        return forecastConsumptionQpl;
    }

    public void setForecastConsumptionQpl(DashboardQpl forecastConsumptionQpl) {
        this.forecastConsumptionQpl = forecastConsumptionQpl;
    }

    public DashboardQpl getActualConsumptionQpl() {
        return actualConsumptionQpl;
    }

    public void setActualConsumptionQpl(DashboardQpl actualConsumptionQpl) {
        this.actualConsumptionQpl = actualConsumptionQpl;
    }

    public DashboardQpl getInventoryQpl() {
        return inventoryQpl;
    }

    public void setInventoryQpl(DashboardQpl inventoryQpl) {
        this.inventoryQpl = inventoryQpl;
    }

    public DashboardQpl getShipmentQpl() {
        return shipmentQpl;
    }

    public void setShipmentQpl(DashboardQpl shipmentQpl) {
        this.shipmentQpl = shipmentQpl;
    }

}
