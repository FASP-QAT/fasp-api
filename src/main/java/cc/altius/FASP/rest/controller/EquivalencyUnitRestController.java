/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.exception.CouldNotSaveException;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.EquivalencyUnit;
import cc.altius.FASP.model.EquivalencyUnitMapping;
import cc.altius.FASP.service.EquivalencyUnitService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/equivalencyUnit")
public class EquivalencyUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquivalencyUnitService equivalencyUnitService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the active EquivalencyUnit list. Will only return those
     * EquivalencyUnits that are marked Active.
     *
     * @param auth
     * @return returns the active list of active EquivalencyUnits
     */
    @GetMapping("")
    @Operation(description = "API used to get the complete EquivalencyUnit list. Will only return those EquivalencyUnits that are marked Active.", summary = "Get active EquivalencyUnit list", tags = ("equivalencyUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the EquivalencyUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of EquivalencyUnit list")
    public ResponseEntity getEquivalencyUnitList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  EquivalencyUnit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete EquivalencyUnit list.
     *
     * @param auth
     * @return returns the complete list of EquivalencyUnits
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete EquivalencyUnit list.", summary = "Get complete EquivalencyUnit list", tags = ("equivalencyUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the EquivalencyUnit list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of EquivalencyUnit list")
    public ResponseEntity getEquivalencyUnitListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  EquivalencyUnit list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add and update EquivalencyUnit
     *
     * @param equivalencyUnitList List<EquivalencyUnit> object that you want to
     * add or update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "")
    @Operation(description = "API used to add or update EquivalencyUnit", summary = "Add or Update EquivalencyUnit", tags = ("equivalencyUnit"))
    @Parameters(
            @Parameter(name = "equivalencyUnit", description = "The list of EquivalencyUnit objects that you want to add or update. If equivalencyUnitId is null or 0 then it is added if equivalencyUnitId is not null and non 0 it is updated"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to add/update this object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not acceptable")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addAndUpadteEquivalencyUnit(@RequestBody List<EquivalencyUnit> equivalencyUnitList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.addAndUpdateEquivalencyUnit(equivalencyUnitList, curUser) + " records updated", HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the active EquivalencyUnitMapping list. Will only return
     * those EquivalencyUnitMappings that are marked Active.
     *
     * @param auth
     * @return returns the active list of active EquivalencyUnitMappings
     */
    @GetMapping("/mapping")
    @Operation(description = "API used to get the complete EquivalencyUnitMapping list. Will only return those EquivalencyUnitMappings that are marked Active.", summary = "Get active EquivalencyUnitMapping list", tags = ("equivalencyUnitMapping, equivalencyUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the EquivalencyUnitMapping list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of EquivalencyUnitMapping list")
    public ResponseEntity getEquivalencyUnitMappingList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitMappingList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  EquivalencyUnitMapping list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete EquivalencyUnitMapping list.
     *
     * @param auth
     * @return returns the complete list of EquivalencyUnitMappings
     */
    @GetMapping("/mapping/all")
    @Operation(description = "API used to get the complete EquivalencyUnitMapping list.", summary = "Get complete EquivalencyUnitMapping list", tags = ("equivalencyUnitMapping, equivalencyUnit"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the EquivalencyUnitMapping list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of EquivalencyUnitMapping list")
    public ResponseEntity getEquivalencyUnitMappingListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitMappingList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  EquivalencyUnitMapping list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add and update EquivalencyUnitMapping
     *
     * @param equivalencyUnitMappingList List<EquivalencyUnitMapping> object
     * that you want to add or update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/mapping")
    @Operation(description = "API used to add or update EquivalencyUnitMapping", summary = "Add or Update EquivalencyUnitMapping", tags = ("equivalencyUnitMapping, equivalencyUnit"))
    @Parameters(
            @Parameter(name = "equivalencyUnitMappingList", description = "The list of EquivalencyUnitMapping objects that you want to add or update. If equivalencyUnitMappingId is null or 0 then it is added if equivalencyUnitMappingId is not null and non 0 it is updated"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to add/update this object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not acceptable")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addAndUpadteEquivalencyUnitMapping(@RequestBody List<EquivalencyUnitMapping> equivalencyUnitMappingList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.addAndUpdateEquivalencyUnitMapping(equivalencyUnitMappingList, curUser) + " records updated", HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (CouldNotSaveException | DuplicateKeyException | IllegalAccessException e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the list of Equivalency Units Mapping based on a Program
     * and a Forecasting Unit. If you have the PU Id convert it to FU Id first
     * and then call this API. If there are two EU for the same FU the one that
     * matches the Program Id will be returned
     *
     * @param forecastingUnitId The Forecasting Unit Id that you want the list
     * of EUM for
     * @param programId The Program Id that you want the priority for
     * @param auth
     * @return List of EUM
     */
    @GetMapping(value = "/forecastingUnitId/{forecastingUnitId}/programId/{programId}")
    @Operation(description = "API used to get the list of Equivalency Units Mapping based on a Program and a Forecasting Unit", summary = "Get list of EquivalencyUnitMapping", tags = ("equivalencyUnitMapping, equivalencyUnit"))
    @Parameters(
            {
                @Parameter(name = "forecastingUnitId", description = "The Forecasting Unit Id that you want the list of EUM for"),
                @Parameter(name = "programId", description = "The Program Id that you want the priority for")
            }
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the EquivalencyUnitMapping list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to the Program that you requested")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of EquivalencyUnitMapping list")
    public ResponseEntity getEquivalencyUnitMappingForForecastingUnit(@PathVariable("forecastingUnitId") int forecastingUnitId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.equivalencyUnitService.getEquivalencyUnitMappingForForecastingUnit(forecastingUnitId, programId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add EquivalencyUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
