/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.model.FundingSource;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.FundingSourceService;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
public class FundingSourceController {

    @Autowired
    FundingSourceService fundingSourceService;

    @GetMapping(value = "/getFundingSourceListForSync")
    public String getFundingSourceListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgFundingSourceDTO> fundingSourceList = this.fundingSourceService.getFundingSourceListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(fundingSourceList, typeList);
        return json;
    }

    @PostMapping(path = "/fundingSource")
    public ResponseFormat postFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int unitId = this.fundingSourceService.addFundingSource(fundingSource, curUser);
            return new ResponseFormat("Successfully added funding source with Id " + unitId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/fundingSource")
    public ResponseFormat putFundingSource(@RequestBody FundingSource fundingSource, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int rows = this.fundingSourceService.updateFundingSource(fundingSource, curUser);
            return new ResponseFormat("Successfully updated funding source");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/fundingSource")
    public ResponseFormat getFundingSource() {
        try {
            return new ResponseFormat("Success", "", this.fundingSourceService.getFundingSourceList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/fundingSource/{fundingSourceId}")
    public ResponseFormat getFundingSource(@PathVariable("manufacturerId") int manufacturerId) {
        try {
            return new ResponseFormat("Success", "", this.fundingSourceService.getFundingSourceById(manufacturerId));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
