/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.Views;
import cc.altius.FASP.service.TreeTemplateService;
import cc.altius.FASP.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api")
public class TreeTemplateRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private TreeTemplateService treeTemplateService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/treeTemplate")
    public ResponseEntity getTreeTemplate(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/treeTemplate/{treeTemplateId}")
    public ResponseEntity getTreeTemplate(@PathVariable("treeTemplateId") int treeTemplateId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplateId, true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
