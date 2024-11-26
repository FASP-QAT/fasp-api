/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProductCategoryTracerCategoryAndForecastingUnitDTO;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.PlanningUnitWithCount;
import cc.altius.FASP.model.ProcurementAgentPlanningUnit;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitWithPrices;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.fasterxml.jackson.annotation.JsonView;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Planning Unit",
    description = "Manage planning units with support for capacity tracking"
)
public class PlanningUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/planningUnit")
    @Operation(
        summary = "Add Planning Unit",
        description = "Save a new planning unit"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The planning unit data to add",
        required = true,
        content = @Content(schema = @Schema(implementation = PlanningUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to add this planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Planning unit already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding planning unit")
    public ResponseEntity postPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.planningUnitService.addPlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateNameException de) {
            logger.error("Error while trying to add PlanningUnit", de);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(path = "/planningUnit")
    @Operation(
        summary = "Update Planning Unit",
        description = "Update an existing planning unit"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The planning unit data to update",
        required = true,
        content = @Content(schema = @Schema(implementation = PlanningUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update this planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating planning unit")
    public ResponseEntity putPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.planningUnitService.updatePlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (DuplicateNameException de) {
            // FIXME: how does this error get thrown on an update?
            logger.error("Error while trying to add PlanningUnit", de);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/planningUnit")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit List",
        description = "Retrieve a list of active planning units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of active planning units")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/planningUnit/byIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit List",
        description = "Retrieve a list of planning units by their IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning unit IDs to retrieve",
        required = true,
        content = @Content(schema = @Schema(type = "array", implementation = String.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitByIdList(@RequestBody List<String> planningUnitIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByIds(planningUnitIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/planningUnit/withPrices/byIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit List with Prices",
        description = "Retrieve a list of planning units with prices by their IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning unit IDs to retrieve",
        required = true,
        content = @Content(schema = @Schema(type = "array", implementation = String.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units with prices")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitWithPricesByIdList(@RequestBody List<String> planningUnitIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListWithPricesByIds(planningUnitIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.InternalView.class)
    @GetMapping("/planningUnit/basic")
    @Operation(
        summary = "Get Planning Unit List Basic",
        description = "Retrieve a list of planning units with basic information"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of planning units with basic information")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitListBasic(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListBasic(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/all")
    @Operation(
        summary = "Get Planning Unit List All",
        description = "Retrieve a list of all planning units (active and disabled)"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of all planning units (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/realmId/{realmId}")
    @Operation(
        summary = "Get Planning Unit List for Realm",
        description = "Retrieve a list of active planning units for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of active planning units for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning units found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(realmId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/realmId/{realmId}/all")
    @Operation(
        summary = "Get Planning Unit List All",
        description = "Retrieve a list of all planning units (active and disabled) for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of all planning units (active and disabled) for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning units found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitForRealmAll(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/planningUnit/{planningUnitId}")
    @Operation(
        summary = "Get Planning Unit",
        description = "Retrieve a planning unit by its ID"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the planning unit with the specified ID")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitById(planningUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.InternalView.class)
    @GetMapping("/planningUnit/{planningUnitId}/withPrograms")
    @Operation(
        summary = "Get Planning Unit with Programs",
        description = "Retrieve a planning unit with its associated supply planning and forecasting programs (active and disabled)"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the planning unit with its associated supply planning and forecasting programs (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitWithProgramsById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            Map<String, Object> data = new HashMap<>();
            data.put("planningUnit", this.planningUnitService.getPlanningUnitById(planningUnitId, curUser));
            data.put("spProgramListActive", this.planningUnitService.getListOfSpProgramsForPlanningUnitId(planningUnitId, true, curUser));
            data.put("spProgramListDisabled", this.planningUnitService.getListOfSpProgramsForPlanningUnitId(planningUnitId, false, curUser));
            data.put("fcProgramListActive", this.planningUnitService.getListOfFcProgramsForPlanningUnitId(planningUnitId, true, curUser));
            data.put("fcProgramListDisabled", this.planningUnitService.getListOfFcProgramsForPlanningUnitId(planningUnitId, false, curUser));
            return new ResponseEntity(data, HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/forecastingUnit/{forecastingUnitId}")
    @Operation(
        summary = "Get Planning Unit by Forecasting Unit",
        description = "Retrieve a list of active planning units associated with a specific forecasting unit"
    )
    @Parameter(name = "forecastingUnitId", description = "The ID of the forecasting unit to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of active planning units associated with a specific forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning units found for the specified forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitByForecastingUnitId(@PathVariable("forecastingUnitId") int forecastingUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByForecastingUnit(forecastingUnitId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/planningUnit/capacity/realmId/{realmId}")
    @Operation(
        summary = "Get Planning Unit Capacity for Realm",
        description = "Retrieve a list of planning unit capacities for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of planning unit capacities for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning unit capacities found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForRealm(realmId, null, null, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/planningUnit/capacity/realmId/{realmId}/between/{startDate}/{stopDate}")
    @Operation(
        summary = "Get Planning Unit Capacity for Realm (Between Dates)",
        description = "Retrieve a list of planning unit capacities for a specific realm between two dates"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @Parameter(name = "startDate", description = "The start date of the range to retrieve planning unit capacities for", required = true)
    @Parameter(name = "stopDate", description = "The stop date of the range to retrieve planning unit capacities for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of planning unit capacities for a specific realm between two dates")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning unit capacities found for the specified realm between the specified dates")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForRealm(realmId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing planningUnitCapacity", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/planningUnit/capacity/{planningUnitId}")
    @Operation(
        summary = "Get Planning Unit Capacity",
        description = "Retrieve a planning unit capacity for a specific planning unit"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the planning unit capacity for a specific planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Planning unit capacity not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForId(planningUnitId, null, null, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/planningUnit/capacity/{planningUnitId}/between/{startDate}/{stopDate}")
    @Operation(
        summary = "Get Planning Unit Capacity (Between Dates)",
        description = "Retrieve a planning unit capacity for a specific planning unit between two dates"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @Parameter(name = "startDate", description = "The start date of the range to retrieve planning unit capacities for", required = true)
    @Parameter(name = "stopDate", description = "The end date of the range to retrieve planning unit capacities for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the planning unit capacity for a specific planning unit between two dates")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have rights to access this planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Planning unit capacity not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForId(planningUnitId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing planningUnitCapacity", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping(value = "/planningUnit/capacity/all")
    @Operation(
        summary = "Get Planning Unit Capacity List",
        description = "Retrieve a list of all planning unit capacities"
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of all planning unit capacities")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No planning unit capacities found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityList(curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PutMapping(value = "/planningUnit/capacity")
    @Operation(
        summary = "Save Planning Unit Capacity",
        description = "Save a list of planning unit capacities"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning unit capacities to save",
        required = true,
        content = @Content(
            mediaType = "application/json", 
            array = @ArraySchema(schema = @Schema(implementation = PlanningUnitCapacity.class))
        )
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a list of planning unit capacities")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning unit capacities found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving planning unit capacity")
    public ResponseEntity savePlanningUnitCapacity(@RequestBody PlanningUnitCapacity[] planningUnitCapacitys, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.planningUnitService.savePlanningUnitCapacity(planningUnitCapacitys, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while updating planningUnitCapacity", p);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while updating planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while updating planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping(value = "/sync/planningUnit/{lastSyncDate}")
//    public ResponseEntity getPlanningUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing planningUnit", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing planningUnit", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/productCategory/{productCategoryId}/all")
    @Operation(
        summary = "Get Planning Unit for Product Category (All)",
        description = "Retrieve a list of all planning units for a specific product category (active and disabled)"
    )
    @Parameter(name = "productCategoryId", description = "The ID of the product category to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of all planning units for a specific product category (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForProductCategoryAll(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategory(productCategoryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @JsonView(Views.ReportView.class)
    @GetMapping("/planningUnit/productCategory/{productCategoryId}/active")
    @Operation(
        summary = "Get Planning Unit for Product Category",
        description = "Retrieve a list of active planning units for a specific product category"
    )
    @Parameter(name = "productCategoryId", description = "The ID of the product category to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of active planning units for a specific product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForproductCategory(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategory(productCategoryId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/planningUnit/productCategoryList/active/realmCountryId/{realmCountryId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit for Product Category List",
        description = "Retrieve a list of active planning units for a list of product categories"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns a list of active planning units for a list of product categories")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForproductCategoryList(@RequestBody String[] productCategoryIds, @PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategoryList(productCategoryIds, realmCountryId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/getPlanningUnitByTracerCategory/planningUnitId/{planningUnitId}/{procurementAgentId}/{term}")
    @Operation(
        summary = "Get Planning Unit by Tracer Category (Procurement Agent)",
        description = "Retrieve a list of planning units for a specific procurement agent and tracer category"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @Parameter(name = "procurementAgentId", description = "The ID of the procurement agent to retrieve planning units for", required = true)
    @Parameter(name = "term", description = "The term to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ProcurementAgentPlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units for a specific procurement agent and tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this procurement agent")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified procurement agent and tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitByTracerCategory(@PathVariable("planningUnitId") int planningUnitId, @PathVariable("procurementAgentId") int procurementAgentId, @PathVariable("term") String term, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitListForTracerCategory(procurementAgentId, planningUnitId, term, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/planningUnit/realmCountry/{realmCountryId}")
    @Operation(
        summary = "Get Planning Unit by Realm Country",
        description = "Retrieve a list of planning units for a specific realm country"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns a list of planning units for a specific realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByRealmCountryId(realmCountryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/planningUnit/tracerCategory/{tracerCategoryId}")
    @Operation(
        summary = "Get Planning Unit by Tracer Category",
        description = "Retrieve a list of active planning units for a specific tracer category"
    )
    @Parameter(name = "tracerCategoryId", description = "The ID of the tracer category to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns a list of active planning units for a specific tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForTracerCategory(@PathVariable(value = "tracerCategoryId", required = true) int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByTracerCategory(tracerCategoryId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/planningUnit/tracerCategorys")
    @Operation(
        summary = "Get Planning Unit by Tracer Category List",
        description = "Retrieve a list of planning units for a list of tracer categories"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of tracer categories to retrieve planning units for",
        required = true,
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = String.class))
        )
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units for a list of tracer categories")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForTracerCategorys(@RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByTracerCategoryIds(tracerCategoryIds, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/planningUnit/withPricing/productCategory/{productCategoryId}")
    @Operation(
        summary = "Get Planning Unit with Pricing for Product Category",
        description = "Retrieve a list of planning units with pricing for a specific product category"
    )
    @Parameter(name = "productCategoryId", description = "The ID of the product category to retrieve planning units with pricing for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimplePlanningUnitWithPrices.class))), responseCode = "200", description = "Returns a list of planning units with pricing for a specific product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found with pricing for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitWithPricingForProductCategory(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListWithPricesForProductCategory(productCategoryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @PostMapping("/planningUnit/tracerCategory/productCategory/forecastingUnit")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit by Tracer Category, Product Category and Forecasting Unit",
        description = "Retrieve a list of planning units for a specific tracer category, product category and forecasting unit"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing tracer category, product category and forecasting unit IDs",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryTracerCategoryAndForecastingUnitDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnitWithCount.class))), responseCode = "200", description = "Returns a list of planning units for a specific tracer category, product category and forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this tracer category, product category and forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified tracer category, product category and forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForTracerCategorys(@RequestBody ProductCategoryTracerCategoryAndForecastingUnitDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitByTracerCategoryProductCategoryAndForecastingUnit(input, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
