/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgSubFundingSourceDTO;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.SubFundingSource;
import cc.altius.FASP.service.SubFundingSourceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SubFundingSourceRestController {

    @Autowired
    private SubFundingSourceService subFundingSourceService;

    @GetMapping(value = "/getSubFundingSourceListForSync")
    public String getSubFundingSourceListForSync(@RequestParam String lastSyncDate,int realmId) throws UnsupportedEncodingException {
        String json;
        List<PrgSubFundingSourceDTO> subFundingSourceList = this.subFundingSourceService.getSubFundingSourceListForSync(lastSyncDate,realmId);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(subFundingSourceList, typeList);
        return json;
    }

    @PostMapping(path = "/subFundingSource")
    public ResponseFormat postSubFundingSource(@RequestBody SubFundingSource subFundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int subFundingSourceId = this.subFundingSourceService.addSubFundingSource(subFundingSource, curUser);
            return new ResponseFormat("Successfully added SubFundingSource with Id " + subFundingSourceId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/subFundingSource")
    public ResponseFormat putSubFundingSource(@RequestBody SubFundingSource subFundingSource, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.subFundingSourceService.updateSubFundingSource(subFundingSource, curUser);
            return new ResponseFormat("Successfully updated SubFundingSource");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/subFundingSource")
    public ResponseFormat getSubFundingSource(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.subFundingSourceService.getSubFundingSourceList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/subFundingSource/{subFundingSourceId}")
    public ResponseFormat getSubFundingSource(@PathVariable("subFundingSourceId") int subFundingSourceId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.subFundingSourceService.getSubFundingSourceById(subFundingSourceId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
