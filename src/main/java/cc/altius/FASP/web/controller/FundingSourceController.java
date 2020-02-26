/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgFundingSourceDTO;
import cc.altius.FASP.service.FundingSourceService;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4202")
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

}
