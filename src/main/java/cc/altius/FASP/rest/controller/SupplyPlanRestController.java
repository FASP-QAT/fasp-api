/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.SupplyPlan;
import cc.altius.FASP.model.SupplyPlanBatchInfo;
import cc.altius.FASP.model.SupplyPlanDate;
import cc.altius.FASP.service.ProgramDataService;
import java.text.SimpleDateFormat;
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
        SupplyPlan sp = this.programDataService.getSupplyPlan(programId, versionId);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yy");
        System.out.println("BATCH_ID\tTRANS_DATE\tEXPIRY_DATE\tSHIPMENT_QTY\tCONSUMPTION\tADJUSTMENT\tEXPIRED\tUNALLOCATED\tCALCULCATED\tOPEN_BAL\tCLOSE_BAL\tUNMET DEMAND");
        for (SupplyPlanDate sd : sp.getSupplyPlanDateList()) {
            for (SupplyPlanBatchInfo spbi : sd.getBatchList()) {
                int prevCB = sp.getPrevClosingBalance(sd.getPlanningUnitId(), spbi.getBatchId(), sd.getPrevTransDate());
                spbi.setOpeningBalance(prevCB);
                sd.setUnallocatedConsumption(spbi.updateUnAllocatedCountAndExpiredStock(sd.getTransDate(), sd.getUnallocatedConsumption()));
            }
            int unallocatedConsumption = sd.getUnallocatedConsumption();
            for (SupplyPlanBatchInfo spbi : sd.getBatchList()) {
                sd.setUnallocatedConsumption(unallocatedConsumption);
                unallocatedConsumption = spbi.updateCB(unallocatedConsumption);
                if (spbi.getBatchId()==0 && unallocatedConsumption > 0) {
                    spbi.setUnmetDemand(unallocatedConsumption);
                    sd.setUnallocatedConsumption(0);
                }
                System.out.println(spbi.getBatchId() + "\t\t" + sd.getTransDateStr() + "\t\t" + spbi.getExpiryDateStr() + "\t\t" + spbi.getShipmentQty() + "\t\t" + spbi.getConsumption() + "\t\t" + spbi.getAdjustment() + "\t\t" + spbi.getExpiredStock() + "\t\t" + sd.getUnallocatedConsumption() + "\t\t" + spbi.getCalculatedConsumption() + "\t\t" + spbi.getOpeningBalance() + "\t\t" + spbi.getClosingBalance() + "\t\t" + spbi.getUnmetDemand());
            }
        }
        this.programDataService.updateSupplyPlanBatchInfo(sp);
        return new ResponseEntity(sp, HttpStatus.OK);
    }
}
