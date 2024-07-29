/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.MasterDataService;

/**
 *
 * @author Akil Mahimwala
 */
@RestController
@RequestMapping("/api")
public class MasterDataRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MasterDataService masterDataService;
    @Autowired
    private UserService userService;

    @GetMapping("/versionType")
    public ResponseEntity getVersionType(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.masterDataService.getVersionTypeList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Type", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/versionStatus")
    public ResponseEntity getVersionStatus(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.masterDataService.getVersionStatusList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Version Status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/shipmentStatus")
    public ResponseEntity getShipmentStatusListActive(Authentication auth) {
        try {
            return new ResponseEntity(this.masterDataService.getShipmentStatusList(true), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while listing Shipment status", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete UsageType list. Will only return those
     * UsageTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active UsageTypes
     */
    @GetMapping("/usageType")
    @Operation(description = "API used to get the complete UsageType list. Will only return those UsageTypes that are marked Active.", summary = "Get active UsageType list", tags = ("usageType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageType list")
    public ResponseEntity getUsageTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.masterDataService.getUsageTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  UsageType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete NodeType list. Will only return those
     * NodeTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active NodeTypes
     */
    @GetMapping("/nodeType")
    @Operation(description = "API used to get the complete NodeType list. Will only return those NodeTypes that are marked Active.", summary = "Get active NodeType list", tags = ("nodeType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the NodeType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of NodeType list")
    public ResponseEntity getNodeTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.masterDataService.getNodeTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  NodeType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete ForecastMethodType list. Will only return
     * those NodeTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active ForecastMethodTypes
     */
    @GetMapping("/forecastMethodType")
    @Operation(description = "API used to get the complete ForecastMethodType list. Will only return those ForecastMethodTypes that are marked Active.", summary = "Get active ForecastMethodType list", tags = ("forecastMethodType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastMethodType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastMethodType list")
    public ResponseEntity getForecastMethodTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.masterDataService.getForecastMethodTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  ForecastMethodType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
