/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Organisation;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author altius
 */
@RestController
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop"})
public class OrganisationRestController {

    @Autowired
    private OrganisationService organisationService;

    @PostMapping(path = "/api/organisation")
    public ResponseFormat postOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int organisationId = this.organisationService.addOrganisation(organisation, curUser);
            return new ResponseFormat("Successfully added Organisation with Id " + organisationId);
        } catch (DuplicateKeyException e) {
            return new ResponseFormat("Failed", "Organisation code already exist.");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/api/organisation")
    public ResponseFormat putOrganisation(@RequestBody Organisation organisation, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.organisationService.updateOrganisation(organisation, curUser);
            return new ResponseFormat("Successfully updated Organisation");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/api/organisation")
    public ResponseFormat getOrganisation(Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.organisationService.getOrganisationList(curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/api/organisation/{organisationId}")
    public ResponseFormat getOrganisation(@PathVariable("organisationId") int organisationId, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            return new ResponseFormat("Success", "", this.organisationService.getOrganisationById(organisationId, curUser));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }
}
