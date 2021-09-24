/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ERPNotificationDTO;
import cc.altius.FASP.model.DTO.ManualTaggingDTO;
import cc.altius.FASP.model.DTO.ManualTaggingOrderDTO;
import cc.altius.FASP.model.LoadProgram;
import cc.altius.FASP.model.Program;
import cc.altius.FASP.model.ProgramInitialize;
import cc.altius.FASP.model.ProgramPlanningUnit;
import cc.altius.FASP.model.ProgramPlanningUnitProcurementAgentPrice;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.ProgramDataService;
import cc.altius.FASP.service.ProgramService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/program")
public class ProgramRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProgramService programService;
    @Autowired
    private UserService userService;

    @Autowired
    private ProgramDataService programDataService;

    /**
     * API used to add a Program
     *
     * @param program Program object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to add a Program", summary = "Add Program", tags = ("program"))
    @Parameters(
            @Parameter(name = "program", description = "Program object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Program object supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/")
    public ResponseEntity postProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.addProgram(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Program
     *
     * @param program Program object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update a Program", summary = "Update Program", tags = ("program"))
    @Parameters(
            @Parameter(name = "program", description = "Program object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping(path = "/")
    public ResponseEntity putProgram(@RequestBody Program program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.updateProgram(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Program list for a list of Program Ids
     *
     * @param programIds Array of ProgramIds that you want to the list of
     * Programs for
     * @param auth
     * @return returns the list of Program based on Program Ids specified
     */
    @Operation(description = "API used to get the Program list for a list of Program Ids", summary = "Get Program list for Program Ids", tags = ("program"))
    @Parameters(
            @Parameter(name = "programIds", description = "Array of ProgramIds that you want to the list of Programs"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Program list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program list")
    @PostMapping("/programIds")
    public ResponseEntity getProgram(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the active Program list
     *
     * @param auth
     * @return returns the active list of Program
     */
    @Operation(description = "API used to get the active Program list", summary = "Get Active Program list", tags = ("program"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Program list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program list")
    @GetMapping("/")
    public ResponseEntity getProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(curUser, true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete Program list
     *
     * @param auth
     * @return returns the complete list of Program
     */
    @Operation(description = "API used to get the complete Program list", summary = "Get Complete Program list", tags = ("program"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Program list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program list")
    @GetMapping("/all")
    public ResponseEntity getProgramAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramList(curUser, false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Planning unit list for specified programId
     *
     * @param programId programId that you want to get Planning unit list for
     * @param auth
     * @return returns the Planning unit list
     */
    @Operation(description = "API used to get the Planning unit list for specified programId", summary = "Get Planning Unit List For Specified ProgramId", tags = ("program"))
    @Parameters(
            @Parameter(name = "programId", description = "programId that you want to get Planning unit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Planning unit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Planning unit list")
    @GetMapping("/{programId}/planningUnit")
    public ResponseEntity getPlanningUnitForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramId(programId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Planning unit list for specified programId and
     * tracerCategoryIds
     *
     * @param programId programId that you want to get Planning unit list for
     * @param tracerCategoryIds Array oftracerCategoryIds that you want to get
     * Planning unit list for
     * @param auth
     * @return returns the Planning unit list
     */
    @Operation(description = "API used to get the Planning unit list for specified programId and tracerCategoryIds", summary = "Get Planning Unit List For Specified ProgramId and TracerCategoryIds", tags = ("program"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to get Planning unit list for"),
        @Parameter(name = "tracerCategoryIds", description = "Array oftracerCategoryIds that you want to get Planning unit list for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Planning unit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Planning unit list")
    @PostMapping("/{programId}/tracerCategory/planningUnit")
    public ResponseEntity getPlanningUnitForProgramTracerCategory(@PathVariable("programId") int programId, @RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramIdAndTracerCategoryIds(programId, true, tracerCategoryIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete Planning unit list for specified programId
     *
     * @param programId programId that you want to get Planning unit list for
     * @param auth
     * @return returns the Planning unit list
     */
    @Operation(description = "API used to get the complete Planning unit list for specified programId ", summary = "Get Complete Planning Unit List For Specified ProgramId", tags = ("program"))
    @Parameters(
            @Parameter(name = "programId", description = "programId that you want to get Planning unit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Planning unit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Planning unit list")
    @GetMapping("/{programId}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAll(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramId(programId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to save a PlanningUnit Unit for specified programId
     *
     * @param ppu Array of ProgramPlanningUnit object that you want to save
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save a PlanningUnit Unit for specified programId", summary = "Save a PlanningUnit Unit For Specified Program", tags = ("program"))
    @Parameters(
            @Parameter(name = "ppu", description = "Array of ProgramPlanningUnit object that you want to save"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/planningUnit")
    public ResponseEntity savePlanningUnitForProgram(@RequestBody ProgramPlanningUnit[] ppu, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.saveProgramPlanningUnit(ppu, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ProcurementAgent list for specified
     * ProgramPlanningUnitId
     *
     * @param programPlanningUnitId ProgramPlanningUnitId that you want to get
     * ProcurementAgent unit list for
     * @param auth
     * @return returns the ProcurementAgent list
     */
    @Operation(description = "API used to get the ProcurementAgent list for specified ProgramPlanningUnitId", summary = "Get ProcurementAgent List For Specified ProgramPlanningUnitId", tags = ("program"))
    @Parameters(
            @Parameter(name = "programPlanningUnitId", description = "ProgramPlanningUnitId that you want to get ProcurementAgent list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ProcurementAgent list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ProcurementAgent list")
    @GetMapping("/planningUnit/procurementAgent/{programPlanningUnitId}")
    public ResponseEntity getProgramPlanningUnitProcurementAgent(@PathVariable("programPlanningUnitId") int programPlanningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramPlanningUnitProcurementAgentList(programPlanningUnitId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get ProgramPrice list for Program Planning Unit Id" + programPlanningUnitId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to get ProgramPrice list for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to save a PlanningUnit Unit, ProcurementAgent and Price
     *
     * @param programPlanningUnitProcurementAgentPrices Array of
     * programPlanningUnitProcurementAgentPrices object that you want to save
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to save a PlanningUnit Unit, ProcurementAgent and Price", summary = "Save PlanningUnit Unit, ProcurementAgent and Price", tags = ("program"))
    @Parameters(
            @Parameter(name = "programPlanningUnitProcurementAgentPrices", description = "Array of programPlanningUnitProcurementAgentPrices object that you want to save"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PutMapping("/planningingUnit/procurementAgent")
    public ResponseEntity saveProgramPlanningUnitProcurementAgentPrices(@RequestBody ProgramPlanningUnitProcurementAgentPrice[] programPlanningUnitProcurementAgentPrices, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.saveProgramPlanningUnitProcurementAgentPrice(programPlanningUnitProcurementAgentPrices, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ProgramPlanningUnit ProcurementAgent Prices", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the PlanningUnit list for specified ProgramIds
     *
     * @param programIds Array of programIds that you want to get PlanningUnit
     * list for
     * @param auth
     * @return returns the PlanningUnit list
     */
    @Operation(description = "API used to get the PlanningUnit list for specified ProgramIds", summary = "get PlanningUnit List For Specified ProgramIds", tags = ("program"))
    @Parameters(
            @Parameter(name = "programIds", description = "Array of programIds that you want to get PlanningUnit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")
    @PostMapping("/planningUnit/programs")
    public ResponseEntity getPlanningUnitForProgramList(@RequestBody Integer[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramIds(programIds, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get PlanningUnit list for Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Program list for specified realm
     *
     * @param realmId realmId that you want to get Program list for
     * @param auth
     * @return returns the Program list
     */
    @Operation(description = "API used to get the Program list for specified realm", summary = "Get Program List For Specified Realm", tags = ("program"))
    @Parameters(
            @Parameter(name = "realmId", description = "realmId that you want to get Program list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Program list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program list")
    @GetMapping("/realmId/{realmId}")
    public ResponseEntity getProgramForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramListForRealmId(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Program object for specified programId
     *
     * @param programId programId that you want to get Program object for
     * @param auth
     * @return returns the Program object
     */
    @Operation(description = "API used to get the Program object for specified programId", summary = "Get Program Object For Specified ProgramId", tags = ("program"))
    @Parameters(
            @Parameter(name = "programId", description = "programId that you want to get Program object for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Program object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Program object")
    @GetMapping("/{programId}")
    public ResponseEntity getProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getProgramById(programId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/program/{lastSyncDate}")
//    public ResponseEntity getProgramListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getProgramListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing program", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing program", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping(value = "/sync/programPlanningUnit/{lastSyncDate}")
//    public ResponseEntity getProgramPlanningUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.programService.getProgramPlanningUnitListForSyncProgram(getProgramIds(new String[]{"2030"}), curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing program planning unit", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing program planning unit", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * API used to get the Planning unit list for specified programId and
     * ProductCategoryId
     *
     * @param programId programId that you want to get Planning unit list for
     * @param productCategoryId productCategoryId that you want to get Planning
     * unit list for
     * @param auth
     * @return returns the Planning unit list
     */
    @Operation(description = "API used to get the Planning unit list for specified programId and ProductCategoryId", summary = "Get Planning Unit List For Specified ProgramId and ProductCategoryId", tags = ("program"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to get Planning unit list for"),
        @Parameter(name = "productCategoryId", description = "productCategoryId that you want to get Planning unit list for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Planning unit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Planning unit list")
    @GetMapping("/{programId}/{productCategory}/planningUnit/all")
    public ResponseEntity getPlanningUnitForProgramAndProductCategory(@PathVariable("programId") int programId, @PathVariable("productCategory") int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getPlanningUnitListForProgramAndCategoryId(programId, productCategoryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a ProgramInitialize
     *
     * @param program ProgramInitialize object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to add a ProgramInitialize", summary = "Add a ProgramInitialize", tags = ("program"))
    @Parameters(
            @Parameter(name = "program", description = "ProgramInitialize object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Program object supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping(path = "/initialize")
    public ResponseEntity postProgramInitialize(@RequestBody ProgramInitialize program, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.addProgramInitialize(program, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add Program", d);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Program", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Program", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ShipmentDetails for specified parentShipmentId
     *
     * @param manualTaggingDTO manualTaggingDTO having parentShipmentId that you
     * want to get ShipmentDetails for
     * @param auth
     * @return returns the ShipmentDetails
     */
    @Operation(description = "API used to get the ShipmentDetails for specified parentShipmentId", summary = "Get ShipmentDetails For Specified ParentShipmentId", tags = ("program"))
    @Parameters(
            @Parameter(name = "manualTaggingDTO", description = "manualTaggingDTO having parentShipmentId that you want to get ShipmentDetails for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ShipmentDetails")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ShipmentDetails list")
    @PostMapping("/getShipmentDetailsByParentShipmentId")
    public ResponseEntity getShipmentDetailsByParentShipmentId(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
//        System.out.println("parentShipmentId--------" + manualTaggingDTO);
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getShipmentDetailsByParentShipmentId(manualTaggingDTO.getParentShipmentId()), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Shipment List for specified manualTagging
     *
     * @param manualTaggingDTO manualTagging that you want to get
     * ShipmentDetails for
     * @param auth
     * @return returns the Shipment List
     */
    @Operation(description = "API used to get the Shipment List for specified manualTagging", summary = "Get Shipment List For Specified ManualTagging", tags = ("program"))
    @Parameters(
            @Parameter(name = "manualTaggingDTO", description = "manualTagging that you want to get ShipmentDetails for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Shipment List")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Shipment list")
    @PostMapping("/manualTagging")
    public ResponseEntity getShipmentListForManualTagging(@RequestBody ManualTaggingDTO manualTaggingDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getShipmentListForManualTagging(manualTaggingDTO, curUser), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to list Shipment list for Manual Tagging 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the shipmentLinkingNotification List
     *
     * @param eRPNotificationDTO eRPNotificationDTO having planningUnitId and
     * ProgramId that you want to get shipmentLinkingNotification list for
     * @param auth
     * @return returns the shipmentLinkingNotification List
     */
    @Operation(description = "API used to get the shipmentLinkingNotification List", summary = "Get ShipmentLinkingNotification List", tags = ("program"))
    @Parameters(
            @Parameter(name = "eRPNotificationDTO", description = "eRPNotificationDTO having planningUnitId and ProgramId that you want to get shipmentLinkingNotification list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the shipmentLinkingNotification List")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of shipmentLinkingNotification list")
    @PostMapping("/shipmentLinkingNotification")
    public ResponseEntity shipmentLinkingNotification(@RequestBody ERPNotificationDTO eRPNotificationDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getNotificationList(eRPNotificationDTO), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Notification
     *
     * @param eRPNotificationDTO Array of eRPNotificationDTO objects that you
     * want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to update a Notification", summary = "Update a Notification", tags = ("program"))
    @Parameters(
            @Parameter(name = "eRPNotificationDTO", description = "Array of eRPNotificationDTO objects that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/updateNotification")
    public ResponseEntity updateNotification(@RequestBody ERPNotificationDTO[] eRPNotificationDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            for (ERPNotificationDTO e : eRPNotificationDTO) {
//                System.out.println("e-------------------*********************" + e);
                this.programService.updateNotification(e, curUser);
            }
            this.programDataService.getNewSupplyPlanList(eRPNotificationDTO[0].getProgramId(), -1, true, false);
            return new ResponseEntity(true, HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Notification Count
     *
     * @param auth
     * @return returns the Notification Count
     */
    @Operation(description = "API used to get the Notification Count", summary = "Get Notification Count", tags = ("program"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Notification Count")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Notification Count")
    @GetMapping("/getNotificationCount")
    public ResponseEntity getNotificationCount(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getNotificationCount(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Notification Summary
     *
     * @param auth
     * @return returns the Notification Summary
     */
    @Operation(description = "API used to get the Notification Summary", summary = "Get Notification Summary", tags = ("program"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Notification Summary")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Notification Summary")
    @GetMapping("/getNotificationSummary")
    public ResponseEntity getNotificationSummary(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getNotificationSummary(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Program", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get not Linked Shipment List For ManualTagging
     *
     * @param programId programId that you want to get not Linked Shipment List
     * For
     * @param linkingType linkingType that you want the Shipment List For
     * @param auth
     * @return returns the not Linked Shipment List
     */
    @Operation(description = "API used to get not Linked Shipment List For ManualTagging", summary = "Get Not Linked Shipment List For ManualTagging", tags = ("program"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to get not Linked Shipment List for"),
        @Parameter(name = "linkingType", description = "linkingType that you want the Shipment List For")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the not Linked Shipment List")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of not Linked Shipment List")
    @GetMapping("/manualTagging/notLinkedShipments/{programId}/{linkingType}")
    public ResponseEntity getNotLinkedShipmentListForManualTagging(@PathVariable("programId") int programId, @PathVariable("linkingType") int linkingType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getNotLinkedShipments(programId, linkingType), HttpStatus.OK);
//            return new ResponseEntity("", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get Artmis History for specified orderNo and primeLineNo
     *
     * @param orderNo orderNo that you want to Artmis History For
     * @param primeLineNo primeLineNo that you want to Artmis History For
     * @param auth
     *
     * @return returns the Artmis History
     */
    @Operation(description = "API used to get Artmis History for specified orderNo and primeLineNo", summary = "Get Artmis History For Specified OrderNo And PrimeLineNo", tags = ("program"))
    @Parameters({
        @Parameter(name = "orderNo", description = "orderNo that you want to Artmis History For"),
        @Parameter(name = "primeLineNo", description = "primeLineNo that you want to Artmis History For")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Artmis History")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Artmis History")
    @GetMapping("/artmisHistory/{orderNo}/{primeLineNo}")
    public ResponseEntity artmisHistory(@PathVariable("orderNo") String orderNo, @PathVariable("primeLineNo") int primeLineNo, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getARTMISHistory(orderNo, primeLineNo), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get Erp Order Search Data by text for specified
     * programId,erpPlanningUnitId and linkingType
     *
     * @param term orderNo text for search
     * @param programId programId that you want to get Erp Order Search Data For
     * @param planningUnitId planningUnitId that you want to get Erp Order
     * Search Data For
     * @param linkingType linkingType that you want to get Erp Order Search Data
     * For
     * @param auth
     *
     * @return returns the Erp Order Search Data
     */
    @Operation(description = "API used to get Erp Order Search Data by text for specified programId,erpPlanningUnitId and linkingType", summary = "Get Erp Order Search Data By Text For Specified ProgramId,ErpPlanningUnitId And LinkingType", tags = ("program"))
    @Parameters({
        @Parameter(name = "term", description = "orderNo text for search"),
        @Parameter(name = "programId", description = "programId that you want to get Erp Order Search Data For"),
        @Parameter(name = "planningUnitId", description = "planningUnitId that you want to get Erp Order Search Data For"),
        @Parameter(name = "linkingType", description = "linkingType that you want to get Erp Order Search Data for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Erp Order Search Data")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Erp Order Search Data")
    @GetMapping("/searchErpOrderData/{term}/{programId}/{erpPlanningUnitId}/{linkingType}")
    public ResponseEntity searchErpOrderData(@PathVariable("term") String term, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getErpOrderSearchData(term, programId, planningUnitId, linkingType), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get Order details for specified orderNo,
     * programId,erpPlanningUnitId,linkingType and parentShipmentId
     *
     * @param roNoOrderNo orderNo that you want to get Order details For
     * @param programId programId that you want to get Order details For
     * @param planningUnitId planningUnitId that you want to get Order details
     * For
     * @param linkingType linkingType that you want to get Order details For
     * @param parentShipmentId parentShipmentId that you want to get Order
     * details For
     * @param auth
     *
     * @return returns the Order details
     */
    @Operation(description = "API used to get Order details for specified orderNo,programId,erpPlanningUnitId,linkingType and parentShipmentId", summary = "Get Order Details For Specified orderNo,programId,erpPlanningUnitId,linkingType and parentShipmentId", tags = ("program"))
    @Parameters({
        @Parameter(name = "roNoOrderNo", description = "orderNo that you want to get Order details For"),
        @Parameter(name = "programId", description = "programId that you want to get Order details For"),
        @Parameter(name = "planningUnitId", description = "planningUnitId that you want to get Order details For"),
        @Parameter(name = "linkingType", description = "linkingType that you want to get Order details For"),
        @Parameter(name = "parentShipmentId", description = "parentShipmentId that you want to get Order details for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Order details")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Order details")
    @GetMapping("/orderDetails/{roNoOrderNo}/{programId}/{erpPlanningUnitId}/{linkingType}/{parentShipmentId}")
    public ResponseEntity getOrderDetailsByOrderNoAndPrimeLineNo(@PathVariable("roNoOrderNo") String roNoOrderNo, @PathVariable("programId") int programId, @PathVariable("erpPlanningUnitId") int planningUnitId, @PathVariable("linkingType") int linkingType, @PathVariable("parentShipmentId") int parentShipmentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            if (linkingType == 3) {
                return new ResponseEntity(this.programService.getOrderDetailsByForNotLinkedERPShipments(roNoOrderNo, planningUnitId, linkingType), HttpStatus.OK);
            } else {
                return new ResponseEntity(this.programService.getOrderDetailsByOrderNoAndPrimeLineNo(roNoOrderNo, programId, planningUnitId, linkingType, parentShipmentId), HttpStatus.OK);
            }
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 1---", e);
            return new ResponseEntity(new ResponseCode("static.mt.noDetailsFound"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Error while trying to get order details for Manual Tagging 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Error while trying to  get order details for Manual Tagging 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to link Shipment with ARTMIS
     *
     * @param erpOrderDTO Array of ManualTaggingOrderDTO that you want to link
     * Shipment with ARTMIS
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to link Shipment with ARTMIS", summary = "Link Shipment With ARTMIS", tags = ("program"))
    @Parameters(
            @Parameter(name = "erpOrderDTO", description = "Array of ManualTaggingOrderDTO that you want to link Shipment with ARTMIS"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/linkShipmentWithARTMIS/")
    @Transactional
    public ResponseEntity linkShipmentWithARTMIS(@RequestBody ManualTaggingOrderDTO[] erpOrderDTO, Authentication auth) {
        try {
//            System.out.println("erpOrderDTO%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" + Arrays.toString(erpOrderDTO));
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            List<Integer> result = this.programService.linkShipmentWithARTMIS(erpOrderDTO, curUser);
            logger.info("ERP Linking : Going to get new supply plan list ");
            this.programDataService.getNewSupplyPlanList(erpOrderDTO[0].getProgramId(), -1, true, false);
            logger.info("ERP Linking : supply plan rebuild done ");
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Linking error 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Linking error 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Linking error 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get shipment list for delinking for specified programId and
     * planningUnitId
     *
     * @param programId programId that you want to get shipment list for
     * delinking for
     * @param planningUnitId planningUnitId that you want to get shipment list
     * for delinking for
     * @param auth
     *
     * @return returns the shipment list for delinking
     */
    @Operation(description = "API used to get shipment list for delinking for specified programId and planningUnitId", summary = "Get Shipment List For Delinking for specified programId and planningUnitId", tags = ("program"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to get shipment list for delinking for"),
        @Parameter(name = "planningUnitId", description = "planningUnitId that you want to get shipment list for delinking for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the shipment list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of shipment list")
    @GetMapping("/shipmentListForDelinking/{programId}/{planningUnitId}")
    public ResponseEntity getShipmentListForDelinking(@PathVariable("programId") int programId, @PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getShipmentListForDelinking(programId, planningUnitId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Shipment list for Manual Tagging", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to delink Shipment
     *
     * @param erpOrderDTO Array of ManualTaggingOrderDTO that you want to delink
     * Shipment
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to delink Shipment", summary = "Delink Shipment", tags = ("program"))
    @Parameters(
            @Parameter(name = "erpOrderDTO", description = "Array of ManualTaggingOrderDTO that you want to delink Shipment"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the some of the underlying data does not match.")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @PostMapping("/delinkShipment")
    public ResponseEntity delinkShipment(@RequestBody ManualTaggingOrderDTO erpOrderDTO, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.programService.delinkShipment(erpOrderDTO, curUser);
            logger.info("ERP Linking : Going to get new supply plan list ");
            this.programDataService.getNewSupplyPlanList(erpOrderDTO.getProgramId(), -1, true, false);
            logger.info("ERP Linking : new supply plan rebuild done ");
            return new ResponseEntity(new ResponseCode("success"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("ERP Linking : Delinking error 1---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("ERP Linking : Delinking error 2---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("ERP Linking : Delinking error 3---", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to load Program
     *
     * @param auth
     *
     * @return returns a Success code if the operation was successful
     */
    @Operation(description = "API used to load Program", summary = "Load Program", tags = ("program"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/loadProgram")
    public ResponseEntity getLoadProgram(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getLoadProgram(curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to list five versions of program from give page number for
     * specified programId
     *
     * @param programId programId that you want to get program list for
     * @param page page number that you want to get program list from
     * @param auth
     *
     * @return returns a list of five versions of program
     */
    @Operation(description = "API used to list five versions of program from give page number for specified programId", summary = "List five versions of program from give page number for specified programId", tags = ("program"))
    @Parameters({
        @Parameter(name = "programId", description = "programId that you want to get program list for "),
        @Parameter(name = "page", description = "number that you want to get program list from")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of five versions of program")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/loadProgram/programId/{programId}/page/{page}")
    public ResponseEntity getLoadProgram(@PathVariable("programId") int programId, @PathVariable("page") int page, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getLoadProgram(programId, page, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to validate Program for specified realmId,programId and
     * programCode
     *
     * @param realmId realmId that you want to validate program for
     * @param programId programId that you want to validate program for
     * @param programCode programCode that you want to validate program for
     * @param auth
     *
     * @return true if the ProgramCode is not present and is a valid entry and
     * false if the ProgramCode exists and cannot be used again
     */
    @Operation(description = "API used to validate Program for specified realmId,programId and programCode", summary = "Validate Program for specified realmId,programId and programCode", tags = ("program"))
    @Parameters({
        @Parameter(name = "realmId", description = "realmId that you want to validate program for"),
        @Parameter(name = "programId", description = "programId that you want to validate program for"),
        @Parameter(name = "programCode", description = "programCode that you want to validate program for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a true/false if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping("/validate/realmId/{realmId}/programId/{programId}/programCode/{programCode}")
    public ResponseEntity validateProgramCode(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, @PathVariable("programCode") String programCode, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.validateProgramCode(realmId, programId, programCode, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity(new LinkedList<LoadProgram>(), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get supply plan reviewer list for specified program
     *
     * @param programId programId that you want to supply plan reviewer list for
     * @param auth
     *
     * @return returns the supply plan reviewer list
     */
    @Operation(description = "API used to get supply plan reviewer list for specified program", summary = "Get supply plan reviewer list for specified program", tags = ("program"))
    @Parameters(
            @Parameter(name = "programId", description = "programId that you want to supply plan reviewer list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the supply plan reviewer list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of supply plan reviewer list")
    @GetMapping("/supplyPlanReviewer/programId/{programId}")
    public ResponseEntity getSupplyPlanReviewerListForProgram(@PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.programService.getSupplyPlanReviewerList(programId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list Programs", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getProgramIds(String[] programIds) {
        if (programIds == null) {
            return "";
        } else {
            String opt = String.join("','", programIds);
            if (programIds.length > 0) {
                return "'" + opt + "'";
            } else {
                return opt;
            }
        }
    }
}
