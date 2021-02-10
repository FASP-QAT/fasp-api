/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.DimensionService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api/dimension")
public class DimensionRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DimensionService dimensionService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Dimension list. Will only return those
     * Dimensions that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active Dimensions
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete Dimension list. Will only return those Dimensions that are marked Active.", summary = "Get active Dimension list", tags = ("dimension"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Dimension list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Dimension list")
    public ResponseEntity getDimension(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(false), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete Dimension list. Will only return those
     * Dimensions that are marked Active.
     *
     * @param auth
     * @return returns the complete list of Dimensions
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete Dimension list.", summary = "Get Dimension list", tags = ("dimension"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Dimension list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Dimension list")
    public ResponseEntity getDimensionAll(Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionList(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Dimension for a specific DimensionId
     *
     * @param dimensionId DimensionId that you want the Dimension Object for
     * @param auth
     * @return returns the list the Dimension object based on DimensionId specified
     */
    @GetMapping(value = "/{dimensionId}")
    @Operation(description = "API used to get the Dimension for a specific DimensionId", summary = "Get Dimension for a DimensionId", tags = ("dimension"))
    @Parameters(
            @Parameter(name = "dimensionId", description = "DimensionId that you want to the Dimension for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Dimension")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the DimensionId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Dimension")
    public ResponseEntity getDimension(@PathVariable("dimensionId") int dimensionId, Authentication auth) {
        try {
            return new ResponseEntity(this.dimensionService.getDimensionById(dimensionId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a Dimension
     *
     * @param dimension Dimension object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a Dimension", summary = "Add Dimension", tags = ("dimension"))
    @Parameters(
            @Parameter(name = "dimension", description = "The Dimension object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Dimension supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dimensionService.addDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Dimension", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Dimension
     *
     * @param dimension Dimension object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a Dimension", summary = "Update Dimension", tags = ("dimension"))
    @Parameters(
            @Parameter(name = "dimension", description = "The Dimension object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the Dimension supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateDimension(@RequestBody Dimension dimension, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.dimensionService.updateDimension(dimension, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_FOUND);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Dimension ", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Dimension", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
