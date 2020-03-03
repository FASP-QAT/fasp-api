/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

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
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class SubFundingSourceController {

    @Autowired
    SubFundingSourceService subFundingSourceService;

    @GetMapping(value = "/getSubFundingSourceListForSync")
    public String getSubFundingSourceListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgSubFundingSourceDTO> subFundingSourceList = this.subFundingSourceService.getSubFundingSourceListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(subFundingSourceList, typeList);
        return json;
    }
    
    @PostMapping(path = "/api/subFundingSource")
    public ResponseFormat postSubFundingSource(@RequestBody SubFundingSource subFundingSource, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int subFundingSourceId = this.subFundingSourceService.addSubFundingSource(subFundingSource, curUser);
            return new ResponseFormat("Successfully added SubFundingSource with Id " + subFundingSourceId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/api/subFundingSource")
    public ResponseFormat putSubFundingSource(@RequestBody SubFundingSource subFundingSource, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int rows = this.subFundingSourceService.updateSubFundingSource(subFundingSource, curUser);
            return new ResponseFormat("Successfully updated SubFundingSource");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/api/subFundingSource")
    public ResponseFormat getSubFundingSource() {
        try {
            return new ResponseFormat("Success", "", this.subFundingSourceService.getSubFundingSourceList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/api/subFundingSource/{subFundingSourceId}")
    public ResponseFormat getSubFundingSource(@PathVariable("subFundingSourceId") int subFundingSourceId) {
        try {
            return new ResponseFormat("Success", "", this.subFundingSourceService.getSubFundingSourceById(subFundingSourceId));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
