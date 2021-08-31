/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.rest.controller;

import cc.altius.FASP.model.CustomUserDetails;
import cc.altius.FASP.model.Realm;
import cc.altius.FASP.model.ResponseCode;
import cc.altius.FASP.service.RealmService;
import cc.altius.FASP.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

/**
 *
 * @author altius
 */
@RestController
@RequestMapping("/api/realm")
public class RealmRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RealmService realmService;
    @Autowired
    private UserService userService;

    /**
     * API used to get the complete Realm list.
     *
     * @param auth
     * @return returns the complete list of Realms
     */
    @GetMapping("/")
    @Operation(description = "API used to get the complete Realm list.", summary = "Get Realm list", tags = ("realm"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Realm list")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Realm list")
    public ResponseEntity getRealm(Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.realmService.getRealmList(true, curUser), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while trying to list Realm", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to get the Realm for a specific RealmId
     *
     * @param realmId RealmId that you want the Realm
     * Object for
     * @param auth
     * @return returns the Realm object based on
     * RealmId specified
     */
    @GetMapping(value = "/{realmId}")
    @Operation(description = "API used to get the Realm for a specific RealmId", summary = "Get Realm for a RealmId", tags = ("realm"))
    @Parameters(
            @Parameter(name = "realmId", description = "RealmId that you want to the Realm for"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns the Realm")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "404", description = "Returns a HttpStatus.NOT_FOUND if the RealmId specified does not exist")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Internal error that prevented the retreival of Realm")
    
    public ResponseEntity getRealmById(@PathVariable("realmId") int realmId, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            return new ResponseEntity(this.realmService.getRealmById(realmId, curUser), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to list Realm", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException ae) {
            logger.error("Error while trying to list Realm", ae);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error while trying to list Realm", e);
            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * API used to add a Realm
     *
     * @param realm Realm object that you want to add
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PostMapping(value = "/")
    @Operation(description = "API used to add a Realm", summary = "Add Realm", tags = ("realm"))
    @Parameters(
            @Parameter(name = "realm", description = "The Realm object that you want to add"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity addRealm(@RequestBody Realm realm, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.realmService.addRealm(realm, curUser);
            return new ResponseEntity(new ResponseCode("static.message.addSuccess"), HttpStatus.OK);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Realm", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Realm", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * API used to update a Realm
     *
     * @param realm Realm object that you want to update
     * @param auth
     * @return returns a Success code if the operation was successful
     */
    @PutMapping(path = "/")
    @Operation(description = "API used to update a Realm", summary = "Update Realm", tags = ("realm"))
    @Parameters(
            @Parameter(name = "realm", description = "The Realm object that you want to update"))
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "200", description = "Returns a Success code if the operation was successful")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "403", description = "Returns a HttpStatus.FORBIDDEN if the User does not have access")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "406", description = "Returns a HttpStatus.NOT_ACCEPTABLE if the data supplied is not unique")
    @ApiResponse(content = @Content(mediaType = "text/json"), responseCode = "500", description = "Returns a HttpStatus.INTERNAL_SERVER_ERROR if there was some other error that did not allow the operation to complete")
    public ResponseEntity updateRealm(@RequestBody Realm realm, Authentication auth) {
        try {
            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
            this.realmService.updateRealm(realm, curUser);
            return new ResponseEntity(new ResponseCode("static.message.updateSuccess"), HttpStatus.OK);
        } catch (AccessDeniedException ae) {
            logger.error("Error while trying to add Realm", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.FORBIDDEN);
        } catch (DuplicateKeyException ae) {
            logger.error("Error while trying to add Realm", ae);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            logger.error("Error while trying to add Realm", e);
            return new ResponseEntity(new ResponseCode("static.message.addFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    @GetMapping(value = "/sync/realm/{lastSyncDate}")
//    public ResponseEntity getRealmListForSync(@PathVariable("lastSyncDate") String lastSyncDate, Authentication auth) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(lastSyncDate);
//            CustomUserDetails curUser = this.userService.getCustomUserByUserId(((CustomUserDetails) auth.getPrincipal()).getUserId());
//            return new ResponseEntity(this.realmService.getRealmListForSync(lastSyncDate, curUser), HttpStatus.OK);
//        } catch (ParseException p) {
//            logger.error("Error while listing realm", p);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.PRECONDITION_FAILED);
//        } catch (Exception e) {
//            logger.error("Error while listing realm", e);
//            return new ResponseEntity(new ResponseCode("static.message.listFailed"), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
