/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgHealthAreaDTO;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.HealthAreaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class HealthAreaRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HealthAreaService healthAreaService;

    @PostMapping(path = "/healthArea")
    public ResponseEntity postHealthArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.healthAreaService.addHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.addSucccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.addFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to add Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/healthArea")
    public ResponseEntity putHealhArea(@RequestBody HealthArea healthArea, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            this.healthAreaService.updateHealthArea(healthArea, curUser);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.updateSucccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to update Health Area", ae);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.updateFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to update Health Area", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/healthArea")
    public ResponseEntity getHealthArea(Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseEntity(this.healthAreaService.getHealthAreaList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/healthArea/realmId/{realmId}")
    public ResponseEntity getHealthAreaByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseEntity(this.healthAreaService.getHealthAreaListByRealmId(realmId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/healthArea/{healthAreaId}")
    public ResponseEntity getHealthArea(@PathVariable("healthAreaId") int healthAreaId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseEntity(this.healthAreaService.getHealthAreaById(healthAreaId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get Health Area list", er);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("Error while trying to get Health Area list", e);
            return new ResponseEntity(new ResponseCode("static.message.healthArea.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getHealthAreaListForSync")
    public String getHealthAreaListForSync(@RequestParam String lastSyncDate, int realmId) throws UnsupportedEncodingException {
        String json;
        List<PrgHealthAreaDTO> healthAreaList = this.healthAreaService.getHealthAreaListForSync(lastSyncDate, realmId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(healthAreaList, typeList);
        return json;
    }
}
