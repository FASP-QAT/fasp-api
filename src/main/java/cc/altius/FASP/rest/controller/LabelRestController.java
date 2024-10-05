/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.DTO.StaticLabelDTO;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.LabelService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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
public class LabelRestController {

    @Autowired
    private LabelService labelService;
    @Autowired
    private UserService userService;

    /**
     * Get the list of all Database Labels
     *
     * @param auth
     * @return
     */
    @JsonView(Views.InternalView.class)
    @GetMapping(value = "/getDatabaseLabelsListAll")
    public ResponseEntity getDatabaseLabelsList(Authentication auth) {
        try {
            CustomUserDetails curUser = userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.labelService.getDatabaseLabelsList(curUser), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Get the list of all Static Labels
     *
     * @return
     */
    @GetMapping(value = "/getStaticLabelsListAll")
    public ResponseEntity getStaticLabelsList() {
        try {
            return new ResponseEntity(this.labelService.getStaticLabelsList(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update the Database lables
     *
     * @param json
     * @param auth
     * @return
     */
    @PutMapping(path = "/saveDatabaseLabels")
    public ResponseEntity putDatabaseLabels(@RequestBody String json, Authentication auth) {
        try {
            CustomUserDetails curUser = userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            Gson gson = new Gson();
            List<String> labelArray = gson.fromJson(json, LinkedList.class);
            this.labelService.saveDatabaseLabels(labelArray, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update Static labels
     *
     * @param staticLabelList
     * @param auth
     * @return
     */
    @PutMapping(path = "/saveStaticLabels")
    public ResponseEntity putStaticLabels(@RequestBody List<StaticLabelDTO> staticLabelList, Authentication auth) {
        try {
            CustomUserDetails curUser = userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.labelService.saveStaticLabels(staticLabelList, curUser);
            return new ResponseEntity(new ResponseCode("static.label.labelSuccess"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseCode("static.label.labelFail"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
