/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.CountryService;
import cc.altius.FASP.service.DashboardService;
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
@RequestMapping("/api")
public class DashboardRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    DashboardService dashboardService;

    @GetMapping(value = "/applicationLevelDashboard")
    public ResponseEntity applicationLevelDashboard(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.dashboardService.getApplicationLevelDashboard(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/realmLevelDashboard/{realmId}")
    public ResponseEntity realmLevelDashboard(@PathVariable("realmId") int realmId,Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.dashboardService.getRealmLevelDashboard(realmId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/applicationLevelDashboardUserList")
    public ResponseEntity applicationLevelDashboardUserList(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.dashboardService.getUserListForApplicationLevelAdmin(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/realmLevelDashboardUserList/{realmId}")
    public ResponseEntity realmLevelDashboardUserList(@PathVariable("realmId") int realmId,Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.dashboardService.getUserListForRealmLevelAdmin(realmId), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while getting country list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
