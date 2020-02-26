/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgUnitTypeDTO;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.UnitType;
import cc.altius.FASP.service.UnitTypeService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
public class UnitTypeController {

    @Autowired
    UnitTypeService unitTypeService;

    @PutMapping(value = "/addUnitType")
    public ResponseEntity addUnitType(@RequestBody(required = true) String json, Authentication authentication) {

        CustomUserDetails cd = (CustomUserDetails) authentication.getPrincipal();
        Gson g = new Gson();
        UnitType ut = g.fromJson(json, UnitType.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            int unitTypeId = this.unitTypeService.addUnitType(ut, cd.getUserId());
            if (unitTypeId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Unit type added successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/unitTypeList")
    public String getUnitTypeList() throws UnsupportedEncodingException {
        String json;
        try {
            List<UnitType> unitTypeList = this.unitTypeService.getUnitTypeList(false);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(unitTypeList, typeList);
            return json;
        } catch (Exception e) {
            //e.printStackTrace();
            return "";
        }

    }

    @PutMapping(value = "/editUnitType")
    public ResponseEntity editUnitType(@RequestBody(required = true) String json, Authentication authentication) {
        ResponseFormat responseFormat = new ResponseFormat();
        Gson g = new Gson();
        UnitType unitType = g.fromJson(json, UnitType.class);
        CustomUserDetails cd = (CustomUserDetails) authentication.getPrincipal();
        try {
            int updateRow = this.unitTypeService.updateUnitType(unitType, cd.getUserId());
            if (updateRow > 0) {
                responseFormat.setMessage("UnitType Updated successfully");
                responseFormat.setStatus("Success");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

            }
        } catch (Exception e) {
            responseFormat.setStatus("Update failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping(value = "/getUnitTypeListForSync")
    public String getUnitTypeListForSync() throws UnsupportedEncodingException {
        String json;
        List<PrgUnitTypeDTO> unitTypeList = this.unitTypeService.getUnitTypeListForSync();
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(unitTypeList, typeList);
        return json;
    }

}
