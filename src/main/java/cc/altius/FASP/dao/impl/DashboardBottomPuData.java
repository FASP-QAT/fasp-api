/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cc.altius.FASP.dao.impl;

import cc.altius.FASP.model.Views;
import cc.altius.FASP.model.report.DashboardActualConsumptionDetails;
import cc.altius.FASP.model.report.DashboardExpiredPu;
import cc.altius.FASP.model.report.DashboardShipmentDetailsReportBy;
import cc.altius.FASP.model.report.DashboardStockStatus;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author akil
 */
public class DashboardBottomPuData {

    @JsonView(Views.ReportView.class)
    private DashboardStockStatus stockStatus;
    @JsonView(Views.ReportView.class)
    private List<DashboardExpiredPu> expiriesList;
    @JsonView(Views.ReportView.class)
    private List<DashboardShipmentDetailsReportBy> shipmentDetailsByFundingSource;
    @JsonView(Views.ReportView.class)
    private List<DashboardShipmentDetailsReportBy> shipmentDetailsByProcurementAgent;
    @JsonView(Views.ReportView.class)
    private List<DashboardShipmentDetailsReportBy> shipmentDetailsByShipmentStatus;
    @JsonView(Views.ReportView.class)
    private int countOfTbdFundingSource;
    @JsonView(Views.ReportView.class)
    private Double forecastError;
    @JsonView(Views.ReportView.class)
    private boolean forecastConsumptionQplPassed;
    private List<DashboardActualConsumptionDetails> actualConsumptionDetails;
    @JsonView(Views.ReportView.class)
    private boolean inventoryQplPassed;
    @JsonView(Views.ReportView.class)
    private boolean shipmentQplPassed;

    public DashboardBottomPuData() {
        this.expiriesList = new LinkedList<>();
        this.shipmentDetailsByFundingSource = new LinkedList<>();
        this.shipmentDetailsByProcurementAgent = new LinkedList<>();
        this.shipmentDetailsByShipmentStatus = new LinkedList<>();
        this.actualConsumptionDetails = new LinkedList<>();
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

    public List<DashboardShipmentDetailsReportBy> getShipmentDetailsByFundingSource() {
        return shipmentDetailsByFundingSource;
    }

    public void setShipmentDetailsByFundingSource(List<DashboardShipmentDetailsReportBy> shipmentDetailsByFundingSource) {
        this.shipmentDetailsByFundingSource = shipmentDetailsByFundingSource;
    }

    public List<DashboardShipmentDetailsReportBy> getShipmentDetailsByProcurementAgent() {
        return shipmentDetailsByProcurementAgent;
    }

    public void setShipmentDetailsByProcurementAgent(List<DashboardShipmentDetailsReportBy> shipmentDetailsByProcurementAgent) {
        this.shipmentDetailsByProcurementAgent = shipmentDetailsByProcurementAgent;
    }

    public List<DashboardShipmentDetailsReportBy> getShipmentDetailsByShipmentStatus() {
        return shipmentDetailsByShipmentStatus;
    }

    public void setShipmentDetailsByShipmentStatus(List<DashboardShipmentDetailsReportBy> shipmentDetailsByShipmentStatus) {
        this.shipmentDetailsByShipmentStatus = shipmentDetailsByShipmentStatus;
    }

    public int getCountOfTbdFundingSource() {
        return countOfTbdFundingSource;
    }

    public void setCountOfTbdFundingSource(int countOfTbdFundingSource) {
        this.countOfTbdFundingSource = countOfTbdFundingSource;
    }

    public void incrementCountOfTbdFundingSource() {
        this.countOfTbdFundingSource++;
    }

    public Double getForecastError() {
        return forecastError;
    }

    public void setForecastError(Double forecastError) {
        this.forecastError = forecastError;
    }

    public boolean isForecastConsumptionQplPassed() {
        return forecastConsumptionQplPassed;
    }

    public void setForecastConsumptionQplPassed(boolean forecastConsumptionQplPassed) {
        this.forecastConsumptionQplPassed = forecastConsumptionQplPassed;
    }

    @JsonView(Views.ReportView.class)
    public boolean isActualConsumptionQplPassed() {
        boolean check1 = false, check2 = false;
        // Step 1 check for 3 months of ActualConsumption
        int actualCount = this.actualConsumptionDetails.stream().limit(4).map(DashboardActualConsumptionDetails::getActualCount).collect(Collectors.summingInt(Integer::intValue));
        if (actualCount > 1) {
            // Step 2 check for gaps in last 6 months 
            int flipCount = 0;
            Boolean prevMonth = null;
            for (DashboardActualConsumptionDetails dacd : this.actualConsumptionDetails) {
                if (prevMonth == null) {
                    if (dacd.getActualCount() == 1) {
                        // exists
                        prevMonth = true;
                    } else {
                        // does not exist
                        prevMonth = false;
                    }
                } else {
                    if (dacd.getActualCount() == 1) {
                        // exists
                        if (prevMonth == false) {
                            prevMonth = true;
                            flipCount++;
                        }
                    } else {
                        // does not exist
                        if (prevMonth == true) {
                            prevMonth = false;
                        }
                    }
                }
            }
            return (flipCount <= 1);
        } else {
            return false;
        }
    }

    public boolean isInventoryQplPassed() {
        return inventoryQplPassed;
    }

    public void setInventoryQplPassed(boolean inventoryQplPassed) {
        this.inventoryQplPassed = inventoryQplPassed;
    }

    public boolean isShipmentQplPassed() {
        return shipmentQplPassed;
    }

    public void setShipmentQplPassed(boolean shipmentQplPassed) {
        this.shipmentQplPassed = shipmentQplPassed;
    }

    public List<DashboardActualConsumptionDetails> getActualConsumptionDetails() {
        return actualConsumptionDetails;
    }

    public void setActualConsumptionDetails(List<DashboardActualConsumptionDetails> actualConsumptionDetails) {
        this.actualConsumptionDetails = actualConsumptionDetails;
    }

}
