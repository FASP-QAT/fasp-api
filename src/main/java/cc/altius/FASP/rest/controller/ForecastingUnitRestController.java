/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.DuplicateNameException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProductCategoryAndTracerCategoryDTO;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.ForecastingUnitWithCount;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleObject;
import cc.altius.FASP.model.Views;
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
import cc.altius.FASP.service.ForecastingUnitService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/forecastingUnit")
@Tag(
    name = "Forecasting Unit",
    description = "Manages forecasting units with realm-based access control."
)
public class ForecastingUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ForecastingUnitService forecastingUnitService;
    @Autowired
    private UserService userService;

    /**
     * Add a FU
     *
     * @param forecastingUnit
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Forecasting Unit",
        description = "Add a new forecasting unit."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The forecasting unit to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForecastingUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Forecasting unit with the same name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to add forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding forecasting unit")
    public ResponseEntity postForecastingUnit(@RequestBody ForecastingUnit forecastingUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.forecastingUnitService.addForecastingUnit(forecastingUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK); // 200
        } catch (DuplicateNameException de) {
            logger.error("Error while trying to add ForecastingUnit", de);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update a FU
     *
     * @param forecastingUnit
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Forecasting Unit",
        description = "Update an existing forecasting unit."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The forecasting unit to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForecastingUnit.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success message")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Forecasting unit with the same name already exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to update forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating forecasting unit")
    public ResponseEntity putForecastingUnit(@RequestBody ForecastingUnit forecastingUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.forecastingUnitService.updateForecastingUnit(forecastingUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK); // 200
        } catch (DuplicateNameException de) {
            logger.error("Error while trying to add ForecastingUnit", de);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active FU’s
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Active Forecasting Units",
        description = "Retrieve a list of active forecasting units."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(true, curUser), HttpStatus.OK); // 200
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to list ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of FU’s filtered by Id’s
     *
     * @param forecastingUnitIdList
     * @param auth
     * @return
     */
    @PostMapping("/byIds")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units",
        description = "Retrieve a list of forecasting units given a list of IDs."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of forecasting unit IDs",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitByIdList(@RequestBody List<String> forecastingUnitIdList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListByIds(forecastingUnitIdList, curUser), HttpStatus.OK); // 200
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all FU’s
     *
     * @param auth
     * @return
     */
    @GetMapping("/all")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get All Forecasting Units",
        description = "Retrieve a list of all forecasting units (active and disabled)."
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(false, curUser), HttpStatus.OK); // 200
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all FU’s for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units for Realm",
        description = "Retrieve a list of active forecasting units for a given realm, identified by its ID."
    )
    @Parameter(name = "realmId", description = "The ID of the realm")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No forecasting units found for the given realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(realmId, true, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get FU by Id
     *
     * @param forecastingUnitId
     * @param auth
     * @return
     */
    @GetMapping("/{forecastingUnitId}")
    @Operation(
        summary = "Get Forecasting Unit",
        description = "Retrieve a forecasting unit by its ID."
    )
    @Parameter(name = "forecastingUnitId", description = "The ID of the forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ForecastingUnit.class)), responseCode = "200", description = "Returns the ForecastingUnit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have permission to get forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Forecasting unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting forecasting unit")
    public ResponseEntity getForecastingUnitById(@PathVariable("forecastingUnitId") int forecastingUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitById(forecastingUnitId, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list ForecastingUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get ForecastingUnitId with information on SP and FC programs
     *
     * @param forecastingUnitId
     * @param auth
     * @return
     */
    @GetMapping("/{forecastingUnitId}/withPrograms")
    @JsonView(Views.InternalView.class)
    @Operation(
        summary = "Get Forecasting Units with Programs",
        description = "Retrieve a forecasting unit and its associated supply plan and forecasting programs (active and disabled)."
    )
    @Parameter(name = "forecastingUnitId", description = "The ID of the forecasting unit")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = Map.class)), responseCode = "200", description = "Returns the ForecastingUnit with programs")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have permission to get forecasting unit with programs")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Forecasting unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while getting forecasting unit with programs")
    public ResponseEntity getForecastingUnitWithProgramsById(@PathVariable("forecastingUnitId") int forecastingUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            Map<String, Object> data = new HashMap<>();
            data.put("forecastingUnit", this.forecastingUnitService.getForecastingUnitById(forecastingUnitId, curUser));
            data.put("spProgramListActive", this.forecastingUnitService.getListOfSpProgramsForForecastingUnitId(forecastingUnitId, true, curUser));
            data.put("spProgramListDisabled", this.forecastingUnitService.getListOfSpProgramsForForecastingUnitId(forecastingUnitId, false, curUser));
            data.put("fcProgramListActive", this.forecastingUnitService.getListOfFcProgramsForForecastingUnitId(forecastingUnitId, true, curUser));
            data.put("fcProgramListDisabled", this.forecastingUnitService.getListOfFcProgramsForForecastingUnitId(forecastingUnitId, false, curUser));
            return new ResponseEntity(data, HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list ForecastingUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get FU by TracerCategory
     *
     * @param forecastingUnitId
     * @param auth
     * @return
     */
    @GetMapping("/tracerCategory/{tracerCategoryId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units for Tracer Category",
        description = "Retrieve a list of active forecasting units for a given tracer category, identified by its ID."
    )
    @Parameter(name = "tracerCategoryId", description = "The ID of the tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "No forecasting units found for the given tracer category")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitForTracerCategory(@PathVariable(value = "tracerCategoryId", required = true) int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListByTracerCategory(tracerCategoryId, true, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get FU filtered by TracerCategory list
     *
     * @param tracerCategoryIds
     * @param auth
     * @return
     */
    @PostMapping("/tracerCategorys")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units for Tracer Category List",
        description = "Retrieve a list of active forecasting units for a given list of tracer category IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of tracer category IDs",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Integer.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnit.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No forecasting units found for the given tracer category list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitForTracerCategory(@RequestBody String[] tracerCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListByTracerCategoryIds(tracerCategoryIds, true, curUser), HttpStatus.OK); // 200
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of FU’s for the Dataset by ProgramId and VersionId
     *
     * @param programId
     * @param versionId
     * @param auth
     * @return
     */
    @GetMapping(value = "/programId/{programId}/versionId/{versionId}")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units for Dataset",
        description = "Retrieve a list of active forecasting units for a given dataset, identified by its program ID and version ID."
    )
    @Parameter(name = "programId", description = "The ID of the program")
    @Parameter(name = "versionId", description = "The ID of the version")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleObject.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "No forecasting units found for the given dataset")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitForDataset(@PathVariable("programId") int programId, @PathVariable("versionId") int versionId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitListForDataset(programId, versionId, curUser), HttpStatus.OK); // 200
        } catch (AccessControlFailedException er) {
            logger.error("Error while trying to list ForecastingUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.CONFLICT);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list ForecastingUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of FU’s filtered by ProductCategory and TracerCategory
     *
     * @param input
     * @param auth
     * @return
     */
    @PostMapping("/tracerCategory/productCategory")
    @JsonView(Views.ReportView.class)
    @Operation(
        summary = "Get Forecasting Units by Tracer Category and Product Category",
        description = "Retrieve a list of active forecasting units for a given tracer category and product category."
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The input object containing tracer category and product category",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryAndTracerCategoryDTO.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = ForecastingUnitWithCount.class))), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have permission to list forecasting units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing forecasting units")
    public ResponseEntity getForecastingUnitByTracerCategoryAndProductCategory(@RequestBody ProductCategoryAndTracerCategoryDTO input, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitByTracerCategoryAndProductCategory(input, curUser), HttpStatus.OK); // 200
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
