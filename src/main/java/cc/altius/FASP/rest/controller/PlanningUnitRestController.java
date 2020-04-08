/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.PlanningUnit;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.PlanningUnitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@RequestMapping("/api")
public class PlanningUnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PlanningUnitService planningUnitService;

    @PostMapping(path = "/planningUnit")
    public ResponseEntity postPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.planningUnitService.addPlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to add PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/planningUnit")
    public ResponseEntity putPlanningUnit(@RequestBody PlanningUnit planningUnit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.planningUnitService.updatePlanningUnit(planningUnit, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update PlanningUnit", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to update PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planningUnit")
    public ResponseEntity getPlanningUnit(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planningUnit/all")
    public ResponseEntity getPlanningUnitAll(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planningUnit/realm/{realmId}")
    public ResponseEntity getPlanningUnitForRealm(@PathVariable(value = "realmId", required = true) int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.planningUnitService.getPlanningUnitList(realmId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/planningUnit/{planningUnitId}")
    public ResponseEntity getPlanningUnitById(@PathVariable("planningUnitId") int planningUnitId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.planningUnitService.getPlanningUnitById(planningUnitId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to list PlanningUnit", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list PlanningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/sync/planningUnit/{lastSyncDate}")
    public ResponseEntity getPlanningUnitListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(lastSyncDate);
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.planningUnitService.getPlanningUnitListForSync(lastSyncDate, curUser), HttpStatus.OK);
        } catch (ParseException p) {
            logger.error("Error while listing planningUnit", p);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
        } catch (Exception e) {
            logger.error("Error while listing planningUnit", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
