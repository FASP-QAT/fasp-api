/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.TreeTemplate;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
     * Get list of Tree templates for Dropdown
     *
     * @param auth
     * @return
     */
//    @JsonView(Views.InternalView.class)
    @GetMapping("")
    @Operation(description = "API used to get the complete TreeTemplate list. Will only return those TreeTemplates that are marked Active.", summary = "Get active TreeTemplate list", tags = ("treeTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
    public ResponseEntity getTreeTemplate(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get TreeTemplate by Id
     *
     * @param treeTemplateId
     * @param auth
     * @return
     */
    @GetMapping("/{treeTemplateId}")
    @Operation(description = "API used to get a specific TreeTemplate based on the Id.", summary = "Get TreeTemplate based on the Id", tags = ("treeTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate for the Id")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate")
    public ResponseEntity getTreeTemplate(@PathVariable("treeTemplateId") int treeTemplateId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplateId, true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add TreeTemplate
     *
     * @param treeTemplate
     * @param auth
     * @return
     */
    @PostMapping("")
    public ResponseEntity addTreeTemplate(@RequestBody TreeTemplate treeTemplate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            int treeTemplateId = this.treeTemplateService.addTreeTemplate(treeTemplate, curUser);
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplateId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Tree Template", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Tree Template", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update TreeTempalte
     *
     * @param treeTemplate
     * @param auth
     * @return
     */
    @PutMapping("")
    public ResponseEntity updateTreeTemplate(@RequestBody TreeTemplate treeTemplate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            this.treeTemplateService.updateTreeTemplate(treeTemplate, curUser);
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplate.getTreeTemplateId(), true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Tree Template", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add Tree Template", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
