/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.report.AnnualShipmentCostInput;
import cc.altius.FASP.model.report.ConsumptionForecastVsActualInput;
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastMetricsInput;
import cc.altius.FASP.model.report.FundingSourceShipmentReportInput;
import cc.altius.FASP.model.report.ProcurementAgentShipmentReportInput;
import cc.altius.FASP.model.report.ProgramLeadTimesInput;
import cc.altius.FASP.model.report.ProgramProductCatalogInput;
import cc.altius.FASP.model.report.ShipmentDetailsInput;
import cc.altius.FASP.model.report.ShipmentGlobalDemandInput;
import cc.altius.FASP.model.report.ShipmentOverviewInput;
import cc.altius.FASP.model.report.StockAdjustmentReportInput;
import cc.altius.FASP.model.report.StockStatusOverTimeInput;
import cc.altius.FASP.model.report.StockStatusForProgramInput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
import cc.altius.FASP.model.report.StockStatusVerticalInput;
import cc.altius.FASP.model.report.WarehouseCapacityInput;
import cc.altius.FASP.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ekta
 */
@RestController
@RequestMapping("/api/report/")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/consumption/{realmId}/{programId}/{planningUnitId}/{startDate}/{endDate}")
    public ResponseEntity getConsumptionData(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getConsumptionData(realmId, programId, planningUnitId, startDate, endDate), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 18
    @RequestMapping(value = "/stockStatusMatrix")
    public ResponseEntity getStockStatusMatrix(@RequestBody StockStatusMatrixInput ssm, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusMatrix(ssm), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/forecastError")
    public ResponseEntity getForecastError(@RequestBody ForecastErrorInput fei, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getForecastError(fei, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/forecastMetrics")
    public ResponseEntity getForecastError(@RequestBody ForecastMetricsInput fmi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getForecastMetrics(fmi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Report no 3
    @RequestMapping(value = "/globalConsumption")
    public ResponseEntity getGlobalConsumption(@RequestBody GlobalConsumptionInput gci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getGlobalConsumption(gci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/stockStatusOverTime")
    public ResponseEntity getStockStatusOverTime(@RequestBody StockStatusOverTimeInput ssot, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusOverTime(ssot, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/annualShipmentCost")
    public ResponseEntity getAnnualShipmentCost(@RequestBody AnnualShipmentCostInput asci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getAnnualShipmentCost(asci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/costOfInventory")
    public ResponseEntity getCostOfInventory(@RequestBody CostOfInventoryInput cii, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getCostOfInventory(cii, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/inventoryTurns")
    public ResponseEntity getInventoryTurns(@RequestBody CostOfInventoryInput it, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getInventoryTurns(it, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/stockAdjustmentReport")
    public ResponseEntity getStockAdjustmentReport(@RequestBody StockAdjustmentReportInput si, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockAdjustmentReport(si, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/procurementAgentShipmentReport")
    public ResponseEntity getProcurementAgentShipmentReport(@RequestBody ProcurementAgentShipmentReportInput pari, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProcurementAgentShipmentReport(pari, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/fundingSourceShipmentReport")
    public ResponseEntity getFundingSourceShipmentReport(@RequestBody FundingSourceShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getFundingSourceShipmentReport(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/aggregateShipmentByProduct")
    public ResponseEntity getAggregateShipmentByProduct(@RequestBody FundingSourceShipmentReportInput fsri, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getAggregateShipmentByProduct(fsri, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/warehouseCapacityReport")
    public ResponseEntity getwarehouseCapacityReport(@RequestBody WarehouseCapacityInput wci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getWarehouseCapacityReport(wci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 28
    @RequestMapping(value = "/stockStatusForProgram")
    public ResponseEntity getStockStatusForProgram(@RequestBody StockStatusForProgramInput sspi, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusForProgram(sspi, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/programLeadTimes")
    public ResponseEntity getProgramLeadTimes(@RequestBody ProgramLeadTimesInput plt, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProgramLeadTimes(plt, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 1
    @RequestMapping(value = "/programProductCatalog")
    public ResponseEntity getProgramProductCatalog(@RequestBody ProgramProductCatalogInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getProgramProductCatalog(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 2
    @RequestMapping(value = "/consumptionForecastVsActual")
    public ResponseEntity getConsumptionForecastVsActual(@RequestBody ConsumptionForecastVsActualInput ppc, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getConsumptionForecastVsActual(ppc, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 16
    @RequestMapping(value = "/stockStatusVertical")
    public ResponseEntity getStockStatusVertical(@RequestBody StockStatusVerticalInput ssv, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusVertical(ssv, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 19
    @RequestMapping(value = "/shipmentDetails")
    public ResponseEntity getShipmentDetails(@RequestBody ShipmentDetailsInput sd, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentDetails(sd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 20
    @RequestMapping(value = "/shipmentOverview")
    public ResponseEntity getShipmentOverview(@RequestBody ShipmentOverviewInput so, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentOverview(so, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Report no 21
    @RequestMapping(value = "/shipmentGlobalDemand")
    public ResponseEntity getShipmentGlobalDemand(@RequestBody ShipmentGlobalDemandInput sgd, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getShipmentGlobalDemand(sgd, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
