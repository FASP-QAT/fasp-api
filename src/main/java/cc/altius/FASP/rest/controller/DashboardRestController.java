/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DashboardService dashboardService;

    /**
     * API used to get data for application level dashboard
     *
     * @param auth
     * @return returns a map of required data for application level dashboard
     */
    @Operation(description = "API used to get data for application level dashboard", summary = "Application level dashboard", tags = ("dashboard"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a map of required data for application level dashboard")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping(value = "/application")
    public ResponseEntity applicationLevelDashboard(Authentication auth) {
        try {
            return new ResponseEntity(this.dashboardService.getApplicationLevelDashboard(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get data for realm level dashboard
     *
     * @param realmId realm Id that you want data for
     * @param auth
     * @return returns a map of required data for realm level dashboard
     */
    @Operation(description = "API used to get data for realm level dashboard", summary = "Realm level dashboard", tags = ("dashboard"))
    @Parameter(name = "realmId", description = "The realm Id that you want data for")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a map of required data for realm level dashboard")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping(value = "/realmId/{realmId}")
    public ResponseEntity realmLevelDashboard(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            return new ResponseEntity(this.dashboardService.getRealmLevelDashboard(realmId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get user list for application level admin
     *
     * @param auth
     * @return returns a list of users for application level admin
     */
    @Operation(description = "API used to get user list for application level admin", summary = "Application level admin user list", tags = ("dashboard"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of users for application level admin")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping(value = "/application/user")
    public ResponseEntity applicationLevelDashboardUserList(Authentication auth) {
        try {
            return new ResponseEntity(this.dashboardService.getUserListForApplicationLevelAdmin(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get user list for realm level admin
     *
     * @param realmId realm Id that you want data for
     * @param auth
     * @return returns a list of users for realm level admin
     */
    @Operation(description = "API used to get user list for realm level admin", summary = "Realm level admin user list", tags = ("dashboard"))
    @Parameter(name = "realmId", description = "The realm Id that you want data for")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a list of users for realm level admin")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    @GetMapping(value = "/realmId/{realmId}/user")
    public ResponseEntity realmLevelDashboardUserList(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            return new ResponseEntity(this.dashboardService.getUserListForRealmLevelAdmin(realmId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
