/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.web.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Label;
import cc.altius.FASP.model.ResponseFormat;
import cc.altius.FASP.service.LabelService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
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
@CrossOrigin(origins = {"http://localhost:4202", "https://faspdeveloper.github.io"})
public class LabelController {

    @Autowired
    private LabelService labelService;

    @RequestMapping(value = "/getLabelsListAll")
    public String getLabelListAll() {
        String json;
        List<Label> labelList = new ArrayList();
        labelList = this.labelService.getLabelsListAll();
        Gson gson = new Gson();
        Type typeList = new TypeToken<List>() {
        }.getType();
        json = gson.toJson(labelList, typeList);
        return json;

    }

    @PutMapping(value = "/updateLabels")
    public ResponseEntity updateLabels(@RequestBody(required = true) String json,Authentication authentication) {
      
        Gson g = new Gson();
        Label labels = g.fromJson(json, Label.class);
        ResponseFormat responseFormat = new ResponseFormat();
        try {
            CustomUserDetails cd = (CustomUserDetails) authentication.getPrincipal();
            int row = this.labelService.updateLabels(labels,cd.getUserId());
            if (row > 0) {
                responseFormat.setMessage("Labels updated successfully");
                responseFormat.setStatus("Success");
                return new ResponseEntity(responseFormat, HttpStatus.OK);
            } else {
                responseFormat.setStatus("failed");
                responseFormat.setMessage("Error accured");
                return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

            }

        }catch (Exception e) {
           // e.printStackTrace();
            responseFormat.setStatus("failed");
            responseFormat.setMessage("Exception Occured :" + e.getClass());
            return new ResponseEntity(responseFormat, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
