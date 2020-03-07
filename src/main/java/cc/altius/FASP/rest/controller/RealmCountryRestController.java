/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.BaseModel;
import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.RealmCountry;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.RealmCountryService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class RealmCountryRestController extends BaseModel implements Serializable {
    
    @Autowired
    private RealmCountryService realmCountryService;
    
    @PostMapping(path = "/realmCountry")
    public ResponseFormat postOrganisation(@RequestBody RealmCountry realmCountry, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int realmCountryId = this.realmCountryService.addRealmCountry(realmCountry, curUser);
            return new ResponseFormat("Successfully added RealmCountry with Id " + realmCountryId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/realmCountry")
    public ResponseFormat putOrganisation(@RequestBody RealmCountry realmCountry, Authentication auth) {
        try {
            int curUser = ((CustomUserDetails) auth.getPrincipal()).getUserId();
            int rows = this.realmCountryService.updateRealmCountry(realmCountry, curUser);
            return new ResponseFormat("Successfully updated RealmCountry");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realmCountry")
    public ResponseFormat getOrganisation() {
        try {
            return new ResponseFormat("Success", "", this.realmCountryService.getRealmCountryList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realmCountry/{realmCountryId}")
    public ResponseFormat getOrganisation(@PathVariable("organisationId") int organisationId) {
        try {
            return new ResponseFormat("Success", "", this.realmCountryService.getRealmCountryById(organisationId));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
    
}
