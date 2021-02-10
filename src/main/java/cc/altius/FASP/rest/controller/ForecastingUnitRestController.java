/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ForecastingUnit;
import cc.altius.FASP.model.ResponseCode;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/forecastingUnit")
public class ForecastingUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ForecastingUnitService forecastingUnitService;
    @Autowired
    private UserService userService;

    
    /**
     * API used to get the complete ForecastingUnit list. Will only return those
     * ForecastingUnits that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active ForecastingUnits
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete ForecastingUnit list. Will only return those ForecastingUnits that are marked Active.", summary = "Get active ForecastingUnit list", tags = ("forecastingUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastingUnit list")
    public ResponseEntity getForecastingUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete ForecastingUnit list. 
     *
     * @param auth
     * @return returns the complete list of ForecastingUnits
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete ForecastingUnit list.", summary = "Get ForecastingUnit list", tags = ("forecastingUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastingUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastingUnit list")
    public ResponseEntity getForecastingUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(false, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the ForecastingUnit for a specific ForecastingUnitId
     *
     * @param forecastingUnitId ForecastingUnitId that you want the ForecastingUnit Object for
     * @param auth
     * @return returns the ForecastingUnit object based on ForecastingUnitId specified
     */
    @GetMapping(value = "/{forecastingUnitId}")
    @Operation(description = "API used to get the ForecastingUnit for a specific ForecastingUnitId", summary = "Get ForecastingUnit for a ForecastingUnitId", tags = ("forecastingUnit"))
    @Parameters(
            @Parameter(name = "forecastingUnitId", description = "ForecastingUnitId that you want to the ForecastingUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastingUnit")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the ForecastingUnitId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastingUnit")
    public ResponseEntity getForecastingUnitById(@PathVariable("forecastingUnitId") int forecastingUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitById(forecastingUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list ForecastingUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * API used to get the ForecastingUnit for a specific RealmId
     *
     * @param realmId RealmId that you want the ForecastingUnit List for
     * @param auth
     * @return returns the list the ForecastingUnit based on RealmId specified
     */
    @GetMapping(value = "realmId/{realmId}")
    @Operation(description = "API used to get all the ForecastingUnits for a specific RealmId", summary = "Get ForecastingUnit for a RealmId", tags = ("forecastingUnit"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the ForecastingUnit for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastingUnits list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastingUnit")
    public ResponseEntity getForecastingUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingUnitService.getForecastingUnitList(realmId, true, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to list ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

    /**
     * API used to add a ForecastingUnit
     *
     * @param forecastingUnit ForecastingUnit object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a ForecastingUnit", summary = "Add ForecastingUnit", tags = ("forecastingUnit"))
    @Parameters(
            @Parameter(name = "forecastingUnit", description = "The ForecastingUnit object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addForecastingUnit(@RequestBody ForecastingUnit forecastingUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.forecastingUnitService.addForecastingUnit(forecastingUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a ForecastingUnit
     *
     * @param forecastingUnit ForecastingUnit object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a ForecastingUnit", summary = "Update ForecastingUnit", tags = ("forecastingUnit"))
    @Parameters(
            @Parameter(name = "forecastingUnit", description = "The ForecastingUnit object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateForecastingUnit(@RequestBody ForecastingUnit forecastingUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.forecastingUnitService.updateForecastingUnit(forecastingUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update ForecastingUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to update ForecastingUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
