/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.model.Unit;
import cc.altius.FASP.service.UnitService;
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
public class UnitRestController {

    @Autowired
    private UnitService unitService;

    @PostMapping(path = "/unit")
    public ResponseFormat postUnit(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int unitId = this.unitService.addUnit(unit, curUser);
            return new ResponseFormat("Successfully added Unit with Id " + unitId);
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @PutMapping(path = "/unit")
    public ResponseFormat putHealhArea(@RequestBody Unit unit, Authentication auth) {
        try {
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            int rows = this.unitService.updateUnit(unit, curUser);
            return new ResponseFormat("Successfully updated Unit");
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/unit")
    public ResponseFormat getUnit() {
        try {
            return new ResponseFormat("Success", "", this.unitService.getUnitList());
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

    @GetMapping("/unit/{unitId}")
    public ResponseFormat getUnit(@PathVariable("unitId") int unitId) {
        try {
            return new ResponseFormat("Success", "", this.unitService.getUnitById(unitId));
        } catch (Exception e) {
            return new ResponseFormat("Failed", e.getMessage());
        }
    }

}
