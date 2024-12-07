/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Supply plan",
    description = "Manage supply plan generation and batch processing"
)
public class SupplyPlanRestController {

    @Autowired
    private ProgramDataService programDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProgramService programService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

//    @GetMapping("/supplyPlan/programId/{programId}/versionId/{versionId}")
//    @ResponseBody
//    public ResponseEntity buildSupplyPlan(@PathVariable(value = "programId", required = true) int programId, @PathVariable(value = "versionId", required = true) int versionId) {
//        System.out.println("Starting supply plan build");
//        System.out.println(new Date());
//        SupplyPlan sp = this.programDataService.getSupplyPlan(programId, versionId);
//        System.out.println("Completed Supply plan build");
//        System.out.println(new Date());
//        System.out.println("Going to save to the rm_supply_plan_batch_info table");
//        List<SimplifiedSupplyPlan> simplifiedSupplyPlan = this.programDataService.updateSupplyPlanBatchInfo(sp);
//        System.out.println("Completed save to the table");
//        System.out.println(new Date());
//        return new ResponseEntity(simplifiedSupplyPlan, HttpStatus.OK);
//    }
//    @GetMapping("/newSupplyPlan/programId/{programId}/versionId/{versionId}/rebuild/{rebuild}")
//    @ResponseBody
//    public ResponseEntity buildNewSupplyPlan(
//            @PathVariable(value = "programId", required = true) int programId,
//            @PathVariable(value = "versionId", required = true) int versionId,
//            @PathVariable(value = "rebuild", required = false) boolean rebuild,
//            Authentication auth
//    ) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
//            SimpleProgram sp = this.programService.getSimpleProgramById(programId, curUser);
//            if (versionId == -1) {
//                versionId = sp.getCurrentVersionId();
//            }
////            System.out.println("Starting supply plan build for ProgramId:" + programId + " versionId:" + versionId + " rebuild:" + rebuild);
////            System.out.println(new Date());
//            List<SimplifiedSupplyPlan> spList = this.programDataService.getNewSupplyPlanList(programId, versionId, rebuild, true);
//            if (rebuild) {
////                System.out.println("Completed Supply plan build");
////                System.out.println(new Date());
//                return new ResponseEntity("Completed", HttpStatus.OK);
//            } else {
////                System.out.println("Completed Supply plan build");
////                System.out.println(new Date());
//                return new ResponseEntity(spList, HttpStatus.OK);
//            }
//
//        } catch (EmptyResultDataAccessException erda) {
//            logger.error("Error while trying to rebuild Supply Plan", erda);
//            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (AccessDeniedException ae) {
//            logger.error("Error while trying to rebuild Supply Plan", ae);
//            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
//        } catch (Exception e) {
//            logger.error("Error while trying to rebuild Supply Plan", e);
//            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    /**Rebuild the Supply Plan for a list of ProgramId and Version Id
     * 
     * @param pvList
     * @param auth
     * @return 
     */
    @PostMapping("/rebuildSupplyPlans")
    @ResponseBody
    @Operation(
        summary = "Rebuild Supply Plan",
        description = "Rebuild the supply plan for a list of program IDs and version IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "A list of program IDs and version IDs to rebuild supply plans for",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProgramIdAndVersionId.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns the supply plan build log")
    public ResponseEntity rebuildSupplyPlans(@RequestBody List<ProgramIdAndVersionId> pvList, Authentication auth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        String nl = "\n";
        CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
        for (ProgramIdAndVersionId pv : pvList) {
            try {
//                System.out.println("Starting supply plan build for ProgramId:" + pv.getProgramId() + " versionId:" + pv.getVersionId());
                sb.append("Starting supply plan build for ProgramId:" + pv.getProgramId() + " versionId:" + pv.getVersionId()).append(nl);
                Date dt1 = new Date();
//                System.out.println(sdf.format(dt1));
                sb.append(sdf.format(dt1)).append(nl);
                List<SimplifiedSupplyPlan> spList = this.programDataService.getNewSupplyPlanList(pv.getProgramId(), pv.getVersionId(), true, true);
                Date dt2 = new Date();
//                System.out.println(sdf.format(dt2));
                sb.append(sdf.format(dt2)).append(nl);
//                System.out.println("Completed Supply plan build in " + (dt2.getTime() - dt1.getTime()) / 1000.0);
                sb.append("Completed Supply plan build in " + (dt2.getTime() - dt1.getTime()) / 1000.0).append(nl);
//                System.out.println("-----------------------------------------------------------");
                sb.append("-----------------------------------------------------------").append(nl);
            } catch (ParseException ex) {
//                System.out.println("Error occurred while trying to rebuild Supply plan " + ex.getMessage());
                sb.append("Error occurred while trying to rebuild Supply plan " + ex.getMessage()).append(nl);
            }
        }
        return new ResponseEntity(sb.toString(), HttpStatus.OK);
    }
}
