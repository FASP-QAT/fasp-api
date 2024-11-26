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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/treeTemplate")
@Tag(
    name = "Tree template",
    description = "Manage tree templates with branch-specific configurations"
)
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
//    @JsonView(Views.InternalView.class)
    @GetMapping("")
    @Operation(description = "API used to get the complete TreeTemplate list. Will only return those TreeTemplates that are marked Active.", summary = "Get active TreeTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json", array = @ArraySchema(schema = @Schema(implementation = TreeTemplate.class))), responseCode = "200", description = "Returns the TreeTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
    public ResponseEntity getTreeTemplate(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the active Tree template list. Will only return those
     * Tree Templates that are marked Active.
     *
     * @param auth
     * @return returns the list of active Tree Templates that have Branch is
     * true
     */
//    @JsonView(Views.InternalView.class)
//    @GetMapping("/branch")
//    @Operation(description = "API used to get the complete TreeTemplate list where Branch is active. Will only return those TreeTemplates that are marked Active.", summary = "Get active TreeTemplate list", tags = ("treeTemplate"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
//    public ResponseEntity getBranchTemplate(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(true, true, curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list TreeTemplate", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    /**
//     * API used to get the complete TreeTemplate list.
//     *
//     * @param auth
//     * @return returns the complete list of TreeTemplates
//     */
//    @GetMapping("/all")
//    @Operation(description = "API used to get the complete TreeTemplate list.", summary = "Get complete TreeTemplate list", tags = ("treeTemplate"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
//    public ResponseEntity getTreeTemplateAll(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(false, false, curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list TreeTemplate", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * API used to get the complete TreeTemplate list.
     *
     * @param auth
     * @return returns the complete list of TreeTemplates
     */
//    @GetMapping("/branch/all")
//    @Operation(description = "API used to get the complete TreeTemplate list where Branch is true.", summary = "Get complete TreeTemplate list where Branch is true", tags = ("treeTemplate"))
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TreeTemplate list where Branch is true")
//    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate list")
//    public ResponseEntity getBranchTemplateAll(Authentication auth) {
//        try {
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.treeTemplateService.getTreeTemplateList(true, false, true, curUser), HttpStatus.OK);
//        } catch (Exception e) {
//            logger.error("Error while trying to list TreeTemplate", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    /**
     * API used to get the complete TreeTemplate list.
     *
     * @param auth
     * @return returns the complete list of TreeTemplates
     */
    @GetMapping("/{treeTemplateId}")
    @Operation(description = "API used to get a specific TreeTemplate based on the Id.", summary = "Get TreeTemplate based on the Id")
    @Parameter(name = "treeTemplateId", description = "The ID of the tree template to get", required = true)
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = TreeTemplate.class)), responseCode = "200", description = "Returns the TreeTemplate for the Id")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error that prevented the retreival of TreeTemplate")
    public ResponseEntity getTreeTemplate(@PathVariable("treeTemplateId") int treeTemplateId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplateId, true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TreeTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @Operation(description = "API used to add a new TreeTemplate.", summary = "Add a new TreeTemplate")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The TreeTemplate to add",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TreeTemplate.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = TreeTemplate.class)), responseCode = "200", description = "Returns the added TreeTemplate")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while adding the TreeTemplate")
    public ResponseEntity addTreeTemplate(@RequestBody TreeTemplate treeTemplate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            int treeTemplateId = this.treeTemplateService.addTreeTemplate(treeTemplate, curUser);
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplateId, true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Tree Template", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Tree Template", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @Operation(description = "API used to update an existing TreeTemplate.", summary = "Update an existing TreeTemplate")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
        description = "The TreeTemplate to update",
        required = true,
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = TreeTemplate.class))
    )
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = TreeTemplate.class)), responseCode = "200", description = "Returns the updated TreeTemplate")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "403", description = "Access denied")
    @ApiResponse(content = @Content(mediaType = "text/json", schema = @Schema(implementation = ResponseCode.class)), responseCode = "500", description = "Internal error while updating the TreeTemplate")
    public ResponseEntity updateTreeTemplate(@RequestBody TreeTemplate treeTemplate, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.treeTemplateService.updateTreeTemplate(treeTemplate, curUser);
            return new ResponseEntity(this.treeTemplateService.getTreeTemplateById(treeTemplate.getTreeTemplateId(), true, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Tree Template", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN); // 403
        } catch (Exception e) {
            logger.error("Error while trying to add Tree Template", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}
