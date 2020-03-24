/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgUnitDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.service.UnitService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UnitRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UnitService unitService;

    @PostMapping(path = "/unit")
    public ResponseEntity postUnit(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.unitService.addUnit(unit, curUser);
            return new ResponseEntity("static.message.unit.addSuccess", HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Unit", ae);
            return new ResponseEntity(new ResponseCode("static.message.unit.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.unit.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/unit")
    public ResponseEntity putUnit(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.unitService.updateUnit(unit, curUser);
            return new ResponseEntity("static.message.unit.updateSuccess", HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to update Unit ", ae);
            return new ResponseEntity(new ResponseCode("static.message.unit.updateFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.unit.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unit")
    public ResponseEntity getUnit(Authentication auth) {
        try {
            return new ResponseEntity(this.unitService.getUnitList(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.unit.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity getUnit(@PathVariable("unitId") int unitId, Authentication auth) {
        try {
            return new ResponseEntity(this.unitService.getUnitById(unitId), HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.unit.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Unit", e);
            return new ResponseEntity(new ResponseCode("static.message.unit.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUnitListForSync")
    public String getUnitListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgUnitDTO> unitList = this.unitService.getUnitListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(unitList, typeList);
        return json;
    }
}
