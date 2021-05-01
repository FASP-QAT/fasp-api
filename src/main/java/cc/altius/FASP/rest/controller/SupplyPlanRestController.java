/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramIdAndVersionId;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimplifiedSupplyPlan;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    
    /**
     * API is to rebuild supply plan based on rebuild true or false
     *
     * @param programId programId that you want to rebuild supply plan for
     * @param versionId versionId that you want to rebuild supply plan for
     * @param rebuild rebuild true for rebuild supply plan , rebuild false for
     * do not rebuild supply plan
     * @param auth
     * @return returns success message if rebuild true, returns
     * SimplifiedSupplyPlan list if rebuild false
     */
    
    @GetMapping("/newSupplyPlan/programId/{programId}/versionId/{versionId}/rebuild/{rebuild}")
    @Operation(description = "API is to rebuild supply plan based on rebuild true or false ", summary = "To rebuild supply plan based on rebuild true or false ", tags = ("supplyPlan"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to rebuild supply plan for"),
        @Parameter(name = "versionId", description = "versionId that you want to rebuild supply plan for"),
        @Parameter(name = "rebuild", description = "rebuild true for rebuild supply plan , rebuild false for do not rebuild supply plan")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the success message or SimplifiedSupplyPlan list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to rebuild supply plan does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of SimplifiedSupplyPlan list or rebuild supply plan")
    @ResponseBody
    public ResponseEntity buildNewSupplyPlan(
            @PathVariable(value = "programId", required = true) int programId,
            @PathVariable(value = "versionId", required = true) int versionId,
            @PathVariable(value = "rebuild", required = false) boolean rebuild,
            Authentication auth
    ) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            Program p = this.programService.getProgramById(programId, curUser);
            if (versionId == -1) {
                versionId = p.getCurrentVersion().getVersionId();
            }
            System.out.println("Starting supply plan build for ProgramId:" + programId + " versionId:" + versionId + " rebuild:" + rebuild);
            System.out.println(new Date());
            List<SimplifiedSupplyPlan> spList = this.programDataService.getNewSupplyPlanList(programId, versionId, rebuild, true);
            if (rebuild) {
                System.out.println("Completed Supply plan build");
                System.out.println(new Date());
                return new ResponseEntity("Completed", HttpStatus.OK);
            } else {
                System.out.println("Completed Supply plan build");
                System.out.println(new Date());
                return new ResponseEntity(spList, HttpStatus.OK);
            }

        } catch (EmptyResultDataAccessException erda) {
            logger.error("Error while trying to rebuild Supply Plan", erda);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to rebuild Supply Plan", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to rebuild Supply Plan", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
    
    /**
     * API is to rebuild supply plan for listed programIds and versionIds
     * @param pvList pvList list of programIds and versionIds to rebuild supply plan for
     * @param auth
     * @return success/error message for rebuild supply plan
     */
    @PostMapping("/rebuildSupplyPlans")
    @Operation(description = "API is to rebuild supply plan for listed programIds and versionIds ", summary = "To rebuild supply plan for listed programIds and versionIds ", tags = ("supplyPlan"))
    @Parameters(
        @Parameter(name = "pvList", description = "pvList list of programIds and versionIds to rebuild supply plan for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the success/error message")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented rebuild supply plan")
    
    @ResponseBody
    public ResponseEntity rebuildSupplyPlans(@RequestBody List<ProgramIdAndVersionId> pvList, Authentication auth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        String nl = "\n";
        CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
        for (ProgramIdAndVersionId pv : pvList) {
            try {
                System.out.println("Starting supply plan build for ProgramId:" + pv.getProgramId() + " versionId:" + pv.getVersionId());
                sb.append("Starting supply plan build for ProgramId:" + pv.getProgramId() + " versionId:" + pv.getVersionId()).append(nl);
                Date dt1 = new Date();
                System.out.println(sdf.format(dt1));
                sb.append(sdf.format(dt1)).append(nl);
                List<SimplifiedSupplyPlan> spList = this.programDataService.getNewSupplyPlanList(pv.getProgramId(), pv.getVersionId(), true, true);
                Date dt2 = new Date();
                System.out.println(sdf.format(dt2));
                sb.append(sdf.format(dt2)).append(nl);
                System.out.println("Completed Supply plan build in " + (dt2.getTime() - dt1.getTime()) / 1000.0);
                sb.append("Completed Supply plan build in " + (dt2.getTime() - dt1.getTime()) / 1000.0).append(nl);
                System.out.println("-----------------------------------------------------------");
                sb.append("-----------------------------------------------------------").append(nl);
            } catch (ParseException ex) {
                System.out.println("Error occurred while trying to rebuild Supply plan " + ex.getMessage());
                sb.append("Error occurred while trying to rebuild Supply plan " + ex.getMessage()).append(nl);
            }
        }
        return new ResponseEntity(sb.toString(), HttpStatus.OK);
    }
}
