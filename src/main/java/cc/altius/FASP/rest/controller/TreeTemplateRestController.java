/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.TreeTemplateService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/treeTemplate")
public class TreeTemplateRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private TreeTemplateService treeTemplateService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * API used to get the active Tree template list. Will only return those
     * Tree Templates that are marked Active.
     *
     * @param auth
     * @return returns the active list of active Tree Templates
     */
    @GetMapping("")
    @Operation(description = "API used to get the complete TreeTemplate list. Will only return those TreeTemplates that are marked Active.", summary = "Get active TreeTemplate list", tags = ("treeTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
    public ResponseEntity getTreeTemplate(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete TreeTemplate list.
     *
     * @param auth
     * @return returns the complete list of TreeTemplates
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete TreeTemplate list.", summary = "Get complete TreeTemplate list", tags = ("treeTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
    public ResponseEntity getTreeTemplateAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete TreeTemplate list.
     *
     * @param auth
     * @return returns the complete list of TreeTemplates
     */
    @GetMapping("/{treeTemplateId}")
    @Operation(description = "API used to get a specific TreeTemplate based on the Id.", summary = "Get TreeTemplate based on the Id", tags = ("treeTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate for the Id")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate")

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
