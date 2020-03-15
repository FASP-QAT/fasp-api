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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = {"http://localhost:4202", "http://192.168.43.113:4202", "https://faspdeveloper.github.io","chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class RegionController {

    @Autowired
    RegionService regionService;

    @PostMapping(value = "/region")
    public ResponseFormat addRegion(@RequestBody Region region, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int regionId = this.regionService.addRegion(region, curUser);
            return new ResponseFormat("Successfully added REgion with Id " + regionId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/region")
    public ResponseFormat putRegion(@RequestBody Region region, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            this.regionService.updateRegion(region, curUser);
            return new ResponseFormat("Successfully updated region");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/region")
    public ResponseFormat getRegion(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.regionService.getRegionList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/region/{regionId}")
    public ResponseFormat getRegion(@PathVariable("regionId") int regionId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.regionService.getRegionById(regionId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
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
