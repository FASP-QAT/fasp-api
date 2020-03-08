/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.RealmService;
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
public class RealmRestController {

    @Autowired
    private RealmService realmService;

    @PostMapping(path = "/realm")
    public ResponseFormat postRealm(@RequestBody Realm realm, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int realmId = this.realmService.addRealm(realm, curUser);
            return new ResponseFormat("Successfully added Realm with Id " + realmId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/realm")
    public ResponseFormat putRealm(@RequestBody Realm realm, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.realmService.updateRealm(realm, curUser);
            return new ResponseFormat("Successfully updated Realm");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realm")
    public ResponseFormat getRealm(Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.realmService.getRealmList(true, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/realm/{realmId}")
    public ResponseFormat getRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseFormat("Success", "", this.realmService.getRealmById(realmId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
