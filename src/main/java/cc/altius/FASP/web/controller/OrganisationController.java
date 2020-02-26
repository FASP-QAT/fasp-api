/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.DTO.PrgOrganisationDTO;
import cc.altius.FASP.service.OrganisationService;
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
public class OrganisationController {
    
    @Autowired
    OrganisationService organisationService;
    
    @GetMapping(value = "/getOrganisationListForSync")
    public String getOrganisationListForSync(@RequestParam String lastSyncDate) throws UnsupportedEncodingException {
        String json;
        List<PrgOrganisationDTO> organisationList = this.organisationService.getOrganisationListForSync(lastSyncDate);
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(organisationList, typeList);
        return json;
    }

    
}
