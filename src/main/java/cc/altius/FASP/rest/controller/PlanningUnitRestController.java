/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.ProgramAndTracerCategoryDTO;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.PlanningUnitCapacity;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.PlanningUnitService;
import cc.altius.FASP.service.ProcurementAgentService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import com.fasterxml.jackson.annotation.JsonView;
import java.text.ParseException;
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
@RequestMapping("/api/planningUnit")
public class PlanningUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlanningUnitService planningUnitService;
    @Autowired
    private ProcurementAgentService procurementAgentService;
    @Autowired
    private UserService userService;

    /**
     * API used to add a PlanningUnit.
     *
     * @param planningUnit PlanningUnit object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(path = "/")
    @Operation(description = "API used to add a PlanningUnit", summary = "Add PlanningUnit", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "planningUnit", description = "The PlanningUnit object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity postPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.planningUnitService.addPlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a PlanningUnit
     *
     * @param planningUnit PlanningUnit object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a PlanningUnit", summary = "Update PlanningUnit", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "planningUnit", description = "The PlanningUnit object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity putPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.planningUnitService.updatePlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the list of active PlanningUnits.
     *
     * @param auth
     * @return returns the list of active PlanningUnits
     */
    @GetMapping("/")
    @Operation(description = "API used to get the list of active PlanningUnits.", summary = "Get active PlanningUnit list", tags = ("planningUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the active PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of active PlanningUnit list")
    public ResponseEntity getPlanningUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete PlanningUnit list.
     *
     * @param auth
     * @return returns the complete list of PlanningUnit
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete PlanningUnit list.", summary = "Get PlanningUnit list", tags = ("planningUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")

    public ResponseEntity getPlanningUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the list of active PlanningUnits for a Realm
     *
     * @param realmId RealmId that you want the PlanningUnit List from
     * @param auth
     * @return returns the list of active PlanningUnits
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the list of active PlanningUnits for a Realm", summary = "Get active PlanningUnit list for Realm", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the PlanningUnit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")
    public ResponseEntity getPlanningUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(realmId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the PlanningUnit list for a Realm
     *
     * @param realmId RealmId that you want the PlanningUnit List from
     * @param auth
     * @return returns the complete list of PlanningUnit
     */
    @GetMapping("/realmId/{realmId}/all")
    @Operation(description = "API used to get the complete PlanningUnit list for a Realm", summary = "Get PlanningUnit list for Realm", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the PlanningUnit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")
    public ResponseEntity getPlanningUnitForRealmAll(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the PlanningUnit for a specific PlanningUnitId
     *
     * @param planningUnitId PlanningUnit that you want the PlanningUnit Object
     * for
     * @param auth
     * @return returns the PlanningUnit object based on PlanningUnitId specified
     */
    @GetMapping("/{planningUnitId}")
    @Operation(description = "API used to get the PlanningUnit for a specific PlanningUnitId", summary = "Get PlanningUnit for a PlanningUnitId", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "planningUnitId", description = "PlanningUnitId that you want to the PlanningUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the HealthAreaId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit")
    public ResponseEntity getPlanningUnitById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitById(planningUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get PlanningUnitCapacity list for a Realm
     *
     * @param realmId realmId that you want a PlanningUnitCapacity list from
     * @param auth
     * @return returns list of PlanningUnitCapacity for a Realm
     */
    @GetMapping(value = "/capacity/realmId/{realmId}")
    @Operation(description = "API used to get PlanningUnitCapacity for a Realm", summary = "Get PlanningUnitCapacity for a Realm", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want a PlanningUnitCapacity from"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnitCapacity list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitCapacity list does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnitCapacity list")
    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForRealm(realmId, null, null, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get PlanningUnitCapacity list for a Realm and a given date
     * range
     *
     * @param realmId realmId that you want a PlanningUnitCapacity from
     * @param startDate startDate the date you want the PlanningUnitCapacity
     * data from
     * @param stopDate stopDate the date you want the PlanningUnitCapacity data
     * till
     * @param auth
     * @return returns list of PlanningUnitCapacity for a Realm and a given date
     * range
     */
    @GetMapping(value = "/capacity/realmId/{realmId}/between/{startDate}/{stopDate}")
    @Operation(description = "API used to get PlanningUnitCapacity for a Realm and a given date range", summary = "Get PlanningUnitCapacity for a Realm and a given date range", tags = ("planningUnit"))
    @Parameters({
        @Parameter(name = "realmId", description = "realmId that you want a PlanningUnitCapacity from"),
        @Parameter(name = "startDate", description = "startDate the date you want the PlanningUnitCapacity data from"),
        @Parameter(name = "stopDate", description = "stopDate the date you want the PlanningUnitCapacity data till")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnitCapacity list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to get the list does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitCapacity list does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnitCapacity list")

    public ResponseEntity getPlanningUnitCapacityForRealmId(@PathVariable("realmId") int realmId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForRealm(realmId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing planningUnitCapacity", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get PlanningUnitCapacity list for a PlanningUnitId
     *
     * @param planningUnitId planningUnitId that you want a PlanningUnitCapacity
     * list for
     * @param auth
     * @return returns list of PlanningUnitCapacity list for a PlanningUnitId
     */
    @GetMapping(value = "/capacity/{planningUnitId}")
    @Operation(description = "API used to get PlanningUnitCapacity list for a PlanningUnitId", summary = "Get PlanningUnitCapacity list for a PlanningUnitId", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "planningUnitId", description = "planningUnitId that you want a PlanningUnitCapacity list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnitCapacity list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to get the list does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnitCapacity list")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForId(planningUnitId, null, null, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get PlanningUnitCapacity list for a PlanningUnitId and a
     * given date range
     *
     * @param planningUnitId planningUnitId that you want a PlanningUnitCapacity
     * list for
     * @param startDate startDate the date you want the PlanningUnitCapacity
     * data from
     * @param stopDate stopDate the date you want the PlanningUnitCapacity data
     * till
     * @param auth
     * @return returns list of PlanningUnitCapacity list for a PlanningUnitId
     * and a given date range
     */
    @GetMapping(value = "/capacity/{planningUnitId}/between/{startDate}/{stopDate}")
    @Operation(description = "API used to get PlanningUnitCapacity list for a PlanningUnitId and a given date range", summary = "Get PlanningUnitCapacity list for a PlanningUnitId and a given date range", tags = ("planningUnit"))
    @Parameters({
        @Parameter(name = "planningUnitId", description = "planningUnitId that you want a PlanningUnitCapacity list for"),
        @Parameter(name = "startDate", description = "startDate the date you want the PlanningUnitCapacity data from"),
        @Parameter(name = "stopDate", description = "stopDate the date you want the PlanningUnitCapacity data till")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnitCapacity list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to get the list does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitCapacity list does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnitCapacity list")
    public ResponseEntity getPlanningUnitCapacityForId(@PathVariable("planningUnitId") int planningUnitId, @PathVariable("startDate") String startDate, @PathVariable("stopDate") String stopDate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityForId(planningUnitId, startDate, stopDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing planningUnitCapacity", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get a complete PlanningUnitCapacity list
     *
     * @param auth
     * @return returns a complete list of PlanningUnitCapacity
     */
    @GetMapping(value = "/capacity/all")
    @Operation(description = "API used to get a complete PlanningUnitCapacity list", summary = "Get a complete PlanningUnitCapacity list", tags = ("planningUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnitCapacity list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitCapacity list does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnitCapacity list")
    public ResponseEntity getPlanningUnitCapacityList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitCapacityList(curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while listing planningUnitCapacity", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while listing planningUnitCapacity", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update PlanningUnitCapacity
     *
     * @param planningUnitCapacitys planningUnitCapacity objects that you want
     * to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(value = "/capacity")
    @Operation(description = "API used to update PlanningUnitCapacity", summary = "Update PlanningUnitCapacity", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "planningUnitCapacitys", description = "planningUnitCapacity objects that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to update the date does not met")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the user does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the PlanningUnitCapacity does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
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

    /**
     * API used to get list of planningUnits which are modified after given date
     *
     * @param lastSyncDate lastSyncDate that you want a planningUnits list
     * modified after the date
     * @param auth
     * @return returns a complete list of PlanningUnits modified after given
     * date
     */
//    @GetMapping(value = "/sync/planningUnit/{lastSyncDate}")
//    @Operation(description = "API used to get list of planningUnits which are modified after given date", summary = "Get list of planningUnits which are modified after given date", tags = ("planningUnit"))
//    @Parameters(
//            @Parameter(name = "lastSyncDate", description = "lastSyncDate that you want a planningUnits list modified after the date"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "412", description = "Returns a HttpStatus.PRECONDITION_FAILED if certain conditions to update the data does not met")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")
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
    /**
     * API used to get the PlanningUnit list for a ProductCategory
     *
     * @param productCategoryId productCategoryId that you want the PlanningUnit
     * List for
     * @param auth
     * @return returns the complete list of PlanningUnit
     */
    @GetMapping("/productCategory/{productCategoryId}/all")
    @Operation(description = "API used to get the complete PlanningUnit list for a ProductCategory", summary = "Get PlanningUnit list for ProductCategory", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "productCategoryId", description = "productCategoryId that you want the PlanningUnit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the productCategoryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")

    public ResponseEntity getPlanningUnitForproductCategoryAll(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategory(productCategoryId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the list of active PlanningUnits for a ProductCategory
     *
     * @param productCategoryId productCategoryId that you want the PlanningUnit
     * List for
     * @param auth
     * @return returns the list of active PlanningUnits
     */
    @GetMapping("/productCategory/{productCategoryId}/active")
    @Operation(description = "API used to get the list of active PlanningUnits for a ProductCategory", summary = "Get active PlanningUnit list for ProductCategory", tags = ("planningUnit"))
    @Parameters(
            @Parameter(name = "productCategoryId", description = "productCategoryId that you want the PlanningUnit list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the active PlanningUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the productCategoryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of PlanningUnit list")
    public ResponseEntity getPlanningUnitForproductCategory(@PathVariable(value = "productCategoryId", required = true) int productCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategory(productCategoryId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/planningUnit/productCategoryList/active")
    @JsonView(Views.ReportView.class)
    public ResponseEntity getPlanningUnitForproductCategoryList(@RequestBody String[] productCategoryIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForProductCategoryList(productCategoryIds, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //TBD
    @GetMapping("/getPlanningUnitByTracerCategory/planningUnitId/{planningUnitId}/{procurementAgentId}/{term}")
    public ResponseEntity getPlanningUnitByTracerCategory(@PathVariable("planningUnitId") int planningUnitId, @PathVariable("procurementAgentId") int procurementAgentId, @PathVariable("term") String term, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.procurementAgentService.getProcurementAgentPlanningUnitListForTracerCategory(procurementAgentId, planningUnitId, term, curUser), HttpStatus.OK);
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

    @JsonView(Views.ReportView.class)
    @PostMapping("/planningUnit/tracerCategory/program/")
    public ResponseEntity getPlanningUnitByTracerCategoryAndProgram(@RequestBody ProgramAndTracerCategoryDTO programAndTracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitByProgramAndTracerCategory(programAndTracerCategory, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list PlanningUnit list for ProgramAndTracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit list for ProgramAndTracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit list for ProgramAndTracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planningUnit/realmCountry/{realmCountryId}")
    public ResponseEntity getPlanningUnitByRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListByRealmCountryId(realmCountryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get Planning Unit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
