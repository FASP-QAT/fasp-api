/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.report.GlobalConsumptionInput;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.report.ForecastErrorInput;
import cc.altius.FASP.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    @RequestMapping(value = "/stockmatrix/{realmId}/{programId}/{planningUnitId}/{view}/{startDate}/{endDate}")
    public ResponseEntity getStockStatusMatrix(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, @PathVariable("view") int view, @PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getStockStatusMatrix(realmId, programId, planningUnitId, view, startDate, endDate), HttpStatus.OK);
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

    @GetMapping(value = "/globalConsumption")
    public ResponseEntity getGlobalConsumption(@RequestBody GlobalConsumptionInput gci, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getGlobalConsumption(gci, curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
