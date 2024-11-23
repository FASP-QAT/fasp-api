/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.ModelingType;
import cc.altius.FASP.service.ModelingTypeService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/modelingType")
public class ModelingTypeRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ModelingTypeService modelingTypeService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the active ModelingType list. Will only return those
     * ModelingTypes that are marked Active.
     *
     * @param auth
     * @return returns the active list of active ModelingTypes
     */
    @GetMapping("")
    @Operation(description = "API used to get the complete ModelingType list. Will only return those ModelingTypes that are marked Active.", summary = "Get active ModelingType list", tags = ("modelingType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ModelingType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ModelingType list")
    public ResponseEntity getModelingTypeList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.modelingTypeService.getModelingTypeList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  ModelingType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete ModelingType list.
     *
     * @param auth
     * @return returns the complete list of ModelingTypes
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete ModelingType list.", summary = "Get complete ModelingType list", tags = ("modelingType"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the ModelingType list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of ModelingType list")
    public ResponseEntity getModelingTypeListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.modelingTypeService.getModelingTypeList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  ModelingType list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add and update ModelingType
     *
     * @param modelingTypeList List<ModelingType> object that you want to add or
     * update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "")
    @Operation(description = "API used to add or update ModelingType", summary = "Add or Update ModelingType", tags = ("modelingType"))
    @Parameters(
            @Parameter(name = "modelingType", description = "The list of ModelingType objects that you want to add or update. If modelingTypeId is null or 0 then it is added if modelingTypeId is not null and non 0 it is updated"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to add/update this object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not acceptable")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addAndUpadteModelingType(@RequestBody List<ModelingType> modelingTypeList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserIdForApi(((CustomUserDetails) auth.getPrincipal()).getUserId(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod(), ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI());
            return new ResponseEntity(this.modelingTypeService.addAndUpdateModelingType(modelingTypeList, curUser) + " records updated", HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add ModelingType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add ModelingType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add ModelingType", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
