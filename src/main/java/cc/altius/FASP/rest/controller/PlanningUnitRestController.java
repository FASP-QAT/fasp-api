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
import cc.altius.FASP.model.PlanningUnitWithPrices;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.SimplePlanningUnitForAdjustPlanningUnit;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/planningUnit")
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

    /**
     * Add PU
     *
     * @param planningUnit
     * @param auth
     * @return
     */
    @PostMapping(path = "")
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.planningUnitService.addPlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
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

    /**
     * Update PU
     *
     * @param planningUnit
     * @param auth
     * @return
     */
    @PutMapping(path = "")
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
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Another planning unit with the same key exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating planning unit")
    public ResponseEntity putPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.planningUnitService.updatePlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (DuplicateNameException de) {
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

    /**
     * Get list of active PU’s
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Units",
        description = "Retrieve a list of active planning units"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of active planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of PU’s by Id’s
     *
     * @param planningUnitIdList
     * @param auth
     * @return
     */
    @PostMapping("/byIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Units",
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByIds(planningUnitIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of PU’s including Price list by Id’s
     *
     * @param planningUnitIdList
     * @param auth
     * @return
     */
    @PostMapping("/withPrices/byIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Units with Prices",
        description = "Retrieve a list of planning units with prices"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning unit IDs to retrieve",
        required = true,
        content = @Content(schema = @Schema(type = "array", implementation = String.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnitWithPrices.class))), responseCode = "200", description = "Returns a list of planning units with prices")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitWithPricesByIdList(@RequestBody List<String> planningUnitIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListWithPricesByIds(planningUnitIdList, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get List of PU's with only Basic information
     *
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/basic")
    @Operation(
        summary = "Get Planning Units (basic)",
        description = "Retrieve a list of planning units with basic information"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimplePlanningUnitForAdjustPlanningUnit.class))), responseCode = "200", description = "Returns a list of planning units with basic information")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitListBasic(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListBasic(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all PU’s
     *
     * @param auth
     * @return
     */
    @GetMapping("/all")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get all Planning Units",
        description = "Retrieve a list of all planning units (active and disabled)"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of all planning units (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active PU’s for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Units for Realm",
        description = "Retrieve a list of active planning units for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of active planning units for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get list of all PU’s for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}/all")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get all Planning Units for Realm",
        description = "Retrieve a list of all planning units (active and disabled) for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of all planning units (active and disabled) for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit list")
    public ResponseEntity getPlanningUnitForRealmAll(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get PU by Id
     *
     * @param planningUnitId
     * @param auth
     * @return
     */
    @GetMapping("/{planningUnitId}")
    @Operation(
        summary = "Get Planning Unit",
        description = "Retrieve a planning unit by its ID"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = PlanningUnit.class)), responseCode = "200", description = "Returns the planning unit with the specified ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitById(planningUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/{planningUnitId}/withPrograms")
    @JsonView(Views.InternalView.class)
    @Operation(
        summary = "Get Planning Unit with Programs",
        description = "Retrieve a planning unit with its associated supply planning and forecasting programs (active and disabled)"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the planning unit with its associated supply planning and forecasting programs (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitWithProgramsById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get list of PU’s filtered by FU
     *
     * @param forecastingUnitId
     * @param auth
     * @return
     */
    @GetMapping("/forecastingUnit/{forecastingUnitId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Unit by Forecasting Unit",
        description = "Retrieve a list of active planning units associated with a specific forecasting unit"
    )
    @Parameter(name = "forecastingUnitId", description = "The ID of the forecasting unit to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of active planning units associated with a specific forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitByForecastingUnitId(@PathVariable("forecastingUnitId") int forecastingUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByForecastingUnit(forecastingUnitId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get PUCapacity list for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping(value = "/capacity/realmId/{realmId}")
    @Operation(
        summary = "Get Planning Unit Capacity for Realm",
        description = "Retrieve a list of planning unit capacities for a specific realm"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnitCapacity.class))), responseCode = "200", description = "Returns a list of planning unit capacities for a specific realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning unit capacities found for the specified realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get PUCapacity list for a Realm filtered by Start and Stop date
     *
     * @param realmId
     * @param startDate
     * @param stopDate
     * @param auth
     * @return
     */
    @GetMapping(value = "/capacity/realmId/{realmId}/between/{startDate}/{stopDate}")
    @Operation(
        summary = "Get Planning Unit Capacity for Realm (Between Dates)",
        description = "Retrieve a list of planning unit capacities for a specific realm between two dates"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve planning units for", required = true)
    @Parameter(name = "startDate", description = "The start date of the range to retrieve planning unit capacities for", required = true)
    @Parameter(name = "stopDate", description = "The stop date of the range to retrieve planning unit capacities for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnitCapacity.class))), responseCode = "200", description = "Returns a list of planning unit capacities for a specific realm between two dates")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning unit capacities found for the specified realm between the specified dates")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get PUCapacity list for a specific Planning Unit
     *
     * @param planningUnitId
     * @param auth
     * @return
     */
    @GetMapping(value = "/capacity/{planningUnitId}")
    @Operation(
        summary = "Get Planning Unit Capacity",
        description = "Retrieve a planning unit capacity for a specific planning unit"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = PlanningUnitCapacity.class)), responseCode = "200", description = "Returns the planning unit capacity for a specific planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit capacity not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get PUCapacity list for a specific Planning Unit filtered by Start and
     * Stop date
     *
     * @param planningUnitId
     * @param startDate
     * @param stopDate
     * @param auth
     * @return
     */
    @GetMapping(value = "/capacity/{planningUnitId}/between/{startDate}/{stopDate}")
    @Operation(
        summary = "Get Planning Unit Capacity (Between Dates)",
        description = "Retrieve a planning unit capacity for a specific planning unit between two dates"
    )
    @Parameter(name = "planningUnitId", description = "The ID of the planning unit to retrieve", required = true)
    @Parameter(name = "startDate", description = "The start date of the range to retrieve planning unit capacities for", required = true)
    @Parameter(name = "stopDate", description = "The end date of the range to retrieve planning unit capacities for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = PlanningUnitCapacity.class)), responseCode = "200", description = "Returns the planning unit capacity for a specific planning unit between two dates")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this planning unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit capacity not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Invalid date format")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get full PUCapacity list
     *
     * @param auth
     * @return
     */
    @GetMapping(value = "/capacity/all")
    @Operation(
        summary = "Get Planning Unit Capacity List",
        description = "Retrieve a full list of all planning unit capacities"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnitCapacity.class))), responseCode = "200", description = "Returns a full list of all planning unit capacities")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning unit capacities found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit capacity")
    public ResponseEntity getPlanningUnitCapacityList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Add and Update Planning Unit Capacity list
     *
     * @param planningUnitCapacitys
     * @param auth
     * @return
     */
    @PutMapping(value = "/capacity")
    @Operation(
        summary = "Save Planning Unit Capacity",
        description = "Create or update a list of planning unit capacities"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning unit capacities to save",
        required = true,
        content = @Content(
            mediaType = "application/json", 
            array = @ArraySchema(schema = @Schema(implementation = PlanningUnitCapacity.class))
        )
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a successful response code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning unit capacities found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving planning unit capacity")
    public ResponseEntity savePlanningUnitCapacity(@RequestBody PlanningUnitCapacity[] planningUnitCapacitys, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @GetMapping("/productCategory/{productCategoryId}/all")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get all Planning Units for Product Category",
        description = "Retrieve a list of all planning units for a specific product category (active and disabled)"
    )
    @Parameter(name = "productCategoryId", description = "The ID of the product category to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = PlanningUnit.class))), responseCode = "200", description = "Returns a list of all planning units for a specific product category (active and disabled)")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForProductCategoryAll(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @GetMapping("/productCategory/{productCategoryId}/active")
    @JsonView(Views.ReportView.class)
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @PostMapping("/productCategoryList/active/realmCountryId/{realmCountryId}")
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    /**
     * Get list of all Planning Units attached to a Supply Plan for a
     * RealmCountry
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/realmCountry/{realmCountryId}")
    @Operation(
        summary = "Get Planning Units by Realm Country",
        description = "Retrieve a list of all planning units attached to a supply plan for a specific realm country"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns a list of planning units for a specific realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @GetMapping("/tracerCategory/{tracerCategoryId}")
    @Operation(
        summary = "Get Planning Units by Tracer Category",
        description = "Retrieve a list of active planning units for a specific tracer category"
    )
    @Parameter(name = "tracerCategoryId", description = "The ID of the tracer category to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns a list of active planning units for a specific tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found for the specified tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitForTracerCategory(@PathVariable(value = "tracerCategoryId", required = true) int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @PostMapping("/tracerCategorys")
    @Operation(
        summary = "Get Planning Units by Tracer Category List",
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @GetMapping("/withPricing/productCategory/{productCategoryId}")
    @Operation(
        summary = "Get Planning Units with Pricing for Product Category",
        description = "Retrieve a list of planning units with pricing for a specific product category"
    )
    @Parameter(name = "productCategoryId", description = "The ID of the product category to retrieve planning units with pricing for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimplePlanningUnitWithPrices.class))), responseCode = "200", description = "Returns a list of planning units with pricing for a specific product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to access this product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No planning units found with pricing for the specified product category")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning unit")
    public ResponseEntity getPlanningUnitWithPricingForProductCategory(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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

    @PostMapping("/tracerCategory/productCategory/forecastingUnit")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Planning Units by Tracer Category, Product Category and Forecasting Unit",
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
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
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
