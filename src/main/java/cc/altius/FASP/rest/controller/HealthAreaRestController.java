/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.AclService;
import cc.altius.FASP.service.HealthAreaService;
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
 * @author akil
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class HealthAreaRestController {

    @Autowired
    private HealthAreaService healthAreaService;
    @Autowired
    private AclService aclService;

    @PostMapping(path = "/healthArea")
    public ResponseFormat postHealthArea(@RequestBody HealthArea heatlhArea, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int healthAreaId = this.healthAreaService.addHealthArea(heatlhArea, curUser);
            return new ResponseFormat("Successfully added HealthArea with Id " + healthAreaId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/healthArea")
    public ResponseFormat putHealhArea(@RequestBody HealthArea heatlhArea, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            this.healthAreaService.updateHealthArea(heatlhArea, curUser);
            return new ResponseFormat("Successfully updated HealthArea");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/healthArea")
    public ResponseFormat getHealthArea(Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.healthAreaService.getHealthAreaList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/healthArea/{healthAreaId}")
    public ResponseFormat getHealthArea(@PathVariable("healthAreaId") int healthAreaId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.healthAreaService.getHealthAreaById(healthAreaId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
}
