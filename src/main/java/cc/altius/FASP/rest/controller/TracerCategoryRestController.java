/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.TracerCategory;
import cc.altius.FASP.model.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import cc.altius.FASP.service.TracerCategoryService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/tracerCategory")
public class TracerCategoryRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TracerCategoryService tracerCategoryService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete TracerCategory list.
     *
     * @param auth
     * @return returns the complete list of tracerCategories
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete TracerCategory list.", summary = "Get TracerCategory list", tags = ("tracerCategory"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TracerCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TracerCategory list")
    public ResponseEntity getTracerCategory(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryList(false, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the TracerCategory for a specific TracerCategoryId
     *
     * @param tracerCategoryId TracerCategoryId that you want the TracerCategory Object for
     * @param auth
     * @return returns the TracerCategory object based on TracerCategoryId specified
     */
    @GetMapping(value = "/{tracerCategoryId}")
    @Operation(description = "API used to get the TracerCategory for a specific TracerCategoryId", summary = "Get TracerCategory for a TracerCategoryId", tags = ("tracerCategory"))
    @Parameters(
            @Parameter(name = "tracerCategoryId", description = "TracerCategoryId that you want to the TracerCategory for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TracerCategory")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the TracerCategoryId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TracerCategory")

    public ResponseEntity getTracerCategory(@PathVariable("tracerCategoryId") int tracerCategoryId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryById(tracerCategoryId, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the TracerCategory list for a Realm
     *
     * @param realmId RealmId that you want the TracerCategory List from
     * @param auth
     * @return returns the complete list of tracerCategories
     */
    @GetMapping("/realmId/{realmId}")
    @Operation(description = "API used to get the complete TracerCategory list for a Realm", summary = "Get TracerCategory list for Realm", tags = ("tracerCategory"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the TracerCategory list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TracerCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TracerCategory list")
    public ResponseEntity getTracerCategoryForRealm(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the TracerCategory list for a Realm and Program
     *
     * @param realmId RealmId that you want the TracerCategory List from
     * @param programId ProgramId that you want the TracerCategory List from
     * @param auth
     * @return returns the complete list of tracerCategories
     */
    @GetMapping("/realmId/{realmId}/programId/{programId}")
    @Operation(description = "API used to get the complete TracerCategory list for a Realm and Program", summary = "Get TracerCategory list for Realm and Program", tags = ("tracerCategory"))
    @Parameters(
            {
                @Parameter(name = "realmId", description = "RealmId that you want the TracerCategory list for"),
                @Parameter(name = "programId", description = "ProgramId that you want the TracerCategory list for")})
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TracerCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TracerCategory list")
    public ResponseEntity getTracerCategoryForRealmProgram(@PathVariable("realmId") int realmId, @PathVariable("programId") int programId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programId, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        /**
     * API used to get the TracerCategory list for a Realm. Only returns those Tracer Categories that are from Planning Units in active Programs
     *
     * @param realmId RealmId that you want the TracerCategory List from
     * @param auth
     * @return returns the complete list of tracerCategories
     */
    @PostMapping("/realmId/{realmId}/programIds")
    @Operation(description = "API used to get the TracerCategory list for a Realm. Only returns those Tracer Categories that are from Planning Units in active Programs", summary = "Get TracerCategory list for Realm and active ProgramPlanningUnits", tags = ("tracerCategory"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want the TracerCategory list for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the TracerCategory list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of TracerCategory list")
    
    public ResponseEntity getTracerCategoryForRealmPrograms(@PathVariable("realmId") int realmId, @RequestBody String[] programIds, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.tracerCategoryService.getTracerCategoryListForRealm(realmId, programIds, false, curUser), HttpStatus.OK);
        } catch (EmptyResultDataAccessException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException er) {
            logger.error("Error while trying to get TracerCategory list", er);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to get TracerCategory list", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to add a TracerCategory
     *
     * @param tracerCategory TracerCategory object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a TracerCategory", summary = "Add TracerCategory", tags = ("tracerCategory"))
    @Parameters(
            @Parameter(name = "tracerCategory", description = "The TracerCategory object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.tracerCategoryService.addTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a TracerCategory
     *
     * @param tracerCategory TracerCategory object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a TracerCategory", summary = "Update TracerCategory", tags = ("tracerCategory"))
    @Parameters(
            @Parameter(name = "tracerCategory", description = "The TracerCategory object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateTracerCategory(@RequestBody TracerCategory tracerCategory, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.tracerCategoryService.updateTracerCategory(tracerCategory, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add TracerCategory", ae);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Error while trying to add TracerCategory", e);
            return new ResponseEntity(new ResponseCode("static.message.updateFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
