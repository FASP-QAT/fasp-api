/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
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
import cc.altius.FASP.service.ForecastingStaticDataService;

/**
 *
 * @author Akil Mahimwala
 */
@RestController
@RequestMapping("/api")
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
    @JsonView(Views.InternalView.class)
    @GetMapping("/usageType")
    @Operation(description = "API used to get the complete UsageType list. Will only return those UsageTypes that are marked Active.", summary = "Get active UsageType list", tags = ("usageType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageType list")
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
    @JsonView(Views.InternalView.class)
    @GetMapping("/nodeType")
    @Operation(description = "API used to get the complete NodeType list. Will only return those NodeTypes that are marked Active.", summary = "Get active NodeType list", tags = ("nodeType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the NodeType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of NodeType list")
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
     * API used to get the complete ForecastMethodType list. Will only return those
     * NodeTypes that are marked Active.
     *
     * @param auth
     * @return returns the complete list of active ForecastMethodTypes
     */
    @JsonView(Views.InternalView.class)
    @GetMapping("/forecastMethodType")
    @Operation(description = "API used to get the complete ForecastMethodType list. Will only return those ForecastMethodTypes that are marked Active.", summary = "Get active ForecastMethodType list", tags = ("forecastMethodType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ForecastMethodType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ForecastMethodType list")
    public ResponseEntity getForecastMethodTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.forecastingStaticDataService.getForecastMethodTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  ForecastMethodType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    /**
//     * API used to get the complete UsageType list. Will only return those
//     * UsageTypes that are marked Active.
//     *
//     * @param auth
//     * @return returns the complete list of UsageTypes
//     */
//    @GetMapping("/all")
//    @Operation(description = "API used to get the complete UsageType list.", summary = "Get UsageType list", tags = ("usageType"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageType list")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageType list")
//    public ResponseEntity getUsageTypeListAll(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.usageTypeService.getUsageTypeList(false, curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to get UsageType list", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    /**
//     * API used to add and update UsageType
//     *
//     * @param usageType UsageType object that you want to add
//     * @param auth
//     * @return returns a Success code if the operation was successful
//     */
//    @PostMapping(value = "/")
//    @Operation(description = "API used to add or update UsageType", summary = "Add or Update UsageType", tags = ("usageType"))
//    @Parameters(
//            @Parameter(name = "usageType", description = "The list of UsageType objects that you want to add or update. If usageTypeId is null then it is added if usageTypeId is not null it is updated"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to add/update this object")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not acceptable")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
//    public ResponseEntity addAndUpadteUsageType(@RequestBody List<UsageType> usageTypeList, Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            this.usageTypeService.addAndUpdateUsageType(usageTypeList, curUser);
//            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
//        } catch (AccessDeniedException e) {
//            logger.error("Error while trying to add UsageType", e);
//            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
//        } catch (DuplicateKeyException e) {
//            logger.error("Error while trying to add UsageType", e);
//            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
//        } catch (Exception e) {
//            logger.error("Error while trying to add UsageType", e);
//            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
