/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgRegionDTO;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Region;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.RegionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "http://192.168.43.113:4202", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class RegionController {

    @Autowired
    RegionService regionService;

    @PutMapping(value = "/addRegion")
    public ResponseEntity addRegion(@RequestBody(required = true) String json, Authentication authentication) throws UnsupportedEncodingException {
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            System.out.println("json---" + json);
            Region region = g.fromJson(json, Region.class);
            CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(curUser);
            int userId = this.regionService.addRegion(region, curUser.getUserId());
            if (userId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Region added successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/editRegion")
    public ResponseEntity editRegion(@RequestBody(required = true) String json, Authentication authentication) throws UnsupportedEncodingException {
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            System.out.println("json---" + json);
            Region region = g.fromJson(json, Region.class);
            CustomUserDetails curUser = (CustomUserDetails) authentication.getPrincipal();
            System.out.println(curUser);
            int row = this.regionService.editRegion(region, curUser.getUserId());
            if (row > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Region updated successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getRegionList")
    public String getRegionList() {
        String json = null;
        try {
            List<Region> regionList = this.regionService.getRegionList(false);
            System.out.println("regionList---" + regionList);
            Gson gson = new Gson();
            Type typeList = new TypeToken<List>() {
            }.getType();
            json = gson.toJson(regionList, typeList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    @GetMapping(value = "/getRegionListForSync")
    public String getRegionListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgRegionDTO> regionList = this.regionService.getRegionListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(regionList, typeList);
        return json;
    }
    
}
