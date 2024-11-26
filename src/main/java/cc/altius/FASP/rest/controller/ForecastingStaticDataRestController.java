/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.SimpleBaseModel;
import cc.altius.FASP.model.NodeType;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cc.altius.FASP.service.ForecastingStaticDataService;

/**
 *
 * @author Akil Mahimwala
 */
@RestController
@RequestMapping("/api")
@Tag(
    name = "Forecasting Static Data",
    description = "Manage static lookup data for forecasting system configuration"
)
public class ForecastingStaticDataRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ForecastingStaticDataService forecastingStaticDataService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete UsageType list. Will only return those
     * UsageTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active UsageTypes
     */
    @GetMapping("/usageType")
    @Operation(
        summary = "Get Active Usage Type List",
        description = "Retrieve a list of active usage types"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleBaseModel.class))), responseCode = "200", description = "Returns the UsageType list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of UsageType list")
    public ResponseEntity getUsageTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingStaticDataService.getUsageTypeList(true, curUser), HttpStatus.OK);
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
    @Operation(
        summary = "Get Active Node Type List",
        description = "Retrieve a list of active node types"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = NodeType.class))), responseCode = "200", description = "Returns the NodeType list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of NodeType list")
    public ResponseEntity getNodeTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingStaticDataService.getNodeTypeList(true, curUser), HttpStatus.OK);
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
    @Operation(
        summary = "Get Active Forecast Method Type List",
        description = "Retrieve a list of active forecast method types"
    )
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = SimpleBaseModel.class))), responseCode = "200", description = "Returns the ForecastMethodType list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of ForecastMethodType list")
    public ResponseEntity getForecastMethodTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingStaticDataService.getForecastMethodTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  ForecastMethodType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
