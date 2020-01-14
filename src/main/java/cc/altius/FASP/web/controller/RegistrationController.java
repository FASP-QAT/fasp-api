/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.Registration;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.RegistrationService;
import com.google.gson.Gson;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:4202")
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @PutMapping(value = "/saveRegistration")
    public ResponseEntity saveRegistration(@RequestBody(required = true) String json) throws UnsupportedEncodingException {
        Map<String, Object> responseMap = null;
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            Gson g = new Gson();
            Registration register = g.fromJson(json, Registration.class);
            int registrationId = this.registrationService.saveRegistration(register);
            if (registrationId > 0) {
                responseFormat.setStatus("Success");
                responseFormat.setMessage("Registration done successfully.");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {                
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Exception Occured. Please try again");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
