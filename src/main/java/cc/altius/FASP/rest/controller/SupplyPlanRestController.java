/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.service.ProgramDataService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
public class SupplyPlanRestController {

    @Autowired
    private ProgramDataService programDataService;

    @GetMapping("/supplyPlan/programId/{programId}/versionId/{versionId}")
    @ResponseBody
    public ResponseEntity buildSupplyPlan(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId) {
        System.out.println("Starting supply plan build");
        System.out.println(new Date());
        SupplyPlan sp = this.programDataService.getSupplyPlan(programId, versionId);
        System.out.println("Completed Supply plan build");
        System.out.println(new Date());
        System.out.println("Going to save to the rm_supply_plan_batch_info table");
        List<SimplifiedSupplyPlan> simplifiedSupplyPlan = this.programDataService.updateSupplyPlanBatchInfo(sp);
        System.out.println("Completed save to the table");
        System.out.println(new Date());
        return new ResponseEntity(simplifiedSupplyPlan, HttpStatus.OK);
    }
}
