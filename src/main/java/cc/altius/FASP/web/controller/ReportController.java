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
import cc.altius.FASP.model.report.CostOfInventoryInput;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.model.report.ForecastMetricsInput;
import cc.altius.FASP.model.report.StockAdjustmentListInput;
import cc.altius.FASP.model.report.StockOverTimeInput;
import cc.altius.FASP.model.report.StockStatusMatrixInput;
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

    @RequestMapping(value = "/stockOverTime")
    public ResponseEntity getStockOverTime(@RequestBody StockOverTimeInput soti, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockOverTime(soti, curUser), HttpStatus.OK);
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

    @RequestMapping(value = "/stockAdjustmentList")
    public ResponseEntity getStockAdjustmentList(@RequestBody StockAdjustmentListInput si, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockAdjustment(si, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
