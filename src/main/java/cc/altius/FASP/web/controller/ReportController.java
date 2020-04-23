/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ekta
 */
@RestController
@RequestMapping("/api")
public class ReportController {
        @Autowired
    private ReportService reportService;
     @RequestMapping(value = "/consumption/{realmId}/{productCategoryId}/{planningUnitId}")
    public ResponseEntity getConsumptionData(@PathVariable("realmId") int realmId,@PathVariable("productCategoryId") int productcategoryId,@PathVariable("planningUnitId") int planningUnitId,Authentication auth)  {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.reportService.getConsumptionData(realmId,productcategoryId,planningUnitId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}
