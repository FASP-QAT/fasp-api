/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.HealthArea;
import cc.altius.FASP.model.ResponseFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
public class HealthAreaRestController {

    
    @PostMapping(path = "/api/healthArea")
    public ResponseFormat postHealthArea(@RequestBody HealthArea heatlhArea) {
        
    }

}
