/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.AccessControlFailedException;
import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.framework.GlobalConstants;
import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.RealmCountryPlanningUnit;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.RealmCountryHealthArea;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.RealmCountryService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.Serializable;
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
@Tag(
    name = "Realm Country",
    description = "Manage realm country assignments and their associated planning units"
)
@RequestMapping("/api/realmCountry")
public class RealmCountryRestController extends BaseModel implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RealmCountryService realmCountryService;
    @Autowired
    private UserService userService;

    /**
     * Add RealmCountry
     *
     * @param realmCountryList
     * @param auth
     * @return
     */
    @PostMapping(path = "")
    @Operation(
        summary = "Add Realm Country",
        description = "Add a list of realm countries"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of realm countries to add",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountry.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to add realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding realm countries")
    public ResponseEntity postRealmCountry(@RequestBody List<RealmCountry> realmCountryList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.realmCountryService.addRealmCountry(realmCountryList, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update Region", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add RealmCountry", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add RealmCountry", d);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to add RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Update RealmCountry
     *
     * @param realmCountryList
     * @param auth
     * @return
     */
    @PutMapping(path = "")
    @Operation(
        summary = "Update Realm Country",
        description = "Update a list of realm countries"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of realm countries to update",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountry.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to update realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating realm countries")
    public ResponseEntity putRealmCountry(@RequestBody List<RealmCountry> realmCountryList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.realmCountryService.updateRealmCountry(realmCountryList, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException d) {
            logger.error("Error while trying to add RealmCountry", d);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update RealmCountry", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to update RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of RealmCountries
     *
     * @param auth
     * @return
     */
    @GetMapping("")
    @Operation(
        summary = "Get Realm Countries",
        description = "Retrieve a list of realm countries"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountry.class))), responseCode = "200", description = "Returns the list of realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while listing realm countries")
    public ResponseEntity getRealmCountry(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryList(curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get RealmCountry by Id
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/{realmCountryId}")
    @Operation(
        summary = "Get Realm Country",
        description = "Retrieve a realm country by its ID"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = RealmCountry.class)), responseCode = "200", description = "Returns the realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to get a realm country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Realm country not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting a realm country")
    public ResponseEntity getRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryById(realmCountryId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get list of RealmCountries for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(
        summary = "Get Realm Country by Realm",
        description = "Retrieve a list of realm countries by their realm ID"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve realm countries for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountry.class))), responseCode = "200", description = "Returns the list of realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list realm countries by realm ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Realm country not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting a realm country by realm ID")
    public ResponseEntity getRealmCountryByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryListByRealmId(realmId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of active ARU’s for a RealmCountry
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/{realmCountryId}/planningUnit")
    @Operation(
        summary = "Get Planning Units for Country",
        description = "Retrieve a list of planning units (administrative reporting units) for a given realm country"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryPlanningUnit.class))), responseCode = "200", description = "Returns the list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list planning units for a country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning units for a country")
    public ResponseEntity getPlanningUnitForCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getPlanningUnitListForRealmCountryId(realmCountryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of ARU’s filtered by a list of ProgramIds
     *
     * @param programIds
     * @param auth
     * @return
     */
    @PostMapping("/programIds/planningUnit")
    @Operation(
        summary = "Get Planning Units for Program List",
        description = "Retrieve a list of planning units (administrative reporting units) for a given list of program IDs"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of program IDs to retrieve planning units for",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryPlanningUnit.class))), responseCode = "200", description = "Returns the list of planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list planning units for a program list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting planning units for a program list")
    public ResponseEntity getPlanningUnitForProgramList(@RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryPlanningUnitListForProgramList(programIds, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of all ARU’s for a RealmCountry
     *
     * @param realmCountryId
     * @param auth
     * @return
     */
    @GetMapping("/{realmCountryId}/planningUnit/all")
    @Operation(
        summary = "Get all Planning Units for Country",
        description = "Retrieve a list of all planning units (administrative reporting units) for a given realm country"
    )
    @Parameter(name = "realmCountryId", description = "The ID of the realm country to retrieve all planning units for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryPlanningUnit.class))), responseCode = "200", description = "Returns the list of all planning units")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list all planning units for a country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Planning unit not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting all planning units for a country")
    public ResponseEntity getPlanningUnitForCountryAll(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getPlanningUnitListForRealmCountryId(realmCountryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Add or Update ARU
     *
     * @param realmCountryPlanningUnits
     * @param auth
     * @return
     */
    @PutMapping("/planningUnit")
    @Operation(
        summary = "Save Planning Unit for Country",
        description = "Save a list of planning units (administrative reporting units) for a given realm country"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The list of planning units to save",
        required = true,
        content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryPlanningUnit.class)))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "200", description = "Returns a success response code")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to save planning units for a country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "406", description = "Another planning unit with the same key exists")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "Unable to save planning units for a country")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while saving planning units for a country")
    public ResponseEntity savePlanningUnitForCountry(@RequestBody RealmCountryPlanningUnit[] realmCountryPlanningUnits, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int rowsEffected = this.realmCountryService.savePlanningUnitForCountry(realmCountryPlanningUnits, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessControlFailedException e) {
            logger.error("Error while trying to update PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.CONFLICT);
        } catch (CouldNotSaveException cnse) {
            logger.error("Error while trying to update PlanningUnit for Country", cnse);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.PRECONDITION_FAILED); // 412
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to update PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (DuplicateKeyException de) {
            logger.error("Error while trying to update PlanningUnit for Country", de);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE); // 406
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit for Country", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * Get list of RealmCountry with HealthArea mapping for a Realm
     *
     * @param realmId
     * @param auth
     * @return
     */
    @GetMapping("/program/realmId/{realmId}")
    @Operation(
        summary = "Get Realm Countries for Active Programs by Realm ID",
        description = "Retrieve a list of realm countries for active programs by their realm ID"
    )
    @Parameter(name = "realmId", description = "The ID of the realm to retrieve realm countries for active programs for", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryHealthArea.class))), responseCode = "200", description = "Returns the list of realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list realm countries for active programs by realm ID")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Realm country not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting realm countries for active programs by realm ID")
    public ResponseEntity getRealmCountryByRealmIdForActivePrograms(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.realmCountryService.getRealmCountryListByRealmIdForActivePrograms(realmId, GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("/program")
    @Operation(
        summary = "Get Realm Countries for Active Programs",
        description = "Retrieve a list of realm countries for active programs"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = RealmCountryHealthArea.class))), responseCode = "200", description = "Returns the list of realm countries")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "User does not have rights to list realm countries for active programs")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "404", description = "Realm country not found")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "412", description = "A User with access to multiple Realms tried to access a RealmCountry Program list without specifying a Realm")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while getting realm countries for active programs")
    public ResponseEntity getRealmCountryForActivePrograms(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            if (curUser.getRealm().getRealmId() == -1) {
                logger.error("A User with access to multiple Realms tried to access a RealmCountry Program list without specifying a Realm");
                return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED); // 412
            }
            return new ResponseEntity(this.realmCountryService.getRealmCountryListByRealmIdForActivePrograms(curUser.getRealm().getRealmId(), GlobalConstants.PROGRAM_TYPE_SUPPLY_PLAN, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND); // 404
        } catch (Exception e) {
            logger.error("Error while trying to list RealmCountry", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

}
