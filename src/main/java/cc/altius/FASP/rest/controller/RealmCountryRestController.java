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
    public ResponseFormat postRealmCountry(@RequestBody List<RealmCountry> realmCountryList, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int realmCountryId = this.realmCountryService.addRealmCountry(realmCountryList, curUser);
            return new ResponseFormat("Successfully added RealmCountry with Id " + realmCountryId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/realmCountry")
    public ResponseFormat putRealmCountry(@RequestBody List<RealmCountry> realmCountryList, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.realmCountryService.updateRealmCountry(realmCountryList, curUser);
            return new ResponseFormat("Successfully updated RealmCountry");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realmCountry")
    public ResponseFormat getRealmCountry(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.realmCountryService.getRealmCountryList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realmCountry/{realmCountryId}")
    public ResponseFormat getRealmCountry(@PathVariable("realmCountryId") int realmCountryId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.realmCountryService.getRealmCountryById(realmCountryId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
    
    @GetMapping("/realmCountry/realmId/{realmId}")
    public ResponseFormat getRealmCountryByRealmId(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.realmCountryService.getRealmCountryListByRealmId(realmId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
    
}
