/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ProcurementAgent;
import cc.altius.FASP.model.ProcurementAgentForecastingUnit;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ProcurementAgentProcurementUnit;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    name = "Procurement Agent",
    description = "Manage procurement agents and their types"
)
public class ProcurementAgentRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private UserService userService;

    /**
     * Add Procurement Agent
     *
     * @param procurementAgent
     * @param auth
     * @return
     */
    @PostMapping(path = "/procurementAgent")
    @Operation(
        summary = "Add Procurement Agent",
        description = "Create a new procurement agent"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing procurement agent details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcurementAgent.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to create a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No procurement agent found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while creating a procurement agent")
    public ResponseEntity postProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int procurementAgentId = this.procurementAgentService.addProcurementAgent(procurementAgent, curUser);
            if (procurementAgentId > 0) {
                return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
            }
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Procurement Agent", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }

    }

    /**
     * Update Procurement Agent
     *
     * @param procurementAgent
     * @param auth
     * @return
     */
    @PutMapping(path = "/procurementAgent")
    @Operation(
        summary = "Update Procurement Agent",
        description = "Update an existing procurement agent"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing procurement agent details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcurementAgent.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Procurement agent already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating a procurement agent")
    public ResponseEntity putProcurementAgent(@RequestBody ProcurementAgent procurementAgent, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int rows = this.procurementAgentService.updateProcurementAgent(procurementAgent, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update  Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Procurement Agent", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to update Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.alreadExists"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active Procurement Agents
     *
     * @param auth
     * @return
     */
    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent")
    @Operation(
        summary = "Get Procurement Agents",
        description = "Retrieve a list of active procurement agents"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgent.class))), responseCode = "200", description = "Returns a list of active procurement agents")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent list")
    public ResponseEntity getProcurementAgent(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active Procurement Agents for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent/realmId/{realmId}")
    @Operation(
        summary = "Get Procurement Agent by Realm",
        description = "Retrieve a list of procurement agents for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve procurement agent list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgent.class))), responseCode = "200", description = "Returns a list of procurement agents for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent list")
    public ResponseEntity getProcurementAgentForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentByRealm(realmId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get Procurement Agent by Id
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @JsonView({Views.ReportView.class})
    @GetMapping("/procurementAgent/{procurementAgentId}")
    @Operation(
        summary = "Get Procurement Agent",
        description = "Retrieve a procurement agent by their unique identifier"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ProcurementAgent.class)), responseCode = "200", description = "Returns a procurement agent by their unique identifier")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent")
    public ResponseEntity getProcurementAgent(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentById(procurementAgentId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add and Update Procurement Agent Planning Unit
     *
     * @param procurementAgentPlanningUnits
     * @param auth
     * @return
     */
    @PutMapping("/procurementAgent/planningUnit")
    @Operation(
        summary = "Save Planning Unit for Procurement Agent",
        description = "Create or update a list of planning units for a procurement agent"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing procurement agent planning unit details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcurementAgentPlanningUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating a procurement agent")
    public ResponseEntity savePlanningUnitForProcurementAgent(@RequestBody ProcurementAgentPlanningUnit[] procurementAgentPlanningUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.procurementAgentService.saveProcurementAgentPlanningUnit(procurementAgentPlanningUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update PlanningUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get the Planning Units for a Procurement Agent Id
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/{procurementAgentId}/planningUnit/all")
    @Operation(
        summary = "Get Procurement Agent Planning Units",
        description = "Retrieve a list of planning units for a procurement agent"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve planning unit list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentPlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units for a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent planning unit list")
    public ResponseEntity getProcurementAgentPlanningUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add and Update the Forecasting Units for a Procurement Agent
     *
     * @param procurementAgentForecastingUnits
     * @param auth
     * @return
     */
    @PutMapping("/procurementAgent/forecastingUnit")
    @Operation(
        summary = "Save Forecasting Unit for Procurement Agent",
        description = "Create or update a list of forecasting units for a procurement agent"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing procurement agent forecasting unit details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcurementAgentForecastingUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating a procurement agent")
    public ResponseEntity saveForecastingUnitForProcurementAgent(@RequestBody ProcurementAgentForecastingUnit[] procurementAgentForecastingUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.procurementAgentService.saveProcurementAgentForecastingUnit(procurementAgentForecastingUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ForecastingUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error while trying to update ForecastingUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of Forecasting Units for a Procurement Agent
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/{procurementAgentId}/forecastingUnit")
    @Operation(
        summary = "Get Procurement Agent Forecasting Units",
        description = "Retrieve a list of forecasting units for a procurement agent"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve forecasting unit list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentForecastingUnit.class))), responseCode = "200", description = "Returns a list of forecasting units for a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent forecasting unit list")
    public ResponseEntity getProcurementAgentForecastingUnitList(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentForecastingUnitList(procurementAgentId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all the Forecasting Units for a Procurement Agent
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/{procurementAgentId}/forecastingUnit/all")
    @Operation(
        summary = "Get Procurement Agent Forecasting Units",
        description = "Retrieve a list of forecasting units for a procurement agent"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve forecasting unit list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentForecastingUnit.class))), responseCode = "200", description = "Returns a list of forecasting units for a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent forecasting unit list")
    public ResponseEntity getProcurementAgentForecastingUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentForecastingUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Agent Id" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add and Update Procurement Agent Procurement Unit
     *
     * @param procurementAgentProcurementUnits
     * @param auth
     * @return
     */
    @PutMapping("/procurementAgent/procurementUnit")
    @Operation(
        summary = "Save Procurement Unit for Procurement Agent",
        description = "Save a list of procurement units for a procurement agent"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing procurement agent procurement unit details",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProcurementAgentProcurementUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating a procurement agent")
    public ResponseEntity saveProcurementUnitForProcurementAgent(@RequestBody ProcurementAgentProcurementUnit[] procurementAgentProcurementUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.procurementAgentService.saveProcurementAgentProcurementUnit(procurementAgentProcurementUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update ProcurementUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update ProcurementUnit for ProcurementAgent", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active Procurement Units by Procurement Agent Id
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/{procurementAgentId}/procurementUnit")
    @Operation(
        summary = "Get Procurement Agent Procurement Units",
        description = "Retrieve a list of active procurement units for a procurement agent"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve procurement unit list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentProcurementUnit.class))), responseCode = "200", description = "Returns a list of procurement units for a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent procurement unit list")
    public ResponseEntity getProcurementAgentProcurementUnitList(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentProcurementUnitList(procurementAgentId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Unit for Procurement Agent" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Unit for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all Procurement Units by Procurement Agent Id
     *
     * @param procurementAgentId
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/{procurementAgentId}/procurementUnit/all")
    @Operation(
        summary = "Get Procurement Agent Procurement Units",
        description = "Retrieve a list of procurement units for a procurement agent"
    )
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve procurement unit list for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentProcurementUnit.class))), responseCode = "200", description = "Returns a list of procurement units for a procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Procurement agent not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent procurement unit list")
    public ResponseEntity getProcurementAgentProcurementUnitListAll(@PathVariable("procurementAgentId") int procurementAgentId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentProcurementUnitList(procurementAgentId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Procurement Unit for Procurement Agent" + procurementAgentId, er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list Procurement Unit for Procurement Agent", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Check if Procurement Agent Display name exists in the same Realm
     *
     * @param realmId
     * @param name
     * @param auth
     * @return
     */
    @GetMapping("/procurementAgent/getDisplayName/realmId/{realmId}/name/{name}")
    @Operation(
        summary = "Get Procurement Agent Display Name",
        description = "Retrieve a procurement agent display name by their realm and name"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve procurement agent display name for", required = true)
    @Parameter(name = "name", description = "The name of the procurement agent to retrieve display name for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = String.class)), responseCode = "200", description = "Returns a procurement agent display name by their realm and name")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting procurement agent display name")
    public ResponseEntity getProcurementAgentDisplayName(@PathVariable("realmId") int realmId, @PathVariable("name") String name, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.procurementAgentService.getDisplayName(realmId, name, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Funding source suggested display name", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
