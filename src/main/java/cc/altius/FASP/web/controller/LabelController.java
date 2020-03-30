/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.LabelService;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author palash
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io", "chrome-extension://fhbjgbiflinjbdggehcddcbncdddomop", "https://qat.altius.cc"})
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "/getDatabaseLabelsListAll")
    public ResponseEntity getDatabaseLabelsList(Authentication auth) {
        try {
            System.out.println("in method");
            CustomUserDetails curUser = (CustomUserDetails) auth.getPrincipal();
            return new ResponseEntity(this.labelService.getDatabaseLabelsList(curUser.getRealm().getRealmId()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @RequestMapping(value = "/getStaticLabelsListAll")
    public ResponseEntity getStaticLabelsList() {
        try {
            return new ResponseEntity(this.labelService.getStaticLabelsList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(path = "/saveDatabaseLabels")
    public ResponseEntity putDatabaseLabels(@RequestBody String json, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            System.out.println("jsonn======================"+json);
            Gson gson = new Gson(); 
            List<String> labelArray = gson.fromJson(json, LinkedList.class);  
            System.out.println("Label array"+labelArray);
            this.labelService.saveDatabaseLabels(labelArray, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(path = "/saveStaticLabels")
    public ResponseEntity putStaticLabels(@RequestBody String json, Authentication auth) {
        try {
            CustomUserDetails curUser = ((CustomUserDetails) auth.getPrincipal());
            System.out.println("jsonn======================"+json);
            Gson gson = new Gson(); 
            List<String> labelArray = gson.fromJson(json, LinkedList.class);  
            System.out.println("Label array"+labelArray);
            this.labelService.saveStaticLabels(labelArray, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
