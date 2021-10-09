/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.model.UsageTemplate;
import cc.altius.FASP.service.UsageTemplateService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author akil
 */
@RestController
@RequestMapping("/api/usageTemplate")
public class UsageTemplateRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsageTemplateService usageTemplateService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the active UsageTemplate list. Will only return those
     * UsageTemplates that are marked Active.
     *
     * @param auth
     * @return returns the active list of active UsageTemplates
     */
    @GetMapping("")
    @Operation(description = "API used to get the complete UsageTemplate list. Will only return those UsageTemplates that are marked Active.", summary = "Get active UsageTemplate list", tags = ("usageTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageTemplate list")
    public ResponseEntity getUsageTemplateList(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.usageTemplateService.getUsageTemplateList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  UsageTemplate list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the complete UsageTemplate list.
     *
     * @param auth
     * @return returns the complete list of UsageTemplates
     */
    @GetMapping("/all")
    @Operation(description = "API used to get the complete UsageTemplate list.", summary = "Get complete UsageTemplate list", tags = ("usageTemplate"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageTemplate list")
    public ResponseEntity getUsageTemplateListAll(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.usageTemplateService.getUsageTemplateList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  UsageTemplate list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add and update UsageTemplate
     *
     * @param usageTemplateList List<UsageTemplate> object that you want to add
     * or update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "")
    @Operation(description = "API used to add or update UsageTemplate", summary = "Add or Update UsageTemplate", tags = ("usageTemplate"))
    @Parameters(
            @Parameter(name = "usageTemplate", description = "The list of UsageTemplate objects that you want to add or update. If usageTemplateId is null or 0 then it is added if usageTemplateId is not null and non 0 it is updated"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if you do not have rights to add/update this object")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not acceptable")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addAndUpadteUsageTemplate(@RequestBody List<UsageTemplate> usageTemplateList, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.usageTemplateService.addAndUpdateUsageTemplate(usageTemplateList, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            logger.error("Error while trying to add UsageTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException e) {
            logger.error("Error while trying to add UsageTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add UsageTemplate", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the active UsageTemplate list based on filters provided.
     * Will only return those UsageTemplates that are marked Active and match
     * with Filter criteria.
     *
     * @param auth
     * @return returns the active list of active UsageTemplates that match with
     * filter criteria
     */
    @GetMapping("/tracerCategory/{tracerCategoryId}/usageType/{usageTypeId}/forecastingUnit/{forecastingUnitId}")
    @Operation(description = "API used to get the complete UsageTemplate list. Will only return those UsageTemplates that are marked Active.", summary = "Get active UsageTemplate list", tags = ("usageTemplate"))
    @Parameters(
            {
                @Parameter(name = "tracerCategoryId", description = "The TracerCategory that you want to filter the UsageTemplate"),
                @Parameter(name = "usageTypeId", description = "The UsageType that you want to filter the UsageTemplate"),
                @Parameter(name = "forecastingUnitId", description = "The ForecastingUnit that you want to filter the UsageTemplate")
            }
    )
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the UsageTemplate list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of UsageTemplate list")
    public ResponseEntity getUsageTemplateListWihtFilters(
            @PathVariable("tracerCategoryId") int tracerCategoryId,
            @PathVariable("usageTypeId") int usageTypeId,
            @PathVariable("forecastingUnitId") int forecastingUnitId,
            Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.usageTemplateService.getUsageTemplateList(tracerCategoryId, usageTypeId, forecastingUnitId, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get  UsageTemplate list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
